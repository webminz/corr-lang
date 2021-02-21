package no.hvl.past.corrlang.goals;

import no.hvl.past.corrlang.domainmodel.traverser.TraverserFacade;
import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.di.DependencyInjectionContainer;
import no.hvl.past.corrlang.reporting.ReportFacade;

public abstract class LanguageGoal extends AbstractGoal {

    private final SyntacticalResult model;
    private final TraverserFacade traverserFacade;

    public LanguageGoal(ReportFacade reportFacade,
                        String goalName,
                        String shortDesc,
                        DependencyInjectionContainer container,
                        SyntacticalResult model,
                        TraverserFacade traverserFacade) {
        super(reportFacade, shortDesc, goalName, container);
        this.model = model;
        this.traverserFacade = traverserFacade;
    }

    public SyntacticalResult getModel() {
        return model;
    }

    public TraverserFacade getTraverserFacade() {
        return traverserFacade;
    }
}
