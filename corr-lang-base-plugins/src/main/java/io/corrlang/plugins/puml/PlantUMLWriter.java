package io.corrlang.plugins.puml;

import io.corrlang.techspaces.SchemaWriter;
import io.corrlang.techspaces.WriteSchemaCapabilities;
import org.springframework.core.env.Environment;

import java.io.OutputStream;
import java.util.Optional;

public class PlantUMLWriter implements WriteSchemaCapabilities {

    public static final String DRAW_DIAGRAMS_CONF = "plugins.puml.drawDiagrams";
    public static final String DRAW_SERVICES_CONF = "plugins.puml.drawServices";
    public static final String PUML_EXECUTABLE_CONF = "plugins.puml.executable";

    private final PlantUMLPlotter plotter;


    public PlantUMLWriter(Environment environment) {
        this.plotter = new PlantUMLPlotter(
                environment.getProperty(DRAW_DIAGRAMS_CONF, Boolean.class, false),
                environment.getProperty(DRAW_SERVICES_CONF, Boolean.class, true),
                environment.getProperty(DRAW_SERVICES_CONF, String.class, null)
        );
    }

    @Override
    public Optional<String> defaultFileEnding() {
        return Optional.of("puml");
    }

    @Override
    public SchemaWriter<OutputStream> serializeSchema() {
        return plotter::plot;
    }
}
