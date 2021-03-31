package no.hvl.past.corrlang.execution;

import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.corrlang.reporting.ReportErrorType;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.di.DependencyInjectionContainer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import java.util.HashMap;
import java.util.Map;

public class ExecutionFacade {

    private final Map<Class<? extends AbstractExecutor>, AbstractExecutor> alreadyExecuted;
    private final DependencyInjectionContainer diContainer;
    private final ReportFacade reportFacade;

    public ExecutionFacade
            (DependencyInjectionContainer diContainer) {
        this.alreadyExecuted = new HashMap<>();
        this.diContainer = diContainer;
        this.reportFacade = diContainer.getBean("defaultReportFacade", ReportFacade.class);
    }

    public boolean isKnownTarget(String target) {
        return true;
    }

    public boolean isSystemTarget(String target) {
        return false;
    }

    public boolean execute(AbstractExecutor executor, SyntacticalResult domainModel) {
       try {
            for (Class<? extends AbstractExecutor> dependency : executor.dependsOn()) {
                if (alreadyExecuted.containsKey(dependency) && alreadyExecuted.get(dependency).isFailed()) {
                    reportFacade.reportError(ReportErrorType.GOAL, "Cannot execute '" + executor.getKey() + "' because dependent goal or task '" + alreadyExecuted.get(dependency).getKey() + "' failed!");
                    return false;
                }
            }
            for (Class<? extends AbstractExecutor> dependency : executor.dependsOn()) {
                if (!alreadyExecuted.containsKey(dependency)) {
                    AbstractExecutor bean = diContainer.getBean(dependency);
                    execute(bean, domainModel);
                }
            }
            executor.execute(domainModel);
            this.alreadyExecuted.put(executor.getClass(), executor);
            return executor.isExecutedCorrectly();
       } catch (Throwable throwable) {
           Logger.getLogger(executor.getClass()).error("Execution of '" + executor.getKey()  + "' failed due to the following exception", throwable);
           reportFacade.reportError(throwable);
           return false;
       }
    }

    public boolean execute(String target, SyntacticalResult domainModel) {
        try {
            AbstractExecutor bean = this.diContainer.getBean(target, AbstractExecutor.class);
            return execute(bean, domainModel);
        } catch (NoSuchBeanDefinitionException nosuchBean) {
            reportFacade.reportError(ReportErrorType.GOAL, "The GOAL/TASK '" + target + "' is unknown!");
            return false;
        }
    }

}
