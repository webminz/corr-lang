package io.corrlang.domain.data;

import io.corrlang.domain.Endpoint;
import no.hvl.past.names.Name;
import no.hvl.past.trees.TreeBuilder;

import java.io.InputStream;

public class DataBuilder {

    private final Endpoint origin;
    private final TreeBuilder builder;

    public DataBuilder(Endpoint origin) {
        this.origin = origin;
        this.builder = TreeBuilder.newInstance();
    }

    /**
     * Builds a data instance directly from serialized JSON by resorting to functionality of the underlying mdegraphlib.
     * This method requires the name of the root type in the JSON in order to be able to _type_ the tree elements
     * correctly. Optionally, you may specify a path of property names (which may include numerical indices when traversing
     * arrays) from the root of the given JSON to a sub-tree. This may be useful if the JSON contains a lot
     * of envelope elements that should be ignored.
     */
    public DataBuilder fromJson(InputStream json, Name rootType, String... skipToPath) {
        // TODO
        return this;
    }

    /**
     * Builds a data instance directly from serialized XML by resorting to functionality of the underlying mdegraphlib.
     * This method requires the name of the root ype in the XML in order to be able to _type_ the tree elements
     * correctly. Optionally, you may specify a path of property names (which may include numerical indices when traversing
     * arrays) from the root of the given JSON to a sub-tree. This may be useful if the JSON contains a lot
     * of envelope elements that should be ignored.
     */
    public DataBuilder fromXML(InputStream xml, Name rootType, String... skipToPath) {
        // TODO
        return this;
    }

    public Data build() {

        return new Data(origin, this.builder.build());
    }
}
