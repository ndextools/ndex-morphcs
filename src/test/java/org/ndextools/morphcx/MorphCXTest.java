package org.ndextools.morphcx;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.ndextools.morphcx.shared.Utilities;

/**
 * Unit tests for MorphCX class
 */
public class MorphCXTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void _ShouldThrowNullPointerException() {
        thrown.expect(NullPointerException.class);
        Utilities.nullReferenceCheck(null, "MorphCXTest");
    }

}
