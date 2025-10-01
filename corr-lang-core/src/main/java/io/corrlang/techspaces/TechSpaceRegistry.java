package io.corrlang.techspaces;

import io.corrlang.domain.exceptions.CorrLangException;

import java.util.HashMap;
import java.util.Map;

public class TechSpaceRegistry {


    private final Map<String, SerializeSchemaCapability> schemaWriters;
    private final Map<String, ParseSchemaCapability> schemaParsers;

    private TechSpaceRegistry() {
        this.schemaParsers = new HashMap<>();
        this.schemaWriters = new HashMap<>();
    }

    public void register(String techSpace, TechSpaceCapability... capabilities) {
        for (TechSpaceCapability cap : capabilities) {
            if (cap instanceof SerializeSchemaCapability) {
                schemaWriters.put(techSpace, (SerializeSchemaCapability) cap);
            }
            if (cap instanceof ParseSchemaCapability) {
                schemaParsers.put(techSpace, (ParseSchemaCapability) cap);
            }
        }
    }

    public ParseSchemaCapability schemaParser(String techSpaceName) {
        if (schemaParsers.containsKey(techSpaceName)) {
            return schemaParsers.get(techSpaceName);
        } else {
            throw CorrLangException.missingTechSpaceCapability(techSpaceName, ParseSchemaCapability.class);
        }
    }

    public SerializeSchemaCapability schemaWriter(String techSpaceName) {
        if (schemaWriters.containsKey(techSpaceName)) {
            return schemaWriters.get(techSpaceName);
        } else {
            throw CorrLangException.missingTechSpaceCapability(techSpaceName, SerializeSchemaCapability.class);
        }
    }

    public static TechSpaceRegistry newRegistry() {
        return new TechSpaceRegistry();
    }

}
