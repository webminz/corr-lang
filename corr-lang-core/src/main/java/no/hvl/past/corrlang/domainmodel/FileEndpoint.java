package no.hvl.past.corrlang.domainmodel;

public class FileEndpoint extends Endpoint {

    public FileEndpoint(String currentName) {
        super(currentName);
    }

    @Override
    public void accept(SyntaxVisitor visitor) throws Throwable {
        visitor.handle(this);
    }
}
