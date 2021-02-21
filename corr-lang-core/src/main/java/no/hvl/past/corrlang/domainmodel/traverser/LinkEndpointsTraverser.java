package no.hvl.past.corrlang.domainmodel.traverser;

import no.hvl.past.corrlang.domainmodel.*;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.corrlang.reporting.ReportErrorType;

import java.util.Collections;
import java.util.Optional;

public class LinkEndpointsTraverser extends AbstractTraverser {

    public LinkEndpointsTraverser(ReportFacade reportFacade) {
        super(reportFacade, Collections.emptySet());
    }

    @Override
    public void handle(CorrSpec corrSpec) {
        for (String endpointName : corrSpec.getEndpointsList()) {
            Optional<Endpoint> endpointWithName = getCurrentSyntacticalResult().getEndpointWithName(endpointName);
            if (endpointWithName.isPresent()) {
                corrSpec.addEndpointRef(endpointName, endpointWithName.get());
            } else {
                failed();
                getReportFacade().reportError(ReportErrorType.SEMANTICS,corrSpec,"The referenced endpoint '" + endpointName + "' does not exist");
            }
        }
    }


    @Override
    public void handle(Relation relation) {
        linkElementRefs(relation);
    }

    @Override
    public void handle(Synchronisation synchronisation) {
        linkElementRefs(synchronisation);
    }

    @Override
    public void handle(Identification identification) {
        linkElementRefs(identification);
    }

    public void linkElementRefs(Commonality commonality) {
        for (ElementRef elementRef : commonality.getRelates()) {
            Optional<Endpoint> endpointWithName = getCurrentSyntacticalResult().getEndpointWithName(elementRef.getName());
            if (endpointWithName.isPresent()) {
                elementRef.setEndpoint(endpointWithName.get());
            } else {
                failed();
                getReportFacade().reportError(ReportErrorType.SEMANTICS,commonality,"The referenced endpoint '" + elementRef.getEndpointName() + "' does not exist");
            }
        }
    }

}
