package io.corrlang.domain;

import io.corrlang.domain.schemas.Schema;
import no.hvl.past.graph.*;
import no.hvl.past.names.Identifier;
import no.hvl.past.names.Name;

import javax.annotation.Nullable;
import java.net.URL;
import java.util.*;

/**
 * A sys(tem) is a convenience wrapper on top of a {@link Sketch},
 * which adds some support message for the most common metamodel-query operations
 * that are known from popular Frameworks such as Ecore.
 *
 * Moreover, it explicitly adds the notion of messages (i.e. means to access and manipulate the data stored in a system).
 *
 */
public class Endpoint {

    private final int order;
    private final String name;
    private final Schema schema;

    @Nullable
    private final URL url;

    public Endpoint(int order, String name, Schema schema, URL url) {
        this.order = order;
        this.name = name;
        this.schema = schema;
        this.url = url;
    }

    public Endpoint(int order, String name, Schema schema) {
        this.order = order;
        this.name = name;
        this.schema = schema;
        this.url = null;
    }

    public int getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    public Identifier asId() {
        return Name.identifier(name);
    }

    public Schema getSchema() {
        return schema;
    }


    public Optional<URL> getUrl() {
        return Optional.ofNullable(url);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endpoint endpoint = (Endpoint) o;
        return order == endpoint.order;
    }

    @Override
    public int hashCode() {
        return Objects.hash(order);
    }
}
