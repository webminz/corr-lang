package io.corrlang.domain.diagrams;



public class ActionGroupChildMarker extends EdgeMarker {

    private static ActionGroupChildMarker instance;

    public ActionGroupChildMarker() {
        super("ACTION_GROUP_CHILD");
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

    public static ActionGroupChildMarker getInstance() {
        if (instance == null) {
            instance = new ActionGroupChildMarker();
        }
        return instance;
    }
}
