package no.hvl.past.corrlang.parser;

import no.hvl.past.corrlang.domainmodel.ConsistencyRule;
import no.hvl.past.corrlang.domainmodel.CorrSpec;
import no.hvl.past.corrlang.domainmodel.Endpoint;
import no.hvl.past.corrlang.referencing.URLReference;

import java.util.*;

public class SyntacticalResult {

    private final List<URLReference> imports;
    private final Map<String, Endpoint> endpoints;
    private final Map<String, CorrSpec> specs;
    private final Map<String, ConsistencyRule> rules;

    public SyntacticalResult() {
        this.imports = new ArrayList<>();
        this.endpoints = new HashMap<>();
        this.specs = new HashMap<>();
        this.rules = new HashMap<>();
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

    public void addImport(String url) {
        this.imports.add(new URLReference(url));
    }

    public List<URLReference> getImports() {
        return imports;
    }
}
