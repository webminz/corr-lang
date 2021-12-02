package io.corrlang.engine.execution.goals;

import io.corrlang.engine.parser.SyntacticalResult;
import io.corrlang.engine.reporting.ReportFacade;
import no.hvl.past.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class ParseGoal extends SystemGoal {

    public static final String GOAL_NAME = "Parse";

    @Autowired
    private ReportFacade reportFacade;

    public ParseGoal() {
        super(GOAL_NAME);
    }

    @Override
    protected void executeTransitive(SyntacticalResult syntaxGraph) throws Throwable {
        reportFacade.reportInfo("No syntax errors!\n\nRESULTS:\n");
        reportFacade.reportInfo("Endpoints:\n");
        syntaxGraph.allEndpoints().forEach(ep -> {
            reportFacade.reportInfo(ep.getName() + " at " + ep.getLocationURL().getUrl());
        });
        reportFacade.reportInfo("\nCorrespondences:\n");
        syntaxGraph.allSpecs().forEach(spec -> {
            reportFacade.reportInfo(spec.getName() + "(" + StringUtils.fuseList(spec.getEndpointsList().stream(), ", ") + ")");
        });
        reportFacade.reportInfo("\nRules:\n");
        syntaxGraph.allRules().forEach(rule -> {
            reportFacade.reportInfo(rule.getName());
        });
        reportFacade.reportInfo("\nGoals:\n");
        syntaxGraph.allGoals().forEach(goal -> {
            reportFacade.reportInfo(goal.getName());
        });
    }
}
