package io.corrlang.domain.diagrams;

import org.checkerframework.checker.units.qual.A;

public class ActionOutputMarker extends EdgeMarker {

    private static ActionOutputMarker instance;

    public ActionOutputMarker() {
        super("ACTION_OUTPUT");
    }

    @Override
    public void accept(MarkerVisitor visitor) {
        visitor.handle(this);
    }

    @Override
    public boolean isPredicate() {
        return false;
    }

    @Override
    public boolean isOperation() {
        return true;
    }

    public static ActionOutputMarker getInstance() {
        if (instance == null) {
            instance = new ActionOutputMarker();
        }
        return instance;
    }
}
