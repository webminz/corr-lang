package no.hvl.past.corrlang.execution.goals;

import no.hvl.past.corrlang.domainmodel.Goal;
import no.hvl.past.corrlang.domainmodel.GoalTarget;
import no.hvl.past.corrlang.execution.AbstractExecutor;
import no.hvl.past.corrlang.execution.ExecutionFacade;
import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.di.DependencyInjectionContainer;

import java.util.Optional;

public abstract class AbstractGoal extends AbstractExecutor {


    public AbstractGoal(String goalName) {
        super(goalName);
    }

    public abstract boolean isSystemGoal();

    public abstract boolean isBatchGoal();

    public abstract boolean isServerGoal();

    public abstract boolean isCodegenGoal();

    public abstract boolean isFileGoal();

    public static void runGoal(DependencyInjectionContainer diContainer, ExecutionFacade executor, SyntacticalResult syntaxResult, String goal) throws Throwable {
        if (goal.equals(InfoGoal.GOAL_NAME)) {
            executor.execute(diContainer.getBean(InfoGoal.class), syntaxResult);
        } else if (goal.equals(HelpGoal.NAME)) {
            executor.execute(diContainer.getBean(HelpGoal.class), syntaxResult);
        } else if (goal.equals(ParseGoal.GOAL_NAME)) {
            executor.execute(diContainer.getBean(ParseGoal.class), syntaxResult);
        } else {
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
                        file.setFile(diContainer.getFSUtils().file(fileCreation.getLocation()), fileCreation.isOverwrite());
                        executor.execute(file, syntaxResult);
                    }

                    @Override
                    public void handle(GoalTarget.Batch batch) throws Exception {
                        BatchGoal batchGoal = diContainer.getBean(BatchGoal.class);
                        // TODO set  params
                        batchGoal.setGoalName(goal);
                        executor.execute(batchGoal, syntaxResult);
                    }
                });
            } else {
                throw new GoalException("Goal with name '" + goalWithName + "' does not exist");
            }
        }


    }

}
