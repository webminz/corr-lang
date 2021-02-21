package no.hvl.past.corrlang.domainmodel.traverser;

import no.hvl.past.corrlang.domainmodel.Endpoint;
import no.hvl.past.corrlang.domainmodel.FileEndpoint;
import no.hvl.past.corrlang.domainmodel.ServerEndpoint;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.corrlang.reporting.ReportErrorType;
import no.hvl.past.plugin.MetaRegistry;
import no.hvl.past.techspace.TechSpace;

import java.util.Collections;
import java.util.Optional;

public class IdentifyTechSpaceTraverser extends AbstractTraverser {

    private final MetaRegistry metaRegistry;

    public IdentifyTechSpaceTraverser(ReportFacade reportFacade,
                                      MetaRegistry metaRegistry) {
        super(reportFacade, Collections.emptySet());
        this.metaRegistry = metaRegistry;
    }

    private void handleEP(Endpoint endpoint) {
        String technology = endpoint.getTechnology();
        Optional<TechSpace> techSpace = metaRegistry.getExtension(technology, TechSpace.class);
        if (techSpace.isPresent()) {
            endpoint.setTechSpace(techSpace.get());
        } else {
            getReportFacade().reportError(ReportErrorType.PLUGIN, endpoint, "Technology '" + technology + "' not known/supported!");
            failed();
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
