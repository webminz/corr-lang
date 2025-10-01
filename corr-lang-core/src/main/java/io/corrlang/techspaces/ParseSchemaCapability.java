package io.corrlang.techspaces;

import io.corrlang.domain.schemas.Schema;
import io.corrlang.domain.schemas.SchemaBuilder;
import no.hvl.past.names.Name;

import java.io.*;
import java.net.URL;
import java.util.Optional;

public interface ParseSchemaCapability extends TechSpaceCapability {

    /**
     * Implementing this method allows to specify techspace-specifc
     * directives (rules) about how the schema shall be aligned with others, e.g.
     * mapping common base types such INT, STRING, automatically.
     */
    Optional<TechnologySpecificRules> schemaRules();

    /**
     * This is the base method that every tech space with the ability
     * to parse schemas has to implement.
     * It returns a functions, that takes an input stream (byte input)
     * and a builder (as helper utility) to produce a Sys object.
     *
     */
    SchemaParser parseSchema();


    /**
     * Parses a schema from a given input.
     */
    @FunctionalInterface
    interface SchemaParser {

        Schema parse(InputStream source, SchemaBuilder builder) throws Exception;
    }
}
