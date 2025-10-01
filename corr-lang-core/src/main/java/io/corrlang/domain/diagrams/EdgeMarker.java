package io.corrlang.domain.diagrams;

import no.hvl.past.graph.Graph;
import no.hvl.past.graph.Universe;

public class EdgeMarker extends Marker {

    public enum EdgeMarkerType {
        CONTAINMENT,
        ACTION_INPUT,
        ACTION_OUTPUT,
        ACTION_GROUP_MEMBER
    }

    private final EdgeMarkerType type;

    public EdgeMarker(EdgeMarkerType type) {
        super(type.name());
        this.type = type;
    }


    public EdgeMarkerType getType() {
        return type;
    }

    @Override
    public final Graph arity() {
        return Universe.ARROW;
    }
}
