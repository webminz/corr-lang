package io.corrlang.domain.keys;

import io.corrlang.domain.Sys;
import no.hvl.past.graph.GraphMorphism;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.graph.trees.Node;
import no.hvl.past.graph.trees.Tree;
import no.hvl.past.names.Name;

import java.util.List;
import java.util.Optional;

/**
 * A means to "globally" identify an element.
 */
public interface Key {

    Name getName();

    /**
     * The name of the global (merged) element (relation or overlap) in the comprehensive schema the key is defined on.
     */
    Name targetType();

    /**
     * The name of the local type on which this key is defined on.
     */
    Name sourceType();

    /**
     * The system which hosts the original type.
     */
    Sys sourceSystem();

    /**
     * The type of the (local) elements that are required to evaluate the key.
     */
    List<Triple> requiredProperties();

    /**
     * Evaluates the key on a given element, the element is identified by its name
     * in the current container = a graph morphism, i.e. typed graph.
     */
    Optional<Name> evaluate(Name element, GraphMorphism typedContainer);

    /**
     * Evaluates the on a given node in typed tree (i.e. a special case of a graph morphism).
     */
//    Optional<Name> evaluate(Node element, Tree typedTree);

    /**
     * Evaluates this key on a given java object if possible.
     */
    Name evaluate(Object element) throws KeyNotEvaluated;

}
