package io.corrlang.techspaces;

import org.springframework.core.io.UrlResource;

import java.io.*;
import java.net.URL;
import java.util.Optional;

public interface ParseSchemaCapabilities extends TechSpaceCapability {

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
    SchemaParser<InputStream> parseSchema();

    default SchemaParser<File> parseSchemaFromFile() {
        return (file, builder) -> {
            try (FileInputStream fis = new FileInputStream(file)) {
                SchemaParser<InputStream> defaultParser = parseSchema();
                return defaultParser.parse(fis, builder);
            } catch (IOException e) {
                throw e;
            }
        };
    }

    default SchemaParser<URL> parseSchemaFromUrl() {
        return (url, builder) -> {
            UrlResource urlResource = new UrlResource(url);
            SchemaParser<InputStream> defaultParser = parseSchema();
            return defaultParser.parse(urlResource.getInputStream(),builder);
        };
    }



}
