package no.hvl.past.corrlang.execution.traverser;

import com.google.common.collect.Sets;
import no.hvl.past.corrlang.domainmodel.Endpoint;
import no.hvl.past.corrlang.execution.AbstractExecutor;
import no.hvl.past.names.Name;
import io.corrlang.domain.Sys;
import io.corrlang.plugins.techspace.TechSpace;
import io.corrlang.plugins.techspace.TechSpaceAdapter;

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
        return Sets.newHashSet(LoadTechSpaceTraverser.class, RetrieveUrls.class);
    }

}
