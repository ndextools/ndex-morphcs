package org.ndextools.morphcx.shared;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit tests for Utilities class.
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

    @Test
    public void _ShouldConvertDelimiterToCommaString() {
        char delimiter = Configuration.ConfigurationConstants.COMMA;
        String strDelimiter = Utilities.delimiterToStringConvert(delimiter);
        Assert.assertEquals(",", strDelimiter);
    }

    @Test
    public void _ShouldConvertDelimiterToTabString() {
        char delimiter = Configuration.ConfigurationConstants.TAB;
        String strDelimiter = Utilities.delimiterToStringConvert(delimiter);
        Assert.assertEquals("\t", strDelimiter);
    }

    @Test
    public void _ShouldConvertDelimiterToCommaText() {
        char delimiter = Configuration.ConfigurationConstants.COMMA;
        String strDelimiter = Utilities.delimiterToDescriptionText(delimiter);
        Assert.assertEquals("COMMA_CHARACTER", strDelimiter);
    }

    @Test
    public void _ShouldConvertDelimiterToTabText() {
        char delimiter = Configuration.ConfigurationConstants.TAB;
        String strDelimiter = Utilities.delimiterToDescriptionText(delimiter);
        Assert.assertEquals("TAB_CHARACTER", strDelimiter);
    }
}
