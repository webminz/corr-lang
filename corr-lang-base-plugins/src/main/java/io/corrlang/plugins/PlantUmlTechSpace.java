package io.corrlang.plugins;

import io.corrlang.plugins.puml.PlantUMLPlotter;
import io.corrlang.techspaces.*;
import no.hvl.past.graph.Universe;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;


@Configuration
public class PlantUmlTechSpace implements TechSpace {

    public static final String SHOW_DIAGRAMS_PROPERTY = "showDiagrams";
    public static final String SHOW_ACTIONS_PROPERTY = "showActions";

    public static final String PUML_EXECUTABLE_PROPERTY = "pumlExecutable";

    public static final String INVOKE_PUML_PROPERTY = "invokePuml";

    public static final String ID = "PUML";

    private final PlantUMLPlotter writeSchemaCapabilities;


    public PlantUmlTechSpace() {
        this.writeSchemaCapabilities = new PlantUMLPlotter();
    }

    @Override
    public String name() {
        return ID;
    }


    @Override
    public Set<TechSpaceCapability> initialize(Universe universe, Map<String, Object> configProperties) {
        if (configProperties.containsKey(SHOW_DIAGRAMS_PROPERTY)) {
            this.writeSchemaCapabilities.setPrintDiagrams(Boolean.parseBoolean(configProperties.get(SHOW_DIAGRAMS_PROPERTY).toString()));
        }
        if (configProperties.containsKey(SHOW_ACTIONS_PROPERTY)) {
            this.writeSchemaCapabilities.setDrawServices(Boolean.parseBoolean(configProperties.get(SHOW_ACTIONS_PROPERTY).toString()));
        }
        if (configProperties.containsKey(INVOKE_PUML_PROPERTY)) {
            this.writeSchemaCapabilities.setInvokePuml(Boolean.parseBoolean(configProperties.get(INVOKE_PUML_PROPERTY).toString()));
        }
        if (configProperties.containsKey(PUML_EXECUTABLE_PROPERTY)) {
            this.writeSchemaCapabilities.setPumlExecutable(configProperties.get(SHOW_DIAGRAMS_PROPERTY).toString());
        }


        return Set.of(this.writeSchemaCapabilities);
    }

    @Override
    public void prepareShutdown() {
        // nothing to do
    }
}
