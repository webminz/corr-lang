package io.corrlang.domain.diagrams;

public class ActionMarker extends NodeMarker {

    private static ActionMarker instance;

    public ActionMarker() {
        super("ACTION");
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

    public static ActionMarker getInstance() {
        if (instance == null) {
            instance = new ActionMarker();
        }
        return instance;
    }
}
