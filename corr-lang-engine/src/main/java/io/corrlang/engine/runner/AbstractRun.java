package io.corrlang.engine.runner;

import io.corrlang.engine.parser.ParseException;
import io.corrlang.engine.parser.SyntacticalResult;
import io.corrlang.engine.execution.ExecutionFacade;
import io.corrlang.engine.execution.goals.AbstractGoal;
import io.corrlang.engine.parser.ParserChain;
import io.corrlang.engine.reporting.ReportFacade;
import no.hvl.past.di.DependencyInjectionContainer;
import no.hvl.past.di.PropertyHolder;
import no.hvl.past.util.FileSystemAccessPoint;
import no.hvl.past.util.Holder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;


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
    private Logger logger;

    public AbstractRun(
            ReportFacade reportFacade,
            Properties properties,
            List<String> corrSpecs) {
        this.reportFacade = reportFacade;
        this.properties = properties;
        this.corrSpecs = corrSpecs;
        this.syntacticalResult = new SyntacticalResult();
    }

    protected SyntacticalResult getSyntacticalResult() {
        return syntacticalResult;
    }

    protected void initialise(boolean silent) throws Exception {
        this.diContainer = DependencyInjectionContainer.create("CorrLang", properties);
        this.logger = LogManager.getLogger(getClass());
        printLogoAndSysInfo(silent);
        this.executionFacade = new ExecutionFacade(diContainer, reportFacade);
    }

    private void printLogoAndSysInfo(boolean silent) throws IOException {
        ClassPathResource versionFile = new ClassPathResource("VERSION.INFO");
        PropertyHolder propertyHolder = diContainer.getPropertyHolder();
        if (versionFile.exists()) {
            InputStream inputStream = versionFile.getInputStream();
            Properties versionProps = new Properties();
            versionProps.load(inputStream);
            inputStream.close();
            propertyHolder.setProperty("corrlang.version", versionProps.getProperty("VERSION"));
        }
        ClassPathResource buildFile = new ClassPathResource("BUILD.INFO");
        if (buildFile.exists()) {
            InputStream inputStream = buildFile.getInputStream();
            Properties buildProps = new Properties();
            buildProps.load(inputStream);
            inputStream.close();
            if (buildProps.getProperty("BUILD_TYPE", "DEVELOPER").equals("RELEASE")) {
                propertyHolder.setProperty("corrlang.build.type","release");
                propertyHolder.setProperty("corrlang.build.desc",buildProps.getProperty("BUILD_NO"));
            } else {
                propertyHolder.setProperty("corrlang.build.type","developer");
                propertyHolder.setProperty("corrlang.build.desc", "DEVELOPER-BUILD: created from git branch <" + buildProps.getProperty("COMMIT") + "> at " + buildProps.getProperty("BUILD_DATE"));
            }
        }
        logger.info("CorrLang started ¯\\_(ツ)_/¯ System Time is: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                " Timezone is: "+ TimeZone.getDefault().getDisplayName() + " (" + TimeZone.getDefault().toZoneId().getRules().getStandardOffset(Instant.now()).getId() + ")");
        logger.info("VERSION:    " + propertyHolder.getProperty("corrlang.version"));
        logger.info("RELEASE:    " + propertyHolder.getProperty("corrlang.build.desc"));
        logger.info("WORKDIR:    " + propertyHolder.getProperty(PropertyHolder.BASE_DIR));
        logger.info("CONFIG:     " + propertyHolder.getProperty(PropertyHolder.CONFIG_FILE));
        logger.info("JAVA:       " + propertyHolder.getProperty(PropertyHolder.JVM));
        logger.info("Log Level:  " + propertyHolder.getProperty(PropertyHolder.LOG_LEVEL));
        if (!silent) {
            reportFacade.reportInfo(LOGO);
            reportFacade.reportInfo("VERSION:    " + propertyHolder.getProperty("corrlang.version"));
            reportFacade.reportInfo("RELEASE:    " + propertyHolder.getProperty("corrlang.build.desc"));
            reportFacade.reportInfo("WORKDIR:    " + propertyHolder.getProperty(PropertyHolder.BASE_DIR));
            reportFacade.reportInfo("CONFIG:     " + propertyHolder.getProperty(PropertyHolder.CONFIG_FILE));
            reportFacade.reportInfo("JAVA:       " + propertyHolder.getProperty(PropertyHolder.JVM));
        }
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("CorrLang shutting down now ... System Time is: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            }
        }));

    }


    private void parseCorrSpecFiles() throws IOException {
        FileSystemAccessPoint fsUtils = this.diContainer.getFSAccessPoint();
        for (String corrSpecFile : corrSpecs) {
            File file = fsUtils.file(corrSpecFile);
            if (!file.exists()) {
                reportFacade.reportError("File '" + file.getAbsolutePath() + "' does not exist! It will be ignored!");
            }
            try {
                this.syntacticalResult = ParserChain.parseFromFile(file, reportFacade, this.syntacticalResult);
            } catch (ParseException e) {
                reportFacade.reportException(e);
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

    protected DependencyInjectionContainer getDiContainer() {
        return diContainer;
    }

    protected List<String> getCorrSpecs() {
        return corrSpecs;
    }
}
