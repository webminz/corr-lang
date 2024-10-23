package io.corrlang.tests;

import io.corrlang.domain.IntfcDesc;
import io.corrlang.domain.Sys;
import io.corrlang.domain.builders.SystemInterfaceDescriptionBuilder;
import no.hvl.past.attributes.BuiltinOperations;
import no.hvl.past.attributes.DataOperation;
import no.hvl.past.graph.Sketch;
import no.hvl.past.graph.Universe;
import no.hvl.past.graph.operations.Invert;
import no.hvl.past.graph.predicates.*;
import no.hvl.past.names.Name;
import no.hvl.past.names.Value;
import no.hvl.past.util.Multiplicity;

public class ExampleSystems {

    private ExampleSystems() {
    }

    public static void buildPatientsExample(SystemInterfaceDescriptionBuilder builder) {

            builder.updateSymbolicName("Patients")
                    .stringValueType("String")
                    .intValueType("Long")
                    .floatValueType("Double")
                    .customValueType("DateTime", DataTypePredicate.getInstance())
                    .customValueType("URI", DataTypePredicate.getInstance())
                    .objectType("Observation")
                        .buildField("observationID", "Long")
                            .multiplicity(Multiplicity.of(1,1))
                            .endField()
                        .buildField("patient", "Patient")
                            .multiplicity(Multiplicity.of(1,1))
                            .endField()
                        .buildField("effectiveDateTime", "DateTime")
                            .multiplicity(Multiplicity.of(0,1 ))
                            .endField()
                        .buildField("coding", "CodeableConcept")
                            .multiplicity(Multiplicity.of(1, -1))
                            .makeContainment()
                            .endField()
                        .buildField("measurement", "Quantity")
                            .multiplicity(Multiplicity.of(1,1))
                            .makeContainment()
                            .endField()
                    .endObjectType()
                    .objectType("Patient")
                        .buildField("name", "String")
                            .multiplicity(Multiplicity.of(2, -1))
                            .endField()
                        .buildField("address", "Address")
                            .multiplicity(Multiplicity.of(0, 1))
                            .endField()
                        .buildField("patientId", "Long")
                            .multiplicity(Multiplicity.of(1,1))
                            .endField()
                    .endObjectType()
                    .objectType("Address")
                        .buildField("street", "String")
                            .multiplicity(Multiplicity.of(1,1))
                            .endField()
                        .buildField("city", "String")
                            .multiplicity(Multiplicity.of(1,1))
                            .endField()
                        .buildField("postalCode", "String")
                            .multiplicity(Multiplicity.of(1,1))
                            .endField()
                    .endObjectType()
                    .objectType("Quantity")
                        .buildField("value", "Double")
                            .multiplicity(Multiplicity.of(1,1))
                            .endField()
                        .buildField("unit", "CodeableConcept")
                            .multiplicity(Multiplicity.of(0,1))
                            .endField()
                    .endObjectType()
                    .objectType("CodeableConcept")
                        .buildField("codeSystem", "URI")
                            .multiplicity(Multiplicity.of(0,1))
                            .endField()
                        .buildField("code", "String")
                            .multiplicity(Multiplicity.of(1,1))
                            .endField()
                    .endObjectType()
                    .objectType("JournalSystem")
                        .buildField("patients", "Patient")
                            .makeContainment()
                            .endField()
                        .buildField("observations", "Observations")
                            .makeContainment()
                            .endField()
                    .endObjectType();

    }

    public enum Gender {
        MALE,
        FEMALE,
        NON_BINARY
    }

    public static void buildPersonsExample(SystemInterfaceDescriptionBuilder builder) {

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

        builder.updateSymbolicName("Persons")

                .stringValueType("String")
                .intValueType("Int")
                .enumValueType("Gender", Gender.class)
                .objectType("Job")
                    .buildField("position", "String")
                        .multiplicity(Multiplicity.of(1,1))
                        .endField()
                    .buildField("employer", "String")
                        .multiplicity(Multiplicity.of(1,1))
                        .endField()
                .endObjectType()
                .objectType("CommunicationChannel")
                .makeAbstract()
                .endObjectType()
                .objectType("PostalAddress")
                    .inheritsFrom("CommunicationChannel")
                    .buildField("street","String")
                        .multiplicity(Multiplicity.of(1,1))
                        .endField()
                    .buildField("city", "String")
                        .multiplicity(Multiplicity.of(1,1))
                        .endField()
                    .buildField("zip", "String")
                        .multiplicity(Multiplicity.of(1,1))
                        .endField()
                .endObjectType()
                .objectType("Email")
                    .inheritsFrom("CommunicationChannel")
                    .buildField("address", "String")
                        .multiplicity(Multiplicity.of(1,1))
                        .endField()
                .endObjectType()
                .objectType("Person")
                    .buildField("name", "String")
                        .multiplicity(Multiplicity.of(2,-1))
                        .endField()
                    .buildField("age", "Int")
                        .multiplicity(Multiplicity.of(1,1))
                        .constraint(greater18, "Person must be grown up")
                        .endField()
                    .buildField("gender", "Gender")
                        .multiplicity(Multiplicity.of(1,1))
                        .endField()
                    .buildField("worksAt", "Job")
                        .multiplicity(Multiplicity.of(0,1))
                        .endField()
                        .buildField("contactPoints", "CommunicationChannel")
                        .multiplicity(Multiplicity.of(1, -1))
                        .makeSymmetric("owner", "commChannels", Multiplicity.of(1,1),false,false)
                        .endField()
                .endObjectType()
                .objectType("Repository")
                .buildField("persons", "Person")
                    .makeContainment()
                    .endField()
                .buildField("channels", "CommunicationChannel")
                    .makeContainment()
                    .endField()
                .buildField("jobs", "Job")
                    .makeContainment()
                    .endField()
                .endObjectType()
                .action("all")
                .outputArg("result","Person")
                .end()
                .action("createPerson")
                .buildInputArg("name", "String")
                .multiplicity(Multiplicity.of(1,1))
                .endArgument()
                .buildInputArg("age","Int")
                .multiplicity(Multiplicity.of(1,1))
                .endArgument()
                .end();

    }

    public enum GradeScale {
        A,
        B,
        C,
        D,
        E,
        F
    }

    public static IntfcDesc buildUniversitySchema(SystemInterfaceDescriptionBuilder builder) {
       return builder.updateSymbolicName("University")
                .floatValueType("Decimal")
                .intValueType("Int")
                .stringValueType("String")
                .enumValueType("Grade", GradeScale.class)
                .customValueType("Date", DataTypePredicate.getInstance())
                .objectType("Person")
                .buildField("givenName", "String").multiplicity(Multiplicity.of(1, 1)).endField()
                .buildField("familyName", "String").multiplicity(Multiplicity.of(1, 1)).endField()
                .buildField("dateOfBirth", "Date").multiplicity(Multiplicity.of(1, 1)).endField()
                .buildField("address", "String").multiplicity(Multiplicity.of(1, -1)).endField()
                .endObjectType()
                .objectType("Student")
                .buildField("studentNo", "Int").multiplicity(Multiplicity.of(1, 1)).endField()
                .buildField("programme", "String").multiplicity(Multiplicity.of(1, 3)).endField()
                .buildField("person", "Person").multiplicity(Multiplicity.of(1, 1)).endField()
                .buildField("enrolledSince", "Date").multiplicity(Multiplicity.of(1, 1)).endField()
                .endObjectType()
                .objectType("Teacher")
                .buildField("salary", "Decimal").multiplicity(Multiplicity.of(1, 1)).endField()
                .buildField("person", "Person").multiplicity(Multiplicity.of(1, 1)).endField()
                .endObjectType()
                .objectType("Course")
                .buildField("courseCode", "String").multiplicity(Multiplicity.of(1, 1)).endField()
                .buildField("title", "String").multiplicity(Multiplicity.of(0, 1)).endField()
                .buildField("ects", "Int").multiplicity(Multiplicity.of(1, 1)).endField()
                .buildField("dependsOn", "Course").setSemantics().endField()
                .buildField("taughtBy", "Teacher").multiplicity(Multiplicity.of(1, -1)).endField()
                .endObjectType()
                .objectType("CourseSubscription")
                .buildField("student", "Student")
                .multiplicity(Multiplicity.of(1, 1))
                .setSemantics()
                .makeSymmetric("takes", "coursesTaken")
                .endField()
                .buildField("course", "Course")
                .multiplicity(Multiplicity.of(1, 1)).endField()
                .buildField("semester", "String").multiplicity(Multiplicity.of(1, 1)).endField()
                .endObjectType()
                .objectType("Examination")
                .buildField("date", "Date").multiplicity(Multiplicity.of(1, 1)).endField()
                .buildField("grade", "Grade").multiplicity(Multiplicity.of(1, 1)).endField()
                .buildField("for", "CourseSubscription")
                .multiplicity(Multiplicity.of(1, 1))
                .makeSymmetric("exam", "courseExamination", Multiplicity.of(0, 3), true, false)
                .endField()
                .endObjectType()
                .objectType("University")
                .buildField("students", "Student").makeContainment().endField()
                .buildField("courses", "Course").makeContainment().endField()
                .buildField("teachers", "Teacher").makeContainment().endField()
                .endObjectType()
                .build();


    }

}
