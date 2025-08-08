package io.corrlang.domain;

import io.corrlang.domain.schemas.Schema;
import io.corrlang.domain.schemas.SchemaBuilder;
import io.corrlang.domain.schemas.SchemaElementType;
import io.corrlang.testfixtures.ExampleSystems;
import no.hvl.past.graph.TestWithGraphLib;
import no.hvl.past.names.Name;
import no.hvl.past.util.Multiplicity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static io.corrlang.domain.ElemRef.eref;
import static org.junit.jupiter.api.Assertions.*;

public class SchemaTest extends TestWithGraphLib {

    private Schema universitySchema;

    private Schema calculatorSchema;

    @BeforeEach
    public void setUp() {
        universitySchema = ExampleSystems.buildUniversitySchema(
                new SchemaBuilder(
                        Name.identifier("University")
                        , getUniverse()));
        calculatorSchema = new SchemaBuilder(Name.identifier("Calculator"), getUniverse())
                .stringValueType("String")
                .intValueType("Number")
                .actionGroup("Arithmetic")
                .action("add")
                .buildInputArg("lhs", "Number").multiplicity(Multiplicity.of(1,1)).endArgument()
                .buildInputArg("rhs", "Number").multiplicity(Multiplicity.of(1, 1)).endArgument()
                .buildReturnArg("Numner").multiplicity(Multiplicity.of(0, 1)).endArgument()
                .end().build();
    }

    @Test
    public void testLookupInUniversity() throws Schema.NotFoundException {
        assertTrue(universitySchema.isMember(eref("Student")));
        assertTrue(universitySchema.isMember(eref("Course", "courseCode")));
        assertFalse(universitySchema.isMember(eref("Car")));

        assertEquals(Name.identifier("Date"), universitySchema.lookup(eref("Date")));
        assertEquals(Name.identifier("givenName").prefixWith(Name.identifier("Person")),
                universitySchema.lookup(eref("Person", "givenName")));
        try {
            universitySchema.lookup(eref("Car"));
            fail();
        } catch (Schema.NotFoundException e) {
            // success
        }

        assertEquals(SchemaElementType.NODE, universitySchema.typeOf(eref("Student")));
        assertEquals(SchemaElementType.PROP, universitySchema.typeOf(eref("Person", "givenName")));
        assertEquals(SchemaElementType.DATA_TYPE, universitySchema.typeOf(eref("Date")));
        assertEquals(SchemaElementType.LINK, universitySchema.typeOf(eref("Course", "taughtBy")));

    }


    @Test
    public void testLookupArgument() throws Schema.NotFoundException {
        Name lookup = calculatorSchema.lookup(eref("Arithmetic", "add", "lhs"));
        assertEquals(Name.identifier("lhs").index(1).prefixWith(Name.identifier("add").prefixWith(Name.identifier("Arithmetic"))), lookup);

    }

}
