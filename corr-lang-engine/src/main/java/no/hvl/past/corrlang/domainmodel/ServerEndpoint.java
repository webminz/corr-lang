package no.hvl.past.corrlang.domainmodel;

public class ServerEndpoint extends Endpoint {

    public ServerEndpoint(String currentName) {
        super(currentName);
    }

    @Override
    public void accept(SyntaxVisitor visitor) throws Throwable {
        visitor.handle(this);
    }
}
