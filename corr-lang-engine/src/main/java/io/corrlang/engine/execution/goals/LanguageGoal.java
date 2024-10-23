package io.corrlang.engine.execution.goals;


import io.corrlang.engine.execution.AbstractExecutor;
import io.corrlang.engine.parser.SyntacticalResult;
import io.corrlang.engine.domainmodel.CorrSpec;
import io.corrlang.engine.domainmodel.Goal;
import io.corrlang.engine.execution.traverser.CreateFormalAlignmentTraverser;
import no.hvl.past.UnsupportedFeatureException;
import io.corrlang.plugins.techspace.TechSpace;
import io.corrlang.plugins.techspace.TechSpaceAdapter;
import io.corrlang.plugins.techspace.TechSpaceException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Set;

public abstract class LanguageGoal extends AbstractGoal {

    private String goalName;


    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }



    @Override
    protected void executeTransitive(SyntacticalResult syntaxGraph) throws Throwable {
        Goal goal = syntaxGraph.getGoalWithName(goalName).get();
        switch (goal.getAction()) {
            case FEDERATION:
                executeFederation(goal.getCorrespondence(), goal.getAdapter());
                break;
            case MATCH:
                executeMatch(goal.getCorrespondence(), goal.getAdapter());
                break;
            case SCHEMA:
                executeSchema(goal.getCorrespondence(), goal.getAdapter());
                break;
            case VERIFY:
                executeVerify(goal.getCorrespondence(), goal.getAdapter());
                break;
            case SYNCHRONIZE:
                executeSynchronize(goal.getCorrespondence(), goal.getAdapter());
                break;
            case TRANSFORMATION:
                executeTransformation(goal.getCorrespondence(), goal.getAdapter());
                break;
        }
    }

    protected abstract void executeTransformation(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace);

    protected abstract void executeSynchronize(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace);

    protected abstract void executeVerify(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) throws UnsupportedFeatureException, TechSpaceException, IOException, URISyntaxException;

    protected abstract void executeSchema(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) throws TechSpaceException, UnsupportedFeatureException, IOException;

    protected abstract void executeMatch(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace);

    protected abstract void executeFederation(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) throws TechSpaceException, UnsupportedFeatureException;


    @Override
    public String getKey() {
        return goalName;
    }

    public LanguageGoal(String goalName) {
        super(goalName);
    }

    @Override
    public boolean isSystemGoal() {
        return false;
    }

    @Override
    public Set<Class<? extends AbstractExecutor>> dependsOn() {
        return Collections.singleton(CreateFormalAlignmentTraverser.class);
    }
}
