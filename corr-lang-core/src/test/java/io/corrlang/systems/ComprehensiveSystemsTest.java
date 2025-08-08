package io.corrlang.systems;

import com.google.common.collect.Sets;
import io.corrlang.domain.*;
import io.corrlang.domain.schemas.Schema;
import io.corrlang.domain.schemas.SchemaBuilder;
import no.hvl.past.graph.*;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.names.Name;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static io.corrlang.domain.QualifiedName.qname;
import static org.junit.jupiter.api.Assertions.*;

public class ComprehensiveSystemsTest extends TestWithGraphLib {


    @Test
    public void testSmallComprehensiveSystem() throws GraphError {

        Schema ep1sch = new SchemaBuilder(Name.identifier("G1"), getUniverse())
                .stringValueType("String")
                .objectType("F").endObjectType()
                .objectType("A")
                .field("x", "F")
                .field( "name", "String")
                .endObjectType().build();
        Endpoint g1 = new Endpoint(1, "G1", ep1sch);

        Schema ep2sch = new SchemaBuilder(Name.identifier("G2"), getUniverse())
                .stringValueType("String")
                .objectType("B")
                .field("y", "G")
                .field( "name", "String")
                .endObjectType().build();
        Endpoint g2 = new Endpoint(2, "G2", ep2sch);


        Schema ep3sch = new SchemaBuilder(Name.identifier("G3"), getUniverse())
                .stringValueType("String")
                .objectType("D")
                .field("d", "C")
                .endObjectType()
                .objectType("C")
                .field("e", "E")
                .field( "name", "String")
                .endObjectType()
                .build();
        Endpoint g3 = new Endpoint(3, "G3", ep3sch);



        Set<Triple> expected = new HashSet<>();
        expected.add(Triple.node(Name.identifier("ID")).prefix(Name.identifier("GG")));
        expected.add(Triple.node(Name.identifier("String")).prefix(Name.identifier("GG")));
        expected.add(Triple.node(Name.identifier("F")).prefix(Name.identifier("G1")));
        expected.add(Triple.node(Name.identifier("G")).prefix(Name.identifier("G2")));
        expected.add(Triple.node(Name.identifier("D")).prefix(Name.identifier("G3")));
        expected.add(Triple.node(Name.identifier("E")).prefix(Name.identifier("G3")));
        expected.add(Triple.node(Name.identifier("FG")).prefix(Name.identifier("GG")));
        expected.add(Triple.edge(
                Name.identifier("ID").prefixWith(Name.identifier("GG")),
                Name.identifier("name").prefixWith(Name.identifier("GG")),
                Name.identifier("String").prefixWith(Name.identifier("GG"))
        ));
        expected.add(Triple.edge(
                Name.identifier("ID").prefixWith(Name.identifier("GG")),
                Name.identifier("x").prefixWith(Name.identifier("G1")),
                Name.identifier("F").prefixWith(Name.identifier("G1"))
        ));
        expected.add(Triple.edge(
                Name.identifier("ID").prefixWith(Name.identifier("GG")),
                Name.identifier("y").prefixWith(Name.identifier("G2")),
                Name.identifier("G").prefixWith(Name.identifier("G2"))
        ));
        expected.add(Triple.edge(
                Name.identifier("ID").prefixWith(Name.identifier("GG")),
                Name.identifier("e").prefixWith(Name.identifier("G3")),
                Name.identifier("E").prefixWith(Name.identifier("G3"))
        ));
        expected.add(Triple.edge(
                Name.identifier("D").prefixWith(Name.identifier("G3")),
                Name.identifier("d").prefixWith(Name.identifier("G3")),
                Name.identifier("ID").prefixWith(Name.identifier("GG"))
        ));
        // linguistic extension
        expected.add(Triple.edge(
                Name.identifier("FG").prefixWith(Name.identifier("GG")),
                Name.identifier("GG").projectionOn(Name.identifier("G1")).appliedTo(Name.identifier("FG")),
                Name.identifier("F").prefixWith(Name.identifier("G1"))
        ));
        expected.add(Triple.edge(
                Name.identifier("FG").prefixWith(Name.identifier("GG")),
                Name.identifier("GG").projectionOn(Name.identifier("G2")).appliedTo(Name.identifier("FG")),
                Name.identifier("G").prefixWith(Name.identifier("G2"))
        ));

        ComprSys comprSys = new ComprSysBuilder(Name.identifier("GG"), universe)
                .addSystem(g1)
                .addSystem(g2)
                .addSystem(g3)
                .nodeCommonality(Name.identifier("ID"), qname(g1, Name.identifier("A")), qname(g2, Name.identifier("B")), qname(g3, Name.identifier("C")))
                .nodeCommonality(Name.identifier("String"), qname(g1, Name.identifier("String")), qname(g2, Name.identifier("String")), qname(g3, Name.identifier("String")))
                .nodeCommonality(Name.identifier("FG"), qname(g1, Name.identifier("F")), qname(g2, Name.identifier("G")))
                .edgeCommonality(Name.identifier("ID"), Name.identifier("name"), Name.identifier("String"), qname(g1, Name.identifier("name")), qname(g2, Name.identifier("name")), qname(g3, Name.identifier("name")))
                .identification(Name.identifier("ID"))
                .identification(Name.identifier("String"))
                .identification(Name.identifier("name"))
                .build();

        Sketch comprehensiveSystem = comprSys.schema();

        comprehensiveSystem.carrier().elements().forEach(System.out::println);

        assertEquals(expected.size(), comprehensiveSystem.carrier().elements().count());
        assertStreamEquals(expected, comprehensiveSystem.carrier().elements());

        GraphMorphism emb1 = comprSys.embeddingOf(g1);
        assertEquals(Name.identifier("ID").prefixWith(Name.identifier("GG")), emb1.map(Name.identifier("A")).get());
    }


    @Test
    public void testComprehensiveSystemsServicesHandling() {

        Schema endASchema = new SchemaBuilder(Name.identifier("A"), getUniverse())
                .objectType("ReturnTypeA").endObjectType()
                .objectType("InputTypeA").endObjectType()
                .actionGroup("Service")
                    .action("op1A")
                        .buildInputArg("argument", "InputTypeA").endArgument()
                        .buildOutputArg(Name.identifier("return"), Name.identifier("ReturnTypeA")).endArgument()
                    .endActionAndBackToGroup()
                .action("op2")
                .buildOutputArg(Name.identifier("return"), Name.identifier("ReturnTypeA"))
                .endArgument().endActionAndBackToGroup().endCurrentGroup().end().build();
        Endpoint endpointA = new Endpoint(0, "A", endASchema);


        Schema endBSchema = new SchemaBuilder(Name.identifier("B"), getUniverse())
                .objectType("ReturnTypeB")
                .endObjectType()
                .actionGroup("Service")
                .action("op1B").buildOutputArg(Name.identifier("return"), Name.identifier("ReturnTypeB"))
                .endArgument().endActionAndBackToGroup().endCurrentGroup().end().build();


        Endpoint endpointB = new Endpoint(1, "B", endBSchema);

        // only identify the returnType, still three Service containers
        ComprSys attempt1 = new ComprSysBuilder(Name.identifier("attempt1"), universe)
                .addSystem(endpointA)
                .addSystem(endpointB)
                .nodeCommonality(Name.identifier("ReturnType"), qname(endpointA, Name.identifier("ReturnTypeA")), qname(endpointB, Name.identifier("ReturnTypeB")))
                .identification(Name.identifier("ReturnType"))
                .build();

        assertTrue(attempt1.schema().carrier().contains(Triple.node(Name.identifier("ReturnType").prefixWith(Name.identifier("attempt1")))));
        assertFalse(attempt1.schema().carrier().contains(Triple.node(Name.identifier("ReturnTypeA").prefixWith(Name.identifier("A")))));
        assertFalse(attempt1.schema().carrier().contains(Triple.node(Name.identifier("ReturnTypeB").prefixWith(Name.identifier("B")))));

        // TODO: find out what to do with messages in comprehensive systems
        //        assertEquals(3, attempt1.messages().count());
//        Set<Name> actualServiceContainers = attempt1.messages().map(MessageType::getGroup)
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .map(MessageContainer::getTypeName)
//                .collect(Collectors.toSet());
//        HashSet<Name> expectedServiceContainers = Sets.newHashSet(Name.identifier("Service").prefixWith(Name.identifier("A")),
//                Name.identifier("Repository").prefixWith(Name.identifier("A")),
//                Name.identifier("Service").prefixWith(Name.identifier("B")));
//        assertEquals(expectedServiceContainers, actualServiceContainers);


        // identify op1A/op1B --> error because container has not been identified
        try {
            new ComprSysBuilder(Name.identifier("attempt2"), universe)
                    .addSystem(endpointA)
                    .addSystem(endpointB)
                    .nodeCommonality(Name.identifier("ReturnType"), qname(endpointA, Name.identifier("ReturnTypeA")), qname(endpointB, Name.identifier("ReturnTypeB")))
                    .nodeCommonality(Name.identifier("op1"),
                            qname(endpointA, Name.identifier("op1A").prefixWith(Name.identifier("Service"))),
                            qname(endpointB, Name.identifier("op1B").prefixWith(Name.identifier("Service"))))
                    .identification(Name.identifier("ReturnType"))
                    .identification(Name.identifier("op1"))
                    .build();
            fail();
        } catch (IllegalStateException e) {
            assertTrue(e.getMessage().contains("Service"));
        }

        // identify op1A/op1B and identify both services, now it works, i.e. only two containers left
        ComprSys attempt3 = new ComprSysBuilder(Name.identifier("attempt3"), universe)
                .addSystem(endpointA)
                .addSystem(endpointB)
                .nodeCommonality(Name.identifier("ReturnType"), qname(endpointA, Name.identifier("ReturnTypeA")), qname(endpointB, Name.identifier("ReturnTypeB")))
                .nodeCommonality(Name.identifier("SuperService"), qname(endpointA, Name.identifier("Service")), qname(endpointB, Name.identifier("Service")))
                .nodeCommonality(Name.identifier("op1"),
                        qname(endpointA, Name.identifier("op1A").prefixWith(Name.identifier("Service"))),
                        qname(endpointB, Name.identifier("op1B").prefixWith(Name.identifier("Service"))))
                .identification(Name.identifier("ReturnType"))
                .identification(Name.identifier("op1"))
                .identification(Name.identifier("SuperService"))
                .build();

//        assertEquals(3, attempt1.messages().count());
//        Set<Name> actualServiceContainers2 = attempt3.messages().map(MessageType::getGroup)
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .map(MessageContainer::getTypeName)
//                .collect(Collectors.toSet());
//        HashSet<Name> expectedServiceContainers2 = Sets.newHashSet(
//                Name.identifier("Repository").prefixWith(Name.identifier("A")),
//                Name.identifier("SuperService").prefixWith(Name.identifier("attempt3")));
//
//        assertEquals(expectedServiceContainers2, actualServiceContainers2);
    }
}
