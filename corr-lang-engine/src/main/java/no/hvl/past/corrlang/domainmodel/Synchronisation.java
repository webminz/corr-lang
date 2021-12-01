package no.hvl.past.corrlang.domainmodel;

public class Synchronisation extends Commonality {

    public Synchronisation() {
    }

    @Override
    public boolean isIdentity() {
        return false;
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
