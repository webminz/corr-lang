package io.corrlang.domain;

import io.corrlang.domain.data.ComprData;
import no.hvl.past.names.Name;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class SynchronizationRule implements ConsistencyRule {

    private final Name commonalityName;
    private final Set<QualifiedName> typeTuple;
    private final List<QualifiedName> propagationHierarchy;
    private final boolean isUnconditional;


    // TODO conditionals for when synchronizations rules should be enforced

    public SynchronizationRule(
            Name commonalityName,
            Set<QualifiedName> typeTuple,
            List<QualifiedName> propagationHierarchy) {
        this.commonalityName = commonalityName;
        this.typeTuple = typeTuple;
        this.propagationHierarchy = propagationHierarchy;
        this.isUnconditional = false;
    }


    @Override
    public Name commonality() {
        return commonalityName;
    }

    @Override
    public Stream<Name> violations(ComprData instance) {
        if (isUnconditional) {
            return doUnconditionalCheck(instance);
        } else {
            return doConditionalCheck(instance);
        }

    }

    private Stream<Name> doConditionalCheck(ComprData instance) {
        return instance.getCommonalities().iterate(commonalityName).filter(commonality -> {
            if (propagationHierarchy.isEmpty()) {
                for (QualifiedName typeQName : typeTuple) {
                    if (commonality.getProjections().stream().noneMatch(qualifiedName -> qualifiedName.getSystem().equals(typeQName.getSystem()))) {
                        return true;
                    }
                }
            } else {
                return false; // TODO hierarchy check
            }
            return false;
        }).flatMap(commonality -> {
            return commonality.getProjections().stream().map(QualifiedName::getElement);
        });
    }

    private Stream<Name> doUnconditionalCheck(ComprData instance) {
        return Stream.empty(); // TODO
    }


}
