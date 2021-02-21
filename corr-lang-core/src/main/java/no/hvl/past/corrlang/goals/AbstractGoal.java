package no.hvl.past.corrlang.goals;

import no.hvl.past.di.DependencyInjectionContainer;
import no.hvl.past.corrlang.reporting.ReportFacade;
import org.apache.log4j.Logger;

public abstract class AbstractGoal {

    protected Logger logger = Logger.getLogger(getClass());

    private final ReportFacade reportFacade;
    private final String goalName;
    private final String shortDesc;
    private final DependencyInjectionContainer container;

    public AbstractGoal(ReportFacade reportFacade, String shortDesc, String goalName, DependencyInjectionContainer container) {
        this.reportFacade = reportFacade;
        this.shortDesc = shortDesc;
        this.goalName = goalName;
        this.container = container;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    protected ReportFacade getReportFacade() {
        return reportFacade;
    }

    public DependencyInjectionContainer getContainer() {
        return container;
    }

    public String getGoalName() {
        return goalName;
    }

    public void execute() {
        reportFacade.reportInfo(">>> Executing Goal " + goalName + " ...\n");
        try {
            doIt();
            reportFacade.reportInfo("\n>>> ...SUCCESS");
        } catch (Throwable cause) {
            logger.error(cause.getMessage(), cause);
            reportFacade.reportInfo("\n>>> ...FAILED");
            reportFacade.reportError(cause);
        }
    }

    protected abstract void doIt() throws Throwable;
}
