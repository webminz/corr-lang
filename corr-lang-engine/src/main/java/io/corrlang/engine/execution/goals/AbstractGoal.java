package io.corrlang.engine.execution.goals;

import io.corrlang.engine.execution.AbstractExecutor;
import io.corrlang.engine.execution.ExecutionFacade;
import io.corrlang.engine.parser.SyntacticalResult;
import io.corrlang.engine.domainmodel.Goal;
import io.corrlang.engine.domainmodel.GoalTarget;
import io.corrlang.engine.domainmodel.URLReference;
import io.corrlang.di.DependencyInjectionContainer;
import no.hvl.past.util.Holder;

import java.io.File;
import java.net.URI;
import java.util.Optional;

public abstract class AbstractGoal extends AbstractExecutor {

    public static final String CURRENT_GOAL_PROPERTY = "goal.current";

    public AbstractGoal(String goalName) {
        super(goalName);
    }

    public abstract boolean isSystemGoal();

    public abstract boolean isBatchGoal();

    public abstract boolean isServerGoal();

    public abstract boolean isCodegenGoal();

    public abstract boolean isFileGoal();

    public static Holder<Object> runGoal(
            DependencyInjectionContainer diContainer,
            ExecutionFacade executor,
            SyntacticalResult syntaxResult,
            String goal) throws Throwable {
        Holder<Object> holder = new Holder<>();
        if (goal.equals(InfoGoal.GOAL_NAME)) {
            executor.execute(diContainer.getBean(InfoGoal.class), syntaxResult);
        } else if (goal.equals(HelpGoal.NAME)) {
            executor.execute(diContainer.getBean(HelpGoal.class), syntaxResult);
        } else if (goal.equals(ParseGoal.GOAL_NAME)) {
            executor.execute(diContainer.getBean(ParseGoal.class), syntaxResult);
        } else {
            diContainer.getPropertyHolder().setProperty(CURRENT_GOAL_PROPERTY, goal);
            Optional<Goal> goalWithName = syntaxResult.getGoalWithName(goal);
            if (goalWithName.isPresent()) {
                goalWithName.get().forTarget(new GoalTarget.Visitor() {
                    @Override
                    public void handle(GoalTarget.ServerRuntime serverRuntime) throws Exception {
                        ServerGoal server = diContainer.getBean(ServerGoal.class);
                        server.setContextPath(serverRuntime.getContextPath());
                        server.setPort(serverRuntime.getPort());
                        server.setGoalName(goal);
                        executor.execute(server, syntaxResult);
                    }

                    @Override
                    public void handle(GoalTarget.CodeGeneration codeGeneration) throws Exception {
                        CodegenGoal codegen = diContainer.getBean(CodegenGoal.class);
                        // TODO set codegen params
                        codegen.setGoalName(goal);
                        executor.execute(codegen, syntaxResult);
                    }

                    @Override
                    public void handle(GoalTarget.FileCreation fileCreation) throws Exception {
                        FileGoal file = diContainer.getBean(FileGoal.class);
                        file.setGoalName(goal);
                        // TODO check whether the file actually exists and give a good error message if not
                        URLReference location = fileCreation.getLocation();
                        location.retrieve(diContainer.getFSAccessPoint());
                        file.setFile(new File(new URI(location.getUrl())), fileCreation.isOverwrite());
                        executor.execute(file, syntaxResult);
                    }

                    @Override
                    public void handle(GoalTarget.Batch batch) throws Exception {
                        BatchGoal batchGoal = diContainer.getBean(BatchGoal.class);
                        batchGoal.setResult(holder);
                        batchGoal.setGoalName(goal);
                        executor.execute(batchGoal, syntaxResult);
                    }
                });
            } else {
                throw new GoalException("Goal with name '" + goal + "' does not exist");
            }
        }
        return holder;
    }

}
