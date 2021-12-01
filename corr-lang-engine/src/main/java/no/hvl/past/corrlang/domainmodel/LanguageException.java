package no.hvl.past.corrlang.domainmodel;

import no.hvl.past.corrlang.reporting.ReportErrorType;

public class LanguageException extends Exception {

    private final CorrLangElement languageElement;
    private final ReportErrorType errorType;
    private final String msg;


    public LanguageException(CorrLangElement languageElement, ReportErrorType errorType, String msg) {
        super(mkMessage(languageElement,errorType,msg));
        this.languageElement = languageElement;
        this.errorType = errorType;
        this.msg = msg;
    }

    public static String mkMessage(CorrLangElement languageElement, ReportErrorType errorType, String msg) {
        return errorType.name() + " ERROR:" +
                msg + "! at " + languageElement.reportElement();
    }
}
