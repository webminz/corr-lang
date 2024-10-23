package io.corrlang.domain;

import io.corrlang.domain.builders.SystemInterfaceDescriptionBuilder;
import io.corrlang.tests.ExampleSystems;
import no.hvl.past.graph.TestWithGraphLib;
import no.hvl.past.names.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static io.corrlang.domain.ElemRef.eref;
import static org.junit.jupiter.api.Assertions.*;

public class IntfDescTest extends TestWithGraphLib {

    private IntfcDesc universitySchema;

    @BeforeEach
    public void setUp() {
        universitySchema = ExampleSystems.buildUniversitySchema(
                new SystemInterfaceDescriptionBuilder(
                        Name.identifier("University")
                        , getUniverse()));
    }

    @Test
    public void testLookupInUniversity() throws IntfcDesc.NotFoundException {
        assertTrue(universitySchema.isMember(eref("Student")));
        assertTrue(universitySchema.isMember(eref("Course", "courseCode")));
        assertFalse(universitySchema.isMember(eref("Car")));

        assertEquals(Name.identifier("Date"), universitySchema.lookup(eref("Date")));
        assertEquals(Name.identifier("givenName").prefixWith(Name.identifier("Person")),
                universitySchema.lookup(eref("Person", "givenName")));
        try {
            universitySchema.lookup(eref("Car"));
            fail();
        } catch (IntfcDesc.NotFoundException e) {
            // success
        }

        assertEquals(SchemaElement.NODE, universitySchema.typeOf(eref("Student")));
        assertEquals(SchemaElement.PROP, universitySchema.typeOf(eref("Person", "givenName")));
        assertEquals(SchemaElement.DATA_TYPE, universitySchema.typeOf(eref("Date")));
        assertEquals(SchemaElement.LINK, universitySchema.typeOf(eref("Course", "taughtBy")));

    }

}
