package no.hvl.past.corrlang.domainmodel.traverser;

import no.hvl.past.corrlang.domainmodel.*;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.corrlang.reporting.ReportErrorType;
import no.hvl.past.plugin.MetaRegistry;
import no.hvl.past.techspace.TechSpace;
import no.hvl.past.techspace.TechSpaceAdapterFactory;

import java.util.Collections;
import java.util.Optional;

public class LoadTechSpaceTraverser extends AbstractTraverser {

    private final MetaRegistry metaRegistry;

    public LoadTechSpaceTraverser(
            ReportFacade reportFacade,
            MetaRegistry metaRegistry) {
        super(reportFacade, Collections.singleton(IdentifyTechSpaceTraverser.class));
        this.metaRegistry = metaRegistry;
    }

    @Override
    public void handle(FileEndpoint fileEndpoint) {
        handleEP(fileEndpoint);
    }


    @Override
    public void handle(ServerEndpoint serverEndpoint) {
        handleEP(serverEndpoint);
    }

    private void handleEP(Endpoint endpoint) {
        TechSpace techSpace = endpoint.getTechSpace().get();
        Optional<TechSpaceAdapterFactory> extension = metaRegistry.getExtension(techSpace.ID(), TechSpaceAdapterFactory.class);
        if (extension.isPresent()) {
            endpoint.setAdaptor(extension.get().createAdapter());
        } else {
            getReportFacade().reportError(ReportErrorType.PLUGIN, endpoint, "Technology " + techSpace.ID() + " does not provide an adapter!");
            failed();
        }
    }
}
