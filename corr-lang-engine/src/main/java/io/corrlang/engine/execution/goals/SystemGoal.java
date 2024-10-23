package io.corrlang.engine.execution.goals;

import io.corrlang.engine.execution.AbstractExecutor;

import java.util.Collections;
import java.util.Set;

public abstract class SystemGoal extends AbstractGoal {

    protected SystemGoal(String goalName) {
        super(goalName);
    }

    @Override
    public boolean isSystemGoal() {
        return true;
    }

    @Override
    public boolean isBatchGoal() {
        return false;
    }

    @Override
    public boolean isServerGoal() {
        return false;
    }

    @Override
    public boolean isCodegenGoal() {
        return false;
    }

    @Override
    public boolean isFileGoal() {
        return false;
    }



    @Override
    public Set<Class<? extends AbstractExecutor>> dependsOn() {
        return Collections.emptySet();
    }
}
