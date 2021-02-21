package no.hvl.past.corrlang.domainmodel.traverser;

import com.google.common.collect.Sets;
import no.hvl.past.corrlang.domainmodel.*;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.corrlang.reporting.ReportErrorType;
import no.hvl.past.graph.Sketch;
import no.hvl.past.names.Name;

public class LinkElementsTraverser extends AbstractTraverser {

    public LinkElementsTraverser(ReportFacade reportFacade) {
        super(reportFacade, Sets.newHashSet(
                ParseEndpointSchemaTraverser.class,
                LinkEndpointsTraverser.class
        ));
    }

    private void handleComm(Commonality commonality) {
        for (ElementRef elementRef : commonality.getRelates()) {
            Endpoint endpoint = elementRef.getEndpoint();
            Sketch sketch = endpoint.getFormalSchemaRepresentation().get();
            if (sketch.carrier().get(Name.identifier(elementRef.getName())).isPresent()) {
                elementRef.setElement(sketch.carrier().get(Name.identifier(elementRef.getName())).get());
            } else if (sketch.carrier().get(Name.identifier(elementRef.getName()).prefixWith(Name.identifier(elementRef.getOwnerName()))).isPresent()) {
                elementRef.setElement(sketch.carrier().get(Name.identifier(elementRef.getName()).prefixWith(Name.identifier(elementRef.getOwnerName()))).get());
            } else {
                failed();
                getReportFacade().reportError(ReportErrorType.SEMANTICS, commonality, "The element '" + elementRef.getName() + "' does not exist in " + elementRef.getEndpointName());
            }
        }
    }

    @Override
    public void handle(Identification identification) {
        handleComm(identification);
    }

    @Override
    public void handle(Relation relation) {
        handleComm(relation);
    }

    @Override
    public void handle(Synchronisation synchronisation) {
        handleComm(synchronisation);
    }
}

