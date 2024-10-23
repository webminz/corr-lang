package io.corrlang.domain.diagrams;

import no.hvl.past.graph.Graph;
import no.hvl.past.graph.Universe;


public abstract class NodeMarker extends Marker {


    public NodeMarker(String tag) {
        super(tag);
    }


    @Override
    public final Graph arity() {
        return Universe.ONE_NODE;
    }
}
