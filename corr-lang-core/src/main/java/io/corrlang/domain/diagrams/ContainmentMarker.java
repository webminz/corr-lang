package io.corrlang.domain.diagrams;

import org.checkerframework.checker.units.qual.C;

public class ContainmentMarker extends EdgeMarker {

    private static ContainmentMarker instance;

    public ContainmentMarker() {
        super("CONTAINMENT");
    }

    @Override
    public void accept(MarkerVisitor visitor) {
        visitor.handle(this);

    }

    @Override
    public boolean isPredicate() {
        return true;
    }

    @Override
    public boolean isOperation() {
        return false;
    }

    public static ContainmentMarker getInstance() {
        if (instance == null) {
            instance = new ContainmentMarker();
        }
        return instance;
    }
}
