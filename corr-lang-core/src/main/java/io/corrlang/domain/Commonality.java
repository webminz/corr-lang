package io.corrlang.domain;

import no.hvl.past.names.Name;

import javax.annotation.Nullable;
import java.util.*;

public sealed interface Commonality permits Commonality.Relates, Commonality.Identifies, Commonality.Synchronizes {

    record Relates(int order, List<QualifiedName> projections, Optional<Commonality> parent, Optional<String> relationName) implements Commonality {}

    record Identifies(int order, List<QualifiedName> projections, String commonName,  Optional<Commonality> parent) implements Commonality {}

    record Synchronizes(int order, List<QualifiedName> projections, List<Integer> projectionDependency,  Optional<Commonality> parent, Optional<String> relationName) implements Commonality {}


    List<QualifiedName> projections();

    Optional<Commonality> parent();

    default boolean contains(QualifiedName name) {
        return projections().contains(name);
    }

    default Optional<Name> projectOn(Endpoint endpoint) {
        return projections().stream().filter(qname -> qname.getEndpoint() == endpoint.getOrder()).map(QualifiedName::getElement).findFirst();
    }



}
