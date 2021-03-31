package no.hvl.past.corrlang.domainmodel;

public class Identification extends Commonality {

    public Identification() {
        super();
    }

    @Override
    public boolean isIdentity() {
        return true;
    }

    @Override
    public boolean isSynchronizeElements() {
        return true;
    }

    @Override
    public void accept(SyntaxVisitor visitor) throws Throwable {
        visitor.handle(this);
    }
}
