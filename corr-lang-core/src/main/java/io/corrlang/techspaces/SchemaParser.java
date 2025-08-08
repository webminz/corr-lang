package io.corrlang.techspaces;


import io.corrlang.domain.schemas.Schema;
import io.corrlang.domain.schemas.SchemaBuilder;

/**
 * Parses a schema from a given input.
 */
@FunctionalInterface
public interface SchemaParser<I> {

    Schema parse(I source, SchemaBuilder builderHelper) throws Exception;
}
