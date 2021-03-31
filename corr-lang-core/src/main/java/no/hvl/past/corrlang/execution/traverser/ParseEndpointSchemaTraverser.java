package no.hvl.past.corrlang.execution.traverser;

import no.hvl.past.corrlang.domainmodel.Endpoint;
import no.hvl.past.corrlang.domainmodel.FileEndpoint;
import no.hvl.past.corrlang.domainmodel.ServerEndpoint;
import no.hvl.past.corrlang.execution.AbstractExecutor;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.corrlang.reporting.ReportErrorType;
import no.hvl.past.graph.Sketch;
import no.hvl.past.names.Name;
import no.hvl.past.plugin.UnsupportedFeatureException;
import no.hvl.past.systems.Sys;
import no.hvl.past.techspace.TechSpace;
import no.hvl.past.techspace.TechSpaceAdapter;
import no.hvl.past.techspace.TechSpaceException;

import java.util.Collections;
import java.util.Set;

public class ParseEndpointSchemaTraverser extends AbstractTraverser {

    public ParseEndpointSchemaTraverser() {
        super("ParseEndpointSchema");
    }

    @Override
    public void handleEndpoint(Endpoint endpoint) throws Throwable {
        TechSpaceAdapter<? extends TechSpace> techSpaceAdapter = endpoint.getAdaptor().get();
        Sys result = techSpaceAdapter.parseSchema(
                Name.identifier(endpoint.getName()),
                endpoint.getSchemaURL().isPresent() ? endpoint.getSchemaURL().get().getUrl() : endpoint.getLocationURL().getUrl());
        endpoint.setSystem(result);
    }

    @Override
    public Set<Class<? extends AbstractExecutor>> dependsOn() {
        return Collections.singleton(LoadTechSpaceTraverser.class);
    }

}
