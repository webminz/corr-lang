package io.corrlang.engine.execution;

import io.corrlang.engine.parser.SyntacticalResult;
import io.corrlang.engine.reporting.ReportFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public abstract class AbstractExecutor {

    private final String key;
    private boolean isExecuted;
    private boolean isFailed;
    private Logger logger;
    private ReportFacade reportFacade = ReportFacade.NULL_REPORT_FACADE;

    protected AbstractExecutor(String key) {
        this.key = key;
        this.isExecuted = false;
        this.isFailed = false;
    }

    protected Logger getLogger() {
        if (logger == null) {
            this.logger = LoggerFactory.getLogger(getClass());
        }
        return this.logger;
    }

    public String getKey() {
        return key;
    }

    public void execute(SyntacticalResult syntaxGraph, ReportFacade reportFacade) throws Throwable {
        this.reportFacade = reportFacade;
        try {
            this.isExecuted = true;
            executeTransitive(syntaxGraph);
        } catch (Throwable throwable) {
            getLogger().error("Exception in task '" + key + "':", throwable);
            this.isFailed = true;
            throw throwable;
        }
    }

    public abstract Set<Class<? extends AbstractExecutor>> dependsOn();

    protected abstract void executeTransitive(SyntacticalResult syntaxGraph) throws Throwable;

    public final boolean isExecuted() {
        return isExecuted;
    }

    public final boolean isFailed() {
        return isFailed;
    }

    public final boolean isExecutedCorrectly() {
        return isExecuted && !isFailed;
    }

    public final boolean isExecutedButFailed() {
        return isExecuted && isFailed;
    }

    public ReportFacade getReportFacade() {
        return reportFacade;
    }
}
