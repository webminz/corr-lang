package io.corrlang.domain.diagrams;

public class ActionInputMarker extends EdgeMarker {

    private static ActionInputMarker instance;

    public ActionInputMarker() {
        super("ACTION_INPUT");
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

    public static ActionInputMarker getInstance() {
        if (instance == null) {
            instance = new ActionInputMarker();
        }
        return instance;
    }
}
