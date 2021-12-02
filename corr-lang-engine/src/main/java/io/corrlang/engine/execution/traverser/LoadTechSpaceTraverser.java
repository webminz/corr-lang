package io.corrlang.engine.execution.traverser;

import io.corrlang.engine.domainmodel.Endpoint;
import io.corrlang.engine.domainmodel.Goal;
import io.corrlang.engine.domainmodel.LanguageException;
import io.corrlang.engine.execution.AbstractExecutor;
import io.corrlang.engine.reporting.ReportErrorType;
import no.hvl.past.MetaRegistry;
import io.corrlang.plugins.techspace.TechSpace;
import io.corrlang.plugins.techspace.TechSpaceAdapterFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
@SuppressWarnings("unchecked") // Java generics are broken
public class LoadTechSpaceTraverser extends AbstractTraverser {

    @Autowired
    private MetaRegistry metaRegistry;

    public LoadTechSpaceTraverser() {
        super("LoadTechSpace");
    }

    @Override
    public void handleEndpoint(Endpoint endpoint) throws Throwable {
        TechSpace techSpace = endpoint.getTechSpace().get();
        Optional<TechSpaceAdapterFactory> extension = metaRegistry.getExtension(techSpace.ID(), TechSpaceAdapterFactory.class);
        if (extension.isPresent()) {
            endpoint.setAdaptor(extension.get().createAdapter());
        } else {
            throw new LanguageException(endpoint, ReportErrorType.PLUGIN, "Technology " + techSpace.ID() + " does not provide an adapter!");
        }
    }

    @Override
    public Set<Class<? extends AbstractExecutor>> dependsOn() {
        return Collections.singleton(IdentifyTechSpaceTraverser.class);
    }

    @Override
    public void handle(Goal goal) throws Throwable {
        TechSpace techSpace = goal.getTechSpace();
        if (techSpace != null) {
            Optional<TechSpaceAdapterFactory> extension = metaRegistry.getExtension(techSpace.ID(), TechSpaceAdapterFactory.class);
            if (extension.isPresent()) {
                goal.linkTechSpaceAdapter(extension.get().createAdapter());
            } else {
                throw new LanguageException(goal, ReportErrorType.PLUGIN, "Technology " + techSpace.ID() + " does not provide an adapter!");
            }
        }
    }
}
