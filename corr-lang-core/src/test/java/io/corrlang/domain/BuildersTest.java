package io.corrlang.domain;

import com.google.common.collect.Sets;
import io.corrlang.domain.builders.SystemInterfaceDescriptionBuilder;
import io.corrlang.domain.diagrams.*;
import no.hvl.past.graph.*;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.graph.elements.Tuple;
import no.hvl.past.graph.operations.Invert;
import no.hvl.past.graph.predicates.*;
import no.hvl.past.names.Name;
import no.hvl.past.util.Multiplicity;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BuildersTest extends TestWithGraphLib {

    enum TestEnum {
        AVAILABLE,
        RESERVED,
        TAKEN
    }

    @Test
    public void testSystemInterfaceDescriptionBuilding() {
        SystemInterfaceDescriptionBuilder builder = new SystemInterfaceDescriptionBuilder(Name.identifier("SysA"), getUniverse());
        IntfcDesc desc = builder.stringValueType("str")
                .intValueType("int")
                .enumValueType("ResourceStatus", TestEnum.class)
                .build();

        Sketch sketch = desc.getFormalRepresentation();

        assertGraphsEqual(sketch.carrier(),
                Triple.node(id("str")),
                Triple.node(id("int")),
                Triple.node(id("ResourceStatus")));

        Optional<Diagram> diagram = sketch.diagramsOn(Triple.node(id("str"))).findFirst();
        assertTrue(diagram.isPresent());
        assertTrue(diagram.get().label() instanceof StringDT);

        diagram = sketch.diagramsOn(Triple.node(id("ResourceStatus"))).findFirst();
        assertTrue(diagram.isPresent());
        assertTrue(diagram.get().label() instanceof EnumValue);
        EnumValue enm = (EnumValue) diagram.get().label();
        assertEquals(enm.literals(), Sets.newHashSet(
                Name.identifier("AVAILABLE"),
                Name.identifier("RESERVED"),
                Name.identifier("TAKEN")));

    }

    @Test
    public void testBuildSymmetricAssociation() {
        SystemInterfaceDescriptionBuilder builder = new SystemInterfaceDescriptionBuilder(Name.identifier("SysA"), getUniverse());
        IntfcDesc desc = builder
                .objectType("A")
                .buildField("x", "B")
                .multiplicity(Multiplicity.of(1, 2))
                .makeSymmetric("y", "r", Multiplicity.of(0, 1), false, false)
                .endField()
                .buildField("z", "A")
                .makeHierarchy()
                .endField()
                .endObjectType()
                .build();

        Sketch sketch = desc.getFormalRepresentation();
        assertGraphsEqual(sketch.carrier(),
                Triple.node(id("A")),
                Triple.node(id("B")),
                Triple.edge(id("A"), id("x").prefixWith(id("A")), id("B")),
                Triple.edge(id("B"), id("y").prefixWith(id("B")), id("A")),
                Triple.edge(id("A"), id("z").prefixWith(id("A")), id("A"))
        );

        Optional<Diagram> diagram = sketch.diagramsOn(
                Triple.edge(
                        id("A"),
                        id("z").prefixWith(id("A")),
                        id("A"))).findFirst();
        assertTrue(diagram.isPresent());
        assertTrue(diagram.get().label() instanceof Acyclicity);

        diagram = sketch.diagramsOn(
                Triple.edge(
                        id("A"),
                        id("x").prefixWith(id("A")),
                        id("B")))
                .filter(d -> d.label() instanceof TargetMultiplicity)
                .findFirst();
        assertTrue(diagram.isPresent());
        TargetMultiplicity tm = (TargetMultiplicity) diagram.get().label();
        assertEquals(Multiplicity.of(1,2), tm.multiplicity());

        diagram = sketch.diagramsOn(
                        Triple.edge(
                                id("B"),
                                id("y").prefixWith(id("B")),
                                id("A")))
                .filter(d -> d.label() instanceof TargetMultiplicity)
                .findFirst();
        assertTrue(diagram.isPresent());
        TargetMultiplicity tm2 = (TargetMultiplicity) diagram.get().label();
        assertEquals(Multiplicity.of(0,1), tm2.multiplicity());

        diagram = sketch.diagramsOn(
                        Triple.edge(
                                id("B"),
                                id("y").prefixWith(id("B")),
                                id("A")))
                .filter(d -> d.label() instanceof Invert)
                .findFirst();
        assertTrue(diagram.isPresent());
        assertEquals(Name.identifier("r"), diagram.get().getName());

    }

    @Test
    public void testBuildWithInheritanceAndContainment() {
        SystemInterfaceDescriptionBuilder builder = new SystemInterfaceDescriptionBuilder(Name.identifier("Sys"), getUniverse());
        IntfcDesc build = builder.objectType("Person")
                .makeAbstract()
                .endObjectType()
                .objectType("NaturalPerson")
                .inheritsFrom("Person")
                .endObjectType()
                .objectType("JuridicalPerson")
                .inheritsFrom("Person")
                .endObjectType()
                .objectType("Registry")
                .buildField("partners", "Person")
                .makeContainment()
                .endField()
                .endObjectType()
                .build();

        Sketch form = build.getFormalRepresentation();

        assertTrue(form.carrier() instanceof InheritanceAugmentedGraph);
        InheritanceAugmentedGraph ig = (InheritanceAugmentedGraph) form.carrier();

        Set<Tuple> tuples = ig.directInheritances().collect(Collectors.toSet());
        assertEquals(Sets.newHashSet(new Tuple(Name.identifier("NaturalPerson"), Name.identifier("Person")),
                new Tuple(Name.identifier("JuridicalPerson"), Name.identifier("Person"))), tuples);

        assertTrue(ig.isUnder(Name.identifier("JuridicalPerson"), Name.identifier("Person")));

        assertEquals(1, form.diagramsOn(
                        Triple.node(Name.identifier("Person")))
                .filter(d -> d.label() instanceof AbstractType)
                .count());

        assertEquals(1, form.diagramsOn(
                        Triple.edge(
                                Name.identifier("Registry"),
                                Name.identifier("partners").prefixWith(Name.identifier("Registry")),
                                Name.identifier("Person")))
                .filter(d -> d.label() instanceof ContainmentMarker)
                .count());

        Optional<Diagram> diagram = form.diagramsOn(
                        Triple.edge(
                                Name.identifier("Registry"),
                                Name.identifier("partners").prefixWith(Name.identifier("Registry")),
                                Name.identifier("Person")))
                .filter(d -> d.label() instanceof SourceMultiplicity)
                .findFirst();
        assertTrue(diagram.isPresent());
        SourceMultiplicity sm = (SourceMultiplicity) diagram.get().label();
        assertEquals(Multiplicity.of(1,1), sm.multiplicity());

    }


    @Test
    public void testBuildMessagesInGroups() {
        SystemInterfaceDescriptionBuilder builder = new SystemInterfaceDescriptionBuilder(Name.identifier("Sys"), getUniverse());
        IntfcDesc desc = builder.customValueType("DateTime", DataTypePredicate.getInstance())
                .stringValueType("String")
                .objectType("Employee")
                .endObjectType()
                .objectType("Contract")
                .endObjectType()
                .actionGroup("company")
                .startChildGroup("people", true)
                .action("GET")
                .buildInputArg("employedSince", "DateTime")
                .multiplicity(Multiplicity.of(false))
                .endArgument()
                .outputArg("RESULT", "Employee")
                .endActionAndBackToGroup()
                .action("POST")
                .buildInputArg("email", "String")
                .multiplicity(Multiplicity.of(true, false))
                .endArgument()
                .buildInputArg("dateOfEmployment", "DateTime")
                .multiplicity(Multiplicity.of(true, false))
                .endArgument()
                .endActionAndBackToGroup()
                .endCurrentGroup()
                .startChildGroup("contracts", true)
                .action("GET")
                .outputArg("RESULT", "Contract")
                .end()
                .build();
        Sketch sketch = desc.getFormalRepresentation();
        Graph graph = sketch.carrier();

        Set<Name> expectedNames = Sets.newHashSet(
                Name.identifier("DateTime"),
                Name.identifier("String"),
                Name.identifier("Employee"),
                Name.identifier("Contract"),
                Name.identifier("company"),
                Name.identifier("people").prefixWith(Name.identifier("company")),
                Name.identifier("contracts").prefixWith(Name.identifier("company")),
                Name.identifier("GET").prefixWith(Name.identifier("people").prefixWith(Name.identifier("company"))),
                Name.identifier("POST").prefixWith(Name.identifier("people").prefixWith(Name.identifier("company"))),
                Name.identifier("GET").prefixWith(Name.identifier("contracts").prefixWith(Name.identifier("company")))
        );

        assertEquals(expectedNames, graph.nodes().collect(Collectors.toSet()));


        assertEquals(1, sketch.diagramsOn(
                        Triple.node(Name.identifier("contracts").prefixWith(Name.identifier("company"))))
                .filter(d -> d.label() instanceof ActionGroupMarker)
                .count());

        assertEquals(1, sketch.diagramsOn(
                        Triple.node(
                                Name.identifier("GET").prefixWith(Name.identifier("contracts").prefixWith(Name.identifier("company")))
                        )
                )
                .filter(d -> d.label() instanceof ActionMarker)
                .count());


        assertEquals(1, sketch.diagramsOn(
                        Triple.edge(
                                Name.identifier("POST").prefixWith(Name.identifier("people").prefixWith(Name.identifier("company"))),
                                Name.identifier("dateOfEmployment").index(2).prefixWith(Name.identifier("POST").prefixWith(Name.identifier("people").prefixWith(Name.identifier("company")))),
                                Name.identifier("DateTime")))
                .filter(d -> d.label() instanceof ActionInputMarker)
                .count());

        assertEquals(1, sketch.diagramsOn(
                        Triple.edge(
                                Name.identifier("GET").prefixWith(Name.identifier("people").prefixWith(Name.identifier("company"))),
                                Name.identifier("RESULT").index(1).prefixWith(Name.identifier("GET").prefixWith(Name.identifier("people").prefixWith(Name.identifier("company")))),
                                Name.identifier("Employee")))
                .filter(d -> d.label() instanceof ActionOutputMarker)
                .count());

    }




}
