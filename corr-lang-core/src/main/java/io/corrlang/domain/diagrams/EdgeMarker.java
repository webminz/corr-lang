package io.corrlang.domain.diagrams;

import no.hvl.past.graph.Graph;
import no.hvl.past.graph.Universe;

public abstract class EdgeMarker extends Marker {


    public EdgeMarker(String tag) {
        super(tag);
    }

    @Override
    public final Graph arity() {
        return Universe.ARROW;
    }
}
