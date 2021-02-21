package no.hvl.past.corrlang.domainmodel.traverser;

import no.hvl.past.corrlang.domainmodel.*;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.util.StreamExt;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


public abstract class AbstractTraverser implements Visitor {

    private final ReportFacade reportFacade;
    private final Set<Class<? extends AbstractTraverser>> dependsOn;
    private SyntacticalResult currentSyntacticalResult;
    private boolean isExecuted = false;
    private boolean errorsFound = false;

    public AbstractTraverser(ReportFacade reportFacade, Set<Class<? extends AbstractTraverser>> dependsOn) {
        this.reportFacade = reportFacade;
        this.dependsOn = dependsOn;
    }

    protected void failed() {
        this.errorsFound = true;
    }

    public Set<Class<? extends AbstractTraverser>> getDependsOn() {
        return dependsOn;
    }

    protected ReportFacade getReportFacade() {
        return reportFacade;
    }

    protected SyntacticalResult getCurrentSyntacticalResult() {
        return currentSyntacticalResult;
    }

    public void run(SyntacticalResult syntacticalResult, Set<AbstractTraverser> alreadyExecuted) {
        this.currentSyntacticalResult = syntacticalResult;
        // TODO make imports a language element
            for (Endpoint ep : syntacticalResult.allEndpoints()) {
                ep.accept(this);
            }
            for (ConsistencyRule rule : syntacticalResult.allRules()) {
                rule.accept(this);
            }
            for (CorrSpec spec : syntacticalResult.allSpecs()) {
                spec.accept(this);
            }
            this.isExecuted = true;

    }

    public Set<Class<? extends AbstractTraverser>> dependenciesThatMustBeRun(Collection<AbstractTraverser> alreadyExecuted) {
        return dependsOn.stream().filter(dependency -> {
            return StreamExt.stream(alreadyExecuted).noneMatch(t -> dependency.isAssignableFrom(t.getClass()));
        }).collect(Collectors.toSet());
    }

    public boolean canRun(Collection<AbstractTraverser> alreadyExecuted) {
        return dependsOn.stream().allMatch(dependency -> {
            return StreamExt.stream(alreadyExecuted).filter(t -> dependency.isAssignableFrom(t.getClass())).allMatchAndNotEmpty(AbstractTraverser::isExecutedCorrectly);
        });
    }

    public boolean isExecutedCorrectly() {
        return isExecuted && !errorsFound;
    }

    public boolean isExecuted() {
        return isExecuted;
    }

    @Override
    public void handle(FileEndpoint fileEndpoint) {
    }

    @Override
    public void handle(ServerEndpoint serverEndpoint) {
    }

    @Override
    public void handle(CorrSpec corrSpec) {
    }

    @Override
    public void handle(ConsistencyRule consistencyRule) {
    }

    @Override
    public void handle(Relation relation) {
    }

    @Override
    public void handle(Synchronisation synchronisation) {
    }

    @Override
    public void handle(Identification identification) {
    }
}
