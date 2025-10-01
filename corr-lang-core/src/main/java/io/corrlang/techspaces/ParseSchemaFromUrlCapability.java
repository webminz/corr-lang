package io.corrlang.techspaces;

import io.corrlang.domain.schemas.Schema;
import io.corrlang.domain.schemas.SchemaBuilder;

import java.io.InputStream;
import java.net.URL;

public interface ParseSchemaFromUrlCapability extends ParseSchemaCapability {

    FromURLSchemaParser schemaParser();

    @FunctionalInterface
    interface FromURLSchemaParser {

        Schema parse(URL source, SchemaBuilder builder) throws Exception;


    }
}
