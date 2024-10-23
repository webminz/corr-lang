package io.corrlang.systems;

import io.corrlang.domain.ComprData;
import io.corrlang.domain.ComprSys;
import io.corrlang.domain.Data;
import io.corrlang.domain.Sys;
import no.hvl.past.graph.GraphError;
import no.hvl.past.graph.GraphExampleLibrary;
import no.hvl.past.graph.GraphMorphism;

import no.hvl.past.graph.TestWithGraphLib;
import no.hvl.past.graph.elements.Triple;
import io.corrlang.domain.keys.AttributeBasedKey;
import io.corrlang.domain.keys.ConcatenatedKey;
import io.corrlang.domain.keys.ConstantKey;
import no.hvl.past.names.Identifier;
import no.hvl.past.names.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static io.corrlang.domain.QualifiedName.qname;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SynchronisationTest extends TestWithGraphLib {



    private ComprSys familiesAndPersons;
    private Sys families;
    private Sys persons;

    @BeforeEach
    public void setUp() throws Exception {
        GraphExampleLibrary.INSTANCE.initialize(getContextCreatingBuilder());


         families = new Sys.Builder("families", GraphExampleLibrary.INSTANCE.Families).build();
         persons = new Sys.Builder("persons", GraphExampleLibrary.INSTANCE.Persons).build();
        Identifier f2pName = Name.identifier("Families2Persons");
        familiesAndPersons = new ComprSys.Builder(f2pName, universe)
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
    }

    @Test
    public void testCorrectlySynchronisedValid() throws GraphError {
        Data famInstance = Data.fromMorphism(families, getContextCreatingBuilder()
                .edge(Name.identifier("1:Family"), Name.identifier("1:name"), Name.value("Hubermann"))
                .edge(Name.identifier("2:FamilyMember"), Name.identifier("2:name"), Name.value("Hans"))
                .edge(Name.identifier("2:FamilyMember"), Name.identifier("2:fatherInverse"), Name.identifier("1:Family"))
                .edge(Name.identifier("3:FamilyMember"), Name.identifier("3:name"), Name.value("Hertha"))
                .edge(Name.identifier("3:FamilyMember"), Name.identifier("3:motherInverse"), Name.identifier("1:Family"))
                .graph(Name.identifier("Inst1"))
                .codomain(families.schema().carrier())
                .map(Name.identifier("1:Family"), Name.identifier("Family"))
                .map(Name.identifier("2:FamilyMember"), Name.identifier("FamilyMember"))
                .map(Name.identifier("3:FamilyMember"), Name.identifier("FamilyMember"))
                .map(Name.value("Hubermann"), Name.identifier("String"))
                .map(Name.value("Hertha"), Name.identifier("String"))
                .map(Name.value("Hans"), Name.identifier("String"))
                .map(Name.identifier("1:name"), Name.identifier("name").prefixWith(Name.identifier("Family")))
                .map(Name.identifier("2:name"), Name.identifier("name").prefixWith(Name.identifier("FamilyMember")))
                .map(Name.identifier("2:fatherInverse"), Name.identifier("fatherInverse").prefixWith(Name.identifier("FamilyMember")))
                .map(Name.identifier("3:name"), Name.identifier("name").prefixWith(Name.identifier("FamilyMember")))
                .map(Name.identifier("3:motherInverse"), Name.identifier("motherInverse").prefixWith(Name.identifier("FamilyMember")))
                .morphism("Inst1Morph")
                .getResult(GraphMorphism.class));

        Data persInstance = Data.fromMorphism(persons,
                getContextCreatingBuilder()
                .edge(Name.identifier("1:Male"), Name.identifier("1:name"), Name.value("Hans Hubermann"))
                .edge(Name.identifier("2:Female"), Name.identifier("2:name"), Name.value("Hertha Hubermann"))
                .graph(Name.identifier("Inst2"))
                .codomain(persons.schema().carrier())
                        .map(Name.identifier("1:Male"), Name.identifier("Male"))
                .map(Name.identifier("2:Female"), Name.identifier("Female"))
                .map(Name.value("Hans Hubermann"), Name.identifier("String"))
                .map(Name.value("Hertha Hubermann"), Name.identifier("String"))
                        .map(Name.identifier("1:name"), Name.identifier("name").prefixWith(Name.identifier("Person")))
                        .map(Name.identifier("2:name"), Name.identifier("name").prefixWith(Name.identifier("Person")))
                .morphism(Name.identifier("Inst2Morph"))
                .getResult(GraphMorphism.class)
        );

        ComprData data = new ComprData.Builder(familiesAndPersons)
                .addDataSource(famInstance)
                .addDataSource(persInstance)
                .build();

        assertTrue(familiesAndPersons.rules().allMatch(rule -> rule.violations(data).count() == 0));
    }

    @Test
    public void testUnmatchedFamilyMember() throws GraphError {
        Data famInstance = Data.fromMorphism(families, getContextCreatingBuilder()
                .edge(Name.identifier("1:Family"), Name.identifier("1:name"), Name.value("Hubermann"))
                .edge(Name.identifier("2:FamilyMember"), Name.identifier("2:name"), Name.value("Hans"))
                .edge(Name.identifier("2:FamilyMember"), Name.identifier("2:fatherInverse"), Name.identifier("1:Family"))
                .edge(Name.identifier("3:FamilyMember"), Name.identifier("3:name"), Name.value("Hertha"))
                .edge(Name.identifier("3:FamilyMember"), Name.identifier("3:motherInverse"), Name.identifier("1:Family"))
                .edge(Name.identifier("4:FamilyMember"), Name.identifier("4:sonsInverse"), Name.identifier("1:Family"))
                .edge(Name.identifier("4:FamilyMember"), Name.identifier("4:name"), Name.value("Hajo"))
                .graph(Name.identifier("Inst1"))
                .codomain(families.schema().carrier())
                .map(Name.identifier("1:Family"), Name.identifier("Family"))
                .map(Name.identifier("2:FamilyMember"), Name.identifier("FamilyMember"))
                .map(Name.identifier("3:FamilyMember"), Name.identifier("FamilyMember"))
                .map(Name.identifier("4:FamilyMember"), Name.identifier("FamilyMember"))
                .map(Name.value("Hubermann"), Name.identifier("String"))
                .map(Name.value("Hertha"), Name.identifier("String"))
                .map(Name.value("Hans"), Name.identifier("String"))
                .map(Name.value("Hajo"), Name.identifier("String"))
                .map(Name.identifier("1:name"), Name.identifier("name").prefixWith(Name.identifier("Family")))
                .map(Name.identifier("2:name"), Name.identifier("name").prefixWith(Name.identifier("FamilyMember")))
                .map(Name.identifier("2:fatherInverse"), Name.identifier("fatherInverse").prefixWith(Name.identifier("FamilyMember")))
                .map(Name.identifier("3:name"), Name.identifier("name").prefixWith(Name.identifier("FamilyMember")))
                .map(Name.identifier("3:motherInverse"), Name.identifier("motherInverse").prefixWith(Name.identifier("FamilyMember")))
                .map(Name.identifier("4:name"), Name.identifier("name").prefixWith(Name.identifier("FamilyMember")))
                .map(Name.identifier("4:sonsInverse"), Name.identifier("sonsInverse").prefixWith(Name.identifier("FamilyMember")))
                .morphism("Inst1Morph")
                .getResult(GraphMorphism.class));

        Data persInstance = Data.fromMorphism(persons,
                getContextCreatingBuilder()
                        .edge(Name.identifier("1:Male"), Name.identifier("1:name"), Name.value("Hans Hubermann"))
                        .edge(Name.identifier("2:Female"), Name.identifier("2:name"), Name.value("Hertha Hubermann"))
                        .graph(Name.identifier("Inst2"))
                        .codomain(persons.schema().carrier())
                        .map(Name.identifier("1:Male"), Name.identifier("Male"))
                        .map(Name.identifier("2:Female"), Name.identifier("Female"))
                        .map(Name.value("Hans Hubermann"), Name.identifier("String"))
                        .map(Name.value("Hertha Hubermann"), Name.identifier("String"))
                        .map(Name.identifier("1:name"), Name.identifier("name").prefixWith(Name.identifier("Person")))
                        .map(Name.identifier("2:name"), Name.identifier("name").prefixWith(Name.identifier("Person")))
                        .morphism(Name.identifier("Inst2Morph"))
                        .getResult(GraphMorphism.class)
        );

        ComprData data = new ComprData.Builder(familiesAndPersons)
                .addDataSource(famInstance)
                .addDataSource(persInstance)
                .build();

        assertEquals(0, familiesAndPersons.rules()
                .filter(rule -> rule.commonality().equals(Name.identifier("syncFemale")))
                .findFirst().get()
                .violations(data).count());

        Set<Name> violations = familiesAndPersons.rules()
                .filter(rule -> rule.commonality().equals(Name.identifier("syncMale")))
                .findFirst().get()
                .violations(data).collect(Collectors.toSet());

        assertEquals(1, violations.size());
        assertTrue(violations.contains(Name.identifier("4:FamilyMember")));

    }


    @Test
    public void testUnmatchedPerson() throws GraphError {
        Data famInstance = Data.fromMorphism(families, getContextCreatingBuilder()
                .edge(Name.identifier("1:Family"), Name.identifier("1:name"), Name.value("Hubermann"))
                .edge(Name.identifier("2:FamilyMember"), Name.identifier("2:name"), Name.value("Hans"))
                .edge(Name.identifier("2:FamilyMember"), Name.identifier("2:fatherInverse"), Name.identifier("1:Family"))
                .edge(Name.identifier("3:FamilyMember"), Name.identifier("3:name"), Name.value("Hertha"))
                .edge(Name.identifier("3:FamilyMember"), Name.identifier("3:motherInverse"), Name.identifier("1:Family"))
                .graph(Name.identifier("Inst1"))
                .codomain(families.schema().carrier())
                .map(Name.identifier("1:Family"), Name.identifier("Family"))
                .map(Name.identifier("2:FamilyMember"), Name.identifier("FamilyMember"))
                .map(Name.identifier("3:FamilyMember"), Name.identifier("FamilyMember"))
                .map(Name.identifier("4:FamilyMember"), Name.identifier("FamilyMember"))
                .map(Name.value("Hubermann"), Name.identifier("String"))
                .map(Name.value("Hertha"), Name.identifier("String"))
                .map(Name.value("Hans"), Name.identifier("String"))
                .map(Name.identifier("1:name"), Name.identifier("name").prefixWith(Name.identifier("Family")))
                .map(Name.identifier("2:name"), Name.identifier("name").prefixWith(Name.identifier("FamilyMember")))
                .map(Name.identifier("2:fatherInverse"), Name.identifier("fatherInverse").prefixWith(Name.identifier("FamilyMember")))
                .map(Name.identifier("3:name"), Name.identifier("name").prefixWith(Name.identifier("FamilyMember")))
                .map(Name.identifier("3:motherInverse"), Name.identifier("motherInverse").prefixWith(Name.identifier("FamilyMember")))
                .morphism("Inst1Morph")
                .getResult(GraphMorphism.class));

        Data persInstance = Data.fromMorphism(persons,
                getContextCreatingBuilder()
                        .edge(Name.identifier("1:Male"), Name.identifier("1:name"), Name.value("Hans Hubermann"))
                        .edge(Name.identifier("2:Female"), Name.identifier("2:name"), Name.value("Hertha Hubermann"))
                        .edge(Name.identifier("3:Male"), Name.identifier("3:name"), Name.value("Hajo Hubermann"))
                        .graph(Name.identifier("Inst2"))
                        .codomain(persons.schema().carrier())
                        .map(Name.identifier("1:Male"), Name.identifier("Male"))
                        .map(Name.identifier("2:Female"), Name.identifier("Female"))
                        .map(Name.identifier("3:Male"), Name.identifier("Male"))
                        .map(Name.value("Hans Hubermann"), Name.identifier("String"))
                        .map(Name.value("Hertha Hubermann"), Name.identifier("String"))
                        .map(Name.value("Hajo Hubermann"), Name.identifier("String"))
                        .map(Name.identifier("1:name"), Name.identifier("name").prefixWith(Name.identifier("Person")))
                        .map(Name.identifier("2:name"), Name.identifier("name").prefixWith(Name.identifier("Person")))
                        .map(Name.identifier("3:name"), Name.identifier("name").prefixWith(Name.identifier("Person")))
                        .morphism(Name.identifier("Inst2Morph"))
                        .getResult(GraphMorphism.class)
        );

        ComprData data = new ComprData.Builder(familiesAndPersons)
                .addDataSource(famInstance)
                .addDataSource(persInstance)
                .build();

        assertEquals(0, familiesAndPersons.rules()
                .filter(rule -> rule.commonality().equals(Name.identifier("syncFemale")))
                .findFirst().get()
                .violations(data).count());

        Set<Name> violations = familiesAndPersons.rules()
                .filter(rule -> rule.commonality().equals(Name.identifier("syncMale")))
                .findFirst().get()
                .violations(data).collect(Collectors.toSet());

        assertEquals(1, violations.size());
        assertTrue(violations.contains(Name.identifier("3:Male")));


    }

    @Test
    public void testUnclearGender() throws GraphError {
        Data famInstance = Data.fromMorphism(families, getContextCreatingBuilder()
                .edge(Name.identifier("1:Family"), Name.identifier("1:name"), Name.value("Hubermann"))
                .edge(Name.identifier("2:FamilyMember"), Name.identifier("2:name"), Name.value("Hans"))
                .edge(Name.identifier("2:FamilyMember"), Name.identifier("2:fatherInverse"), Name.identifier("1:Family"))
                .edge(Name.identifier("3:FamilyMember"), Name.identifier("3:name"), Name.value("Hertha"))
                .edge(Name.identifier("3:FamilyMember"), Name.identifier("3:motherInverse"), Name.identifier("1:Family"))
                .edge(Name.identifier("4:FamilyMember"), Name.identifier("4:daughtersInverse"), Name.identifier("1:Family"))
                .edge(Name.identifier("4:FamilyMember"), Name.identifier("4:name"), Name.value("X-I AII"))
                .graph(Name.identifier("Inst1"))
                .codomain(families.schema().carrier())
                .map(Name.identifier("1:Family"), Name.identifier("Family"))
                .map(Name.identifier("2:FamilyMember"), Name.identifier("FamilyMember"))
                .map(Name.identifier("3:FamilyMember"), Name.identifier("FamilyMember"))
                .map(Name.identifier("4:FamilyMember"), Name.identifier("FamilyMember"))
                .map(Name.value("Hubermann"), Name.identifier("String"))
                .map(Name.value("Hertha"), Name.identifier("String"))
                .map(Name.value("Hans"), Name.identifier("String"))
                .map(Name.value("X-I AII"), Name.identifier("String"))
                .map(Name.identifier("1:name"), Name.identifier("name").prefixWith(Name.identifier("Family")))
                .map(Name.identifier("4:name"), Name.identifier("name").prefixWith(Name.identifier("FamilyMember")))
                .map(Name.identifier("2:name"), Name.identifier("name").prefixWith(Name.identifier("FamilyMember")))
                .map(Name.identifier("2:fatherInverse"), Name.identifier("fatherInverse").prefixWith(Name.identifier("FamilyMember")))
                .map(Name.identifier("3:name"), Name.identifier("name").prefixWith(Name.identifier("FamilyMember")))
                .map(Name.identifier("3:motherInverse"), Name.identifier("motherInverse").prefixWith(Name.identifier("FamilyMember")))
                .map(Name.identifier("4:daughtersInverse"), Name.identifier("daughtersInverse").prefixWith(Name.identifier("FamilyMember")))
                .morphism("Inst1Morph")
                .getResult(GraphMorphism.class));

        Data persInstance = Data.fromMorphism(persons,
                getContextCreatingBuilder()
                        .edge(Name.identifier("1:Male"), Name.identifier("1:name"), Name.value("Hans Hubermann"))
                        .edge(Name.identifier("2:Female"), Name.identifier("2:name"), Name.value("Hertha Hubermann"))
                        .edge(Name.identifier("3:Male"), Name.identifier("3:name"), Name.value("X-I AII Hubermann"))
                        .graph(Name.identifier("Inst2"))
                        .codomain(persons.schema().carrier())
                        .map(Name.identifier("1:Male"), Name.identifier("Male"))
                        .map(Name.identifier("2:Female"), Name.identifier("Female"))
                        .map(Name.identifier("3:Male"), Name.identifier("Male"))
                        .map(Name.value("Hans Hubermann"), Name.identifier("String"))
                        .map(Name.value("Hertha Hubermann"), Name.identifier("String"))
                        .map(Name.value("X-I AII Hubermann"), Name.identifier("String"))
                        .map(Name.identifier("1:name"), Name.identifier("name").prefixWith(Name.identifier("Person")))
                        .map(Name.identifier("2:name"), Name.identifier("name").prefixWith(Name.identifier("Person")))
                        .map(Name.identifier("3:name"), Name.identifier("name").prefixWith(Name.identifier("Person")))
                        .morphism(Name.identifier("Inst2Morph"))
                        .getResult(GraphMorphism.class)
        );

        ComprData data = new ComprData.Builder(familiesAndPersons)
                .addDataSource(famInstance)
                .addDataSource(persInstance)
                .build();

        Set<Name> syncFemaleViolations = familiesAndPersons.rules()
                .filter(rule -> rule.commonality().equals(Name.identifier("syncFemale")))
                .findFirst().get()
                .violations(data).collect(Collectors.toSet());

        Set<Name> syncMaleViolations = familiesAndPersons.rules()
                .filter(rule -> rule.commonality().equals(Name.identifier("syncMale")))
                .findFirst().get()
                .violations(data).collect(Collectors.toSet());
        assertEquals(1,syncFemaleViolations.size());
        assertEquals(1,syncMaleViolations.size());
        assertTrue(syncFemaleViolations.contains(Name.identifier("4:FamilyMember")));
        assertTrue(syncMaleViolations.contains(Name.identifier("3:Male")));
    }

    @Test
    public void testWrongLastname() throws GraphError {
        Data famInstance = Data.fromMorphism(families, getContextCreatingBuilder()
                .edge(Name.identifier("1:Family"), Name.identifier("1:name"), Name.value("Hubermann"))
                .edge(Name.identifier("2:FamilyMember"), Name.identifier("2:name"), Name.value("Hans"))
                .edge(Name.identifier("2:FamilyMember"), Name.identifier("2:fatherInverse"), Name.identifier("1:Family"))
                .edge(Name.identifier("3:FamilyMember"), Name.identifier("3:name"), Name.value("Hertha"))
                .edge(Name.identifier("3:FamilyMember"), Name.identifier("3:motherInverse"), Name.identifier("1:Family"))
                .edge(Name.identifier("4:FamilyMember"), Name.identifier("4:sonsInverse"), Name.identifier("1:Family"))
                .edge(Name.identifier("4:FamilyMember"), Name.identifier("4:name"), Name.value("Hajo"))
                .graph(Name.identifier("Inst1"))
                .codomain(families.schema().carrier())
                .map(Name.identifier("1:Family"), Name.identifier("Family"))
                .map(Name.identifier("2:FamilyMember"), Name.identifier("FamilyMember"))
                .map(Name.identifier("3:FamilyMember"), Name.identifier("FamilyMember"))
                .map(Name.identifier("4:FamilyMember"), Name.identifier("FamilyMember"))
                .map(Name.value("Hubermann"), Name.identifier("String"))
                .map(Name.value("Hertha"), Name.identifier("String"))
                .map(Name.value("Hans"), Name.identifier("String"))
                .map(Name.value("Hajo"), Name.identifier("String"))
                .map(Name.identifier("1:name"), Name.identifier("name").prefixWith(Name.identifier("Family")))
                .map(Name.identifier("4:name"), Name.identifier("name").prefixWith(Name.identifier("FamilyMember")))
                .map(Name.identifier("2:name"), Name.identifier("name").prefixWith(Name.identifier("FamilyMember")))
                .map(Name.identifier("2:fatherInverse"), Name.identifier("fatherInverse").prefixWith(Name.identifier("FamilyMember")))
                .map(Name.identifier("3:name"), Name.identifier("name").prefixWith(Name.identifier("FamilyMember")))
                .map(Name.identifier("3:motherInverse"), Name.identifier("motherInverse").prefixWith(Name.identifier("FamilyMember")))
                .map(Name.identifier("4:sonsInverse"), Name.identifier("sonsInverse").prefixWith(Name.identifier("FamilyMember")))
                .morphism("Inst1Morph")
                .getResult(GraphMorphism.class));

        Data persInstance = Data.fromMorphism(persons,
                getContextCreatingBuilder()
                        .edge(Name.identifier("1:Male"), Name.identifier("1:name"), Name.value("Hans Hubermann"))
                        .edge(Name.identifier("2:Female"), Name.identifier("2:name"), Name.value("Hertha Hubermann"))
                        .edge(Name.identifier("3:Male"), Name.identifier("3:name"), Name.value("Hajo Hubermann-Hansen"))
                        .graph(Name.identifier("Inst2"))
                        .codomain(persons.schema().carrier())
                        .map(Name.identifier("1:Male"), Name.identifier("Male"))
                        .map(Name.identifier("2:Female"), Name.identifier("Female"))
                        .map(Name.identifier("3:Male"), Name.identifier("Male"))
                        .map(Name.value("Hans Hubermann"), Name.identifier("String"))
                        .map(Name.value("Hertha Hubermann"), Name.identifier("String"))
                        .map(Name.value("Hajo Hubermann-Hansen"), Name.identifier("String"))
                        .map(Name.identifier("1:name"), Name.identifier("name").prefixWith(Name.identifier("Person")))
                        .map(Name.identifier("2:name"), Name.identifier("name").prefixWith(Name.identifier("Person")))
                        .map(Name.identifier("3:name"), Name.identifier("name").prefixWith(Name.identifier("Person")))
                        .morphism(Name.identifier("Inst2Morph"))
                        .getResult(GraphMorphism.class)
        );

        ComprData data = new ComprData.Builder(familiesAndPersons)
                .addDataSource(famInstance)
                .addDataSource(persInstance)
                .build();

        Set<Name> syncFemaleViolations = familiesAndPersons.rules()
                .filter(rule -> rule.commonality().equals(Name.identifier("syncFemale")))
                .findFirst().get()
                .violations(data).collect(Collectors.toSet());

        Set<Name> syncMaleViolations = familiesAndPersons.rules()
                .filter(rule -> rule.commonality().equals(Name.identifier("syncMale")))
                .findFirst().get()
                .violations(data).collect(Collectors.toSet());
        assertEquals(0,syncFemaleViolations.size());
        assertEquals(2,syncMaleViolations.size());
        assertTrue(syncMaleViolations.contains(Name.identifier("4:FamilyMember")));
        assertTrue(syncMaleViolations.contains(Name.identifier("3:Male")));
    }


}
