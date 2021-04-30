package no.hvl.past.corrlang.reporting;

import no.hvl.past.corrlang.domainmodel.CorrLangElement;

public interface ReportFacade {

    public static final ReportFacade NULL_REPORT_FACADE = new ReportFacade() {
        @Override
        public void reportError(String message) {
        }

        @Override
        public void reportInfo(String message) {
        }

        @Override
        public void reportError(Throwable cause) {
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
    };

    void reportError(String message);

    void reportInfo(String message);

    void reportError(Throwable cause);

    void reportSyntaxError(String file, int line, int column, String message);

    void reportError(ReportErrorType errorType, CorrLangElement languageElement, String message);

    void reportError(ReportErrorType errorType, Throwable cause);

    void reportError(ReportErrorType errorType, String message);

    void reportError(ReportErrorType errorType, String key, String message);

    void reportError(ReportErrorType errorType, Throwable cause, CorrLangElement langElement);
}
