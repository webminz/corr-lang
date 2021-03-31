package no.hvl.past.corrlang.execution;

import no.hvl.past.corrlang.domainmodel.*;
import no.hvl.past.corrlang.parser.SyntacticalResult;
import org.apache.log4j.Logger;

import java.util.Set;

public abstract class AbstractExecutor {

    private String key;
    private boolean isExecuted;
    private boolean isFailed;
    private Logger logger;

    protected AbstractExecutor(String key) {
        this.key = key;
        this.isExecuted = false;
        this.isFailed = false;
        this.logger = Logger.getLogger(getClass());
    }

    protected Logger getLogger() {
        return this.logger;
    }

    public String getKey() {
        return key;
    }

    public void execute(SyntacticalResult syntaxGraph) throws Throwable {
        try {
            this.isExecuted = true;
            executeTransitive(syntaxGraph);
        } catch (Throwable throwable) {
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

}
