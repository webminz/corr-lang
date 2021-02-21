package no.hvl.past.corrlang.reporting;

import no.hvl.past.corrlang.domainmodel.CorrLangElement;
import java.io.PrintStream;

public class ReportFacade {

    private final PrintStream out;

    public ReportFacade(PrintStream out) {
        this.out = out;
    }

    public void reportError(String message) {
        out.println("[" + ReportErrorType.OTHER+ "_ERROR]: " + message);
    }

    public void reportInfo(String message) {
        out.println(message);
    }

    public void reportError(Throwable cause) {
        out.println("[" + ReportErrorType.OTHER + "_ERROR]: " + cause.getMessage());
    }

    public void reportSyntaxError(String file, int line, int column, String message) {
        out.println("[" + ReportErrorType.SYNTAX + "_ERROR]: " + message + " at (" + file + "|" + line + ":" + column + ")");
    }

    public void reportError(ReportErrorType errorType, CorrLangElement languageElement, String message) {
        out.println("[" +errorType.name() + "_ERROR]: " + message + " at element "+ languageElement.reportElement());
    }

    public void reportError(ReportErrorType errorType, Throwable cause) {
        out.println("[" +errorType.name() + "_ERROR]: " + cause.getMessage());
    }

    public void reportError(ReportErrorType errorType, Throwable cause, CorrLangElement langElement) {
        out.println("[" +errorType.name() + "_ERROR]: " + cause.getMessage() + " at element "+ langElement.reportElement());
    }


}
