package no.hvl.past.corrlang.parser;

import no.hvl.past.corrlang.domainmodel.*;
import org.checkerframework.checker.nullness.Opt;

import java.util.*;

public class SyntacticalResult {

    private final List<URLReference> imports;
    private final Map<String, Endpoint> endpoints;
    private final Map<String, CorrSpec> specs;
    private final Map<String, ConsistencyRule> rules;
    private final Map<String, Goal> goals;

    public SyntacticalResult() {
        this.imports = new ArrayList<>();
        this.endpoints = new LinkedHashMap<>();
        this.specs = new LinkedHashMap<>();
        this.rules = new LinkedHashMap<>();
        this.goals = new LinkedHashMap<>();
    }

    public Collection<Goal> allGoals() {
        return goals.values();
    }

    public Collection<Endpoint> allEndpoints() {
        return endpoints.values();
    }

    public Collection<CorrSpec> allSpecs() {
        return specs.values();
    }

    public Collection<ConsistencyRule> allRules() {
        return rules.values();
    }

    public void addEndpoint(Endpoint currentEndpoint) {
        this.endpoints.put(currentEndpoint.getName(), currentEndpoint);
    }

    public Optional<Endpoint> getEndpointWithName(String name) {
        if (!endpoints.containsKey(name)) {
            return Optional.empty();
        } else {
            return Optional.of(this.endpoints.get(name));
        }
    }

    public void addCorrspec(CorrSpec currentSpec) {
        this.specs.put(currentSpec.getName(), currentSpec);
    }

    public Optional<CorrSpec> getCorrSpecWithName(String name) {
        if (specs.containsKey(name)) {
            return Optional.of(this.specs.get(name));
        } else {
            return Optional.empty();
        }
    }

    public void addRule(ConsistencyRule currentConsistencyRule) {
        this.rules.put(currentConsistencyRule.getName(), currentConsistencyRule);
    }

    public Optional<ConsistencyRule> getRuleWithName(String name) {
        if (this.rules.containsKey(name)) {
            return Optional.of(this.rules.get(name));
        } else {
            return Optional.empty();
        }
    }

    public void addGoal(Goal goal) {
        this.goals.put(goal.getName(), goal);
    }

    public Optional<Goal> getGoalWithName(String name) {
        if (this.goals.containsKey(name)) {
            return Optional.of(this.goals.get(name));
        } else {
            return Optional.empty();
        }
    }

    public void addImport(String url) {
        this.imports.add(new URLReference(url));
    }

    public List<URLReference> getImports() {
        return imports;
    }
}
