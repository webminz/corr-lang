package io.corrlang.engine.execution.traverser;

import io.corrlang.engine.domainmodel.Endpoint;
import io.corrlang.engine.execution.AbstractExecutor;
import no.hvl.past.util.FileSystemAccessPoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Set;

public class RetrieveUrls extends AbstractTraverser {

    @Autowired
    FileSystemAccessPoint fileSystemUtils;

    public RetrieveUrls() {
        super("RetrieveUrls");
    }

    @Override
    public Set<Class<? extends AbstractExecutor>> dependsOn() {
        return Collections.emptySet();
    }

    @Override
    public void handleEndpoint(Endpoint endpoint) throws Throwable {
        endpoint.getLocationURL().retrieve(fileSystemUtils);
        if (endpoint.getSchemaURL().isPresent()) {
            endpoint.getSchemaURL().get().retrieve(fileSystemUtils);
        }
    }
}
