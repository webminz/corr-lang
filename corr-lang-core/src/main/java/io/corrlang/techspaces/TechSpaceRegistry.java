package io.corrlang.techspaces;

import io.corrlang.domain.exceptions.CorrLangException;

import java.util.HashMap;
import java.util.Map;

public class TechSpaceRegistry {


    private final Map<String, WriteSchemaCapabilities> schemaWriters;
    private final Map<String, ParseSchemaCapabilities> schemaParsers;

    private TechSpaceRegistry() {
        this.schemaParsers = new HashMap<>();
        this.schemaWriters = new HashMap<>();
    }

    public void register(String techSpace, TechSpaceCapability... capabilities) {
        for (TechSpaceCapability cap : capabilities) {
            if (cap instanceof WriteSchemaCapabilities) {
                schemaWriters.put(techSpace, (WriteSchemaCapabilities) cap);
            }
            if (cap instanceof ParseSchemaCapabilities) {
                schemaParsers.put(techSpace, (ParseSchemaCapabilities) cap);
            }
        }
    }

    public ParseSchemaCapabilities schemaParser(String techSpaceName) {
        if (schemaParsers.containsKey(techSpaceName)) {
            return schemaParsers.get(techSpaceName);
        } else {
            throw CorrLangException.missingTechSpaceCapability(techSpaceName, ParseSchemaCapabilities.class);
        }
    }

    public WriteSchemaCapabilities schemaWriter(String techSpaceName) {
        if (schemaWriters.containsKey(techSpaceName)) {
            return schemaWriters.get(techSpaceName);
        } else {
            throw CorrLangException.missingTechSpaceCapability(techSpaceName, WriteSchemaCapabilities.class);
        }
    }

    public static TechSpaceRegistry newRegistry() {
        return new TechSpaceRegistry();
    }

}
