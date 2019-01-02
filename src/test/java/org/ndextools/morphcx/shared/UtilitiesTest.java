package org.ndextools.morphcx.shared;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit tests for CXReader class.
 */
public class UtilitiesTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void _ShouldThrowNullPointerException() {
        thrown.expect(NullPointerException.class);
        Utilities.nullReferenceCheck(null, Configuration.class.getSimpleName());
    }

    @Test
    public void _ShouldRecognizeNonNullReference() {
        String str = "foo";
        Utilities.nullReferenceCheck(str, Configuration.class.getSimpleName());
    }

}
