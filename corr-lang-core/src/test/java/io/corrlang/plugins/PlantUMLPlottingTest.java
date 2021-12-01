package io.corrlang.plugins;

import io.corrlang.domain.ComprSys;
import io.corrlang.domain.Sys;
import no.hvl.past.attributes.BuiltinOperations;
import no.hvl.past.attributes.DataOperation;
import no.hvl.past.graph.TestWithGraphLib;
import no.hvl.past.graph.GraphError;
import no.hvl.past.graph.Sketch;
import no.hvl.past.graph.Universe;
import no.hvl.past.graph.operations.Invert;
import io.corrlang.plugins.dm.PlantUMLPlotter;
import no.hvl.past.graph.predicates.*;
import no.hvl.past.names.Name;
import no.hvl.past.names.Value;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


import static io.corrlang.domain.QualifiedName.qname;

// TODO make comparison tests (before move that test method part of the core test library)

public class PlantUMLPlottingTest extends TestWithGraphLib {


    public static final String PLANTUML_ACTUAL = "plantuml/actual";
    public static final String PLANTUML_EXPECTED = "plantuml/expected";

    @Test
    public void testLocalSystem() throws IOException, GraphError {
        File outputFolder = getTestResource(PLANTUML_ACTUAL);
        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }
        String filename = "test1.png";
        File outputFile = new File(outputFolder, filename);
        if (outputFile.exists()) {
            outputFile.delete();
        }

        FileOutputStream fos = new FileOutputStream(outputFile);

        PlantUMLPlotter plotter = new PlantUMLPlotter(false, false);


        Sys sytstem = makePersonExample();

        plotter.plot(sytstem, fos);
        fos.close();
        assertBinaryFileContentAsExpected(new File(getTestResource(PLANTUML_EXPECTED), filename), outputFile);
    }


    @Test
    public void testComprehensiveSystem() throws GraphError, IOException {
        Sys personsAndJobs = makePersonExample();

        Sys patientsAndObservations = makePatientsExample();

        ComprSys comprSys = new ComprSys.Builder(Name.identifier("Corr"), universe)
                .addSystem(personsAndJobs)
                .addSystem(patientsAndObservations)
                .nodeCommonality(ComprSys.GLOBAL_STRING_NAME,
                        qname(personsAndJobs, Name.identifier("String")),
                        qname(patientsAndObservations, Name.identifier("String")))
                .nodeCommonality(Name.identifier("PP"),
                        qname(personsAndJobs, Name.identifier("Person")),
                        qname(patientsAndObservations, Name.identifier("Patient")))
                .nodeCommonality(Name.identifier("Address"),
                        qname(personsAndJobs, Name.identifier("PostalAddress")),
                        qname(patientsAndObservations, Name.identifier("Address")))
                .edgeCommonality(
                        Name.identifier("Address"),
                        Name.identifier("street"),
                        ComprSys.GLOBAL_STRING_NAME,
                        qname(personsAndJobs, Name.identifier("street")),
                        qname(patientsAndObservations, Name.identifier("street")))
                .edgeCommonality(
                        Name.identifier("Address"),
                        Name.identifier("city"),
                        ComprSys.GLOBAL_STRING_NAME,
                        qname(personsAndJobs, Name.identifier("city")),
                        qname(patientsAndObservations, Name.identifier("city")))
                .edgeCommonality(
                        Name.identifier("Address"),
                        Name.identifier("zip"),
                        ComprSys.GLOBAL_STRING_NAME,
                        qname(personsAndJobs, Name.identifier("zip")),
                        qname(patientsAndObservations, Name.identifier("postCode")))
                .identification(Name.identifier("Address"))
                .identification(Name.identifier("street"))
                .identification(Name.identifier("city"))
                .identification(Name.identifier("zip"))
                .identification(ComprSys.GLOBAL_STRING_NAME)
                .build();

        File outputFolder = getTestResource(PLANTUML_ACTUAL);
        String filename = "personsAndPatients.png";
        File outputFile = new File(outputFolder, filename);
        if (outputFile.exists()) {
            outputFile.delete();
        }


        FileOutputStream fos = new FileOutputStream(outputFile);

        PlantUMLPlotter plotter = new PlantUMLPlotter( false, false);

        plotter.plot(comprSys,fos);
        fos.close();
        assertBinaryFileContentAsExpected(new File(getTestResource(PLANTUML_EXPECTED), filename), outputFile);
    }

    private Sys makePatientsExample() throws GraphError {
        Sketch result = getContextCreatingBuilder()

                .node("Patient")
                .node("Observation")
                .node("Quantity")
                .node("CodeableConcept")
                .node("JournalSystem")
                .node("Address")

                .edge("JournalSystem", "patients", "Patient")
                .edge("JournalSystem", "observations", "Observation")
                .edge("Patient", "name", "String")
                .edge("Patient", "address", "Address")
                .edge("Patient", "patientId", "Long")
                .edge("Observation", "observationId", "Long")
                .edge("Observation", "patient", "Patient")
                .edge("Observation", "effectiveDateTime", "DateTime")
                .edge("Observation", "coding", "CodeableConcept")
                .edge("Observation", "measurement", "Quantity")
                .edge("Quantity", "value", "Double")
                .edge("Quantity", "unit", "CodeableConcept")
                .edge("CodeableConcept", "codeSystem", "URI")
                .edge("CodeableConcept", "code", "String")
                .edge("Address", "street", "String")
                .edge("Address", "city", "String")
                .edge("Address", "postCode", "String")

                .graph(Name.identifier("Patients").absolute())

                .startDiagram(StringDT.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier("String"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(IntDT.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier("Long"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(FloatDT.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier("Double"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(DataTypePredicate.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier("DateTime"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(DataTypePredicate.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier("URI"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(TargetMultiplicity.getInstance(1, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("Observation"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("patient"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("Patient"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(TargetMultiplicity.getInstance(1, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("Observation"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("observationId"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("Long"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(TargetMultiplicity.getInstance(1, -1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("Observation"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("coding"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("CodeableConcept"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(TargetMultiplicity.getInstance(1, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("Observation"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("measurement"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("Quantity"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(TargetMultiplicity.getInstance(1, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("Patient"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("patientId"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("Long"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(TargetMultiplicity.getInstance(0, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("Patient"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("address"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("Address"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(TargetMultiplicity.getInstance(2, -1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("Patient"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("name"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("String"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(TargetMultiplicity.getInstance(1, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("CodeableConcept"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("code"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("String"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(TargetMultiplicity.getInstance(1, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("Quantity"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("value"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("Double"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(TargetMultiplicity.getInstance(0, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("Quantity"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("unit"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("CodeableConcept"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(Acyclicity.getInstance())
                .map(Universe.ARROW_SRC_NAME, Name.identifier("JournalSystem"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("patients"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("Patient"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(Acyclicity.getInstance())
                .map(Universe.ARROW_SRC_NAME, Name.identifier("JournalSystem"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("observations"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("Observation"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(Acyclicity.getInstance())
                .map(Universe.ARROW_SRC_NAME, Name.identifier("Observation"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("measurement"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("Quantity"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(Acyclicity.getInstance())
                .map(Universe.ARROW_SRC_NAME, Name.identifier("Observation"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("coding"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("CodeableConcept"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(SourceMultiplicity.getInstance(1,1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("JournalSystem"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("patients"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("Patient"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(SourceMultiplicity.getInstance(1,1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("JournalSystem"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("observations"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("Observation"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(SourceMultiplicity.getInstance(1,1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("Observation"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("measurement"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("Quantity"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(SourceMultiplicity.getInstance(1,1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("Observation"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("coding"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("CodeableConcept"))
                .endDiagram(Name.anonymousIdentifier())


                .sketch(Name.identifier("Patients"))
                .getResult(Sketch.class);

        return new Sys.Builder("test2", result)
                .build();

    }


    private Sys makePersonExample() throws GraphError {
        DataOperation greater18 = new DataOperation() {
            @Override
            public String name() {
                return "age >= 18";
            }

            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Value applyImplementation(Value[] arguments) {
                Value[] left = new Value[2];
                left[0] = Name.value(18);
                left[1] = arguments[0];
                return BuiltinOperations.LessOrEqual.getInstance().apply(left);
            }

        };


        Sketch result = getContextCreatingBuilder()

                .node("Person")
                .node("Job")
                .node("CommunicationChannel")
                .node("PostalAddress")
                .node("Email")
                .node("Gender")
                .node("Repository")

                .edge("Repository", "persons", "Person")
                .edge("Repository", "channels", "CommunicationChannel")
                .edge("Repository", "jobs", "Job")
                .edge("PostalAddress", "street", "String")
                .edge("PostalAddress", "city", "String")
                .edge("PostalAddress", "zip", "String")
                .edge("Email", "address", "String")
                .edge("Person", "name", "String")
                .edge("Person", "age", "Int")
                .edge("Person", "gender", "Gender")
                .edge("Person", "worksAt", "Job")
                .edge("Job", "position", "String")
                .edge("Job", "employer", "String")
                .edge("CommunicationChannel", "owner", "Person")
                .edge("Person", "contacts", "CommunicationChannel")
                .map("PostalAddress", "CommunicationChannel")
                .map("Email", "CommunicationChannel")

                .node("all")
                .node("createPerson")
                .edgePrefixWithOwner("all", "result","Person")
                .edgePrefixWithOwner("createPerson", "name","String")
                .edgePrefixWithOwner("createPerson", "age","Int")

                .graph(Name.identifier("Test").absolute())
                .startDiagram(StringDT.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier("String"))
                .endDiagram(Name.identifier("d1"))
                .startDiagram(IntDT.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier("Int"))
                .endDiagram(Name.identifier("d2"))
                .startDiagram(EnumValue.getInstance(Name.identifier("MALE"), Name.identifier("FEMALE")))
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier("Gender"))
                .endDiagram(Name.identifier("d3"))
                .startDiagram(TargetMultiplicity.getInstance(0, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("Person"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("worksAt"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("Job"))
                .endDiagram(Name.identifier("d4"))
                .startDiagram(TargetMultiplicity.getInstance(1, -1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("CommunicationChannel"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("owner"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("Person"))
                .endDiagram(Name.identifier("d5"))
                .startDiagram(Invert.getInstance())
                .map(Universe.CYCLE_FWD.getSource(), Name.identifier("CommunicationChannel"))
                .map(Universe.CYCLE_FWD.getTarget(), Name.identifier("Person"))
                .map(Universe.CYCLE_FWD.getLabel(), Name.identifier("owner"))
                .map(Universe.CYCLE_BWD.getLabel(), Name.identifier("contacts"))
                .endDiagram(Name.identifier("commChannels"))
                .startDiagram(Acyclicity.getInstance())
                .map(Universe.ARROW_SRC_NAME, Name.identifier("Repository"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("persons"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("Person"))
                .endDiagram(Name.identifier("d7"))
                .startDiagram(Acyclicity.getInstance())
                .map(Universe.ARROW_SRC_NAME, Name.identifier("Repository"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("channels"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("CommunicationChannel"))
                .endDiagram(Name.identifier("d8"))
                .startDiagram(Acyclicity.getInstance())
                .map(Universe.ARROW_SRC_NAME, Name.identifier("Repository"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("jobs"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("Job"))
                .endDiagram(Name.identifier("d9"))
                .startDiagram(SourceMultiplicity.getInstance(0, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("Repository"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("persons"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("Person"))
                .endDiagram(Name.identifier("d10"))
                .startDiagram(SourceMultiplicity.getInstance(0, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("Repository"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("channels"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("CommunicationChannel"))
                .endDiagram(Name.identifier("d11"))
                .startDiagram(SourceMultiplicity.getInstance(0, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("Repository"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("jobs"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("Job"))
                .endDiagram(Name.identifier("d12"))
                .startDiagram(AttributePredicate.getInstance(greater18))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("Person"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("age"))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("Int"))
                .endDiagram(Name.identifier("Req1: Must be an adult!"))

                .startDiagram(TargetMultiplicity.getInstance(0,1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("createPerson"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("name").prefixWith(Name.identifier("createPerson")))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("String"))
                .endDiagram(Name.anonymousIdentifier())

                .startDiagram(TargetMultiplicity.getInstance(0,1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier("createPerson"))
                .map(Universe.ARROW_LBL_NAME, Name.identifier("age").prefixWith(Name.identifier("createPerson")))
                .map(Universe.ARROW_TRG_NAME, Name.identifier("Int"))
                .endDiagram(Name.anonymousIdentifier())
                .sketch("Persons")
                .getResult(Sketch.class);

        return new Sys.Builder("test", result)
                .beginMessage(Name.identifier("all"), false)
                    .output(Name.identifier("result"))
                .endMessage()
                .beginMessage(Name.identifier("createPerson"), true)
                    .input(Name.identifier("name"))
                    .input(Name.identifier("age"))
                    .endMessage()
                .build();
    }

}
