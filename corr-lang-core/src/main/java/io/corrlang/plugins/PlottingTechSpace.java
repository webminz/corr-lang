package io.corrlang.plugins;

import io.corrlang.domain.Data;
import io.corrlang.domain.QueryHandler;
import io.corrlang.domain.Sys;
import no.hvl.past.di.PropertyHolder;
import no.hvl.past.graph.GraphMorphism;
import io.corrlang.plugins.dm.PlantUMLPlotter;
import no.hvl.past.names.Name;
import no.hvl.past.MetaRegistry;
import no.hvl.past.UnsupportedFeatureException;

import io.corrlang.plugins.techspace.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.stream.Stream;

public class PlottingTechSpace implements TechSpace {

    public static final String PLOTTING_CONFIG_ARG_DRAW_DIAGRAMS = "plot.draw.diagrams";
    public static final String PLOTTING_CONFIG_ARG_DRAW_SERVICES = "plot.draw.services";


    public static final PlottingTechSpace IMAGE = new PlottingTechSpace();



    private PlottingTechSpace() {
    }

    @Override
    public String ID() {
        return "IMAGE";
    }

    public static final class AdapterFactory implements TechSpaceAdapterFactory<PlottingTechSpace> {

        @Autowired
        MetaRegistry pluginRegistry;

        @Autowired
        PropertyHolder propertyHolder;

        private final PlottingTechSpace techSpace;

        public AdapterFactory(PlottingTechSpace techSpace) {
            this.techSpace = techSpace;
        }

        @Override
        public void doSetUp() {
        }

        @Override
        public TechSpaceAdapter<PlottingTechSpace> createAdapter() {
            PlantUMLPlotter plotter = new PlantUMLPlotter(
                    propertyHolder.getBooleanProperty(PLOTTING_CONFIG_ARG_DRAW_DIAGRAMS),
                    Boolean.parseBoolean(propertyHolder.getPropertyAndSetDefaultIfNecessary(PLOTTING_CONFIG_ARG_DRAW_SERVICES, "true")));
            return new Adapter(plotter, techSpace);
        }

        @Override
        public void prepareShutdown() {
        }


        @PostConstruct
        public void setUp() {
            pluginRegistry.register(techSpace.ID(),techSpace);
            pluginRegistry.register(techSpace.ID(), this);
        }
    }


    public static class Adapter implements TechSpaceAdapter<PlottingTechSpace>, TechSpaceDirective {

        private final PlantUMLPlotter plotter;
        private final PlottingTechSpace techSpace;

        public Adapter(PlantUMLPlotter plotter, PlottingTechSpace techSpace) {
            this.plotter = plotter;
            this.techSpace = techSpace;
        }

        @Override
        public TechSpaceDirective directives() {
            return this;
        }

        @Override
        public Sys parseSchema(Name name, String locationURI) throws UnsupportedFeatureException {
            throw new UnsupportedFeatureException();
        }

        @Override
        public void writeSchema(Sys sys, OutputStream outputStream) throws TechSpaceException {
            try {
                plotter.plot(sys, outputStream);
            } catch (IOException e) {
                throw new TechSpaceException(e, techSpace);
            }
        }

        @Override
        public QueryHandler queryHandler(Sys system) throws TechSpaceException, UnsupportedFeatureException {
            throw new UnsupportedFeatureException();
        }

        @Override
        public Data readInstance(Sys system, InputStream inputStream) throws UnsupportedFeatureException {
            throw new UnsupportedFeatureException();
        }

        @Override
        public void writeInstance(Sys system, GraphMorphism instance, OutputStream outputStream) throws TechSpaceException, UnsupportedFeatureException {
            throw new UnsupportedFeatureException(); // TODO add support for this
        }

        @Override
        public Stream<StringTypeDescription> stringDataType() {
            return Stream.empty();
        }

        @Override
        public Stream<BaseTypeDescription> boolDataType() {
            return Stream.empty();
        }

        @Override
        public Stream<IntTypeDescription> integerDataType() {
            return Stream.empty();
        }

        @Override
        public Stream<FloatTypeDescription> floatingPointDataType() {
            return Stream.empty();
        }

        @Override
        public Stream<CustomBaseTypeDescription> otherDataTypes() {
            return Stream.empty();
        }

        @Override
        public void additionalTechnologySpecificRules(TechnologySpecificRules configure) {

        }

    }


}
