package io.corrlang.engine.reporting;


public interface ReportFacade {

    public static final ReportFacade NULL_REPORT_FACADE = new ReportFacade() {
        @Override
        public void reportError(String message) {
        }

        @Override
        public void reportInfo(String message) {
        }

        @Override
        public void reportException(Throwable cause) {
        }

        @Override
        public void reportSyntaxError(String file, int line, int column, String message) {
        }

    };

    void reportError(String message);

    void reportInfo(String message);

    void reportException(Throwable cause);

    void reportSyntaxError(String file, int line, int column, String message);


}
