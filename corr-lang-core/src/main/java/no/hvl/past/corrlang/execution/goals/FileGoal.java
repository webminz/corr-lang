package no.hvl.past.corrlang.execution.goals;

import no.hvl.past.corrlang.domainmodel.CorrSpec;
import no.hvl.past.corrlang.domainmodel.ServerEndpoint;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.di.PropertyHolder;
import no.hvl.past.graph.GraphMorphism;
import no.hvl.past.names.Name;
import no.hvl.past.names.PrintingStrategy;
import no.hvl.past.plugin.UnsupportedFeatureException;
import no.hvl.past.systems.ComprData;
import no.hvl.past.systems.ConsistencyRule;
import no.hvl.past.systems.Data;
import no.hvl.past.systems.Sys;
import no.hvl.past.techspace.TechSpace;
import no.hvl.past.techspace.TechSpaceAdapter;
import no.hvl.past.techspace.TechSpaceException;
import no.hvl.past.util.FileSystemUtils;
import no.hvl.past.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileUrlResource;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileGoal extends LanguageGoal {

    public static final String PRINT_VERIFICATION = "goals.verify.print";
    private File file;
    private String queryFile;


    @Autowired
    PropertyHolder propertyHolder;

    @Autowired
    ReportFacade reportFacade;

    public void setFile(File file, boolean overwrite) throws GoalException {
        this.file = file;
        if (!this.file.getParentFile().exists()) {
            this.file.mkdirs();
        }
        if (file.exists() && !overwrite) {
            throw new GoalException("Cannot execute '" + getKey() + "' because " + file.getAbsolutePath() + " already exists and overwrite = false");
        }
        if (file.exists() && overwrite) {
            this.file.delete();
        }
    }



    public FileGoal() {
        super("FILE");
    }

    @Override
    protected void executeTransformation(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) {

    }

    @Override
    protected void executeSynchronize(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) {

    }

    @Override
    protected void executeVerify(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) throws UnsupportedFeatureException, TechSpaceException, IOException, URISyntaxException {
        ComprData comprDataInstance = getComprDataInstance(correspondence);
        Set<Pair<ConsistencyRule, Name>> violations = new HashSet<>();
        correspondence.getComprSys().rules().forEach(rule -> {
            rule.violations(comprDataInstance).forEach(v -> violations.add(new Pair<>(rule,v)));
        });

        boolean printToConsole = propertyHolder.getBooleanProperty(PRINT_VERIFICATION);

        if (techSpace == null) {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            if (violations.isEmpty()) {
                bufferedWriter.append("CONSISTENT");
            } else {
                // TODO good if violations also knows its original system and better if rules also have proper names
                long noOfViolations = 0;
                for (Pair<ConsistencyRule, Name> violation : violations) {
                    noOfViolations++;
                    bufferedWriter.append(violation.getFirst().getClass().getName());
                    bufferedWriter.append(" on '");
                    bufferedWriter.append(violation.getFirst().commonality().printRaw());
                    bufferedWriter.append("' is violated for Element: '");
                    bufferedWriter.append(violation.getRight().print(PrintingStrategy.DETAILED));
                    bufferedWriter.append("'!\n");
                    if (printToConsole) {
                        reportFacade.reportInfo(violation.getFirst().getClass().getName() +  " on '" + violation.getFirst().commonality().printRaw() + "' is violated for Element '" + violation.getRight().print(PrintingStrategy.DETAILED) + "'");
                    }
                }
                bufferedWriter.append(Long.toString(noOfViolations));
                bufferedWriter.append(" violations");
                if (printToConsole) {
                    reportFacade.reportInfo(noOfViolations + " violations");
                }
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            fileOutputStream.close();
        } else {
            throw new UnsupportedFeatureException("Cannot create violations file for technology " + techSpace.getClass());
            // make the special violation schema
        }

    }

    private ComprData getComprDataInstance(CorrSpec correspondence) throws IOException, UnsupportedFeatureException, TechSpaceException, URISyntaxException {
        boolean isWhitebox = correspondence.getEndpointRefs().values().stream().noneMatch(e -> e instanceof ServerEndpoint);

        if (isWhitebox) {
            ComprData.Builder cdata = new ComprData.Builder(correspondence.getComprSys());
            for (String ep : correspondence.getEndpointsList()) {
                TechSpaceAdapter<? extends TechSpace> techSpaceAdapter = correspondence.getEndpointRefs().get(ep).getAdaptor().get();
                File file = new File(new URI(correspondence.getEndpointRefs().get(ep).getLocationURL().getUrl()));
                FileInputStream fis = new FileInputStream(file);
                Sys system = correspondence.getEndpointRefs().get(ep).getSystem().get();
                Data d = techSpaceAdapter.readInstance(system, fis);
                fis.close();
                cdata.addDataSource(d);
            }
            return cdata.build();
        } else {
            if (queryFile == null) {
                throw new UnsupportedFeatureException("There is at least one server endpoint, thus you have to specify a Query against whose results the verification should be applied!");
            } else {
                return null;
                // TODO calculate a federation and execute the query
            }
        }
    }

    @Override
    protected void executeSchema(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) throws IOException, TechSpaceException, UnsupportedFeatureException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        techSpace.writeSchema(correspondence.getComprSys(), fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    @Override
    protected void executeMatch(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) {

    }

    @Override
    protected void executeFederation(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) throws TechSpaceException, UnsupportedFeatureException {

    }

    @Override
    public boolean isBatchGoal() {
        return false;
    }

    @Override
    public boolean isServerGoal() {
        return false;
    }

    @Override
    public boolean isCodegenGoal() {
        return false;
    }

    @Override
    public boolean isFileGoal() {
        return true;
    }
}
