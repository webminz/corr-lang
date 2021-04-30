package no.hvl.past.corrlang.execution.goals;


import no.hvl.past.corrlang.domainmodel.CorrSpec;
import no.hvl.past.corrlang.domainmodel.Goal;
import no.hvl.past.corrlang.execution.AbstractExecutor;
import no.hvl.past.corrlang.execution.traverser.CreateFormalAlignmentTraverser;
import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.plugin.UnsupportedFeatureException;
import no.hvl.past.techspace.TechSpace;
import no.hvl.past.techspace.TechSpaceAdapter;
import no.hvl.past.techspace.TechSpaceException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Optional;
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
