package io.corrlang.domain.data;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
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
public class Data {

    /**
     * The system, this data is associated with.
     */
    private final Endpoint origin;

    /**
     * Data is formally represented by a tree (i.e. a DAG with exactly one root).
     */
    private final Tree tree;


    public Data(Endpoint origin, Tree tree) {
        this.origin = origin;
        this.tree = tree;
    }

    public Endpoint getOrigin() {
        return origin;
    }

    public Tree getTree() {
        return tree;
    }
}
