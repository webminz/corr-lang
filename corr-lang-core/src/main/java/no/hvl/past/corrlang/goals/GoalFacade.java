package no.hvl.past.corrlang.goals;

import no.hvl.past.corrlang.domainmodel.traverser.TraverserFacade;
import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.di.DependencyInjectionContainer;
import no.hvl.past.corrlang.reporting.ReportFacade;

import java.util.LinkedHashMap;
import java.util.Map;

public class GoalFacade {

    private interface GoalFactory<G extends AbstractGoal> {

        G create(DependencyInjectionContainer diContainer,
                 ReportFacade reportFacade,
                 SyntacticalResult syntacticalResult,
                 TraverserFacade traverserFacade,
                 String argument);

    }

    private final Map<String, Class<? extends AbstractGoal>> goalTypes;
    private final Map<Class<? extends AbstractGoal>, GoalFactory<? extends AbstractGoal>> goalFactories;

    public GoalFacade() {
        this.goalTypes = new LinkedHashMap<>();
        this.goalFactories = new LinkedHashMap<>();

        registerGoalFactoris();
    }

    private void registerGoalFactoris() {
        this.goalTypes.put(InfoGoal.GOAL_NAME, InfoGoal.class);
        this.goalFactories.put(InfoGoal.class, new GoalFactory<AbstractGoal>() {
            @Override
            public AbstractGoal create(DependencyInjectionContainer diContainer,
                                       ReportFacade reportFacade,
                                       SyntacticalResult syntacticalResult,
                                       TraverserFacade traverserFacade,
                                       String argument) {
                return new InfoGoal(reportFacade, diContainer);
            }
        });
        this.goalTypes.put(FederationRuntime.GOAL_NAME, FederationRuntime.class);
        this.goalFactories.put(FederationRuntime.class, new GoalFactory<AbstractGoal>() {
            @Override
            public AbstractGoal create(
                    DependencyInjectionContainer diContainer,
                    ReportFacade reportFacade,
                    SyntacticalResult syntacticalResult,
                    TraverserFacade traverserFacade, String argument) {
                return new FederationRuntime(reportFacade, diContainer, syntacticalResult, traverserFacade, argument);
            }
        });
    }

    public boolean isKnownGoal(String goalName) {
        return this.goalTypes.containsKey(goalName);
    }

    public boolean isSystemGoal(String goalName) {
        return isKnownGoal(goalName) && SystemGoal.class.isAssignableFrom(this.goalTypes.get(goalName));
    }

    public void runGoal(
            String goalName,
            String goalArgument,
            DependencyInjectionContainer diContainer,
            ReportFacade reportFacade,
            SyntacticalResult syntacticalResult,
            TraverserFacade traverserFacade) {
        this.goalFactories.get(goalTypes.get(goalName)).create(diContainer, reportFacade, syntacticalResult, traverserFacade, goalArgument).execute();
    }



}
