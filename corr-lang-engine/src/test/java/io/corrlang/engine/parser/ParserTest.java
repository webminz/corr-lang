package io.corrlang.engine.parser;

import com.google.common.collect.Sets;
import io.corrlang.engine.domainmodel.*;
import io.corrlang.engine.parser.ParseException;
import io.corrlang.engine.parser.ParserChain;
import io.corrlang.engine.parser.SyntacticalResult;
import io.corrlang.engine.reporting.PrintStreamReportFacade;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void testImportsWithURLs() throws ParseException {
        String input = "import :STDLIB;\n" +
                "import ./more-rules.etl;\n" +
                "import ../../default/defs.corr;\n" +
                "import /home/user/Documents/a.txt; " +
                "endpoint X { type FILE at file:///y technology ECORE }";
        SyntacticalResult result = ParserChain.parseFromString(input,new PrintStreamReportFacade(System.out), new SyntacticalResult());
        assertTrue(result.getImports().contains(new URLReference(":STDLIB")));
        assertTrue(result.getImports().contains(new URLReference("./more-rules.etl")));
        assertTrue(result.getImports().contains(new URLReference("../../default/defs.corr")));
        assertTrue(result.getImports().contains(new URLReference("/home/user/Documents/a.txt")));
    }

    @Test
    public void testReadOneEndpoint() throws IOException, ParseException {
       String input = "endpoint First {\n" +
                "    type  SERVER\r\n" +
                "    at http://www.my-random-domain.com/public/api/v1.0/graphql\n" +
                "\ttechnology GRAPH_QL\n" +
                "}";
        SyntacticalResult result = ParserChain.parseFromString(input, new PrintStreamReportFacade(System.out), new SyntacticalResult());
        assertTrue(result.getEndpointWithName("First").isPresent());
        Endpoint first = result.getEndpointWithName("First").get();
        assertEquals("First", first.getName());
        assertTrue(first instanceof ServerEndpoint);
    }

    @Test
    public void testTwoEndpoints() throws ParseException {
        String input = "\n" +
                "endpoint First {\n" +
                "    type SERVER\n" +
                "    at http://www.my-random-domain.com/public/api/v1.0/graphql\n" +
                "    technology GRAPH_QL\n" +
                "}\n" +
                "\n" +
                "endpoint Second {\n" +
                "    type FILE\n" +
                "    at file:///home/user/Documents/example/one.xmi\n" +
                "    technology ECORE\n" +
                "    schema file:///home/user/Documents/example/one.ecore\n" +
                "}";
        SyntacticalResult result = ParserChain.parseFromString(input, new PrintStreamReportFacade(System.out), new SyntacticalResult());
        assertTrue(result.getEndpointWithName("First").isPresent());
        assertTrue(result.getEndpointWithName("Second").isPresent());
        assertFalse(result.getEndpointWithName("Third").isPresent());
        Endpoint first = result.getEndpointWithName("First").get();
        Endpoint second = result.getEndpointWithName("Second").get();
        assertEquals("First", first.getName());
        assertEquals("Second", second.getName());
        assertTrue(first instanceof ServerEndpoint);
        assertTrue(second instanceof FileEndpoint);
        assertEquals("GRAPH_QL", first.getTechnology());
        assertEquals("ECORE", second.getTechnology());
        assertEquals(new URLReference("http://www.my-random-domain.com/public/api/v1.0/graphql"), first.getLocationURL());
        assertEquals(new URLReference("file:///home/user/Documents/example/one.xmi"), second.getLocationURL());
        assertEquals(Optional.of(new URLReference("file:///home/user/Documents/example/one.ecore")), second.getSchemaURL());
    }

    @Test
    public void testRule() throws ParseException {
        String input = "rule AllCapitals {\n" +
                "    using OCL '''\n" +
                "       context A inv allCaps:\n" +
                "          self.name->toUpper() = self.name\n" +
                "    '''\n" +
                "}";
        SyntacticalResult result = ParserChain.parseFromString(input, new PrintStreamReportFacade(System.out), new SyntacticalResult());
        assertTrue(result.getRuleWithName("AllCapitals").isPresent());
        ConsistencyRule rule = result.getRuleWithName("AllCapitals").get();
        assertEquals("OCL", rule.getLanguage());
        assertEquals("context A inv allCaps:\n" +
                "          self.name->toUpper() = self.name", rule.getBody());
    }

    @Test
    public void testEmptyCorrspec() throws ParseException {
        String input = "correspondence C (A, B) { }";
        SyntacticalResult result = ParserChain.parseFromString(input, new PrintStreamReportFacade(System.out), new SyntacticalResult());
        assertTrue(result.getCorrSpecWithName("C").isPresent());
        CorrSpec spec = result.getCorrSpecWithName("C").get();
        assertEquals(2, spec.getEndpointsList().size());
        assertTrue(spec.getEndpointsList().contains("A"));
        assertTrue(spec.getEndpointsList().contains("B"));
        assertTrue(spec.getCommonalities().isEmpty());
    }

    @Test
    public void testSimpleCommsCorrspec() throws ParseException {
        String input = "correspondence OO2RDBM (OO,RDBM) {\n" +
                "    relate (OO.ClassDiagram , RDBM.Schema);\n" +
                "    sync (OO.Class , RDBM.Table);\n" +
                "    identify (OO.Attribute,RDBM.Column);\n" +
                "}";
        SyntacticalResult result = ParserChain.parseFromString(input, new PrintStreamReportFacade(System.out), new SyntacticalResult());
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
    public void testHierarchicalCommsCorrspec() throws ParseException {
        String input = "correspondence OO2RDBM (OO,RDBM) {\n" +
                "    relate (OO.ClassDiagram, RDBM.Schema) with {\n" +
                "        sync (OO.ClassDiagram.classes , RDBM.Schema.tables) as entities with {\n" +
                "            identify (OO.ClassDiagram.classes.Attribute ,RDBM.Schema.tables.Column); \n" +
                "        };\n" +
                "    };\n" +
                "}";
        SyntacticalResult result = ParserChain.parseFromString(input, new PrintStreamReportFacade(System.out), new SyntacticalResult());
        assertTrue(result.getCorrSpecWithName("OO2RDBM").isPresent());
        CorrSpec spec = result.getCorrSpecWithName("OO2RDBM").get();
        assertEquals(1, spec.getCommonalities().size());
        Commonality relate = spec.getCommonalities().iterator().next();
        assertTrue(Relation.class.isAssignableFrom(relate.getClass()));
        assertEquals(2, relate.getRelates().size());
        assertTrue(relate.getRelates().contains(new ElementRef("OO", "ClassDiagram")));
        assertTrue(relate.getRelates().contains(new ElementRef("RDBM", "Schema")));
        assertEquals(1, relate.getSubCommonalities().size());
        Commonality sync = relate.getSubCommonalities().iterator().next();
        assertTrue(Synchronisation.class.isAssignableFrom(sync.getClass()));
        assertEquals("entities", sync.getName());
        assertEquals(2, sync.getRelates().size());
        assertEquals(1, sync.getSubCommonalities().size());
        Commonality ident = sync.getSubCommonalities().iterator().next();
        assertTrue(Identification.class.isAssignableFrom(ident.getClass()));
        assertEquals(0, ident.getSubCommonalities().size());
        assertEquals(2, ident.getRelates().size());
        assertTrue(ident.getRelates().contains(new ElementRef("OO", "ClassDiagram", "classes", "Attribute")));
        assertTrue(ident.getRelates().contains(new ElementRef("RDBM", "Schema", "tables" , "Column")));
    }

    @Test
    public void testIdentifyInIdentify() throws ParseException {
        String input = "correspondence Fed (Sales,Invoices,HR) {\n" +
                "\n" +
                "   identify (Sales.Query,Invoices.Query,HR.Query) as Query with {\n" +
                "     identify (Sales.Query.customers,Invoices.Query.clients,HR.Query.employees) as partners;\n" +
                "   };\n" +
                "\n" +
                "   identify (Sales.Customer,Sales.Client,HR.Employee) as Partner;\n" +
                "\n" +
                "}\n";
        SyntacticalResult result = ParserChain.parseFromString(input, new PrintStreamReportFacade(System.out), new SyntacticalResult());
        assertTrue(result.getCorrSpecWithName("Fed").isPresent());
        CorrSpec fed = result.getCorrSpecWithName("Fed").get();
        assertEquals(2, fed.getCommonalities().size());
        Commonality commonality = fed.getCommonalities().stream().filter(comm -> comm.getName().equals("Query")).findFirst().get();
        assertTrue(Identification.class.isAssignableFrom(commonality.getClass()));
        assertEquals(1, commonality.getSubCommonalities().size());
        Commonality next = commonality.getSubCommonalities().iterator().next();
        assertTrue(Identification.class.isAssignableFrom(next.getClass()));
        assertEquals("partners", next.getName());
        assertTrue(next.getRelates().contains(new ElementRef("Sales", "Query", "customers")));
        assertTrue(next.getRelates().contains(new ElementRef("Invoices", "Query", "clients")));
        assertTrue(next.getRelates().contains(new ElementRef("HR", "Query", "employees")));
    }

    @Test
    public void testCommsWithKeysCorrspec() throws ParseException {
        String input = "correspondence C (First,Second) {\n" +
                "\n" +
                "\trelate (First.C, Second.D) as C1 with {\n" +
                "\t   sync (First.C.id,Second.D.identifier);\n" +
                "\t} when (Second.C.name == First.C.firstname ++ First.C.lastname );\n" +
                "\n" +
                "}";
        SyntacticalResult result = ParserChain.parseFromString(input, new PrintStreamReportFacade(System.out), new SyntacticalResult());
        assertTrue(result.getCorrSpecWithName("C").isPresent());
        CorrSpec fed = result.getCorrSpecWithName("C").get();
        assertEquals(1, fed.getCommonalities().size());
        Commonality relate = fed.getCommonalities().iterator().next();
        assertTrue(relate.hasKey());
        ElementCondition key = relate.getKey().get();

        ElementCondition.Identification keyId = new ElementCondition.Identification(new ElementRef("Second", "C", "name"),
                new ElementCondition.ArgumentConcatenation(new ElementRef("First", "C", "firstname"),
                new ElementRef("First", "C", "lastname")));
        assertEquals(keyId, key);
    }

    @Test
    public void testMoreComplicatedKeys() throws ParseException {
        String input = "correspondence C (A,B,D) {\n" +
                "\trelate (A.X,B.Y,D.W) \n" +
                "\twhen (A.X.id == B.Y.identifier || D.W.proxy == true && A.X <~> B.Y ~~> D.W);\n" +
                "}";
        SyntacticalResult result = ParserChain.parseFromString(input, new PrintStreamReportFacade(System.out), new SyntacticalResult());
        CorrSpec fed = result.getCorrSpecWithName("C").get();
        Commonality relate = fed.getCommonalities().iterator().next();
        assertTrue(relate.getKey().isPresent());
        ElementCondition key = relate.getKey().get();
        assertTrue(key instanceof ElementCondition.Alternative);
        ElementCondition.Alternative expected = new ElementCondition.Alternative();
        expected.add(new ElementCondition.Identification(new ElementRef("A","X","id"), new ElementRef("B","Y","identifier")));
        ElementCondition.Conjunction and = new ElementCondition.Conjunction();
        expected.add(and);
        and.add(new ElementCondition.Identification(new ElementRef("D", "W", "proxy"), new ElementCondition.ConstantArgument("true", ElementCondition.ConstantArgument.Type.BOOL)));
        ElementCondition.RelationRule krel1 = new ElementCondition.RelationRule();
        krel1.setLeft(new ElementRef("A", "X"));
        krel1.setDirection(ElementCondition.RelationRule.Direction.SYMM);
        ElementCondition.RelationRule krel2 = new ElementCondition.RelationRule();
        krel2.setLeft(new ElementRef("B","Y"));
        krel2.setDirection(ElementCondition.RelationRule.Direction.LR);
        krel2.setRight(new ElementRef("D", "W"));
        krel1.setRight(krel2);
        and.add(krel1);
        assertEquals(expected, key);
    }

    @Test
    public void testCommsWithRulesCorrspec() throws ParseException {
        String input = "correspondence C (A,B) {\n" +
                "\trelate (A.X,B.Y) as one check allCaps;\n" +
                "\n" +
                "\trelate (A.Z,B.W) as two check {\n" +
                "  using EVL '''\n" +
                "var numMovies = Movie.all.size();\n" +
                "\t\t\tvar numActors = Person.all.size();\n" +
                "\t\t\tvar apm = numActors / numMovies;\n" +
                "\n" +
                "\t\t\tconstraint ValidActors {\n" +
                "   \t\t\t guard : self.persons.size() > apm    \n" +
                "    \t\t check : self.persons.forAll(p | p.satisfies(\"HasValidName\"))\n" +
                "  \t\t\t}" +
                "'''" +
                "\t};\n" +
                "}";
        SyntacticalResult result = ParserChain.parseFromString(input, new PrintStreamReportFacade(System.out), new SyntacticalResult());
        assertTrue(result.getCorrSpecWithName("C").isPresent());
        CorrSpec c = result.getCorrSpecWithName("C").get();
        assertEquals(2, c.getCommonalities().size());
        Commonality first = c.getCommonalities().stream().filter(comm -> comm.getName().equals("one")).findFirst().get();
        Commonality second = c.getCommonalities().stream().filter(comm -> comm.getName().equals("two")).findFirst().get();
        assertEquals(Optional.of("allCaps"), first.getConsistencyRuleName());
        ConsistencyRule consistencyRule = second.getConsistencyRule().get();
        String onlyEVL = "var numMovies = Movie.all.size();\n" +
                "\t\t\tvar numActors = Person.all.size();\n" +
                "\t\t\tvar apm = numActors / numMovies;\n" +
                "\n" +
                "\t\t\tconstraint ValidActors {\n" +
                "   \t\t\t guard : self.persons.size() > apm    \n" +
                "    \t\t check : self.persons.forAll(p | p.satisfies(\"HasValidName\"))\n" +
                "  \t\t\t}";
        assertEquals(onlyEVL,consistencyRule.getBody());
    }

    @Test
    public void testMultipleSubComms() throws ParseException {
        String input =
                "correspondence Fed (Sales,Invoices,HR) {\n" +
                "identify (Sales.Customer,Invoices.Client,HR.Employee) as Partner\n" +
                "         with {\n" +
                "             identify (Sales.Customer.id,Invoices.Client.id,HR.Employee.id) as id;\n" +
                "             identify (Sales.Customer.name,Invoices.Client.name) as name;\n" +
                "             identify (Sales.Customer.address,Invoices.Client.address) as address;\n" +
                "         };\n" +
                "}";
        SyntacticalResult result = ParserChain.parseFromString(input, new PrintStreamReportFacade(System.out), new SyntacticalResult());
        assertTrue(result.getCorrSpecWithName("Fed").isPresent());
        CorrSpec fed = result.getCorrSpecWithName("Fed").get();
        assertTrue(fed.getCommonalityWithName("Partner").isPresent());
        Commonality partner = fed.getCommonalityWithName("Partner").get();
        assertEquals(3, partner.getSubCommonalities().size());
        assertEquals(Sets.newHashSet("id", "name", "address"), partner.getSubCommonalities().stream().map(CorrLangElement::getName).collect(Collectors.toSet()));

    }

    @Test
    public void testMultipleCorrspecs() throws ParseException {
        String input = "correspondence First (A,B,C,D) {\n" +
                "\trelate (A.a,B.b,C.c,D.d);\n" +
                "}\n" +
                "correspondence Second (X,Y,Z) {\n" +
                "\tsync (X.x1,Y.y,Z.w,X.x2) as a;\n" +
                "}";
        SyntacticalResult result = ParserChain.parseFromString(input, new PrintStreamReportFacade(System.out), new SyntacticalResult());
        assertTrue(result.getCorrSpecWithName("First").isPresent());
        assertTrue(result.getCorrSpecWithName("Second").isPresent());
        CorrSpec first = result.getCorrSpecWithName("First").get();
        CorrSpec second = result.getCorrSpecWithName("Second").get();
        assertEquals(4,first.getEndpointsList().size());
        assertEquals(3,second.getEndpointsList().size());
        assertEquals(4,second.getCommonalities().iterator().next().getRelates().size());

    }

    @Test
    public void testParseGoal() throws Throwable {
        String input = "goal GraphQLGlobal {\n" +
                "\tcorrespondence Fed\n" +
                "\taction FEDERATION\n" +
                "\ttechnology GRAPH_QL\n" +
                "\ttarget SERVER {\n" +
                "\t\tcontextPath graphql/\n" +
                "\t\tport 8081\n" +
                "\t}\n" +
                "}";
        SyntacticalResult result = ParserChain.parseFromString(input, new PrintStreamReportFacade(System.out), new SyntacticalResult());
        assertTrue(result.getGoalWithName("GraphQLGlobal").isPresent());
        Goal goal = result.getGoalWithName("GraphQLGlobal").get();
        assertEquals(Goal.Action.FEDERATION, goal.getAction());
        assertEquals("GRAPH_QL", goal.getTechnology());
        assertEquals("Fed", goal.getCorrespondenceName());
        goal.forTarget(new GoalTarget.Visitor() {
            @Override
            public void handle(GoalTarget.ServerRuntime serverRuntime) throws Exception {
                assertEquals("graphql/", serverRuntime.getContextPath());
                assertEquals(8081, serverRuntime.getPort());
            }

            @Override
            public void handle(GoalTarget.CodeGeneration codeGeneration) throws Exception {
                fail();
            }

            @Override
            public void handle(GoalTarget.FileCreation fileCreation) throws Exception {
                fail();
            }

            @Override
            public void handle(GoalTarget.Batch batch) throws Exception {
                fail();
            }
        });
    }

    @Test
    public void testParsePNGGoal() throws ParseException {
        String input = "goal GQLPlot {\n" +
                "    correspondence Fed\n" +
                "    action SCHEMA\n" +
                "\ttechnology PNG\n" +
                "\ttarget FILE {\n" +
                "\t\tat ./visualisation.png\n" +
                "\t}\n" +
                "}";
        SyntacticalResult result = ParserChain.parseFromString(input, new PrintStreamReportFacade(System.out), new SyntacticalResult());
        assertTrue(result.getGoalWithName("GQLPlot").isPresent());
        Goal goal = result.getGoalWithName("GQLPlot").get();
        assertEquals(Goal.Action.SCHEMA, goal.getAction());
        assertEquals("PNG", goal.getTechnology());

    }


    @Test
    public void testSyntaxError() {
        String input = "correspondence C (A,B {\n" +
                "\trelate A.x, B.y as z\n" +
                "}";
        try {
            SyntacticalResult result = ParserChain.parseFromString(input, new PrintStreamReportFacade(System.out), new SyntacticalResult());
            fail();
        } catch (ParseException p) {
            assertEquals(3, p.getRecognitionExceptions().size());
            assertEquals("Location: line: 1 column: 22 details: extraneous input '{' expecting {',', ')'}", p.getRecognitionExceptions().get(0));

        }
    }

    @Test
    public void testWithAlternativeKeys() throws ParseException {
        String input = "correspondence C (A,B) {\n" +
                "\t\n" +
                "\trelate (A.X,B.Y) \n" +
                "\twhen (A.x.id == B.Y.key ++ \"suffix\" || A.x.id == B.Y.identifier ++ \"suffix\" );\n" +
                "}";
        SyntacticalResult result = ParserChain.parseFromString(input, new PrintStreamReportFacade(System.out), new SyntacticalResult());
        Commonality c = result.getCorrSpecWithName("C").get().getCommonalities().iterator().next();
        assertTrue(c.getKey().isPresent());

        ElementCondition.Alternative alternative = new ElementCondition.Alternative();
        ElementCondition.Identification lhs = new ElementCondition.Identification(
                new ElementRef("A", "x", "id"),
                new ElementCondition.ArgumentConcatenation(
                        new ElementRef("B", "Y", "key"),
                        new ElementCondition.ConstantArgument("suffix", ElementCondition.ConstantArgument.Type.STRING)));
        ElementCondition.Identification rhs = new ElementCondition.Identification(
                new ElementRef("A", "x", "id"),
                new ElementCondition.ArgumentConcatenation(
                        new ElementRef("B", "Y", "identifier"),
                        new ElementCondition.ConstantArgument("suffix", ElementCondition.ConstantArgument.Type.STRING)));
        alternative.add(lhs);
        alternative.add(rhs);
        assertEquals(alternative, c.getKey().get());
    }

    @Test
    public void testFamPersonsKey() throws ParseException {
        String input = "correspondence Families2Persons (Families,Persons) {\n" +
                "\t\n" +
                "\n" +
                "sync (Families.FamilyMember as fm , Persons.Male as m) as syncMale \n" +
                "      when (fm.name ++ \" \" ++ fm.fatherInverse.name == m.name ||  fm.name  ++ \" \" ++ fm.sonsInverse.name == m.name );\n" +
                "\n" +
                "sync (Families.FamilyMember as fm, Persons.Female as f) as syncFemale\n" +
                "\t  when (fm.name ++ \" \" ++  fm.motherInverse.name == f.name ||  fm.name  ++ \" \" ++ fm.daughtersInverse.name == f.name );\n" +
                "\n" +
                "}\n";

        ElementCondition.Alternative alternative = new ElementCondition.Alternative();
        ElementCondition.Identification lhs = new ElementCondition.Identification(
                new ElementCondition.ArgumentConcatenation(
                        new ElementRef("fm", "name"),
                        new ElementCondition.ConstantArgument(" ", ElementCondition.ConstantArgument.Type.STRING),
                        new ElementRef("fm", "fatherInverse", "name")
                ),
                new ElementRef("m", "name"));
        ElementCondition.Identification rhs = new ElementCondition.Identification(
                new ElementCondition.ArgumentConcatenation(
                        new ElementRef("fm", "name"),
                        new ElementCondition.ConstantArgument(" ", ElementCondition.ConstantArgument.Type.STRING),
                        new ElementRef("fm", "sonsInverse", "name")
                ),
                new ElementRef("m", "name"));
        alternative.add(lhs);
        alternative.add(rhs);
        SyntacticalResult result = ParserChain.parseFromString(input, new PrintStreamReportFacade(System.out), new SyntacticalResult());
        Collection<Commonality> coms = result.getCorrSpecWithName("Families2Persons").get().getCommonalities();
        Commonality suncMale = coms.iterator().next();

        assertEquals(alternative, suncMale.getKey().get());


    }



}
