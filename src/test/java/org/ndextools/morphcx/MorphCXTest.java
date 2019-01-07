package org.ndextools.morphcx;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.ndextools.morphcx.shared.Configuration;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Unit tests for MorphCX class
 */
public class MorphCXTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void _ShouldHandleAnExceptionThrownByConfiguration() throws Exception {
        String[] args = {"--foobar"};
        thrown.expect(Exception.class);
        Configuration cfg = MorphCX.configureApp(args);
    }

    @Test
    public void _ShouldReturnAConfigurationOpject() throws Exception {
        String[] args = {"-S"};
        Configuration cfg = MorphCX.configureApp(args);
        Assert.assertTrue(cfg instanceof Configuration);
    }

    @Test
    public void _ShouldPrintHelpTextToConsole() throws Exception {
        String[] args = {"-h"};

        PrintStream ps;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ps = new PrintStream(outputStream);
        System.setOut(ps);

        Configuration cfg = MorphCX.configureApp(args);
        MorphCX.dispatchByOperation(cfg);

        System.setOut(System.out);

        String string = outputStream.toString();
        Assert.assertTrue(string.contains("usage: java -jar morphcx.jar"));

    }
}
