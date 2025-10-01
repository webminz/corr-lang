package io.corrlang.techspaces;

import io.corrlang.domain.Endpoint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Optional;

public interface SerializeSchemaCapability extends TechSpaceCapability {

    Optional<String> defaultFileEnding();

    SchemaWriter<OutputStream> serializeSchema();

    interface SchemaWriter<O> {

        void serialize(Endpoint schema, O target) throws Exception;
    }
}
