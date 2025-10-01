package io.corrlang.domain;

import io.corrlang.domain.schemas.Schema;
import io.corrlang.domain.schemas.SchemaBuilder;
import no.hvl.past.graph.TestWithGraphLib;
import no.hvl.past.names.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for construction of the global view, i.e. the most important activity in CorrLang.
 */
public class GlobalViewTest extends TestWithGraphLib {


    private Schema left;

    private Schema middle;

    private Schema right;


    @BeforeEach
    public void setUp() {
        left = new SchemaBuilder(Name.identifier("left"), getUniverse())
                .objectType("A")
                .field("ab", "B")
                .field("ac", "C")
                .endObjectType()
                .objectType("B").field("bb", "B").endObjectType()
                .objectType("C").field("cb", "B").endObjectType()
                .build();

        middle = new SchemaBuilder(Name.identifier("middle"), getUniverse())
                .objectType("X").field("x", "Y").field("z", "Y").endObjectType()
                .objectType("Z").field("z", "X").endObjectType().build();

        right = new SchemaBuilder(Name.identifier("right"), getUniverse())
                .objectType("M").field("m", "N").endObjectType()
                .objectType("N").field("n", "P").endObjectType()
                .objectType("P").endObjectType()
                .objectType("O").inheritsFrom("N").endObjectType()
                .objectType("R").inheritsFrom("N").endObjectType()
                .build();
    }

    @Test
    public void testGlobalViewSum() {



    }

}
