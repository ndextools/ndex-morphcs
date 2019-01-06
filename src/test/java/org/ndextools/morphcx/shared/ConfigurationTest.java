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

    @Ignore
    public void _Default_Input_Stream_Should_Not_Be_A_File() throws Exception {
        String[] args = {"tocsv"};
        Configuration cfg = performConfiguration(args);
        Assert.assertFalse(cfg.getInputIsFile());
    }

    @Ignore
    public void _Should_Show_Input_Is_A_File_1() throws Exception {
        String[] args = {"tocsv", "-i", RAW_CX_NETWORK_LBC};
        Configuration cfg = performConfiguration(args);
        Assert.assertTrue(cfg.getInputIsFile());
        Assert.assertEquals( RAW_CX_NETWORK_LBC, cfg.getInputFilename() );
    }

    @Ignore
    public void _Should_Show_Input_Is_A_File_2() throws Exception {
        String[] args = {"tocsv", "--input", RAW_CX_NETWORK_LBC};
        Configuration cfg = performConfiguration(args);
        Assert.assertTrue(cfg.getInputIsFile());
        Assert.assertEquals( RAW_CX_NETWORK_LBC, cfg.getInputFilename() );
    }

    @Ignore
    public void _Should_Show_Output_Is_A_File_1() throws Exception {
        String[] args = {"tocsv", "-o", RAW_CX_NETWORK_LBC};
        Configuration cfg = performConfiguration(args);
        Assert.assertTrue(cfg.getOutputIsFile());
        Assert.assertEquals( RAW_CX_NETWORK_LBC, cfg.getOutputFilename() );
    }

    @Ignore
    public void _Should_Show_Output_Is_A_File_2() throws Exception {
        String[] args = {"tocsv", "--output", RAW_CX_NETWORK_LBC};
        Configuration cfg = performConfiguration(args);
        Assert.assertTrue(cfg.getOutputIsFile());
        Assert.assertEquals( RAW_CX_NETWORK_LBC, cfg.getOutputFilename() );
    }

//    @Ignore
//    public void _Default_Newline_Should_Be_System_Property_Line_Separator() throws Exception {
//        String[] args = {"tocsv"};
//        Configuration cfg = performConfiguration(args);
//        Assert.assertFalse(cfg.isWindowsNewline());
//        Assert.assertFalse(cfg.isLinuxNewline());
//        Assert.assertFalse(cfg.isOSXNewline());
//        Assert.assertFalse(cfg.isOldMacNewline());
//        Assert.assertTrue(cfg.isSystemNewline());
//        Assert.assertEquals( System.getProperty("line.separator"), cfg.getNewline() );
//    }
//
//    @Ignore
//    public void _Should_Show_Windows_Newline_Separator_1() throws Exception {
//        String[] args = {"tocsv", "-n", "windows"};
//        Configuration cfg = performConfiguration(args);
//        Assert.assertTrue(cfg.isWindowsNewline());
//        Assert.assertFalse(cfg.isLinuxNewline());
//        Assert.assertFalse(cfg.isOSXNewline());
//        Assert.assertFalse(cfg.isOldMacNewline());
//        Assert.assertFalse(cfg.isSystemNewline());
//        Assert.assertEquals( "\r\n", cfg.getNewline() );
//    }
//
//    @Ignore
//    public void _Should_Show_Windows_Newline_Separator_2() throws Exception {
//        String[] args = {"tocsv", "--newline", "windows"};
//        Configuration cfg = performConfiguration(args);
//        Assert.assertTrue(cfg.isWindowsNewline());
//        Assert.assertFalse(cfg.isLinuxNewline());
//        Assert.assertFalse(cfg.isOSXNewline());
//        Assert.assertFalse(cfg.isOldMacNewline());
//        Assert.assertFalse(cfg.isSystemNewline());
//        Assert.assertEquals( "\r\n", cfg.getNewline() );
//    }
//
//
//    @Ignore
//    public void _Should_Show_Linux_Newline_Separator_1() throws Exception {
//        String[] args = {"tocsv", "-n", "linux"};
//        Configuration cfg = performConfiguration(args);
//        Assert.assertFalse(cfg.isWindowsNewline());
//        Assert.assertTrue(cfg.isLinuxNewline());
//        Assert.assertFalse(cfg.isOSXNewline());
//        Assert.assertFalse(cfg.isOldMacNewline());
//        Assert.assertFalse(cfg.isSystemNewline());
//        Assert.assertEquals( "\n", cfg.getNewline() );
//    }
//
//    @Ignore
//    public void _Should_Show_Linux_Newline_Separator_2() throws Exception {
//        String[] args = {"tocsv", "--newline", "linux"};
//        Configuration cfg = performConfiguration(args);
//        Assert.assertFalse(cfg.isWindowsNewline());
//        Assert.assertTrue(cfg.isLinuxNewline());
//        Assert.assertFalse(cfg.isOSXNewline());
//        Assert.assertFalse(cfg.isOldMacNewline());
//        Assert.assertFalse(cfg.isSystemNewline());
//        Assert.assertEquals( "\n", cfg.getNewline() );
//    }
//
//
//    @Ignore
//    public void _Should_Show_OSX_Newline_Separator_1() throws Exception {
//        String[] args = {"tocsv", "-n", "osx"};
//        Configuration cfg = performConfiguration(args);
//        Assert.assertFalse(cfg.isWindowsNewline());
//        Assert.assertFalse(cfg.isLinuxNewline());
//        Assert.assertTrue(cfg.isOSXNewline());
//        Assert.assertFalse(cfg.isOldMacNewline());
//        Assert.assertFalse(cfg.isSystemNewline());
//        Assert.assertEquals( "\n", cfg.getNewline() );
//    }
//
//    @Ignore
//    public void _Should_Show_OSX_Newline_Separator_2() throws Exception {
//        String[] args = {"tocsv", "--newline", "osx"};
//        Configuration cfg = performConfiguration(args);
//        Assert.assertFalse(cfg.isWindowsNewline());
//        Assert.assertFalse(cfg.isLinuxNewline());
//        Assert.assertTrue(cfg.isOSXNewline());
//        Assert.assertFalse(cfg.isOldMacNewline());
//        Assert.assertFalse(cfg.isSystemNewline());
//        Assert.assertEquals( "\n", cfg.getNewline() );
//    }
//
//    @Ignore
//    public void _Should_Show_OldMac_Newline_Separator_1() throws Exception {
//        String[] args = {"tocsv", "-n", "oldmac"};
//        Configuration cfg = performConfiguration(args);
//        Assert.assertFalse(cfg.isWindowsNewline());
//        Assert.assertFalse(cfg.isLinuxNewline());
//        Assert.assertFalse(cfg.isOSXNewline());
//        Assert.assertTrue(cfg.isOldMacNewline());
//        Assert.assertFalse(cfg.isSystemNewline());
//        Assert.assertEquals( "\r", cfg.getNewline() );
//    }
//
//    @Ignore
//    public void _Should_Show_OldMac_Newline_Separator_2() throws Exception {
//        String[] args = {"tocsv", "--newline", "oldmac"};
//        Configuration cfg = performConfiguration(args);
//        Assert.assertFalse(cfg.isWindowsNewline());
//        Assert.assertFalse(cfg.isLinuxNewline());
//        Assert.assertFalse(cfg.isOSXNewline());
//        Assert.assertTrue(cfg.isOldMacNewline());
//        Assert.assertFalse(cfg.isSystemNewline());
//        Assert.assertEquals( "\r", cfg.getNewline() );
//    }
//
//
//    @Ignore
//    public void _Should_Show_System_Newline_Separator_1() throws Exception {
//        String[] args = {"tocsv", "-n", "system"};
//        Configuration cfg = performConfiguration(args);
//        Assert.assertFalse(cfg.isWindowsNewline());
//        Assert.assertFalse(cfg.isLinuxNewline());
//        Assert.assertFalse(cfg.isOSXNewline());
//        Assert.assertFalse(cfg.isOldMacNewline());
//        Assert.assertTrue(cfg.isSystemNewline());
//        Assert.assertEquals( System.getProperty("line.separator"), cfg.getNewline() );
//    }
//
//    @Ignore
//    public void _Should_Show_System_Newline_Separator_2() throws Exception {
//        String[] args = {"tocsv", "--newline", "system"};
//        Configuration cfg = performConfiguration(args);
//        Assert.assertFalse(cfg.isWindowsNewline());
//        Assert.assertFalse(cfg.isLinuxNewline());
//        Assert.assertFalse(cfg.isOSXNewline());
//        Assert.assertFalse(cfg.isOldMacNewline());
//        Assert.assertTrue(cfg.isSystemNewline());
//        Assert.assertEquals( System.getProperty("line.separator"), cfg.getNewline() );
//    }

    @Rule
    public ExpectedException thrown1 = ExpectedException.none();
    @Ignore
    public void _ShouldVerifyThatTheFileExistsAndCanBeRead() throws FileNotFoundException {
        String[] args = {"tocsv", "-i", RAW_CX_NETWORK_LBC};
        Configuration cfg = new Configuration(args);
        cfg.fileExists(RAW_CX_NETWORK_LBC);
    }

    @Ignore
    public void _ShouldThrowAFileNotFoundException() throws FileNotFoundException {
        String[] args = {"tocsv", "-i", "file_does_not_exist"};
        Configuration cfg = new Configuration(args);
        thrown1.expect(FileNotFoundException.class);
        cfg.fileExists("file_does_not_exist");
    }

}