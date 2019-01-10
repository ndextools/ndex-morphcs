package org.ndextools.morphcx.shared;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;

/**
 * Unit tests for Configuration class.
 */
@FixMethodOrder (MethodSorters.NAME_ASCENDING)
public class ConfigurationTest {
    private final String LBC_FILTERED_ERK_AKT = "src/test/resources/LBC_FILTERED_ERK_AKT.json";

    static Configuration performConfiguration(String[] args) throws Exception {
        Configuration configuration = new Configuration(args);
        configuration.configure();
        return configuration;
    }

    @Test
    public void _HelpOptionGiven() throws Exception {
        String[] args = {"-h"};
        Configuration cfg = performConfiguration(args);
        Assert.assertTrue(cfg.isHelp());
    }

    @Test
    public void _Should_Show_Input_Is_A_File_1() throws Exception {
        String[] args = {"-c", "tsv", "-i", LBC_FILTERED_ERK_AKT};
        Configuration cfg = performConfiguration(args);
        Assert.assertTrue(cfg.getInputIsFile());
        Assert.assertEquals(LBC_FILTERED_ERK_AKT, cfg.getInputFilename() );
    }

    @Test
    public void _Should_Show_Input_Is_A_File_2() throws Exception {
        String[] args = {"--input", LBC_FILTERED_ERK_AKT};
        Configuration cfg = performConfiguration(args);
        Assert.assertTrue(cfg.getInputIsFile());
        Assert.assertEquals(LBC_FILTERED_ERK_AKT, cfg.getInputFilename() );
    }

    @Test
    public void _Should_Show_Output_Is_A_File_1() throws Exception {
        String[] args = {"-o", LBC_FILTERED_ERK_AKT};
        Configuration cfg = performConfiguration(args);
        Assert.assertTrue(cfg.getOutputIsFile());
        Assert.assertEquals(LBC_FILTERED_ERK_AKT, cfg.getOutputFilename() );
    }

    @Test
    public void _Should_Show_Output_Is_A_File_2() throws Exception {
        String[] args = {"--output", LBC_FILTERED_ERK_AKT};
        Configuration cfg = performConfiguration(args);
        Assert.assertTrue(cfg.getOutputIsFile());
        Assert.assertEquals(LBC_FILTERED_ERK_AKT, cfg.getOutputFilename() );
    }

    @Ignore
    public void _Default_Newline_Should_Be_System_Property_Line_Separator() throws Exception {
        String[] args = {"-n", "system", "-i", LBC_FILTERED_ERK_AKT};
        Configuration cfg = performConfiguration(args);
        Assert.assertEquals(System.getProperty("line.separator"), cfg.getNewlineAsString());
    }

    @Test
    public void _Should_Be_Windows_Newline() throws Exception {
        String[] args = {"-n", "windows", "-i", LBC_FILTERED_ERK_AKT};
        Configuration cfg = performConfiguration(args);
        Assert.assertEquals(Configuration.OptionConstants.ESCAPE_R_ESCAPE_N, cfg.getNewlineAsString());
    }

    @Test
    public void _Should_Be_Linux_Newline() throws Exception {
        String[] args = {"-n", "linux", "-i", LBC_FILTERED_ERK_AKT};
        Configuration cfg = performConfiguration(args);
        Assert.assertEquals(Configuration.OptionConstants.ESCAPE_N, cfg.getNewlineAsString());
    }

    @Test
    public void _Should_Be_OSX_Newline() throws Exception {
        String[] args = {"-n", "osx", "-i", LBC_FILTERED_ERK_AKT};
        Configuration cfg = performConfiguration(args);
        Assert.assertEquals(Configuration.OptionConstants.ESCAPE_N, cfg.getNewlineAsString());
    }


    @Test
    public void _Should_Be_Oldmac_Newline() throws Exception {
        String[] args = {"-n", "oldmac", "-i", LBC_FILTERED_ERK_AKT};
        Configuration cfg = performConfiguration(args);
        Assert.assertEquals(Configuration.OptionConstants.ESCAPE_R, cfg.getNewlineAsString());
    }

    @Rule
    public ExpectedException thrown1 = ExpectedException.none();
    @Test
    public void _ShouldVerifyThatTheFileExistsAndCanBeRead() throws FileNotFoundException {
        String[] args = {"-i", LBC_FILTERED_ERK_AKT};
        Configuration cfg = new Configuration(args);
        cfg.fileExists(LBC_FILTERED_ERK_AKT);
    }

    @Test
    public void _ShouldThrowAFileNotFoundException() throws FileNotFoundException {
        String[] args = {"-i", "file_does_not_exist"};
        Configuration cfg = new Configuration(args);
        thrown1.expect(FileNotFoundException.class);
        cfg.fileExists("file_does_not_exist");
    }

}