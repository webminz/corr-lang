package no.hvl.past.corrlang.goals;

import no.hvl.past.di.DependencyInjectionContainer;
import no.hvl.past.corrlang.reporting.ReportFacade;

public class InfoGoal extends SystemGoal {

    static final String GOAL_NAME = "INFO";
    private static final String DESC = "Prints information about the current runtime, e.g. installed plugins etc.";


    InfoGoal(ReportFacade reportFacade, DependencyInjectionContainer container) {
        super(reportFacade, GOAL_NAME, DESC, container);
    }

    @Override
    protected void doIt() throws Throwable {
        getReportFacade().reportInfo(getContainer().getPropertyHolder().reportContent());
        getReportFacade().reportInfo(getContainer().getPluginRegistry().printPluginInfo());
    }
}
