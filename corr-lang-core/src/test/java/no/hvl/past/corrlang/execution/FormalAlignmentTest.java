package no.hvl.past.corrlang.execution;

import no.hvl.past.corrlang.domainmodel.*;
import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.di.DependencyInjectionContainer;
import no.hvl.past.graph.Star;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.keys.AttributeBasedKey;
import no.hvl.past.keys.ConcatenatedKey;
import no.hvl.past.keys.ConstantKey;
import no.hvl.past.names.Name;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static junit.framework.TestCase.*;

public class FormalAlignmentTest {

    private DependencyInjectionContainer container;

    @Before
    public void setUp() throws IOException {
        container = DependencyInjectionContainer.create();
    }

    @Test
    public void testCreateAlignment() {
        Endpoint sales = new ServerEndpoint("Sales");
        sales.setTechnology("TEST_A");
        sales.setLocationURL("http://sales");
        Endpoint invoices = new ServerEndpoint("Invoices");
        invoices.setTechnology("TEST_A");
        invoices.setLocationURL("http://invoices");
        Endpoint hr = new ServerEndpoint("HR");
        hr.setTechnology("TEST_A");
        hr.setLocationURL("http://hr");

        CorrSpec fed = new CorrSpec("Fed");
        fed.addEndpoint("Sales");
        fed.addEndpoint("Invoices");
        fed.addEndpoint("HR");

        Identification rel = new Identification();
        rel.setName("Partner");
        rel.addRelatedElement(new ElementRef("Sales","Customer"));
        rel.addRelatedElement(new ElementRef("Invoices","Client"));
        rel.addRelatedElement(new ElementRef("HR","Employee"));

        ElementCondition.Alternative key = new ElementCondition.Alternative();
        ElementCondition.Identification id1 = new ElementCondition.Identification(
                new ElementRef("Sales", "Customer", "id"),
                new ElementRef("Invoices", "Client", "id"));
        ElementCondition.Identification id2 = new ElementCondition.Identification(
                new ElementRef("Sales", "Customer", "fullName"),
                new ElementCondition.ArgumentConcatenation(
                        new ElementRef("HR", "Employee", "firstname"),
                        new ElementCondition.ConstantArgument(" ", ElementCondition.ConstantArgument.Type.STRING),
                        new ElementRef("HR", "Employee", "lastname")
                ));
        key.add(id1);
        key.add(id2);

        Identification idAttRel = new Identification();
        idAttRel.setName("id");
        idAttRel.addRelatedElement(new ElementRef("Sales", "Customer", "id"));
        idAttRel.addRelatedElement(new ElementRef("Invoices", "Client", "id"));

        rel.addSubCommonality(idAttRel);
        rel.setKey(key);
        fed.addCommonality(rel);

        Synchronisation sync = new Synchronisation();
        sync.setName("sync");
        sync.addRelatedElement(new ElementRef("Sales","Purchase"));
        sync.addRelatedElement(new ElementRef("Invoices","Invoice"));
        fed.addCommonality(sync);

        SyntacticalResult dm = new SyntacticalResult();
        dm.addEndpoint(sales);
        dm.addEndpoint(invoices);
        dm.addEndpoint(hr);
        dm.addCorrspec(fed);

        assertFalse(fed.getFormalRepresentation().isPresent());

        ExecutionFacade executionFacade = new ExecutionFacade(container);
        executionFacade.execute("ALIGN_SCHEMA", dm);

        // check that dependent traverseers have run as well
        assertEquals(invoices, fed.getEndpointRefs().get("Invoices")); // ep linking in corrspec
        assertEquals(sales, sync.getRelates().get(0).getEndpoint()); // ep linking in elemRef
        assertTrue(sync.getRelates().get(0).getElement().isPresent()); // elemRef linking

        // FormalAlignment was run
        assertTrue(fed.getFormalRepresentation().isPresent());

        // test if members are built correctly

        Star star = fed.getFormalRepresentation().get();
        Set<Name> commWitns = star.apex().carrier().nodes().collect(Collectors.toSet());
        assertEquals(5, commWitns.size());
        assertTrue(commWitns.contains(Name.identifier("Partner")));
        assertTrue(commWitns.contains(Name.identifier("sync")));
        assertEquals(Name.identifier("Purchase"), star.projection(1).get().map(Name.identifier("sync")).get());
        assertEquals(Name.identifier("Invoice"), star.projection(2).get().map(Name.identifier("sync")).get());
        assertFalse(star.projection(3).get().map(Name.identifier("sync")).isPresent());
        Set<Triple> edges = star.apex().carrier().edges().collect(Collectors.toSet());
        assertEquals(1, edges.size());
        Triple edge = edges.iterator().next();
        assertEquals(Name.identifier("Partner"), edge.getSource());
        assertEquals(Name.identifier("id"), edge.getLabel());
        assertEquals(Name.identifier("INTEGER_NUMBER_TYPE_IMPLICIT_IDENTITY"), edge.getTarget());
        // implicit identities
        assertTrue(commWitns.contains(Name.identifier("STRING_TYPE_IMPLICIT_IDENTITY")));
        assertTrue(commWitns.contains(Name.identifier("FLOATING_POINT_NUMBER_TYPE_IMPLICIT_IDENTITY")));
        assertTrue(commWitns.contains(Name.identifier("INTEGER_NUMBER_TYPE_IMPLICIT_IDENTITY")));

        Set<no.hvl.past.keys.Key> formalKeys = fed.getFormalKeys();
        assertEquals(4, formalKeys.size());

        AttributeBasedKey k1 = new AttributeBasedKey(
                fed.getComprehensiveSchema().get().carrier(),
                Triple.edge(
                Name.identifier("Customer"), Name.identifier("id"), Name.identifier("Integer"
                )), Name.identifier("Partner"));
        assertTrue(formalKeys.contains(k1));
        AttributeBasedKey k2 = new AttributeBasedKey(
                fed.getComprehensiveSchema().get().carrier(),
                Triple.edge(
                        Name.identifier("Client"), Name.identifier("id"), Name.identifier("Integer"
                        )), Name.identifier("Partner"));
        assertTrue(formalKeys.contains(k2));
        AttributeBasedKey k3 = new AttributeBasedKey(
                fed.getComprehensiveSchema().get().carrier(),
                Triple.edge(
                        Name.identifier("Customer"), Name.identifier("fullName"), Name.identifier("String"
                        )), Name.identifier("Partner"));
        assertTrue(formalKeys.contains(k3));
        ConcatenatedKey concatenatedKey = new ConcatenatedKey(fed.getComprehensiveSchema().get().carrier(), Name.identifier("Partner"), Arrays.asList(
                new AttributeBasedKey(
                        fed.getComprehensiveSchema().get().carrier(),
                        Triple.edge(
                                Name.identifier("Employee"), Name.identifier("firstname"), Name.identifier("String"
                                )), Name.identifier("Partner")),
                new ConstantKey(fed.getComprehensiveSchema().get().carrier(),Name.value(" "), Name.identifier("Partner")),
                new AttributeBasedKey(
                        fed.getComprehensiveSchema().get().carrier(),
                        Triple.edge(
                                Name.identifier("Employee"), Name.identifier("lastname"), Name.identifier("String"
                                )), Name.identifier("Partner"))
        ));
        assertTrue(formalKeys.contains(concatenatedKey));
    }
}
