package no.hvl.past.corrlang.goals;

import no.hvl.past.di.DependencyInjectionContainer;
import no.hvl.past.corrlang.reporting.ReportFacade;


public abstract class SystemGoal extends AbstractGoal {

    public SystemGoal(ReportFacade reportFacade, String goalName, String shortDesc, DependencyInjectionContainer container) {
        super(reportFacade, goalName, shortDesc, container);
    }
}
