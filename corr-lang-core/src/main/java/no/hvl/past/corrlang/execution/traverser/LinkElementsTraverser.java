package no.hvl.past.corrlang.execution.traverser;

import com.google.common.collect.Sets;
import no.hvl.past.corrlang.domainmodel.*;
import no.hvl.past.corrlang.execution.AbstractExecutor;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.corrlang.reporting.ReportErrorType;
import no.hvl.past.graph.Sketch;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.names.Name;
import no.hvl.past.systems.Sys;

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

