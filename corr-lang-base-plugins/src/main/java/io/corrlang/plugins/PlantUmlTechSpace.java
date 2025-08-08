package io.corrlang.plugins;

import io.corrlang.plugins.puml.PlantUMLWriter;
import io.corrlang.techspaces.TechSpace;
import io.corrlang.techspaces.TechSpaceRegistry;
import io.corrlang.techspaces.WriteSchemaCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;


@Configuration
public class PlantUmlTechSpace implements TechSpace {

    public static final String ID = "PUML";

    private final TechSpaceRegistry registry;

    private final WriteSchemaCapabilities writeSchemaCapabilities;


    public PlantUmlTechSpace(@Autowired TechSpaceRegistry registry, @Autowired Environment environment) {
        this.registry = registry;
        this.writeSchemaCapabilities = new PlantUMLWriter(environment);
    }

    @Override
    public String ID() {
        return ID;
    }

    @PostConstruct
    public void init() {
        registry.register(ID(), writeSchemaCapabilities );
    }
}
