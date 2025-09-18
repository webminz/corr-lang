package io.corrlang.engine.execution.traverser;

import com.google.common.collect.Sets;
import io.corrlang.engine.execution.AbstractExecutor;
import no.hvl.past.names.Name;
import io.corrlang.domain.Endpoint;
import io.corrlang.techspaces.TechSpace;
import io.corrlang.techspaces.TechSpaceAdapter;

import java.util.Set;

public class ParseEndpointSchemaTraverser extends AbstractTraverser {

    public ParseEndpointSchemaTraverser() {
        super("ParseEndpointSchema");
    }

    @Override
    public void handleEndpoint(io.corrlang.engine.domainmodel.Endpoint endpoint) throws Throwable {
        TechSpaceAdapter<? extends TechSpace> techSpaceAdapter = endpoint.getAdaptor().get();
        Endpoint result = techSpaceAdapter.parseSchema(
                Name.identifier(endpoint.getName()),
                endpoint.getSchemaURL().isPresent() ? endpoint.getSchemaURL().get().getUrl() : endpoint.getLocationURL().getUrl());
        endpoint.setSystem(result);
    }

    @Override
    public Set<Class<? extends AbstractExecutor>> dependsOn() {
        return Sets.newHashSet(LoadTechSpaceTraverser.class, RetrieveUrls.class);
    }

}
