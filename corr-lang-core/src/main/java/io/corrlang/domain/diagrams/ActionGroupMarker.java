package io.corrlang.domain.diagrams;

public class ActionGroupMarker extends NodeMarker {

    private static ActionGroupMarker instance;

    public ActionGroupMarker() {
        super( "ACTION_GROUP");
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

    public static ActionGroupMarker getInstance() {
        if (instance == null) {
            instance = new ActionGroupMarker();
        }
        return instance;
    }
}
