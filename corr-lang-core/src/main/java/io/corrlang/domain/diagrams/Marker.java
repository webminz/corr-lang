package io.corrlang.domain.diagrams;

import no.hvl.past.graph.Graph;
import no.hvl.past.graph.GraphTheory;
import no.hvl.past.logic.Model;

public abstract class Marker implements GraphTheory {

    private final String representation;

    public Marker(String representation) {
        this.representation = representation;
    }

    @Override
    public String nameAsString() {
        return representation;
    }

    @Override
    public final boolean isSatisfied(Model<Graph> model) {
        return true;
    }

    public abstract void accept(MarkerVisitor visitor);
}
