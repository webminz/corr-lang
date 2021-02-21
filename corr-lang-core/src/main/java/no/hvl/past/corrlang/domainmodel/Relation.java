package no.hvl.past.corrlang.domainmodel;

public class Relation extends Commonality {
    public Relation(String name) {
        super(name);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.handle(this);

    }


}
