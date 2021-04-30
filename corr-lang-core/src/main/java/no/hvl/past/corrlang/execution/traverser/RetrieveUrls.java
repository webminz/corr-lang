package no.hvl.past.corrlang.execution.traverser;

import no.hvl.past.corrlang.domainmodel.Endpoint;
import no.hvl.past.corrlang.execution.AbstractExecutor;
import no.hvl.past.util.FileSystemUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Set;

public class RetrieveUrls extends AbstractTraverser {

    @Autowired
    FileSystemUtils fileSystemUtils;

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
