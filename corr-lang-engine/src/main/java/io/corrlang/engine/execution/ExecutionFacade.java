package io.corrlang.engine.execution;

import io.corrlang.engine.parser.SyntacticalResult;
import io.corrlang.engine.reporting.ReportErrorType;
import io.corrlang.engine.reporting.ReportFacade;
import io.corrlang.di.DependencyInjectionContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import java.util.HashMap;
import java.util.Map;

public class ExecutionFacade {

    private final Map<Class<? extends AbstractExecutor>, AbstractExecutor> alreadyExecuted;
    private final DependencyInjectionContainer diContainer;
    private final ReportFacade reportFacade;
    private Logger logger;

    public ExecutionFacade(DependencyInjectionContainer diContainer, ReportFacade reportFacade) {
        this.alreadyExecuted = new HashMap<>();
        this.diContainer = diContainer;
        this.reportFacade = reportFacade;
    }

    public Logger getLogger() {
        if (this.logger == null) {
            logger = LoggerFactory.getLogger(ExecutionFacade.class);
        }
        return logger;
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
                    getLogger().error(ReportErrorType.GOAL.toString() + ": Cannot execute '" + executor.getKey() + "' because dependent goal or task '" + alreadyExecuted.get(dependency).getKey() + "' failed!");
                    return false;
                }
            }
            for (Class<? extends AbstractExecutor> dependency : executor.dependsOn()) {
                if (!alreadyExecuted.containsKey(dependency)) {
                    AbstractExecutor bean = diContainer.getBean(dependency);
                    boolean result = execute(bean, domainModel);
                    if (!result) {
                        getLogger().error(ReportErrorType.GOAL + "Cannot execute '" + executor.getKey() + "' because dependent goal or task '" + dependency.getName() + "' failed!");
                        return false;
                    }
                }
            }
            executor.execute(domainModel, reportFacade);
            getLogger().debug("Task '" + executor.getKey() + "' successfully executed!");
            this.alreadyExecuted.put(executor.getClass(), executor);
            return executor.isExecutedCorrectly();
       } catch (Throwable throwable) {
           getLogger().error("", throwable);
           reportFacade.reportException(throwable);
           return false;
       }
    }

    public boolean execute(String target, SyntacticalResult domainModel) {
        try {
            AbstractExecutor bean = this.diContainer.getBean(target, AbstractExecutor.class);
            return execute(bean, domainModel);
        } catch (NoSuchBeanDefinitionException nosuchBean) {
            reportFacade.reportError("The goal or task '" + target + "' is unknown!");
            return false;
        }
    }

}
