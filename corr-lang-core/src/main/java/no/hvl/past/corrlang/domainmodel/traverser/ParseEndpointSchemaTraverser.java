package no.hvl.past.corrlang.domainmodel.traverser;

import no.hvl.past.corrlang.domainmodel.Endpoint;
import no.hvl.past.corrlang.domainmodel.FileEndpoint;
import no.hvl.past.corrlang.domainmodel.ServerEndpoint;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.corrlang.reporting.ReportErrorType;
import no.hvl.past.graph.Sketch;
import no.hvl.past.names.Name;
import no.hvl.past.plugin.UnsupportedFeatureException;
import no.hvl.past.techspace.TechSpace;
import no.hvl.past.techspace.TechSpaceAdapter;
import no.hvl.past.techspace.TechSpaceException;

import java.util.Collections;

public class ParseEndpointSchemaTraverser extends AbstractTraverser {

    public ParseEndpointSchemaTraverser(ReportFacade reportFacade) {
        super(reportFacade, Collections.singleton(LoadTechSpaceTraverser.class));
    }

    private void handleEP(Endpoint endpoint) {
        TechSpaceAdapter<? extends TechSpace> techSpaceAdapter = endpoint.getAdaptor().get();
        try {
            Sketch result = techSpaceAdapter.parseSchema(Name.identifier(endpoint.getName()),
                    endpoint.getSchemaURL().isPresent() ? endpoint.getSchemaURL().get().getUrl() : endpoint.getLocationURL().getUrl());
            endpoint.setSchema(result);
        } catch (TechSpaceException | UnsupportedFeatureException e) {
            failed();
            getReportFacade().reportError(ReportErrorType.PLUGIN,e,endpoint);
        }
    }

    @Override
    public void handle(ServerEndpoint serverEndpoint) {
        handleEP(serverEndpoint);
    }

    @Override
    public void handle(FileEndpoint fileEndpoint) {
        handleEP(fileEndpoint);
    }
}
