package no.hvl.past.corrlang.execution.traverser;

import no.hvl.past.corrlang.domainmodel.Commonality;
import no.hvl.past.corrlang.domainmodel.CorrSpec;
import no.hvl.past.corrlang.domainmodel.ElementRef;
import no.hvl.past.corrlang.execution.AbstractExecutor;

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
