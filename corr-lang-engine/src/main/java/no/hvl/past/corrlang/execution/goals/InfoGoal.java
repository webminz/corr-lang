package no.hvl.past.corrlang.execution.goals;

import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.di.PropertyHolder;
import no.hvl.past.MetaRegistry;
import org.springframework.beans.factory.annotation.Autowired;

public class InfoGoal extends SystemGoal {

    @Autowired
    private PropertyHolder propertyHolder;
    @Autowired
    private MetaRegistry pluginRegistry;
    @Autowired
    private ReportFacade reportFacade;

    public static final String GOAL_NAME = "INFO";

    InfoGoal() {
        super(GOAL_NAME);
    }

    @Override
    protected void executeTransitive(SyntacticalResult syntacticalResult) {
        reportFacade.reportInfo(propertyHolder.reportContent());
        reportFacade.reportInfo(pluginRegistry.printPluginInfo());

    }


}
