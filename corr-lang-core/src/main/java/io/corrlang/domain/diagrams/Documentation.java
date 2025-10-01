package io.corrlang.domain.diagrams;

import no.hvl.past.graph.Graph;

public class Documentation extends Marker {

    private final String documentationText;

    private final Graph arity;

    public Documentation(String documentationText, Graph arity) {
        super("doc");
        this.documentationText = documentationText;
        this.arity = arity;
    }

    @Override
    public Graph arity() {
        return arity;
    }
}
