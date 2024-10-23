package io.corrlang.engine.execution.traverser;

import io.corrlang.engine.execution.AbstractExecutor;
import io.corrlang.engine.domainmodel.Commonality;
import io.corrlang.engine.domainmodel.CorrSpec;
import io.corrlang.engine.domainmodel.ElementRef;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DesugarAliases extends AbstractTraverser{


    private CorrSpec currentCorrSpec;
    private final Map<String, ElementRef> aliases = new HashMap<>();

    public DesugarAliases() {
        super("DesugarElementRefAliases");
    }


    @Override
    public Set<Class<? extends AbstractExecutor>> dependsOn() {
        return Collections.emptySet();
    }

    @Override
    public void handle(CorrSpec corrSpec) throws Throwable {
        currentCorrSpec = corrSpec;
        this.aliases.clear();
    }

    @Override
    public void handleCommonality(Commonality commonality) throws Throwable {
        if (currentCorrSpec.getCommonalities().contains(commonality)) {
            aliases.clear();
            // Top level commonalite --> clear
        }
    }

    @Override
    public void handle(ElementRef ref) throws Throwable {
        ref.desugarAlias(aliases);
        if (ref.getAlias() != null) {
            aliases.put(ref.getAlias(), ref);
        }
    }
}
