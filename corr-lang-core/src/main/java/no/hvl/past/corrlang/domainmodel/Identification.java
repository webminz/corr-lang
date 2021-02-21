package no.hvl.past.corrlang.domainmodel;

public class Identification extends Commonality {

    public Identification(String name) {
        super(name);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.handle(this);
    }
}
