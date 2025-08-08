package plugins;

import io.corrlang.di.DependencyInjectionContainer;
import io.corrlang.domain.Endpoint;
import io.corrlang.domain.schemas.Schema;
import io.corrlang.domain.schemas.SchemaBuilder;
import io.corrlang.plugins.PlantUmlTechSpace;
import io.corrlang.techspaces.TechSpaceRegistry;
import io.corrlang.techspaces.WriteSchemaCapabilities;
import io.corrlang.testfixtures.ExampleSystems;
import no.hvl.past.names.Name;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.io.*;
import java.nio.charset.StandardCharsets;


import static io.corrlang.domain.QualifiedName.qname;
import static org.junit.jupiter.api.Assertions.*;


public class PlantUMLPlottingTest {
    // TODO: make TestWithDIContainer


    private final static File outputDir = new File("temp/plantumlOutputs");

    @BeforeAll
    public static void setUp() {
        outputDir.mkdirs();
        if (outputDir.exists()) {
            outputDir.delete();
        }
        outputDir.mkdir();
    }


    @Test
    public void testPersonExample() throws Exception {
        DependencyInjectionContainer di = DependencyInjectionContainer.create();
        TechSpaceRegistry reg = di.getBean(TechSpaceRegistry.class);
        WriteSchemaCapabilities schemaWriters = reg.schemaWriter(PlantUmlTechSpace.ID);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        SchemaBuilder builder = new SchemaBuilder(Name.identifier("Persons"), di.getUniverse());
        ExampleSystems.buildPersonsJobsAndCommunication(builder);
        Endpoint endpoint = new Endpoint(0, "Persons", builder.build());
        schemaWriters.serializeSchema().serialize(endpoint, bos);
        String actual = bos.toString(StandardCharsets.UTF_8);
        String expected = """
@startuml
set namespaceSeparator ::
abstract class CommunicationChannel {
}

class Person {
   name : String [2..*]
   gender : Gender [1..1]
   age : Int [1..1]
}

class Email {
   address : String [1..1]
}

class Repository {
}

class PostalAddress {
   street : String [1..1]
   zip : String [1..1]
   city : String [1..1]
}

class Job {
   employer : String [1..1]
   position : String [1..1]
}

class PersonsService << (S,orchid) Service >> {
   all() : Person[0..*]
   createPerson(name : String[1..1], age : Int[1..1]) : void
}

Repository --> "0..*" CommunicationChannel : channels
Person "owner  1..1" -- "contactPoints  1..*" CommunicationChannel : commChannels
Repository --> "0..*" Person : persons
Person --> "0..0" Job : worksAt
Repository --> "0..*" Job : jobs
Email -up-|> CommunicationChannel
PostalAddress -up-|> CommunicationChannel

@enduml
                """.trim();
        assertFalse(actual.isEmpty());
        assertEquals(expected, actual.trim());
        schemaWriters.serializeToFile().serialize(endpoint, outputDir);
    }


    @Test
    public void testSalesInvoicesHR() throws Exception {
        DependencyInjectionContainer di = DependencyInjectionContainer.create();
        TechSpaceRegistry reg = di.getBean(TechSpaceRegistry.class);
        WriteSchemaCapabilities schemaWriters = reg.schemaWriter(PlantUmlTechSpace.ID);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        SchemaBuilder builder = new SchemaBuilder(Name.identifier("Invoices"), di.getUniverse());
        Schema s = ExampleSystems.buildSalesSystemSchema(builder);
        schemaWriters.serializeSchema().serialize(new Endpoint(0, "Invoices", s), bos);
        String actual = bos.toString(StandardCharsets.UTF_8);
        System.out.println(actual); // TODO: there shall not be any mention query or mutation with the simple setting
        assertFalse(actual.contains("Query"));
    }



//    @Test
//    public void testLocalSystem() throws IOException, GraphError {
//        File cwd = new File(System.getProperty("user.dir"));
//        File outputFolder = new File(cwd, "temp");
//        outputFolder.mkdirs();
//        for (File child : outputFolder.listFiles()) {
//            child.delete();
//        }
//
//
//        PlantUMLPlotter plotter = new PlantUMLPlotter(false, false, false, false);
//
//        Endpoint sytstem = makePersonExample();
//
//        plotter.plotToDir(sytstem, outputFolder);
//
//        InputStream stream = PlantUMLPlottingTest.class.getResourceAsStream("/expected/test.puml");
//        String expected = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
//        stream.close();
//
//        File actualFile = new File(outputFolder, "test.puml");
//        assertTrue(actualFile.exists());
//
//        String actual = Files.readString(actualFile.toPath());
//        assertEquals(expected, actual);
//    }
//
//
//    @Test
//    public void testComprehensiveSystem() throws GraphError, IOException {
//        Endpoint personsAndJobs = makePersonExample();
//
//        Endpoint patientsAndObservations = makePatientsExample();
//
//        ComprSys comprSys = new ComprSysBuilder(Name.identifier("Corr"), TestWithGraphLib.universe)
//                .addSystem(personsAndJobs)
//                .addSystem(patientsAndObservations)
//                .nodeCommonality(ComprSys.GLOBAL_STRING_NAME,
//                        QualifiedName.qname(personsAndJobs, Name.identifier("String")),
//                        QualifiedName.qname(patientsAndObservations, Name.identifier("String")))
//                .nodeCommonality(Name.identifier("PP"),
//                        QualifiedName.qname(personsAndJobs, Name.identifier("Person")),
//                        QualifiedName.qname(patientsAndObservations, Name.identifier("Patient")))
//                .nodeCommonality(Name.identifier("Address"),
//                        QualifiedName.qname(personsAndJobs, Name.identifier("PostalAddress")),
//                        QualifiedName.qname(patientsAndObservations, Name.identifier("Address")))
//                .edgeCommonality(
//                        Name.identifier("Address"),
//                        Name.identifier("street"),
//                        ComprSys.GLOBAL_STRING_NAME,
//                        QualifiedName.qname(personsAndJobs, Name.identifier("street")),
//                        QualifiedName.qname(patientsAndObservations, Name.identifier("street")))
//                .edgeCommonality(
//                        Name.identifier("Address"),
//                        Name.identifier("city"),
//                        ComprSys.GLOBAL_STRING_NAME,
//                        QualifiedName.qname(personsAndJobs, Name.identifier("city")),
//                        QualifiedName.qname(patientsAndObservations, Name.identifier("city")))
//                .edgeCommonality(
//                        Name.identifier("Address"),
//                        Name.identifier("zip"),
//                        ComprSys.GLOBAL_STRING_NAME,
//                        QualifiedName.qname(personsAndJobs, Name.identifier("zip")),
//                        QualifiedName.qname(patientsAndObservations, Name.identifier("postCode")))
//                .identification(Name.identifier("Address"))
//                .identification(Name.identifier("street"))
//                .identification(Name.identifier("city"))
//                .identification(Name.identifier("zip"))
//                .identification(ComprSys.GLOBAL_STRING_NAME)
//                .build();
//
//        File outputFolder = getTestResource(PLANTUML_ACTUAL);
//        String filename = "personsAndPatients.png";
//        File outputFile = new File(outputFolder, filename);
//        if (outputFile.exists()) {
//            outputFile.delete();
//        }
//
//
//        FileOutputStream fos = new FileOutputStream(outputFile);
//
//        PlantUMLPlotter plotter = new PlantUMLPlotter( false, false, false, false);
//
//        plotter.plot(comprSys,fos);
//        fos.close();
//        assertBinaryFileContentAsExpected(new File(getTestResource(PLANTUML_EXPECTED), filename), outputFile);
//    }
//
//    private Endpoint makePatientsExample() throws GraphError {
//        Sketch result = getContextCreatingBuilder()
//
//                .node("Patient")
//                .node("Observation")
//                .node("Quantity")
//                .node("CodeableConcept")
//                .node("JournalSystem")
//                .node("Address")
//
//                .edge("JournalSystem", "patients", "Patient")
//                .edge("JournalSystem", "observations", "Observation")
//                .edge("Patient", "name", "String")
//                .edge("Patient", "address", "Address")
//                .edge("Patient", "patientId", "Long")
//                .edge("Observation", "observationId", "Long")
//                .edge("Observation", "patient", "Patient")
//                .edge("Observation", "effectiveDateTime", "DateTime")
//                .edge("Observation", "coding", "CodeableConcept")
//                .edge("Observation", "measurement", "Quantity")
//                .edge("Quantity", "value", "Double")
//                .edge("Quantity", "unit", "CodeableConcept")
//                .edge("CodeableConcept", "codeSystem", "URI")
//                .edge("CodeableConcept", "code", "String")
//                .edge("Address", "street", "String")
//                .edge("Address", "city", "String")
//                .edge("Address", "postCode", "String")
//
//                .graph(Name.identifier("Patients").absolute())
//
//                .startDiagram(StringDT.getInstance())
//                .map(Universe.ONE_NODE_THE_NODE, Name.identifier("String"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(IntDT.getInstance())
//                .map(Universe.ONE_NODE_THE_NODE, Name.identifier("Long"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(FloatDT.getInstance())
//                .map(Universe.ONE_NODE_THE_NODE, Name.identifier("Double"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(DataTypePredicate.getInstance())
//                .map(Universe.ONE_NODE_THE_NODE, Name.identifier("DateTime"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(DataTypePredicate.getInstance())
//                .map(Universe.ONE_NODE_THE_NODE, Name.identifier("URI"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(TargetMultiplicity.getInstance(1, 1))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("Observation"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("patient"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("Patient"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(TargetMultiplicity.getInstance(1, 1))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("Observation"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("observationId"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("Long"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(TargetMultiplicity.getInstance(1, -1))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("Observation"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("coding"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("CodeableConcept"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(TargetMultiplicity.getInstance(1, 1))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("Observation"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("measurement"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("Quantity"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(TargetMultiplicity.getInstance(1, 1))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("Patient"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("patientId"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("Long"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(TargetMultiplicity.getInstance(0, 1))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("Patient"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("address"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("Address"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(TargetMultiplicity.getInstance(2, -1))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("Patient"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("name"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("String"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(TargetMultiplicity.getInstance(1, 1))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("CodeableConcept"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("code"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("String"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(TargetMultiplicity.getInstance(1, 1))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("Quantity"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("value"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("Double"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(TargetMultiplicity.getInstance(0, 1))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("Quantity"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("unit"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("CodeableConcept"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(Acyclicity.getInstance())
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("JournalSystem"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("patients"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("Patient"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(Acyclicity.getInstance())
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("JournalSystem"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("observations"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("Observation"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(Acyclicity.getInstance())
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("Observation"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("measurement"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("Quantity"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(Acyclicity.getInstance())
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("Observation"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("coding"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("CodeableConcept"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(SourceMultiplicity.getInstance(1,1))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("JournalSystem"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("patients"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("Patient"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(SourceMultiplicity.getInstance(1,1))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("JournalSystem"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("observations"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("Observation"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(SourceMultiplicity.getInstance(1,1))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("Observation"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("measurement"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("Quantity"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(SourceMultiplicity.getInstance(1,1))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("Observation"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("coding"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("CodeableConcept"))
//                .endDiagram(Name.anonymousIdentifier())
//
//
//                .sketch(Name.identifier("Patients"))
//                .getResult(Sketch.class);
//
//        return new Endpoint.Builder("test2", result)
//                .build();
//
//    }
//
//
//    private Endpoint makePersonExample() throws GraphError {
//        DataOperation greater18 = new DataOperation() {
//            @Override
//            public String name() {
//                return "age >= 18";
//            }
//
//            @Override
//            public int arity() {
//                return 1;
//            }
//
//            @Override
//            public Value applyImplementation(Value[] arguments) {
//                Value[] left = new Value[2];
//                left[0] = Name.value(18);
//                left[1] = arguments[0];
//                return BuiltinOperations.LessOrEqual.getInstance().apply(left);
//            }
//
//        };
//
//
//        Sketch result = getContextCreatingBuilder()
//
//                .node("Person")
//                .node("Job")
//                .node("CommunicationChannel")
//                .node("PostalAddress")
//                .node("Email")
//                .node("Gender")
//                .node("Repository")
//
//                .edge("Repository", "persons", "Person")
//                .edge("Repository", "channels", "CommunicationChannel")
//                .edge("Repository", "jobs", "Job")
//                .edge("PostalAddress", "street", "String")
//                .edge("PostalAddress", "city", "String")
//                .edge("PostalAddress", "zip", "String")
//                .edge("Email", "address", "String")
//                .edge("Person", "name", "String")
//                .edge("Person", "age", "Int")
//                .edge("Person", "gender", "Gender")
//                .edge("Person", "worksAt", "Job")
//                .edge("Job", "position", "String")
//                .edge("Job", "employer", "String")
//                .edge("CommunicationChannel", "owner", "Person")
//                .edge("Person", "contacts", "CommunicationChannel")
//                .map("PostalAddress", "CommunicationChannel")
//                .map("Email", "CommunicationChannel")
//
//                .node("all")
//                .node("createPerson")
//                .edgePrefixWithOwner("all", "result","Person")
//                .edgePrefixWithOwner("createPerson", "name","String")
//                .edgePrefixWithOwner("createPerson", "age","Int")
//
//                .graph(Name.identifier("Test").absolute())
//                .startDiagram(StringDT.getInstance())
//                .map(Universe.ONE_NODE_THE_NODE, Name.identifier("String"))
//                .endDiagram(Name.identifier("d1"))
//                .startDiagram(IntDT.getInstance())
//                .map(Universe.ONE_NODE_THE_NODE, Name.identifier("Int"))
//                .endDiagram(Name.identifier("d2"))
//                .startDiagram(EnumValue.getInstance(Name.identifier("MALE"), Name.identifier("FEMALE")))
//                .map(Universe.ONE_NODE_THE_NODE, Name.identifier("Gender"))
//                .endDiagram(Name.identifier("d3"))
//                .startDiagram(TargetMultiplicity.getInstance(0, 1))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("Person"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("worksAt"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("Job"))
//                .endDiagram(Name.identifier("d4"))
//                .startDiagram(TargetMultiplicity.getInstance(1, -1))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("CommunicationChannel"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("owner"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("Person"))
//                .endDiagram(Name.identifier("d5"))
//                .startDiagram(Invert.getInstance())
//                .map(Universe.CYCLE_FWD.getSource(), Name.identifier("CommunicationChannel"))
//                .map(Universe.CYCLE_FWD.getTarget(), Name.identifier("Person"))
//                .map(Universe.CYCLE_FWD.getLabel(), Name.identifier("owner"))
//                .map(Universe.CYCLE_BWD.getLabel(), Name.identifier("contacts"))
//                .endDiagram(Name.identifier("commChannels"))
//                .startDiagram(Acyclicity.getInstance())
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("Repository"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("persons"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("Person"))
//                .endDiagram(Name.identifier("d7"))
//                .startDiagram(Acyclicity.getInstance())
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("Repository"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("channels"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("CommunicationChannel"))
//                .endDiagram(Name.identifier("d8"))
//                .startDiagram(Acyclicity.getInstance())
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("Repository"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("jobs"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("Job"))
//                .endDiagram(Name.identifier("d9"))
//                .startDiagram(SourceMultiplicity.getInstance(0, 1))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("Repository"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("persons"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("Person"))
//                .endDiagram(Name.identifier("d10"))
//                .startDiagram(SourceMultiplicity.getInstance(0, 1))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("Repository"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("channels"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("CommunicationChannel"))
//                .endDiagram(Name.identifier("d11"))
//                .startDiagram(SourceMultiplicity.getInstance(0, 1))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("Repository"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("jobs"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("Job"))
//                .endDiagram(Name.identifier("d12"))
//                .startDiagram(AttributePredicate.getInstance(greater18))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("Person"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("age"))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("Int"))
//                .endDiagram(Name.identifier("Req1: Must be an adult!"))
//
//                .startDiagram(TargetMultiplicity.getInstance(0,1))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("createPerson"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("name").prefixWith(Name.identifier("createPerson")))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("String"))
//                .endDiagram(Name.anonymousIdentifier())
//
//                .startDiagram(TargetMultiplicity.getInstance(0,1))
//                .map(Universe.ARROW_SRC_NAME, Name.identifier("createPerson"))
//                .map(Universe.ARROW_LBL_NAME, Name.identifier("age").prefixWith(Name.identifier("createPerson")))
//                .map(Universe.ARROW_TRG_NAME, Name.identifier("Int"))
//                .endDiagram(Name.anonymousIdentifier())
//                .sketch("Persons")
//                .getResult(Sketch.class);
//
//        return new Endpoint.Builder("test", result)
//                .beginMessage(Name.identifier("all"), false)
//                    .output(Name.identifier("result"))
//                .endMessage()
//                .beginMessage(Name.identifier("createPerson"), true)
//                    .input(Name.identifier("name"))
//                    .input(Name.identifier("age"))
//                    .endMessage()
//                .build();
//    }

}
