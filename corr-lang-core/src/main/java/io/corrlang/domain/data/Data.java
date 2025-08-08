package io.corrlang.domain.data;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.corrlang.domain.Commonalities;
import io.corrlang.domain.Endpoint;
import no.hvl.past.graph.GraphMorphism;
import no.hvl.past.graph.elements.Triple;
import io.corrlang.domain.keys.Key;
import no.hvl.past.names.Name;
import no.hvl.past.trees.Branch;
import no.hvl.past.trees.Node;
import no.hvl.past.trees.Tree;
import no.hvl.past.trees.TreeIterator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Represents an abstract view of the data stemming from a system.
 */
public interface Data {

    /**
     * The system, this data is associated with.
     */
    Endpoint origin();

    /**
     * Retrieves the identifiers of all elements that are (transitively) typed over the given type.
     */
    Stream<Name> all(Name type);

    /**
     * Retrieves the values of the given property for the given element id.
     */
    Stream<Name> properties(Name elementId, Name propertyName);

    void index(Multimap<Name, Key> keys, Commonalities commonalities);

    Name typeOf(Name element);

    static Data fromMorphism(Endpoint system, GraphMorphism instance) {
        return new Data() {
            @Override
            public Endpoint origin() {
                return system;
            }

            @Override
            public Stream<Name> all(Name type) {
                return instance.allInstances(type).map(Triple::getLabel);
            }

            @Override
            public Stream<Name> properties(Name elementId, Name propertyName) {
                return instance.codomain().get(propertyName).map(t -> instance.allOutgoingInstances(t, elementId).map(Triple::getTarget)).orElse(Stream.empty());
            }

            @Override
            public void index(Multimap<Name, Key> keys, Commonalities commonalities) {
                for (Name type : keys.keySet()) {
                    all(type).forEach(el -> {
                            Multimap<Name, Name> evalKeys = HashMultimap.create();
                            for (Key key : keys.get(type)) {
                                key.evaluate(el, instance).ifPresent(value -> evalKeys.put(key.targetType(), value));
                            }
                            for (Name commType : evalKeys.keySet()) {
                            for (Name commId : evalKeys.get(commType)) {
                                commonalities.put(commType, commId, el, origin().asId());
                            }
                            if (evalKeys.get(commType).size() > 1) {
                                commonalities.notifyDoubleRelationship(commType, el, origin().asId(), new HashSet<>(evalKeys.get(commType)));
                            }
                        }
                    });
                }

            }

            @Override
            public Name typeOf(Name element) {
                return instance.map(element).orElse(null);
            }
        };
    }


    static Data fromTree(Endpoint system, Tree tree) {
        return new Data() {
            @Override
            public Endpoint origin() {
                return system;
            }

            @Override
            public Stream<Name> all(Name type) {
                Set<Node> result = new HashSet<>();
                tree.root().aggregateNodesOfType(type, result);
                return result.stream().map(Node::elementName);
            }

            @Override
            public Stream<Name> properties(Name elementId, Name propertyName) {
                return tree.findNodeById(elementId)
                        .filter(n -> n instanceof Node)
                        .map(n -> (Node)n)// TODO also include the parent branch?
                        .map(tn -> tn.children().filter(tb -> tb.type().get().getLabel().equals(propertyName)).map(Branch::child).map(Node::elementName))
                        .orElse(Stream.empty());
            }

            @Override
            public void index(Multimap<Name, Key> keys, Commonalities commonalities) {
                Iterator<Node> iterator = TreeIterator.depthFirstTypedComplex(tree.root());
                while (iterator.hasNext()) {
                    Node typedNode = iterator.next();
                    if (!keys.get(typedNode.nodeType().get()).isEmpty()) {
                        Multimap<Name, Name> evalKeys = HashMultimap.create();
                        for (Key key : keys.get(typedNode.nodeType().get())) {
                            key.evaluate(typedNode.elementName(), tree).ifPresent(value -> evalKeys.put(key.targetType(), value));
                        }
                        for (Name commType : evalKeys.keySet()) {
                            for (Name commId : evalKeys.get(commType)) {
                                commonalities.put(commType, commId, typedNode.elementName(), origin().asId());
                            }
                            if (evalKeys.get(commType).size() > 1) {
                                commonalities.notifyDoubleRelationship(commType, typedNode.elementName(), origin().asId(), new HashSet<>(evalKeys.get(commType)));
                            }

                        }
                    }
                }
            }

            @Override
            public Name typeOf(Name element) {
                return tree.findNodeById(element).filter(n -> n instanceof Node).map(n -> (Node)n).map(Node::nodeType).orElse(null).get();
            }
        };
    }




}
