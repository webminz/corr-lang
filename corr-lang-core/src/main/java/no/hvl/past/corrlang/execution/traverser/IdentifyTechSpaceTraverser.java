package no.hvl.past.corrlang.execution.traverser;

import no.hvl.past.corrlang.domainmodel.*;
import no.hvl.past.corrlang.execution.AbstractExecutor;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.corrlang.reporting.ReportErrorType;
import no.hvl.past.plugin.MetaRegistry;
import no.hvl.past.techspace.TechSpace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class IdentifyTechSpaceTraverser extends AbstractTraverser {

    @Autowired
    private MetaRegistry metaRegistry;

    public IdentifyTechSpaceTraverser() {
        super("IdentifyTechSpace");
    }

    @Override
    public Set<Class<? extends AbstractExecutor>> dependsOn() {
        return Collections.emptySet();
    }

    @Override
    public void handleEndpoint(Endpoint endpoint) throws Throwable {
        String technology = endpoint.getTechnology();
        Optional<TechSpace> techSpace = metaRegistry.getExtension(technology, TechSpace.class);
        if (techSpace.isPresent()) {
            endpoint.setTechSpace(techSpace.get());
        } else {
            throw new LanguageException(endpoint, ReportErrorType.PLUGIN, "Technology '" + endpoint.getTechnology() + "' is not supported");
        }
    }

    @Override
    public void handle(Goal goal) throws Throwable {
        String technology = goal.getTechnology();
        Optional<TechSpace> techSpace = metaRegistry.getExtension(technology, TechSpace.class);
        if (techSpace.isPresent()) {
            goal.linkTechSpace(techSpace.get());
        } else {
            throw new LanguageException(goal, ReportErrorType.PLUGIN, "Technology '" + goal.getTechnology() + "' is not supported");
        }
    }
}
