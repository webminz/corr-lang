package io.corrlang.domain.diagrams;

import no.hvl.past.graph.Graph;
import no.hvl.past.graph.Universe;


public class NodeMarker extends Marker {

    public enum NodeMarkerType {
        ACTION,
        ACTION_GROUP

    }

    private final NodeMarkerType type;

    public NodeMarker(NodeMarkerType type) {
        super(type.name());
        this.type = type;
    }

    public NodeMarkerType getType() {
        return type;
    }

    @Override
    public final Graph arity() {
        return Universe.ONE_NODE;
    }
}
