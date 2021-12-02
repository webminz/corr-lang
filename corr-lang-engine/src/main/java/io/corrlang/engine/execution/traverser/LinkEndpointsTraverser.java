package io.corrlang.engine.execution.traverser;

import io.corrlang.engine.domainmodel.*;
import io.corrlang.engine.execution.AbstractExecutor;
import io.corrlang.engine.parser.SyntacticalResult;
import io.corrlang.engine.reporting.ReportErrorType;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class LinkEndpointsTraverser extends AbstractTraverser {

    private SyntacticalResult domainModel;

    public LinkEndpointsTraverser() {
        super("CorrespondencesToEndpointsLinker");
    }

    @Override
    public Set<Class<? extends AbstractExecutor>> dependsOn() {
        return Collections.singleton(DesugarAliases.class);
    }


    @Override
    public void preBlock(SyntacticalResult domainModel, String... args) throws Throwable {
        this.domainModel = domainModel;
    }

    @Override
    public void handle(CorrSpec corrSpec) throws Exception {
        for (String endpointName : corrSpec.getEndpointsList()) {
            Optional<Endpoint> endpointWithName = domainModel.getEndpointWithName(endpointName);
            if (endpointWithName.isPresent()) {
                corrSpec.addEndpointRef(endpointName, endpointWithName.get());
            } else {
                throw new LanguageException(corrSpec, ReportErrorType.SEMANTICS, "The referenced endpoint '" + endpointName + "' does not exist");
            }
        }
    }

    @Override
    public void handle(ElementRef ref) throws Throwable {
        Optional<Endpoint> endpointWithName = domainModel.getEndpointWithName(ref.getEndpointName());
        if (endpointWithName.isPresent()) {
            ref.setEndpoint(endpointWithName.get());
        } else {
            throw new LanguageException(ref, ReportErrorType.SEMANTICS, "The referenced endpoint '" + ref.getEndpointName() + "' does not exist");
        }
    }

    @Override
    public void handle(Goal goal) throws Throwable {
        String correspondence = goal.getCorrespondenceName();
        Optional<CorrSpec> corrSpecWithName = domainModel.getCorrSpecWithName(correspondence);
        if (corrSpecWithName.isPresent()) {
            goal.linkCorrespondence(corrSpecWithName.get());
        } else {
            throw new LanguageException(goal, ReportErrorType.SEMANTICS, "The referenced correspondence '" + correspondence + "' does not exist");
        }
    }
}
