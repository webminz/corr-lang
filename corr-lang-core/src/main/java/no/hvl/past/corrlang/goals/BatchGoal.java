package no.hvl.past.corrlang.goals;

import no.hvl.past.corrlang.domainmodel.traverser.TraverserFacade;
import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.di.DependencyInjectionContainer;
import no.hvl.past.corrlang.reporting.ReportFacade;


public abstract class BatchGoal extends LanguageGoal {

    public BatchGoal(ReportFacade reportFacade,
                     String goalName,
                     String shortDesc,
                     DependencyInjectionContainer container,
                     SyntacticalResult model,
                     TraverserFacade traverserFacade) {
        super(reportFacade, shortDesc, goalName, container, model, traverserFacade);
    }
}
