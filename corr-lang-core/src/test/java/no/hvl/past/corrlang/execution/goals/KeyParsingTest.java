package no.hvl.past.corrlang.execution.goals;

import no.hvl.past.corrlang.domainmodel.*;
import no.hvl.past.corrlang.execution.traverser.CreateFormalAlignmentTraverser;
import no.hvl.past.corrlang.execution.traverser.DesugarAliases;
import no.hvl.past.corrlang.execution.traverser.LinkElementsTraverser;
import no.hvl.past.corrlang.execution.traverser.LinkEndpointsTraverser;
import no.hvl.past.corrlang.parser.ParserChain;
import no.hvl.past.corrlang.parser.SyntacticalResult;
import no.hvl.past.corrlang.reporting.PrintStreamReportFacade;
import no.hvl.past.graph.GraphExampleLibrary;
import no.hvl.past.graph.GraphTest;
import no.hvl.past.graph.elements.Triple;
import no.hvl.past.keys.AttributeBasedKey;
import no.hvl.past.keys.ConcatenatedKey;
import no.hvl.past.keys.ConstantKey;
import no.hvl.past.keys.Key;
import no.hvl.past.names.Identifier;
import no.hvl.past.names.Name;
import no.hvl.past.systems.ComprSys;
import no.hvl.past.systems.Sys;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static no.hvl.past.systems.QualifiedName.qname;

public class KeyParsingTest extends GraphTest {


    Sys families;
    Sys persons;

    @Before
    public void setUp() throws Exception {
        GraphExampleLibrary.INSTANCE.initialize(getContextCreatingBuilder());


        families = new Sys.Builder("families", GraphExampleLibrary.INSTANCE.Families).build();
        persons = new Sys.Builder("persons", GraphExampleLibrary.INSTANCE.Persons).build();

    }

    @Test
    public void testFamiliesAndPersonsKey() throws Throwable {
        String spec = "correspondence Families2Persons (Families,Persons) {\n" +
                "\t\n" +
                "\n" +
                "sync (Families.FamilyMember as fm , Persons.Male as m) as syncMale \n" +
                "      when (fm.name ++ \" \" ++ fm.fatherInverse.name == m.name ||  fm.name ++ \" \" ++ fm.sonsInverse.name == m.name );\n" +
                "\n" +
                "sync (Families.FamilyMember as fm, Persons.Female as f) as syncFemale\n" +
                "\t  when (fm.name ++ \" \" ++ fm.motherInverse.name == f.name ||  fm.name ++ \" \" ++ fm.daughtersInverse.name == f.name );\n" +
                "\n" +
                "}\n";
        SyntacticalResult result = ParserChain.parseFromString(spec, new PrintStreamReportFacade(System.out), new SyntacticalResult());

        new DesugarAliases().executeTransitive(result);

        CorrSpec families2Persons = result.getCorrSpecWithName("Families2Persons").get();
        Endpoint famEndpoint = new FileEndpoint("Families");
        famEndpoint.setSystem(families);

        Endpoint persEndpoint = new FileEndpoint("Persons");
        persEndpoint.setSystem(persons);
        result.addEndpoint(famEndpoint);
        result.addEndpoint(persEndpoint);

        new LinkEndpointsTraverser().executeTransitive(result);
        new LinkElementsTraverser().executeTransitive(result);

        Identification stringId = new Identification();
        stringId.setName("String");
        ElementRef famString = new ElementRef("Families", "String");
        famString.setEndpoint(famEndpoint);
        famString.setElement(Triple.node(Name.identifier("String")));
        ElementRef persString = new ElementRef("Persons", "String");
        persString.setEndpoint(persEndpoint);
        persString.setElement(Triple.node(Name.identifier("String")));
        stringId.getRelates().add(famString);
        stringId.getRelates().add(persString);
        families2Persons.getCommonalities().add(stringId);

        CreateFormalAlignmentTraverser createFormalAlignmentTraverser = new CreateFormalAlignmentTraverser();
        createFormalAlignmentTraverser.setUniverse(universe);
        createFormalAlignmentTraverser.executeTransitive(result);


        Identifier f2pName = Name.identifier("Families2Persons");
        ComprSys expected = new ComprSys.Builder(f2pName, universe)
                .addSystem(families)
                .addSystem(persons)
                .nodeCommonality(Name.identifier("String"), qname(families, Name.identifier("String")), qname(persons, Name.identifier("String")))
                .nodeCommonality(Name.identifier("syncMale"), qname(families, Name.identifier("FamilyMember")), qname(persons, Name.identifier("Male")))
                .nodeCommonality(Name.identifier("syncFemale"), qname(families, Name.identifier("FamilyMember")), qname(persons, Name.identifier("Female")))
                .synchronisation(Name.identifier("syncMale"))
                .synchronisation(Name.identifier("syncFemale"))
                .identification(Name.identifier("String"))
                .key(new AttributeBasedKey(persons, Name.identifier("syncMale"), Triple.node(Name.identifier("Male")), Triple.edge(Name.identifier("Person"), Name.identifier("name").prefixWith(Name.identifier("Person")), Name.identifier("String"))))
                .key(new AttributeBasedKey(persons, Name.identifier("syncFemale"), Triple.node(Name.identifier("Female")), Triple.edge(Name.identifier("Person"), Name.identifier("name").prefixWith(Name.identifier("Person")), Name.identifier("String"))))
                .key(new ConcatenatedKey(
                        Name.identifier("syncMale"),
                        Name.identifier("FamilyMember"),
                        families,
                        Arrays.asList(
                                new AttributeBasedKey(
                                        families,
                                        Triple.edge(
                                                Name.identifier("FamilyMember"),
                                                Name.identifier("name").prefixWith(Name.identifier("FamilyMember")),
                                                Name.identifier("String")
                                        ),
                                        Name.identifier("syncMale")),
                                new ConstantKey(Name.value(" "), Name.identifier("syncMale")),
                                new AttributeBasedKey(
                                        families,
                                        Name.identifier("syncMale"),
                                        Triple.node(Name.identifier("FamilyMember")),
                                        Triple.edge(
                                                Name.identifier("FamilyMember"),
                                                Name.identifier("fatherInverse").prefixWith(Name.identifier("FamilyMember")),
                                                Name.identifier("Family")
                                        ),
                                        Triple.edge(
                                                Name.identifier("Family"),
                                                Name.identifier("name").prefixWith(Name.identifier("Family")),
                                                Name.identifier("String")
                                        )))))
                .key(new ConcatenatedKey(
                        Name.identifier("syncMale"),
                        Name.identifier("FamilyMember"),
                        families,
                        Arrays.asList(
                                new AttributeBasedKey(
                                        families,
                                        Triple.edge(
                                                Name.identifier("FamilyMember"),
                                                Name.identifier("name").prefixWith(Name.identifier("FamilyMember")),
                                                Name.identifier("String")
                                        ),
                                        Name.identifier("syncMale")),
                                new ConstantKey(Name.value(" "), Name.identifier("syncMale")),
                                new AttributeBasedKey(
                                        families,
                                        Name.identifier("syncMale"),
                                        Triple.node(Name.identifier("FamilyMember")),
                                        Triple.edge(
                                                Name.identifier("FamilyMember"),
                                                Name.identifier("sonsInverse").prefixWith(Name.identifier("FamilyMember")),
                                                Name.identifier("Family")
                                        ),
                                        Triple.edge(
                                                Name.identifier("Family"),
                                                Name.identifier("name").prefixWith(Name.identifier("Family")),
                                                Name.identifier("String")
                                        )))))
                .key(new ConcatenatedKey(
                        Name.identifier("syncFemale"),
                        Name.identifier("FamilyMember"),
                        families,
                        Arrays.asList(
                                new AttributeBasedKey(
                                        families,
                                        Triple.edge(
                                                Name.identifier("FamilyMember"),
                                                Name.identifier("name").prefixWith(Name.identifier("FamilyMember")),
                                                Name.identifier("String")
                                        ),
                                        Name.identifier("syncFemale")),
                                new ConstantKey(Name.value(" "), Name.identifier("syncFemale")),
                                new AttributeBasedKey(
                                        families,
                                        Name.identifier("syncFemale"),
                                        Triple.node(Name.identifier("FamilyMember")),
                                        Triple.edge(
                                                Name.identifier("FamilyMember"),
                                                Name.identifier("motherInverse").prefixWith(Name.identifier("FamilyMember")),
                                                Name.identifier("Family")
                                        ),
                                        Triple.edge(
                                                Name.identifier("Family"),
                                                Name.identifier("name").prefixWith(Name.identifier("Family")),
                                                Name.identifier("String")
                                        )))))
                .key(new ConcatenatedKey(
                        Name.identifier("syncFemale"),
                        Name.identifier("FamilyMember"),
                        families,
                        Arrays.asList(
                                new AttributeBasedKey(
                                        families,
                                        Triple.edge(
                                                Name.identifier("FamilyMember"),
                                                Name.identifier("name").prefixWith(Name.identifier("FamilyMember")),
                                                Name.identifier("String")
                                        ),
                                        Name.identifier("syncFemale")),
                                new ConstantKey(Name.value(" "), Name.identifier("syncFemale")),
                                new AttributeBasedKey(
                                        families,
                                        Name.identifier("syncFemale"),
                                        Triple.node(Name.identifier("FamilyMember")),
                                        Triple.edge(
                                                Name.identifier("FamilyMember"),
                                                Name.identifier("daughtersInverse").prefixWith(Name.identifier("FamilyMember")),
                                                Name.identifier("Family")
                                        ),
                                        Triple.edge(
                                                Name.identifier("Family"),
                                                Name.identifier("name").prefixWith(Name.identifier("Family")),
                                                Name.identifier("String")
                                        )))))

                .build();


        assertGraphsEqual(expected.schema().carrier(), families2Persons.getComprSys().schema().carrier());

        Set<Key> expectedKeys = expected.relationKeys().collect(Collectors.toSet());
        Set<Key> actualKeys = families2Persons.getComprSys().relationKeys().collect(Collectors.toSet());
        assertEquals(expectedKeys, actualKeys);
    }

}
