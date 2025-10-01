package io.corrlang.domain;

import io.corrlang.domain.keys.ConsistencyRule;
import no.hvl.past.graph.*;
import no.hvl.past.graph.elements.Triple;
import io.corrlang.domain.keys.Key;
import no.hvl.past.names.Name;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A correspondence is a relation that exists between endpoints.
 * It is expressed via commonalities, i.e. the different systems sharing
 * common elements.
 */
public class Correspondence {

    private final String name;

    private final Map<Integer, Endpoint> endpoints;

    private final List<Commonality> commonalities;

    private final Set<Key> keys;
    private final Set<ConsistencyRule> rules;


    public Correspondence(String name, Map<Integer, Endpoint> endpoints, List<Commonality> commonalities, Set<Key> keys, Set<ConsistencyRule> rules) {
        this.name = name;
        this.endpoints = endpoints;
        this.commonalities = commonalities;
        this.keys = keys;
        this.rules = rules;
    }

    public Stream<Key> keys() {
        return keys.stream();
    }

    public Stream<ConsistencyRule> rules() {
        return rules.stream();
    }

    public List<Endpoint> endpoints() {
        return endpoints.keySet().stream().sorted().map(endpoints::get).collect(Collectors.toList());
    }


    public Stream<Key> relationKeys() {
        return keys.stream();
    }

    public String getName() {
        return name;
    }
}
