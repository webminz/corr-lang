package no.hvl.past.corrlang.runner;

import no.hvl.past.corrlang.execution.ExecutionFacade;
import no.hvl.past.corrlang.execution.goals.AbstractGoal;
import no.hvl.past.corrlang.parser.ParseException;
import no.hvl.past.corrlang.parser.ParserChain;
import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.di.DependencyInjectionContainer;
import no.hvl.past.di.PropertyHolder;
import no.hvl.past.util.FileSystemUtils;
import no.hvl.past.util.Holder;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class AbstractRun {


    private static final String LOGO =
                    "╔═╗┌─┐┬─┐┬─┐╦  ┌─┐┌┐┌┌─┐\n" +
                    "║  │ │├┬┘├┬┘║  ├─┤││││ ┬\n" +
                    "╚═╝└─┘┴└─┴└─╩═╝┴ ┴┘└┘└─┘";

    private final ReportFacade reportFacade;
    private final Properties properties;
    private final List<String> corrSpecs;
    private SyntacticalResult syntacticalResult;

    private DependencyInjectionContainer diContainer;
    private ExecutionFacade executionFacade;

    public AbstractRun(
            ReportFacade reportFacade,
            Properties properties,
            List<String> corrSpecs) {
        this.reportFacade = reportFacade;
        this.properties = properties;
        this.corrSpecs = corrSpecs;
        this.syntacticalResult = new SyntacticalResult();
    }

    protected void initialise(boolean silent) throws IOException {
        if (properties.containsKey(PropertyHolder.CONFIG_FILE)) {
            this.diContainer = DependencyInjectionContainer.create(new File(properties.getProperty(PropertyHolder.CONFIG_FILE)));
        } else if (properties.containsKey(PropertyHolder.BASE_DIR)) {
            this.diContainer = DependencyInjectionContainer.create(properties.getProperty(PropertyHolder.BASE_DIR));
        } else {
            this.diContainer = DependencyInjectionContainer.create();
        }
        this.diContainer.load(properties);
        if (!silent) {
            printLogoAndSysInfo();
        }
        this.executionFacade = new ExecutionFacade(diContainer);
    }

    private void printLogoAndSysInfo() throws IOException {
        reportFacade.reportInfo(LOGO);
        ClassPathResource versionFile = new ClassPathResource("VERSION.INFO");
        if (versionFile.exists()) {
            InputStream inputStream = versionFile.getInputStream();
            Properties versionProps = new Properties();
            versionProps.load(inputStream);
            inputStream.close();
            if (versionProps.containsKey("VERSION")) {
                reportFacade.reportInfo("VERSION: " + versionProps.getProperty("VERSION"));
            }
        }
        ClassPathResource buildFile = new ClassPathResource("BUILD.INFO");
        if (buildFile.exists()) {
            InputStream inputStream = buildFile.getInputStream();
            Properties buildProps = new Properties();
            buildProps.load(inputStream);
            inputStream.close();
            if (buildProps.getProperty("BUILD_TYPE", "DEVELOPER").equals("RELEASE")) {
                reportFacade.reportInfo("RELEASE: " + buildProps.getProperty("BUILD_NO"));
            } else {
                reportFacade.reportInfo("DEVELOPER-BUILD: created from git branch <" + buildProps.getProperty("COMMIT") + "> at " + buildProps.getProperty("BUILD_DATE"));
            }
        }
        reportFacade.reportInfo("WORKDIR: " + diContainer.getPropertyHolder().getBaseDir().getAbsolutePath());
        reportFacade.reportInfo("JAVA: " + System.getProperty("java.runtime.version"));

    }


    private void parseCorrSpecFiles() throws IOException {
        FileSystemUtils fsUtils = this.diContainer.getFSUtils();
        for (String corrSpecFile : corrSpecs) {
            File file = fsUtils.file(corrSpecFile);
            if (!file.exists()) {
                reportFacade.reportError("File '" + file.getAbsolutePath() + "' does not exist! It will be ignored!");
            }
            try {
                this.syntacticalResult = ParserChain.parseFromFile(file, reportFacade, this.syntacticalResult);
            } catch (ParseException e) {
                reportFacade.reportError(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected <R> R executeGoal(String goalName, Class<R> expectedReturnType) throws Throwable {
        parseCorrSpecFiles();
        Holder<Object> objectHolder = AbstractGoal.runGoal(diContainer, executionFacade, syntacticalResult, goalName);
        if (Void.class.isAssignableFrom(expectedReturnType)) {
            return null;
        } else {
            if (!objectHolder.hasValue()) {
                throw new Exception("The goal '" + goalName + "' that you just executed is not a LIB goal! Only LIB goals can produce Java-Objects as a result!");
            }
            Object o = objectHolder.unsafeGet();
            if (!expectedReturnType.isAssignableFrom(o.getClass())) {
                throw new Exception("The goal '" + goalName + "' that you just executed produced a JavaObject of type '" + o.getClass().getCanonicalName() + "' but you expected it to be '" + expectedReturnType.getCanonicalName() + "'!");
            }
            return (R) o;
        }

    }



}
