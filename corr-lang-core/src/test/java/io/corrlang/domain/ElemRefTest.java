package io.corrlang.domain;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ElemRefTest {

    @Test
    public void testMatching() {
        ElemRef n1 = ElemRef.eref("name");
        ElemRef n2 = ElemRef.eref("Person", "name");
        ElemRef n3 = n2.addPrefix("Sales");

        assertTrue(n1.exactMatch(n1));
        assertFalse(n1.exactMatch(n2));
        assertTrue(n1.prefixMatch(n1));
        assertTrue(n1.suffixMatch(n1));

        assertTrue(n2.suffixMatch(n1));
        assertFalse(n1.suffixMatch(n2));
        assertTrue(n3.suffixMatch(n2));
        assertTrue(n3.suffixMatch(n1));
        assertTrue(n3.exactMatch(ElemRef.eref("Sales", "Person", "name")));
        assertTrue(n3.prefixMatch(ElemRef.eref("Sales", "Person")));
    }
}
