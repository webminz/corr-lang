package no.hvl.past.corrlang.execution;

import no.hvl.past.corrlang.domainmodel.*;
import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.corrlang.reporting.PrintStreamReportFacade;
import no.hvl.past.di.TestWithDIContainer;
import no.hvl.past.graph.elements.Triple;
import io.corrlang.domain.keys.AttributeBasedKey;
import io.corrlang.domain.keys.ConcatenatedKey;
import io.corrlang.domain.keys.ConstantKey;
import no.hvl.past.names.Name;
import io.corrlang.domain.ComprSys;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;

import static junit.framework.TestCase.*;

public class FormalAlignmentTest extends TestWithDIContainer {


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

        ExecutionFacade executionFacade = new ExecutionFacade(getDiContainer(), new PrintStreamReportFacade(System.out));
        executionFacade.execute("ALIGN_SCHEMA", dm);

        // check that dependent traverseers have run as well
        assertEquals(invoices, fed.getEndpointRefs().get("Invoices")); // ep linking in corrspec
        assertEquals(sales, sync.getRelates().get(0).getEndpoint()); // ep linking in elemRef
        assertTrue(sync.getRelates().get(0).getElement().isPresent()); // elemRef linking

        // FormalAlignment was run
        assertNotNull(fed.getComprSys());

        // test if members are built correctly
        ComprSys compSys = fed.getComprSys();




        assertTrue(compSys.schema().carrier().mentions(Name.identifier("Partner").prefixWith(Name.identifier("Fed"))));
        assertTrue(compSys.schema().carrier().mentions(Name.identifier("sync").prefixWith(Name.identifier("Fed"))));
        assertEquals(Name.identifier("Purchase"), compSys.projection(sales.getSystem().get(), Name.identifier("sync")).get());
        assertEquals(Name.identifier("Invoice"), compSys.projection(invoices.getSystem().get(), Name.identifier("sync")).get());
        assertFalse(compSys.projection(hr.getSystem().get(), Name.identifier("sync").prefixWith(Name.identifier("Fed"))).isPresent());

        Triple edge = compSys.schema().carrier().get(Name.identifier("id").prefixWith(Name.identifier("Partner")).prefixWith(Name.identifier("Fed"))).get();
        assertEquals(Name.identifier("Partner").prefixWith(Name.identifier("Fed")), edge.getSource());
        assertEquals(ComprSys.GLOBAL_INT_NAME.prefixWith(Name.identifier("Fed")), edge.getTarget());

//        // implicit identities
        assertTrue(compSys.schema().carrier().mentions(ComprSys.GLOBAL_STRING_NAME.prefixWith(Name.identifier("Fed"))));
        assertTrue(compSys.schema().carrier().mentions(ComprSys.GLOBAL_FLOAT_NAME.prefixWith(Name.identifier("Fed"))));
        assertFalse(compSys.schema().carrier().mentions(ComprSys.GLOBAL_BOOL_NAME.prefixWith(Name.identifier("Fed"))));

        Set<io.corrlang.domain.keys.Key> formalKeys = fed.getFormalKeys();
        assertEquals(4, formalKeys.size());

        AttributeBasedKey k1 = new AttributeBasedKey(
                sales.getSystem().get(),
                Triple.edge(Name.identifier("Customer"), Name.identifier("id"), Name.identifier("Integer")),
                Name.identifier("Partner"));
        assertTrue(formalKeys.contains(k1));
        AttributeBasedKey k2 = new AttributeBasedKey(
                invoices.getSystem().get(),
                Triple.edge(Name.identifier("Client"), Name.identifier("id"), Name.identifier("Integer")),
                Name.identifier("Partner"));
        assertTrue(formalKeys.contains(k2));
        AttributeBasedKey k3 = new AttributeBasedKey(
                sales.getSystem().get(),
                Triple.edge(Name.identifier("Customer"), Name.identifier("fullName"), Name.identifier("String")),
                Name.identifier("Partner"));
        assertTrue(formalKeys.contains(k3));
        ConcatenatedKey concatenatedKey = new ConcatenatedKey(
                Name.identifier("Partner"),
                Name.identifier("Employee"),
                hr.getSystem().get(),
                Arrays.asList(
                        new AttributeBasedKey(
                                hr.getSystem().get(),
                                Triple.edge(Name.identifier("Employee"), Name.identifier("firstname"), Name.identifier("String")),
                                Name.identifier("Partner")),
                        new ConstantKey(Name.value(" "), Name.identifier("Partner")),
                        new AttributeBasedKey(
                                hr.getSystem().get(),
                                Triple.edge(Name.identifier("Employee"), Name.identifier("lastname"), Name.identifier("String")),
                                Name.identifier("Partner"))
        ));
        assertTrue(formalKeys.contains(concatenatedKey));
    }
}
