package io.corrlang.domain;

import no.hvl.past.names.Name;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Commonality {

    private final Name type;
    private final Name id;
    private final Set<QualifiedName> projections;

    // TODO commonalities must also be able express links

    Commonality(Name type, Name id, Set<QualifiedName> projections) {
        this.type = type;
        this.id = id;
        this.projections = projections;
    }

    public Name getType() {
        return type;
    }

    public Name getId() {
        return id;
    }

    public boolean contains(Name system, Name element) {
        return this.projections.contains(new QualifiedName(system, element));
    }

    public Set<QualifiedName> getProjections() {
        return projections;
    }

    public Optional<Name> projectionOn(Name system) {
        return projections.stream().filter(qualifiedName -> qualifiedName.getSystem().equals(system)).findFirst().map(QualifiedName::getElement);
    }

    public Commonality add(Name system, Name element) {
        Set<QualifiedName> related = new HashSet<>(projections);
        related.add(new QualifiedName(system, element));
        return new Commonality(type, id, related);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commonality that = (Commonality) o;
        return type.equals(that.type) && id.equals(that.id) && projections.equals(that.projections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, id, projections);
    }

    public static Commonality create(Name type, Name commId) {
        return new Commonality(type, commId, new HashSet<>());
    }
}
