package io.corrlang.engine.domainmodel;

import com.google.common.collect.Multimap;
import no.hvl.past.graph.Graph;
import no.hvl.past.logic.Formula;
import no.hvl.past.names.Name;

import java.util.*;

public abstract class Commonality extends CorrLangElement {

    private List<ElementRef> relates;
    private ElementCondition key;
    private String consistencyRuleName;
    private ConsistencyRule consistencyRule;
    private Collection<Commonality> subCommonalities;

    private Set<String> predicateName;
    private Set<Formula<Graph>> predicates;

    private String targetName;

    private Commonality source;
    private Commonality target;
    private Set<Name> witnesses;

    Commonality() {
        super();
        this.relates = new ArrayList<>();
        this.subCommonalities = new ArrayList<>();
        this.witnesses = new HashSet<>();
        this.predicateName = new HashSet<>();
        this.predicates = new HashSet<>();
    }

    // Getters

    public abstract boolean isIdentity();

    public abstract boolean isSynchronizeElements();

    public boolean hasKey() {
        return getKey().isPresent();
    }

    public boolean hasAttachedRule() {
        return getConsistencyRuleName().isPresent();
    }

    public List<ElementRef> getRelates() {
        return relates;
    }

    public Optional<ElementCondition> getKey() {
        return Optional.ofNullable(key);
    }

    public Optional<ConsistencyRule> getConsistencyRule() {
        return Optional.ofNullable(consistencyRule);
    }

    public Optional<String> getConsistencyRuleName() {
        return Optional.of(consistencyRuleName);
    }

    public Collection<Commonality> getSubCommonalities() {
        return subCommonalities;
    }

    public Optional<Commonality> getSubCommonalityWithName(String name) {
        return getSubCommonalities().stream().filter(comm -> comm.getName().equals(name)).findFirst();
    }

    public Set<Name> getWitnesses() {
        return witnesses;
    }

    public void addPredicate(String predicateName) {
        this.predicateName.add(predicateName);
    }

    public void addPredicate(Formula<Graph> formula) {
        this.predicates.add(formula);
    }

    // Setters


    public Set<String> getPredicateName() {
        return predicateName;
    }

    public Set<Formula<Graph>> getPredicates() {
        return predicates;
    }

    public void addRelatedElement(ElementRef ref) {
        this.relates.add(ref);
    }

    public void addSubCommonality(Commonality comm) {
        this.subCommonalities.add(comm);
    }

    public void setKey(ElementCondition key) {
        this.key = key;
    }

    public void setConsistencyRule(ConsistencyRule consistencyRule) {
        this.consistencyRuleName = consistencyRule.getName();
        this.consistencyRule = consistencyRule;
    }


    public void setSource(Commonality source) {
        this.source = source;
    }

    public void setTarget(Commonality target) {
        this.target = target;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public void setRuleName(String text) {
        this.consistencyRuleName = text;
    }

    public void addCommonalityWitness(Name witness) {
        this.witnesses.add(witness);
    }

    public boolean endpointRefsAreDistinct() {
        Set<String> epRefs = new HashSet<>();
        for (ElementRef ref : getRelates()) {
            if (!epRefs.contains(ref.getEndpointName())) {
                epRefs.add(ref.getEndpointName());
            } else {
                return false;
            }
        }
        return true;
    }

    public Optional<String> getTargetName() {
        return Optional.ofNullable(targetName);
    }



    public Optional<Commonality> getParent() {
        return Optional.ofNullable(this.source);
    }

    public Optional<Commonality> getTarget() {
        return Optional.ofNullable(this.target);
    }

    public boolean isNodeCorrespondence() {
        return endpointRefsAreDistinct() && getRelates().stream().allMatch(elemRef -> elemRef.getElement().isPresent() && elemRef.getElement().get().isNode());
    }

    public boolean isEdgeCorrespondence() {
        return endpointRefsAreDistinct() && getRelates().stream().allMatch(elemRef -> elemRef.getElement().isPresent() && elemRef.getElement().get().isEddge());
    }

    public boolean isComplexCorrespondence() {
        return !endpointRefsAreDistinct() || (!isNodeCorrespondence() || !isEdgeCorrespondence());
    }

    public boolean matchesAll(Multimap<String, Name> relates) {
        for (Map.Entry<String, Name> requiredRef : relates.entries()) {
            if (!matches(requiredRef.getKey(), requiredRef.getValue())) {
                return false;
            }
        }
        return true;
    }

    public boolean matches(String endpoint, Name elementName) {
        for (ElementRef ref : getRelates()) {
            if (ref.getEndpointName().equals(endpoint) && ref.getElement().get().getLabel().equals(elementName)) {
                return true;
            }
        }
        return false;
    }
}
