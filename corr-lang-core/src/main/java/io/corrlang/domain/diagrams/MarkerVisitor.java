package io.corrlang.domain.diagrams;

public interface MarkerVisitor {

    void handle(ActionMarker actionMarker);

    void handle(ActionGroupMarker actionGroupMarker);

    void handle(ActionInputMarker actionInputMarker);

    void handle(ActionOutputMarker actionOutputMarker);

    void handle(ActionGroupChildMarker actionGroupContainmentMarker);

    void handle(ContainmentMarker containmentMarker);
}
