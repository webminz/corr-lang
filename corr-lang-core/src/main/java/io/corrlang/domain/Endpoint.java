package io.corrlang.domain;

import io.corrlang.domain.schemas.Schema;
import no.hvl.past.graph.*;
import no.hvl.past.names.Identifier;
import no.hvl.past.names.Name;

import javax.annotation.Nullable;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Endpoints are the basic ingredients for building correspondences.
 * An endpoint represents a system.
 * This can either be a dataset, a service, a sink, or a source.
 * Moreover, each endpoint is expected to have a schema, which is essentially a diagrammatic graph.
 *
 */
public class Endpoint {

    public enum EndpointType {
        DATASET,
        SERVICE,
        SOURCE,
        SINK
    }

    private static final AtomicInteger endpointIDSequence = new AtomicInteger();

    private final int order;
    private final String name;
    private final Schema schema;

    private final EndpointType type;
    @Nullable
    private final URL url;

    private Endpoint(int order, String name, Schema schema, EndpointType type, URL url) {
        this.order = order;
        this.name = name;
        this.schema = schema;
        this.type = type;
        this.url = url;
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

    public EndpointType getType() {
        return type;
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

    public static Endpoint create(String name, Schema schema, EndpointType type) {
        return new Endpoint(endpointIDSequence.incrementAndGet(), name, schema, type, null);
    }

    public static Endpoint create(URL url, String name, Schema schema, EndpointType type) {
        return new Endpoint(endpointIDSequence.incrementAndGet(), name, schema, type, url);
    }
}
