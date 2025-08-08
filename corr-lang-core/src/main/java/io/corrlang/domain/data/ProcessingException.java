package io.corrlang.domain.data;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Signals that an error occurred while processing a request, which is not related to failure
 * of the infrastructure but a logical error.
 */
public class ProcessingException extends Exception {

    public ProcessingException(String message) {
        super(message);
    }

    public ProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public void printStackTrace() {
        printStackTrace(System.err);
    }

    @Override
    public void printStackTrace(PrintStream s) {
        s.println(getMessage());
        String details = printDetails();
        if (!details.isEmpty()) {
            s.println("DETAILS:");
            s.println(details);
        }
        super.printStackTrace(s);
    }

    protected String printDetails() {
        return "";
    }

}
