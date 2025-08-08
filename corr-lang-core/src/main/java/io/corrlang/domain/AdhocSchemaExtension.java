package io.corrlang.domain;

import no.hvl.past.graph.Graph;
import no.hvl.past.graph.GraphMorphism;
import no.hvl.past.graph.Sketch;
import no.hvl.past.graph.Visitor;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.graph.elements.Tuple;
import no.hvl.past.names.Name;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class AdhocSchemaExtension implements GraphMorphism, Graph {

    private final Sketch codomain;
    private final Name name;

    private final List<Triple> elements = new ArrayList<>();
    private final List<Tuple> mappings = new ArrayList<>();

    public AdhocSchemaExtension(Sketch codomain, Name name) {
        this.codomain = codomain;
        this.name = name;
    }

    public Name addNode(Name originalNodeName) {
        Name newName = originalNodeName.addSuffix(Name.anonymousIdentifier());
        elements.add(Triple.node(newName));
        mappings.add(new Tuple(newName, originalNodeName));
        return newName;
    }

    public Triple addEdge(Name source, Triple originalEdge) {
        Name newLabel = originalEdge.getLabel().addSuffix(Name.anonymousIdentifier());
        Name newTarget = originalEdge.getTarget().addSuffix(Name.anonymousIdentifier());
        mappings.add(new Tuple(newLabel, originalEdge.getLabel()));
        mappings.add(new Tuple(newTarget, originalEdge.getTarget()));
        Triple t = new Triple(source, newLabel, newTarget);
        this.elements.add(t);
        return t;
    }

    @Override
    public Name getName() {
        return null;
    }

    @Override
    public Graph domain() {
        return this;
    }

    @Override
    public Graph codomain() {
        return codomain.carrier();
    }

    @Override
    public Optional<Name> map(Name name) {
        return this.mappings.stream().filter(t -> t.getDomain().equals(name)).map(Tuple::getCodomain).findFirst();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.beginMorphism();
        visitor.handleElementName(name);
        visitor.beginGraph();
        visitor.handleElementName(name.absolute());
        this.elements.stream().filter(Triple::isNode).map(Triple::getLabel).forEach(visitor::handleNode);
        this.elements.stream().filter(Triple::isEddge).forEach(visitor::handleEdge);
        visitor.endGraph();
        codomain.carrier().accept(visitor);
        this.mappings.forEach(visitor::handleMapping);
        visitor.endMorphism();
    }

    @Override
    public boolean verify() {
        return GraphMorphism.super.verify();
    }

    @Override
    public Stream<Triple> elements() {
        return elements.stream();
    }



    @Override
    public boolean isInfinite() {
        return false;
    }
}
