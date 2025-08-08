package io.corrlang.domain;

import no.hvl.past.names.Name;

import java.util.Objects;

public class QualifiedName {

    private final Name system;
    private final Name element;

    public QualifiedName(Name system, Name element) {
        this.system = system;
        this.element = element;
    }

    public Name getSystem() {
        return system;
    }

    public Name getElement() {
        return element;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QualifiedName that = (QualifiedName) o;
        return system.equals(that.system) && element.equals(that.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(system, element);
    }

    @Override
    public String toString() {
        return system.printRaw() + "." + element.printRaw();
    }


    public static QualifiedName qname(Name systemURI, Name element) {
        return new QualifiedName(systemURI, element);
    }

    public static QualifiedName qname(String systemURI, Name element) {
        return new QualifiedName(Name.identifier(systemURI), element);
    }

    public static QualifiedName qname(Endpoint system, Name element) {
        return new QualifiedName(system.asId(), element);
    }
}
