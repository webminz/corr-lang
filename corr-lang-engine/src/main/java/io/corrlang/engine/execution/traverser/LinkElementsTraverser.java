package io.corrlang.engine.execution.traverser;

import com.google.common.collect.Sets;
import io.corrlang.engine.domainmodel.ElementRef;
import io.corrlang.engine.domainmodel.LanguageException;
import io.corrlang.engine.execution.AbstractExecutor;
import io.corrlang.engine.reporting.ReportErrorType;
import no.hvl.past.graph.elements.Triple;
import io.corrlang.domain.Sys;

import java.util.Optional;
import java.util.Set;

public class LinkElementsTraverser extends AbstractTraverser {

    public LinkElementsTraverser() {
        super("LinkElementRefs");
    }

    @Override
    public Set<Class<? extends AbstractExecutor>> dependsOn() {
        return Sets.newHashSet(
                ParseEndpointSchemaTraverser.class,
                LinkEndpointsTraverser.class
        );
    }


    @Override
    public void handle(ElementRef ref) throws Throwable {
        if (!ref.getElement().isPresent()) {
            Sys endpoint = ref.getEndpoint().getSystem().get();
            Optional<Triple> lookup = ref.lookup(endpoint);
            if (!lookup.isPresent()) {
                throw new LanguageException(ref, ReportErrorType.SEMANTICS, "The element '" + ref.toString()+ "' does not exist in " + ref.getEndpointName());
            }
        }
    }


}

