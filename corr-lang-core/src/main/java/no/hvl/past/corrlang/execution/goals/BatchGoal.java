package no.hvl.past.corrlang.execution.goals;

import no.hvl.past.corrlang.domainmodel.CorrSpec;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.plugin.UnsupportedFeatureException;
import no.hvl.past.techspace.TechSpace;
import no.hvl.past.techspace.TechSpaceAdapter;
import no.hvl.past.techspace.TechSpaceException;
import no.hvl.past.util.Holder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class BatchGoal extends LanguageGoal {

    private Holder<Object> result;

    public Holder<Object> getResult() {
        return result;
    }

    public void setResult(Holder<Object> result) {
        this.result = result;
    }

    @Autowired
    private ReportFacade reportFacade;

    public BatchGoal() {
        super("BATCH");
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
        return false;
    }

    @Override
    public boolean isBatchGoal() {
        return true;
    }

    @Override
    protected void executeTransformation(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) {

    }

    @Override
    protected void executeSynchronize(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) {

    }

    @Override
    protected void executeVerify(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) {

    }

    @Override
    protected void executeSchema(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) throws TechSpaceException, UnsupportedFeatureException, UnsupportedEncodingException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        techSpace.writeSchema(correspondence.getComprSys(), bos);
        String string = bos.toString("UTF-8");
        reportFacade.reportInfo(string);
    }

    @Override
    protected void executeMatch(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) {

    }

    @Override
    protected void executeFederation(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) throws TechSpaceException, UnsupportedFeatureException {

    }
}
