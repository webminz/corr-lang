package io.corrlang.systems;

import com.google.common.collect.Sets;
import io.corrlang.domain.ComprSys;
import io.corrlang.domain.MessageContainer;
import io.corrlang.domain.MessageType;
import io.corrlang.domain.Sys;
import no.hvl.past.graph.*;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.names.Name;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static io.corrlang.domain.QualifiedName.qname;
import static org.junit.Assert.*;

public class ComprehensiveSystemsTest extends GraphTest {


    @Test
    public void testSmallComprehensiveSystem() throws GraphError {

        Sys g1 = new Sys.Builder("http:/1", getContextCreatingBuilder()
                .edge("A", "x", "F")
                .edge("A", "name", "String")
                .graph(Name.identifier("G1").absolute())
                .sketch("G1")
                .getResult(Sketch.class)).build();

        Sys g2 = new Sys.Builder("http:/2", getContextCreatingBuilder()
                .edge("B", "y", "G")
                .edge("B", "name", "String")
                .graph(Name.identifier("G2").absolute())
                .sketch("G2")
                .getResult(Sketch.class)).build();

        Sys g3 = new Sys.Builder("http:/3", getContextCreatingBuilder()
                .edge("D", "d", "C")
                .edge("C", "e", "E")
                .edge("C", "name", "String")
                .graph(Name.identifier("G3").absolute())
                .sketch("G3")
                .getResult(Sketch.class)).build();


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

        ComprSys comprSys = new ComprSys.Builder(Name.identifier("GG"), universe)
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
        Sys sysA = new Sys.Builder("http://1", getContextCreatingBuilder()
                .node("Service")
                .node("Repository")
                .node("ReturnTypeA")
                .node("InputTypeA")
                .node(Name.identifier("op1A").prefixWith(Name.identifier("Service")))
                .node(Name.identifier("op2A").prefixWith(Name.identifier("Repository")))
                .edgePrefixWithOwner(Name.identifier("op1A").prefixWith(Name.identifier("Service")), "argument", Name.identifier("InputTypeA"))
                .edgePrefixWithOwner(Name.identifier("op1A").prefixWith(Name.identifier("Service")), "return", Name.identifier("ReturnTypeA"))
                .edgePrefixWithOwner(Name.identifier("op2A").prefixWith(Name.identifier("Repository")), "return", Name.identifier("ReturnTypeA"))
                .graph(Name.identifier("A").absolute())
                .sketch(Name.identifier("A"))
                .getResult(Sketch.class)).
                beginMessageContainer(Name.identifier("Service"))
                .beginMessage(Name.identifier("op1A"), true)
                .input(Name.identifier("argument"))
                .output(Name.identifier("return"))
                .endMessage()
                .endMessageContainer()
                .beginMessageContainer(Name.identifier("Repository"))
                .beginMessage(Name.identifier("op2A"), false)
                .output(Name.identifier("return"))
                .endMessage().endMessageContainer().build();


        Sys sysB = new Sys.Builder("http://2", getContextCreatingBuilder()
                .node("ReturnTypeB")
                .node("Service")
                .node(Name.identifier("op1B").prefixWith(Name.identifier("Service")))
                .edgePrefixWithOwner(Name.identifier("op1B").prefixWith(Name.identifier("Service")), "return", Name.identifier("ReturnTypeB"))
                .graph(Name.identifier("B").absolute())
                .sketch(Name.identifier("B"))
                .getResult(Sketch.class)).
                beginMessageContainer(Name.identifier("Service"))
                .beginMessage(Name.identifier("op1B"), true)
                .output(Name.identifier("return"))
                .endMessage()
                .endMessageContainer()
                .build();


        // only identify the returnType, still three Service containers
        ComprSys attempt1 = new ComprSys.Builder(Name.identifier("attempt1"), universe)
                .addSystem(sysA)
                .addSystem(sysB)
                .nodeCommonality(Name.identifier("ReturnType"), qname(sysA, Name.identifier("ReturnTypeA")), qname(sysB, Name.identifier("ReturnTypeB")))
                .identification(Name.identifier("ReturnType"))
                .build();

        assertTrue(attempt1.schema().carrier().contains(Triple.node(Name.identifier("ReturnType").prefixWith(Name.identifier("attempt1")))));
        assertFalse(attempt1.schema().carrier().contains(Triple.node(Name.identifier("ReturnTypeA").prefixWith(Name.identifier("A")))));
        assertFalse(attempt1.schema().carrier().contains(Triple.node(Name.identifier("ReturnTypeB").prefixWith(Name.identifier("B")))));
        assertEquals(3, attempt1.messages().count());
        Set<Name> actualServiceContainers = attempt1.messages().map(MessageType::getGroup)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(MessageContainer::getTypeName)
                .collect(Collectors.toSet());
        HashSet<Name> expectedServiceContainers = Sets.newHashSet(Name.identifier("Service").prefixWith(Name.identifier("A")),
                Name.identifier("Repository").prefixWith(Name.identifier("A")),
                Name.identifier("Service").prefixWith(Name.identifier("B")));

        assertEquals(expectedServiceContainers, actualServiceContainers);


        // identify op1A/op1B --> error because container has not been identified
        try {
            new ComprSys.Builder(Name.identifier("attempt2"), universe)
                    .addSystem(sysA)
                    .addSystem(sysB)
                    .nodeCommonality(Name.identifier("ReturnType"), qname(sysA, Name.identifier("ReturnTypeA")), qname(sysB, Name.identifier("ReturnTypeB")))
                    .nodeCommonality(Name.identifier("op1"),
                            qname(sysA, Name.identifier("op1A").prefixWith(Name.identifier("Service"))),
                            qname(sysB, Name.identifier("op1B").prefixWith(Name.identifier("Service"))))
                    .identification(Name.identifier("ReturnType"))
                    .identification(Name.identifier("op1"))
                    .build();
            fail();
        } catch (ComprSys.ConstructionException t) {
            assertTrue(t.getMessage().contains("Service"));
        }

        // identify op1A/op1B and identify both services, now it works, i.e. only two containers left
        ComprSys attempt3 = new ComprSys.Builder(Name.identifier("attempt3"), universe)
                .addSystem(sysA)
                .addSystem(sysB)
                .nodeCommonality(Name.identifier("ReturnType"), qname(sysA, Name.identifier("ReturnTypeA")), qname(sysB, Name.identifier("ReturnTypeB")))
                .nodeCommonality(Name.identifier("SuperService"), qname(sysA, Name.identifier("Service")), qname(sysB, Name.identifier("Service")))
                .nodeCommonality(Name.identifier("op1"),
                        qname(sysA, Name.identifier("op1A").prefixWith(Name.identifier("Service"))),
                        qname(sysB, Name.identifier("op1B").prefixWith(Name.identifier("Service"))))
                .identification(Name.identifier("ReturnType"))
                .identification(Name.identifier("op1"))
                .identification(Name.identifier("SuperService"))
                .build();
        assertEquals(3, attempt1.messages().count());

        Set<Name> actualServiceContainers2 = attempt3.messages().map(MessageType::getGroup)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(MessageContainer::getTypeName)
                .collect(Collectors.toSet());
        HashSet<Name> expectedServiceContainers2 = Sets.newHashSet(
                Name.identifier("Repository").prefixWith(Name.identifier("A")),
                Name.identifier("SuperService").prefixWith(Name.identifier("attempt3")));

        assertEquals(expectedServiceContainers2, actualServiceContainers2);
    }
}
