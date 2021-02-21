package no.hvl.past.corrlang.parser;

import no.hvl.past.corrlang.domainmodel.*;
import no.hvl.past.corrlang.reporting.ReportFacade;
import no.hvl.past.corrlang.referencing.URLReference;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ParserTest {

    @Test
    public void testImportsWithURLs() {
        String input = "import :STDLIB;\n" +
                "import ./more-rules.etl;\n" +
                "import ../../default/defs.corr;\n" +
                "import /home/user/Documents/a.txt; " +
                "endpoint X { type file at file:///y technology ECORE }";
        SyntacticalResult result = ParserChain.parseFromString(input,new ReportFacade(System.out), new SyntacticalResult());
        assertTrue(result.getImports().contains(new URLReference(":STDLIB")));
        assertTrue(result.getImports().contains(new URLReference("./more-rules.etl")));
        assertTrue(result.getImports().contains(new URLReference("../../default/defs.corr")));
        assertTrue(result.getImports().contains(new URLReference("/home/user/Documents/a.txt")));
    }

    @Test
    public void testReadOneEndpoint() throws IOException {
       String input = "endpoint First {\n" +
                "    type  server\r\n" +
                "    at http://www.my-random-domain.com/public/api/v1.0/graphql\n" +
                "\ttechnology GRAPH_QL\n" +
                "}";
        SyntacticalResult result = ParserChain.parseFromString(input, new ReportFacade(System.out), new SyntacticalResult());
        assertTrue(result.getEndpointWithName("First").isPresent());
        Endpoint first = result.getEndpointWithName("First").get();
        assertEquals("First", first.getName());
        assertTrue(first instanceof ServerEndpoint);
    }

    @Test
    public void testTwoEndpoints() {} {
        String input = "\n" +
                "endpoint First {\n" +
                "    type server\n" +
                "    at http://www.my-random-domain.com/public/api/v1.0/graphql\n" +
                "    technology GRAPH_QL\n" +
                "}\n" +
                "\n" +
                "endpoint Second {\n" +
                "    type file\n" +
                "    at file:///home/user/Documents/example/one.xmi\n" +
                "    technology ECORE\n" +
                "    schema file:///home/user/Documents/example/one.ecore\n" +
                "}";
        SyntacticalResult result = ParserChain.parseFromString(input, new ReportFacade(System.out), new SyntacticalResult());
        assertTrue(result.getEndpointWithName("First").isPresent());
        assertTrue(result.getEndpointWithName("Second").isPresent());
        assertFalse(result.getEndpointWithName("Third").isPresent());
        Endpoint first = result.getEndpointWithName("First").get();
        Endpoint second = result.getEndpointWithName("Second").get();
        assertEquals("First", first.getName());
        assertEquals("Second", second.getName());
        assertTrue(first instanceof ServerEndpoint);
        assertTrue(second instanceof FileEndpoint);
        assertEquals(Platform.GRAPH_QL, first.getTechnology());
        assertEquals(Platform.ECORE, second.getTechnology());
        assertEquals(new URLReference("http://www.my-random-domain.com/public/api/v1.0/graphql"), first.getLocationURL());
        assertEquals(new URLReference("file:///home/user/Documents/example/one.xmi"), second.getLocationURL());
        assertEquals(Optional.of(new URLReference("file:///home/user/Documents/example/one.ecore")), second.getSchemaURL());
    }

    @Test
    public void testRule() {
        String input = "rule AllCapitals {\n" +
                "    using OCL \"\"\"\n" +
                "       context A inv allCaps:\n" +
                "          self.name->toUpper() = self.name\n" +
                "    \"\"\"\n" +
                "}";
        SyntacticalResult result = ParserChain.parseFromString(input, new ReportFacade(System.out), new SyntacticalResult());
        assertTrue(result.getRuleWithName("AllCapitals").isPresent());
        ConsistencyRule rule = result.getRuleWithName("AllCapitals").get();
        assertEquals(ConsistencyRule.ConsistencyRuleLanguage.OCL, rule.getLanguage());
        assertEquals("context A inv allCaps:\n" +
                "          self.name->toUpper() = self.name", rule.getBody());
    }

    @Test
    public void testEmptyCorrspec() {
        String input = "correspondence C (A, B) { }";
        SyntacticalResult result = ParserChain.parseFromString(input, new ReportFacade(System.out), new SyntacticalResult());
        assertTrue(result.getCorrSpecWithName("C").isPresent());
        CorrSpec spec = result.getCorrSpecWithName("C").get();
        assertEquals(2, spec.getEndpointsList().size());
        assertTrue(spec.getEndpointsList().contains("A"));
        assertTrue(spec.getEndpointsList().contains("B"));
        assertTrue(spec.getCommonalities().isEmpty());
    }

    @Test
    public void testSimpleCommsCorrspec() {
        String input = "correspondence OO2RDBM (OO,RDBM) {\n" +
                "    relate (OO.ClassDiagram , RDBM.Schema);\n" +
                "    sync (OO.Class , RDBM.Table);\n" +
                "    identify (OO.Attribute,RDBM.Column);\n" +
                "}";
        SyntacticalResult result = ParserChain.parseFromString(input, new ReportFacade(System.out), new SyntacticalResult());
        assertTrue(result.getCorrSpecWithName("OO2RDBM").isPresent());
        CorrSpec spec = result.getCorrSpecWithName("OO2RDBM").get();
        assertEquals(3, spec.getCommonalities().size());
        Commonality cd2s = spec.getCommonalities().stream().filter(x -> x instanceof Relation).findFirst().get();
        Commonality c2t = spec.getCommonalities().stream().filter(x -> x instanceof Synchronisation).findFirst().get();
        Commonality att2col = spec.getCommonalities().stream().filter(x -> x instanceof Identification).findFirst().get();

        List<ElementRef> expecteed = new ArrayList<>();
        expecteed.add(new ElementRef("OO", "ClassDiagram"));
        expecteed.add(new ElementRef("RDBM", "Schema"));
        assertEquals(expecteed, cd2s.getRelates());

        expecteed = new ArrayList<>();
        expecteed.add(new ElementRef("OO", "Class"));
        expecteed.add(new ElementRef("RDBM", "Table"));
        assertEquals(expecteed, c2t.getRelates());

        expecteed = new ArrayList<>();
        expecteed.add(new ElementRef("OO", "Attribute"));
        expecteed.add(new ElementRef("RDBM", "Column"));
        assertEquals(expecteed, att2col.getRelates());

    }

    @Test
    public void testHierarchicalCommsCorrspec() {
        String input = "correspondence OO2RDBM {\n" +
                "    relate (OO.ClassDiagram, RDBM.Schema) with {\n" +
                "        sync (OO.ClassDiagram.classes , RDBM.Schema.tables) as entities with {\n" +
                "            identify (OO.ClassDiagram.classes.Column ,RDBM.Schema.tables.column); \n" +
                "        };\n" +
                "    };\n" +
                "}";
        SyntacticalResult result = ParserChain.parseFromString(input, new ReportFacade(System.out), new SyntacticalResult());
        assertTrue(result.getCorrSpecWithName("OO2RDBM").isPresent());
        CorrSpec spec = result.getCorrSpecWithName("OO2RDBM").get();
        assertEquals(1, spec.getCommonalities().size());
        // TODO
    }

    @Test
    public void testMultiaryCommsCorrspec() {
        // TODO
    }

    @Test
    public void testCommsWithKeysCorrspec() {
        // TODO
    }

    @Test
    public void testCommsWithRulesCorrspec() {
        // TODO
    }

    @Test
    public void testMultipleCorrspecs() {
        // TODO
    }

    @Test
    public void testFullCircle() throws IOException {
        SyntacticalResult result = ParserChain.parseFromFile(this.getClass().getClassLoader().getResource("./").getFile() + "test1.corr", new ReportFacade(System.out), new SyntacticalResult());
        assertTrue(result.getEndpointWithName("First").isPresent());
        assertTrue(result.getEndpointWithName("Second").isPresent());
        assertFalse(result.getEndpointWithName("Third").isPresent());
        Endpoint first = result.getEndpointWithName("First").get();
        Endpoint second = result.getEndpointWithName("Second").get();
        assertEquals("First", first.getName());
        assertEquals("Second", second.getName());
        assertTrue(first instanceof ServerEndpoint);
        assertTrue(second instanceof FileEndpoint);
        assertEquals(Platform.GRAPH_QL, first.getTechnology());
        assertEquals(Platform.ECORE, second.getTechnology());
        assertEquals(new URLReference("http://www.my-random-domain.com/public/api/v1.0/graphql"), first.getLocationURL());
        assertEquals(new URLReference("file:///home/user/Documents/example/one.xmi"), second.getLocationURL());
        assertEquals(Optional.of(new URLReference("file:///home/user/Documents/example/one.ecore")), second.getSchemaURL());

        assertTrue(result.getCorrSpecWithName("C").isPresent());
        CorrSpec spec = result.getCorrSpecWithName("C").get();
        assertEquals(3, spec.getCommonalities().size());

        assertTrue(result.getRuleWithName("AllCapitals").isPresent());
        ConsistencyRule rule = result.getRuleWithName("AllCapitals").get();
        assertEquals(ConsistencyRule.ConsistencyRuleLanguage.OCL, rule.getLanguage());
        assertEquals("\"\"\"xyz\"\"\"", rule.getBody());
    }

}
