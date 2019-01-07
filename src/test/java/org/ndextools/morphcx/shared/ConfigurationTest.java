package org.ndextools.morphcx.shared;

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;

/**
 * Unit tests for Configuration class.
 */
public class ConfigurationTest {
    private final String RAW_CX_NETWORK_LBC = "src/test/resources/LBC_FILTERED_ERK_AKT.json";

    static Configuration performConfiguration(String[] args) throws Exception {
        Configuration configuration = new Configuration(args);
        configuration.configure();
        return configuration;
    }

    @Test
    public void _HelpOptionGiven() throws Exception {
        String[] args = {"-h"};
        Configuration cfg = performConfiguration(args);
        Assert.assertTrue(Configuration.isHelp());
    }

    @Ignore
    public void _Should_Show_Input_Is_A_File_1() throws Exception {
        String[] args = {"-i", RAW_CX_NETWORK_LBC};
        Configuration cfg = performConfiguration(args);
        Assert.assertTrue(cfg.getInputIsFile());
        Assert.assertEquals( RAW_CX_NETWORK_LBC, Configuration.getInputFilename() );
    }

    @Ignore
    public void _Should_Show_Input_Is_A_File_2() throws Exception {
        String[] args = {"--input", RAW_CX_NETWORK_LBC};
        Configuration cfg = performConfiguration(args);
        Assert.assertTrue(cfg.getInputIsFile());
        Assert.assertEquals( RAW_CX_NETWORK_LBC, Configuration.getInputFilename() );
    }

    @Test
    public void _Should_Show_Output_Is_A_File_1() throws Exception {
        String[] args = {"-o", RAW_CX_NETWORK_LBC};
        Configuration cfg = performConfiguration(args);
        Assert.assertTrue(cfg.getOutputIsFile());
        Assert.assertEquals( RAW_CX_NETWORK_LBC, Configuration.getOutputFilename() );
    }

    @Test
    public void _Should_Show_Output_Is_A_File_2() throws Exception {
        String[] args = {"--output", RAW_CX_NETWORK_LBC};
        Configuration cfg = performConfiguration(args);
        Assert.assertTrue(cfg.getOutputIsFile());
        Assert.assertEquals( RAW_CX_NETWORK_LBC, Configuration.getOutputFilename() );
    }

    @Test
    public void _Default_Newline_Should_Be_System_Property_Line_Separator() throws Exception {
        String[] args = {"-n", "system", "-i", RAW_CX_NETWORK_LBC};
        Configuration cfg = performConfiguration(args);
        Assert.assertEquals(System.getProperty("line.separator"), Configuration.getNewlineAsString());
    }

    @Ignore
    public void _Should_Be_Windows_Newline() throws Exception {
        String[] args = {"-n", "windows", "-i", RAW_CX_NETWORK_LBC};
        Configuration cfg = performConfiguration(args);
        Assert.assertEquals(Configuration.OptionConstants.ESCAPE_R_ESCAPE_N, Configuration.getNewlineAsString());
    }

    @Test
    public void _Should_Be_Linux_Newline() throws Exception {
        String[] args = {"-n", "linux", "-i", RAW_CX_NETWORK_LBC};
        Configuration cfg = performConfiguration(args);
        Assert.assertEquals(Configuration.OptionConstants.ESCAPE_N, Configuration.getNewlineAsString());
    }

    @Test
    public void _Should_Be_OSX_Newline() throws Exception {
        String[] args = {"-n", "osx", "-i", RAW_CX_NETWORK_LBC};
        Configuration cfg = performConfiguration(args);
        Assert.assertEquals(Configuration.OptionConstants.ESCAPE_N, Configuration.getNewlineAsString());
    }


    @Test
    public void _Should_Be_Oldmac_Newline() throws Exception {
        String[] args = {"-n", "oldmac", "-i", RAW_CX_NETWORK_LBC};
        Configuration cfg = performConfiguration(args);
        Assert.assertEquals(Configuration.OptionConstants.ESCAPE_R, Configuration.getNewlineAsString());
    }

    @Rule
    public ExpectedException thrown1 = ExpectedException.none();
    @Test
    public void _ShouldVerifyThatTheFileExistsAndCanBeRead() throws FileNotFoundException {
        String[] args = {"-i", RAW_CX_NETWORK_LBC};
        Configuration cfg = new Configuration(args);
        cfg.fileExists(RAW_CX_NETWORK_LBC);
    }

    @Test
    public void _ShouldThrowAFileNotFoundException() throws FileNotFoundException {
        String[] args = {"-i", "file_does_not_exist"};
        Configuration cfg = new Configuration(args);
        thrown1.expect(FileNotFoundException.class);
        cfg.fileExists("file_does_not_exist");
    }

    @Test
    public void _ServerOptionForcesStdinAndStdoutEvenIfInputAndOutputSpecified() throws Exception {
        String[] args = {"-S", "i", RAW_CX_NETWORK_LBC, "-o", RAW_CX_NETWORK_LBC};
        Configuration cfg = performConfiguration(args);
        Assert.assertFalse(Configuration.getInputIsFile());
        Assert.assertFalse(Configuration.getOutputIsFile());
    }

}