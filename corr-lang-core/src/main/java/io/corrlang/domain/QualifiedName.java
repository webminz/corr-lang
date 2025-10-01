package io.corrlang.domain;

import no.hvl.past.names.Name;

import java.util.Objects;

public class QualifiedName {

    private final int endpoint;
    private final Name element;

    public QualifiedName(int system, Name element) {
        this.endpoint = system;
        this.element = element;
    }


    public Name getElement() {
        return element;
    }


    public int getEndpoint() {
        return endpoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QualifiedName that = (QualifiedName) o;
        return endpoint == that.endpoint && Objects.equals(element, that.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(endpoint, element);
    }

    @Override
    public String toString() {
        return endpoint + "::" + element.toString();
    }



    public static QualifiedName qname(Endpoint system, Name element) {
        return new QualifiedName(system.getOrder(), element);
    }


}
