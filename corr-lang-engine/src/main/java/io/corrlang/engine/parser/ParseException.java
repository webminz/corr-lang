package io.corrlang.engine.parser;


import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;

public class ParseException extends Exception {

    private final String file;
    private final List<String> recognitionExceptions;

    public ParseException(String file, List<String> recognitionExceptions) {
        super(makeErrorMessage(file));
        this.file = file;
        this.recognitionExceptions = recognitionExceptions;
    }


    @Override
    public void printStackTrace(PrintStream s) {
        s.println(makeErrorMessage(file));
        for (String e : recognitionExceptions) {
             s.println(e);
        }
        super.printStackTrace(s);
    }

    private static String makeErrorMessage(String file) {
        return "SYNTAX Exception: There are some syntax errors in '" + file + "'!";
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        s.println(makeErrorMessage(file));
        for (String e : recognitionExceptions) {
            s.println(e);
        }
        super.printStackTrace(s);
    }

    public List<String> getRecognitionExceptions() {
        return recognitionExceptions;
    }
}
