package no.hvl.past.corrlang.reporting;

import no.hvl.past.corrlang.domainmodel.CorrLangElement;
import java.io.PrintStream;

public class PrintStreamReportFacade implements ReportFacade {

    private final PrintStream out;

    public PrintStreamReportFacade(PrintStream out) {
        this.out = out;
    }

    @Override
    public void reportError(String message) {
        out.println("[" + ReportErrorType.OTHER+ "_ERROR]: " + message);
    }

    @Override
    public void reportInfo(String message) {
        out.println(message);
    }

    @Override
    public void reportError(Throwable cause) {
        out.println("[" + ReportErrorType.OTHER + "_ERROR]: " + cause.getMessage());
    }

    @Override
    public void reportSyntaxError(String file, int line, int column, String message) {
        out.println("[" + ReportErrorType.SYNTAX + "_ERROR]: " + message + " at (" + file + "|" + line + ":" + column + ")");
    }

    @Override
    public void reportError(ReportErrorType errorType, CorrLangElement languageElement, String message) {
        out.println("[" +errorType.name() + "_ERROR]: " + message + " at element "+ languageElement.reportElement());
    }

    @Override
    public void reportError(ReportErrorType errorType, Throwable cause) {
        out.println("[" +errorType.name() + "_ERROR]: " + cause.getMessage());
    }

    @Override
    public void reportError(ReportErrorType errorType, String message) {
        out.println("[" +errorType.name() + "_ERROR]: " + message);
    }
    @Override
    public void reportError(ReportErrorType errorType, String key, String message) {
        out.println("[" + errorType.name() + "_ERROR]: " + key + ": " + message);
    }

    @Override
    public void reportError(ReportErrorType errorType, Throwable cause, CorrLangElement langElement) {
        out.println("[" +errorType.name() + "_ERROR]: " + cause.getMessage() + " at element "+ langElement.reportElement());

    }


}
