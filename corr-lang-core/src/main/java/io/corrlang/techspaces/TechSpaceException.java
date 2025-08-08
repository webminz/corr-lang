package io.corrlang.techspaces;

public class TechSpaceException extends Exception {

    private final TechSpace techSpace;

    public TechSpaceException(String message, TechSpace techSpace) {
        super("ERROR in tech space " + techSpace.ID() + ": " + message);
        this.techSpace = techSpace;
    }

    public TechSpaceException(String message, Throwable cause, TechSpace techSpace) {
        super("ERROR in tech space " + techSpace.ID() + ": " + message);
        this.techSpace = techSpace;
    }

    public TechSpaceException(Throwable cause, TechSpace techSpace) {
        super("ERROR in tech space " + techSpace.ID() + ": " + cause.getMessage() , cause);
        this.techSpace = techSpace;
    }
}
