package io.corrlang.domain;

import no.hvl.past.graph.*;
import no.hvl.past.graph.elements.Triple;
import io.corrlang.domain.keys.Key;
import no.hvl.past.names.Name;

import java.util.*;
import java.util.stream.Stream;

public class ComprSys  {

    private final String url;
    private final Sketch comprehensiveSchema;
    private final Map<Name, String> namingMap;
    private final Map<Endpoint, GraphMorphism> systemsWithEmbeddings;
    private final Set<Name> mergedElements;
    private final Set<Key> keys;
    private final Set<ConsistencyRule> rules;


    public ComprSys(
            String url,
            Sketch comprehensiveSchema,
            Map<Name, String> namingMap,
            Map<Endpoint, GraphMorphism> systemsWithEmbeddings,
            Set<Name> mergedElements,
            Set<Key> keys,
            Set<ConsistencyRule> rules) {
        this.url = url;
        this.comprehensiveSchema = comprehensiveSchema;
        this.namingMap = namingMap;
        this.systemsWithEmbeddings = systemsWithEmbeddings;
        this.mergedElements = mergedElements;
        this.keys = keys;
        this.rules = rules;
    }


    public Stream<Key> keys() {
        return keys.stream();
    }

    public Stream<ConsistencyRule> rules() {
        return rules.stream();
    }

    public Optional<Name> projection(Endpoint origin, Name comm) {
        return comprehensiveSchema.carrier()
                .outgoing(comm.prefixWith(comprehensiveSchema.getName()))
                .filter(t -> t.getTarget().firstPart().equals(origin.asId()))
                .findFirst()
                .map(Triple::getTarget)
                .map(Name::secondPart);
    }

    public Stream<Key> relationKeys() {
        return keys.stream().filter(key -> !mergedElements.contains(key.targetType()));
    }

    public GraphMorphism embeddingOf(Endpoint endpoint) {
        return this.systemsWithEmbeddings.get(endpoint);
    }

    public Stream<Endpoint> components() {
        return systemsWithEmbeddings.keySet().stream();
    }

    public boolean isMerged(Name elementName) {
        return mergedElements.contains(elementName);
    }

    public Stream<Name> localNames(Endpoint component, Name elementName) {
        GraphMorphism morphism = this.systemsWithEmbeddings.get(component);
        return comprehensiveSchema.carrier().get(elementName).map(t -> morphism.allInstances(t).map(Triple::getLabel)).orElse(Stream.empty());
    }


    public String displayName(Name name) {
        if (this.namingMap.containsKey(name)) {
            return this.namingMap.get(name);
        }
        if (name.hasPrefix(this.comprehensiveSchema.getName())) {
            return name.printRaw();
        }
        for (Endpoint s : this.systemsWithEmbeddings.keySet()) {
            if (name.hasPrefix(s.getSchema().sketch().getName())) {
                return s.getSchema().displayName(name.unprefixTop());
            }
        }
        // Should not happen
        return name.printRaw();
    }



    public Sketch schema() {
        return comprehensiveSchema;
    }

    public String name() {
        return url;
    }


}
