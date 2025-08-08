package io.corrlang.techspaces;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Optional;

public interface WriteSchemaCapabilities extends TechSpaceCapability {

    Optional<String> defaultFileEnding();

    SchemaWriter<OutputStream> serializeSchema();

    default SchemaWriter<File> serializeToFile() {
        return (schema, targetDir) -> {
            if (targetDir.exists() && targetDir.isDirectory()) {
                String n = schema.getName();
                if (defaultFileEnding().isPresent()) {
                    n = n + "." + defaultFileEnding().get();
                }
                File f = new File(targetDir, n);
                try (FileOutputStream fos = new FileOutputStream(f)) {
                    serializeSchema().serialize(schema, fos);
                }
            }
        };
    }

}
