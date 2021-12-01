package no.hvl.past.corrlang.execution.traverser;

import no.hvl.past.corrlang.domainmodel.*;
import no.hvl.past.corrlang.execution.AbstractExecutor;
import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.corrlang.reporting.ReportErrorType;
import no.hvl.past.MetaRegistry;
import io.corrlang.plugins.techspace.TechSpace;
import io.corrlang.plugins.techspace.TechSpaceAdapterFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class IdentifyTechSpaceTraverser extends AbstractTraverser {

    private Set<TechSpace> discoveredTechSpaces = new HashSet<>();

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
            discoveredTechSpaces.add(techSpace.get());
        } else {
            throw new LanguageException(endpoint, ReportErrorType.PLUGIN, "Technology '" + endpoint.getTechnology() + "' is not supported");
        }
    }

    @Override
    public void handle(Goal goal) throws Throwable {
        String technology = goal.getTechnology();
        if (technology != null) {
            Optional<TechSpace> techSpace = metaRegistry.getExtension(technology, TechSpace.class);
            if (techSpace.isPresent()) {
                goal.linkTechSpace(techSpace.get());
                discoveredTechSpaces.add(techSpace.get());
            } else {
                throw new LanguageException(goal, ReportErrorType.PLUGIN, "Technology '" + goal.getTechnology() + "' is not supported");
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void postBlock(SyntacticalResult domainModel, String... args) throws Throwable {
        for (TechSpace ts : discoveredTechSpaces) {
            Optional<TechSpaceAdapterFactory> extension = metaRegistry.getExtension(ts.ID(), TechSpaceAdapterFactory.class);
            if (extension.isPresent()) {
                extension.get().doSetUp();
            }
        }
    }
}
