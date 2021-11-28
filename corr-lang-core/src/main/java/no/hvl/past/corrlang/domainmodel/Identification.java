package no.hvl.past.corrlang.domainmodel;

import no.hvl.past.util.StringUtils;

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
        return false;
    }

    @Override
    public void accept(SyntaxVisitor visitor) throws Throwable {
        visitor.handle(this);
    }

    @Override
    public String toString() {
        return "identify (" + StringUtils.fuseList(getRelates().stream().map(ElementRef::toString), ", ") + ") as " + getName() + ";";
    }
}
