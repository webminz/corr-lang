package io.corrlang.engine.domainmodel;

public class Relation extends Commonality {

    public Relation() {
        super();
    }

    @Override
    public boolean isIdentity() {
        return false;
    }

    @Override
    public boolean isSynchronizeElements() {
        return false;
    }

    @Override
    public void accept(SyntaxVisitor visitor) throws Throwable {
        visitor.handle(this);

    }


}
