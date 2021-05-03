package no.hvl.past.corrlang;

import no.hvl.past.corrlang.domainmodel.CorrLangElement;
import no.hvl.past.corrlang.reporting.ReportErrorType;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.corrlang.runner.AbstractRun;
import no.hvl.past.di.PropertyHolder;
import no.hvl.past.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class SystemTestRun extends AbstractRun {

    public SystemTestRun(ReportFacade reportFacade, Properties properties, List<String> corrSpecs) {
        super(reportFacade, properties, corrSpecs);
    }

    public static class TestReportFacade implements ReportFacade {

        private final StringBuilder infos = new StringBuilder();
        private final StringBuilder errors = new StringBuilder();
        private final List<Throwable> exceptions = new ArrayList<>();

        public String getInfos() {
            return infos.toString();
        }

        public String getErrors() {
            return errors.toString();
        }

        public List<Throwable> getExceptions() {
            return exceptions;
        }

        @Override
        public void reportError(String message) {
            errors.append(message);
        }

        @Override
        public void reportInfo(String message) {
            infos.append(message);
        }

        @Override
        public void reportError(Throwable cause) {
            exceptions.add(cause);
        }

        @Override
        public void reportSyntaxError(String file, int line, int column, String message) {

        }

        @Override
        public void reportError(ReportErrorType errorType, CorrLangElement languageElement, String message) {

        }

        @Override
        public void reportError(ReportErrorType errorType, Throwable cause) {

        }

        @Override
        public void reportError(ReportErrorType errorType, String message) {

        }

        @Override
        public void reportError(ReportErrorType errorType, String key, String message) {

        }

        @Override
        public void reportError(ReportErrorType errorType, Throwable cause, CorrLangElement langElement) {

        }
    }

    public static Pair<SystemTestRun, TestReportFacade> create(File baseDir, File corrSpec) {
        return create(baseDir, corrSpec, new Properties());
    }

    public static Pair<SystemTestRun, TestReportFacade> create(File baseDir, File corrSpec, Properties properties) {
        properties.setProperty(PropertyHolder.BASE_DIR, baseDir.getAbsolutePath());
        TestReportFacade testReportFacade = new TestReportFacade();
        SystemTestRun testRun = new SystemTestRun(testReportFacade, properties, Collections.singletonList(corrSpec.getAbsolutePath()));
        return new Pair<>(testRun, testReportFacade);
    }

    public void testRun(String goal, boolean silent) throws Throwable {
        initialise(silent);
        executeGoal(goal, Void.class);
    }
}
