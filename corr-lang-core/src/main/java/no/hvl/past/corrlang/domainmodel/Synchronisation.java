package no.hvl.past.corrlang.domainmodel;

public class Synchronisation extends Commonality {

    public Synchronisation(String name) {
        super(name);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.handle(this);

    }
}
