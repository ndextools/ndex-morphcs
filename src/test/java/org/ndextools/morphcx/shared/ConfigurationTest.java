package org.ndextools.morphcx.shared;

import org.junit.*;
import org.junit.rules.ExpectedException;

/**
 * Unit tests for Configuration class.
 */
public class ConfigurationTest {
    private final String LBC_FILTERED_ERK_AKT = "src/test/resources/LBC_FILTERED_ERK_AKT.json";

    static Configuration performConfiguration(String[] args) throws Exception {
        Configuration c = new Configuration();
        return c.configure(args);
    }

    @Test
    public void _Should_Show_Convert_TSV_Is_Set() throws Exception {
        String[] args = {"-c", "tsv"};
        Configuration cfg = performConfiguration(args);
        Assert.assertEquals(Configuration.Operation.TSV.toString(), cfg.getOperation().toString());
    }

    @Test
    public void _Should_Show_Convert_CSV_Is_Set() throws Exception {
        String[] args = {"-c", "csv"};
        Configuration cfg = performConfiguration(args);
        Assert.assertEquals(Configuration.Operation.CSV.toString(), cfg.getOperation().toString());
    }

    @Test
    public void _Should_Show_Convert_EXCEL_Is_Set() throws Exception {
        String[] args = {"-c", "excel"};
        Configuration cfg = performConfiguration(args);
        Assert.assertEquals(Configuration.Operation.EXCEL.toString(), cfg.getOperation().toString());
    }

    @Test
    public void _Should_Show_Convert_Default_Is_TSV() throws Exception {
        String[] args = {};
        Configuration cfg = performConfiguration(args);
        Assert.assertEquals(Configuration.Operation.TSV.toString(), cfg.getOperation().toString());
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
        Assert.assertTrue(cfg.inputIsFile());
        Assert.assertEquals(LBC_FILTERED_ERK_AKT, cfg.getInputFilename() );
    }

    @Test
    public void _Should_Show_Input_Is_A_File_2() throws Exception {
        String[] args = {"--input", LBC_FILTERED_ERK_AKT};
        Configuration cfg = performConfiguration(args);
        Assert.assertTrue(cfg.inputIsFile());
        Assert.assertEquals(LBC_FILTERED_ERK_AKT, cfg.getInputFilename() );
    }

    @Test
    public void _Should_Show_Output_Is_A_File_1() throws Exception {
        String[] args = {"-o", LBC_FILTERED_ERK_AKT};
        Configuration cfg = performConfiguration(args);
        Assert.assertTrue(cfg.outputIsFile());
        Assert.assertEquals(LBC_FILTERED_ERK_AKT, cfg.getOutputFilename() );
    }

    @Test
    public void _Should_Show_Output_Is_A_File_2() throws Exception {
        String[] args = {"--output", LBC_FILTERED_ERK_AKT};
        Configuration cfg = performConfiguration(args);
        Assert.assertTrue(cfg.outputIsFile());
        Assert.assertEquals(LBC_FILTERED_ERK_AKT, cfg.getOutputFilename() );
    }

    @Test
    public void _Default_Newline_Should_Be_System_Property_Line_Separator() throws Exception {
        String[] args = {"-n", "system", "-i", LBC_FILTERED_ERK_AKT};
        Configuration cfg = performConfiguration(args);
        Assert.assertEquals(System.getProperty("line.separator"), cfg.getNewlineAsString());
    }

    @Test
    public void _Should_Be_Windows_Newline() throws Exception {
        String[] args = {"-n", "windows", "-i", LBC_FILTERED_ERK_AKT};
        Configuration cfg = performConfiguration(args);
        Assert.assertEquals(Configuration.ConfigurationConstants.ESCAPE_R_ESCAPE_N, cfg.getNewlineAsString());
    }

    @Test
    public void _Should_Be_Linux_Newline() throws Exception {
        String[] args = {"-n", "linux", "-i", LBC_FILTERED_ERK_AKT};
        Configuration cfg = performConfiguration(args);
        Assert.assertEquals(Configuration.ConfigurationConstants.ESCAPE_N, cfg.getNewlineAsString());
    }

    @Test
    public void _Should_Be_OSX_Newline() throws Exception {
        String[] args = {"-n", "osx", "-i", LBC_FILTERED_ERK_AKT};
        Configuration cfg = performConfiguration(args);
        Assert.assertEquals(Configuration.ConfigurationConstants.ESCAPE_N, cfg.getNewlineAsString());
    }


    @Test
    public void _Should_Be_Oldmac_Newline() throws Exception {
        String[] args = {"-n", "oldmac", "-i", LBC_FILTERED_ERK_AKT};
        Configuration cfg = performConfiguration(args);
        Assert.assertEquals(Configuration.ConfigurationConstants.ESCAPE_R, cfg.getNewlineAsString());
    }

    @Test
    public void _Should_Show_Server_Option_Is_Set() throws Exception {
        String[] args = {"-S"};
        Configuration cfg = performConfiguration(args);
        Assert.assertTrue(cfg.isServer());
    }

    @Test
    public void _Should_Show_Server_Option_Results_In_System_IO() throws Exception {
        String[] args = {"-S"};
        Configuration cfg = performConfiguration(args);
        Assert.assertFalse(cfg.inputIsFile());
        Assert.assertFalse(cfg.outputIsFile());
    }

    @Rule
    public ExpectedException thrown1 = ExpectedException.none();
    @Test
    public void _ShouldVerifyThatTheFileExistsAndCanBeRead() throws Exception {
        String[] args = {"-i", LBC_FILTERED_ERK_AKT};
        Configuration cfg = new Configuration();
        cfg = cfg.configure(args);
    }

    @Test
    public void _ShouldThrowAFileNotFoundException() throws Exception {
        String[] args = {"-i", "file_does_not_exist"};
        Configuration cfg = new Configuration();
        thrown1.expect(Exception.class);
        cfg = cfg.configure(args);
    }

//    @Test
//    public void _ShouldShowToString() throws Exception {
//        String[] args = {"-i", LBC_FILTERED_ERK_AKT, "-o", LBC_FILTERED_ERK_AKT};
//        Configuration cfg = new Configuration();
//        String str = cfg.configure(args).toString();
//        System.err.println(str);
//    }

}