package no.hvl.past.corrlang.execution.traverser;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import no.hvl.past.corrlang.domainmodel.*;
import no.hvl.past.corrlang.execution.AbstractExecutor;
import no.hvl.past.corrlang.reporting.ReportErrorType;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.names.Name;
import no.hvl.past.util.StreamExt;

import java.util.*;

public class LinkCommonalities extends AbstractTraverser  {

    private Collection<Commonality> allCurrentCommonalities;

    public LinkCommonalities() {
        super("LinkCommonalities");
        this.allCurrentCommonalities = Collections.emptySet();
    }

    @Override
    public Set<Class<? extends AbstractExecutor>> dependsOn() {
        return Sets.newHashSet(LinkElementsTraverser.class, AddImplicitCommonalities.class);
    }


    @Override
    public void handle(CorrSpec corrSpec) throws Throwable {
        this.allCurrentCommonalities = corrSpec.allTransitiveCommonalities();
    }

    @Override
    public void handleCommonality(Commonality commonality) throws Throwable {
        if (commonality.getTargetName().isPresent()) {
            commonality.setTarget(findByName(commonality.getTargetName().get(), commonality));
        }
        for (Commonality sub : commonality.getSubCommonalities()) {
            sub.setSource(commonality);
        }
        if (commonality.isEdgeCorrespondence()) {
            if (!commonality.getParent().isPresent()) {
                Multimap<String, Name> targetTypes = HashMultimap.create();
                for (ElementRef elementRef : commonality.getRelates()) {
                    targetTypes.put(elementRef.getEndpointName(), elementRef.getElement().get().getSource());
                }
                commonality.setSource(findCommonality(targetTypes, commonality));
            }
            if (!commonality.getTarget().isPresent()) {
                Multimap<String, Name> targetTypes = HashMultimap.create();
                for (ElementRef elementRef : commonality.getRelates()) {
                    targetTypes.put(elementRef.getEndpointName(), elementRef.getElement().get().getTarget());
                }
                commonality.setTarget(findCommonality(targetTypes, commonality));
            }
        }
    }

    private Commonality findByName(String name, Commonality parent) throws LanguageException {
        for (Commonality commonality : allCurrentCommonalities) {
            if (commonality.getName().equals(name)) {
                return commonality;
            }
        }
        throw new LanguageException(parent, ReportErrorType.SEMANTICS, "Could not infer target commonality: The referenced commonality with name '" + parent + "' does not exist!");
    }

    public Commonality findCommonality(Multimap<String, Name> betweenTypes, Commonality parent) throws LanguageException {
        Commonality result = null;
        for (Commonality commonality : allCurrentCommonalities) {
            if (commonality.matchesAll(betweenTypes)) {
                if (result == null) {
                    result = commonality;
                } else {
                    throw new LanguageException(parent, ReportErrorType.SEMANTICS,
                            "Could not infer target commonality: There are two commonalities that establish a relation between (" +
                                    StreamExt.stream(parent.getRelates()).fuse(", ", ElementRef::toString) +
                                    "), namely '" +
                                    result.getName() + "' and '" +
                                    commonality.getName() + "'. Commonality references must be unambiguos! Consider using the keyword 'target'");
                }
            }
        }
        if (result != null) {
            return result;
        } else {
            throw new LanguageException(parent, ReportErrorType.SEMANTICS,
                    "Could not infer target commonality: There is no commonality between (" +
                            StreamExt.stream(parent.getRelates()).fuse(", ", ElementRef::toString) +
                            ")");
        }
    }
}
