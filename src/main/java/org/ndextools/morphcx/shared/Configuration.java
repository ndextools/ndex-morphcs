package org.ndextools.morphcx.shared;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.cli.*;
import org.ndextools.morphcx.MorphCX;

/**
 * The Configuration class validates command-line parameters used when starting this application, and
 * the runtime configuration used by the application is finalized.
 */
public final class Configuration {
    private String[] args;
    private Options helpOptions;

    private static boolean isHelp = false;
    private static boolean isDebug = false;
    private static boolean producesCSV = false;
    private static boolean producesTSV = false;

    private boolean inputIsFile = false;
    private boolean outputFilespecGiven = false;

    private boolean isFormatDefault = false;
    private boolean isFormatTDF = false;
    private boolean isFormatRFC4180 = false;
    private boolean isFormatEXCEL = false;

    private boolean isWindowsNewline = false;
    private boolean isLinuxNewline = false;
    private boolean isOSXNewline = false;
    private boolean isOldMacNewline = false;
    private boolean isSystemNewline = false;

    private String inputFilespec;
    private String outputFilespec;
    private char columnSeparator;
    private String newline;

    enum ColumnSeparator {
        COMMA(','), TAB('\t');

        private char columnSeparator;
        private char getColumnSeparator() {
            return this.getColumnSeparator();
        }

        ColumnSeparator(char columnSeparator) {
            this.columnSeparator = columnSeparator;
        }
    }

    enum NewLine {
        WINDOWS("\r\n"), LINUX("\n"), OSX("\n"), OLDMAC("\r");

        private String endLine;
        private String getEndLine() {
            return this.endLine;
        }

        NewLine(String endLine) {
            this.endLine = endLine;
        }
    }

    /**
     * Class constructor
     *
     * @param args command-line parameters (if any) when invoking the application.
     */
    public Configuration(final String[] args) {
        this.args = args;
    }

    public final void configure() throws Exception {

        MorphCX.nullReferenceCheck(this.args, Configuration.class.getSimpleName());

        CommandLine parsedParams = defineParams();
        resolve(parsedParams);
        validate();
    }

    /**
     * This method defines all command-line parameters used when starting this application.
     *
     * @throws ParseException occurs when an invalid parameter exists in the command-line
     */
    private CommandLine defineParams() throws ParseException {

        // Apache Commons CLI - Step #1 of 3: Define all command-line parameters
        setHelpOptions( new Options());

        getHelpOptions().addOption(
                Option.builder(OptionConstants.OPT_CONVERT)
                        .longOpt(OptionConstants.LONG_OPT_CONVERT)

                        .hasArg()
                        .desc("Converts an NDEx CX network to a .csv or .tsv format. < CSV | TSV >  Default: 'TSV'.")
                        .build()
        );
        getHelpOptions().addOption(
                 Option.builder(OptionConstants.OPT_HELP)
                        .longOpt(OptionConstants.LONG_OPT_HELP)
                        .desc("Displays this help information.")
                        .build()
        );
        getHelpOptions().addOption(
                Option.builder(OptionConstants.OPT_INPUT)
                        .longOpt(OptionConstants.LONG_OPT_INPUT)
                        .hasArg()
                        .desc("Full input path and file specification. Default: input comes from STDIN rather than a file.")
                        .build()
        );
        getHelpOptions().addOption(
                Option.builder(OptionConstants.OPT_NEWLINE)
                        .longOpt(OptionConstants.LONG_OPT_NEWLINE)
                        .hasArg()
                        .desc("Platform-dependent newline characters. < WINDOWS | LINUX | OSX | OLDMAC > Default: obtained from the Java VM system runtime.")
                        .build()
        );
        getHelpOptions().addOption(
                Option.builder(OptionConstants.OPT_OUTPUT)
                        .longOpt(OptionConstants.LONG_OPT_OUTPUT)
                        .hasArg()
                        .desc("Full output path and file specification. Default: output sent to STDOUT rather than a file.")
                        .build()
        );
        getHelpOptions().addOption(
                Option.builder(OptionConstants.OPT_FORMAT)
                        .longOpt(OptionConstants.LONG_OPT_FORMAT)
                        .hasArg()
                        .desc("Output format as defined by the Apache Commons CSV library. Other options: RFC4180 and EXCEL. < DEFAULT | TDF | RFC4180 | EXCEL >  Default: 'DEFAULT'.")
                        .build()
        );
        getHelpOptions().addOption(
                Option.builder(OptionConstants.OPT_SERVER)
                        .desc("Program to run as server process. IO is forced to STDIN and STDOUT.")
                        .build()
        );

        getHelpOptions().addOption(
                 Option.builder(OptionConstants.OPT_DEBUG)
                        .desc("Used for development and purposes where log messages are output to STDERR.")
                        .build()
        );

        // Apache Commons CLI - Step #2 of 3: Parse all command-line parameters
        CommandLine parsed;
        CommandLineParser parser = new DefaultParser();
        try {
            parsed = parser.parse(getHelpOptions(), getArgs());
        } catch (ParseException e) {
            String errMsg = this.getClass().getSimpleName() + ": " + e.getMessage();
            throw new ParseException(errMsg);
        }

        return parsed;
    }

    /**
     * This method resolves all command-line options associated with their respective CLI parameters.
     */
    private final void resolve(CommandLine parsed) {

        // Apache Commons CLI - Step #3 of 3: Interrogate and resolve parameter settings
        if (parsed.hasOption(OptionConstants.OPT_HELP)) {
            setIsHelp(true);
        }

        if (parsed.hasOption(OptionConstants.OPT_DEBUG)) {
            setIsDebug(true);
        }

        if (parsed.hasOption(OptionConstants.OPT_CONVERT)) {
            String export = parsed.getOptionValue(OptionConstants.OPT_CONVERT).toUpperCase();
            switch (export) {
                case OptionConstants.CSV:
                    setProducesCSV(true);
                    setColumnSeparator(OptionConstants.COMMA);
                    break;
                case OptionConstants.TSV:
                default:
                    setProducesTSV(true);
                    setColumnSeparator(OptionConstants.TAB);
                    break;
            }
        } else {
            setProducesTSV(true);
        }

        if (parsed.hasOption(OptionConstants.OPT_FORMAT)) {
            String format = parsed.getOptionValue(OptionConstants.OPT_FORMAT).toUpperCase();
            switch (format) {
                case OptionConstants.EXCEL:
                    setFormatEXCEL(true);
                    break;
                case OptionConstants.RFC4180:
                    setFormatRFC4180(true);
                    break;
                case OptionConstants.DEFAULT:
                default:
                    setFormatDefault(true);
            }
        } else {
            setFormatDefault(true);
        }

        if (parsed.hasOption(OptionConstants.OPT_INPUT)) {
            setInputIsFile(true);
            setInputFilespec(parsed.getOptionValue(OptionConstants.OPT_INPUT));
        }

        if (parsed.hasOption(OptionConstants.OPT_OUTPUT)) {
            setOutputFilespecGiven(true);
            setOutputFilespec(parsed.getOptionValue(OptionConstants.OPT_OUTPUT));
        }

        if (parsed.hasOption(OptionConstants.OPT_NEWLINE)) {
            String newline = parsed.getOptionValue(OptionConstants.OPT_NEWLINE).toUpperCase();
            switch (newline) {
                case OptionConstants.WINDOWS:
                    setWindowsNewline(true);
                    setNewline(OptionConstants.ESCAPE_R_ESCAPE_N);
                    break;
                case OptionConstants.LINUX:
                    setLinuxNewline(true);
                    setNewline(OptionConstants.ESCAPE_N);
                    break;
                case OptionConstants.OSX:
                    setOSXNewline(true);
                    setNewline(OptionConstants.ESCAPE_N);
                    break;
                case OptionConstants.OLDMAC:
                    setOldMacNewline(true);
                    setNewline(OptionConstants.ESCAPE_R);
                    break;
                case OptionConstants.SYSTEM:
                    setSystemNewline(true);
                    setNewline(System.getProperty("line.separator"));
                    break;
            }
        } else {
            setSystemNewline(true);
            setNewline(System.getProperty("line.separator"));
        }
    }

    private final void validate()throws IOException, SecurityException {
        if (isHelp()) {
            String prefix = "java -jar morphcx.jar";
            String header = "where parameter options are:";
            String footer = "";
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(132, prefix, header, getHelpOptions(), footer, true);
            return;
        }

        if (getInputIsFile()) {
            fileExists(getInputFilespec());
        }

        if (!outputFilespecGiven) {
            if (producesTSV) {
                setOutputFilespec( buildOutputFilespec(getInputFilespec(), OptionConstants.TSV_EXT) );
            } else {
                setOutputFilespec( buildOutputFilespec(getInputFilespec(), OptionConstants.CSV_EXT) );
            }
        }
    }

    /**
     *  This method checks if a file (not a directory) exists and can be read/written to.
     *
     * @param fileSpec the directory path, name and extension of the file to be accessed.
     * @return a validated file specification, i.e. a file that can be opened.
     * @throws FileNotFoundException occurs when the input file is not found.
     * @throws SecurityException occurs when read-access to the input file is denied.
     */
    final boolean fileExists(String fileSpec)
            throws FileNotFoundException, SecurityException {

        File file = new File(fileSpec);
        if( !file.isFile() ){
            String errMsg = this.getClass().getSimpleName() + ": " + fileSpec + " - file not found";
            throw new FileNotFoundException(errMsg);
        }
        if( !file.canRead() ){
            String errMsg = this.getClass().getSimpleName() + ": " + fileSpec + " - access denied";
            throw new SecurityException(errMsg);
        }

        return true;
    }

    private static String buildOutputFilespec(String inpFilespec, String extension) {
        int lastDot = inpFilespec.lastIndexOf(".");
        if (lastDot != -1 && lastDot != 0) {
            return inpFilespec.substring(0, lastDot) + extension;
        } else {
            return inpFilespec + extension;
        }

    }

    public String[] getArgs() { return this.args; }

    public Options getHelpOptions() {
        return this.helpOptions;
    }

    void setHelpOptions(Options helpOptions) {
        this.helpOptions = helpOptions;
    }

    public boolean isHelp() {
        return this.isHelp;
    }

    void setIsHelp(boolean value) {
        this.isHelp = value;
    }

    public boolean isDebug() {
        return this.isDebug;
    }

    void setIsDebug(boolean value) {
        this.isDebug = value;
    }
    public boolean getInputIsFile() {
        return this.inputIsFile;
    }

    void setInputIsFile(boolean value) {
        this.inputIsFile = value;
    }

    public boolean getOutputFilespecGiven() {
        return this.outputFilespecGiven;
    }

    void setOutputFilespecGiven(boolean value) {
        this.outputFilespecGiven = value;
    }

    public boolean getProducesCSV() {
        return this.producesCSV;
    }

    void setProducesCSV(boolean producesCSV) {
        this.producesCSV = producesCSV;
    }

    public boolean getProducesTSV() {
        return this.producesTSV;
    }

    void setProducesTSV(boolean producesTSV) {
        this.producesTSV = producesTSV;
    }

    public boolean isFormatDefault() {
        return isFormatDefault;
    }

    public void setFormatDefault(boolean formatDefault) {
        isFormatDefault = formatDefault;
    }

    public boolean isFormatTDF() {
        return isFormatTDF;
    }

    public void setFormatTDF(boolean formatTDF) {
        isFormatTDF = formatTDF;
    }

    public boolean isFormatRFC4180() {
        return isFormatRFC4180;
    }

    public void setFormatRFC4180(boolean formatRFC4180) {
        isFormatRFC4180 = formatRFC4180;
    }

    public boolean isFormatEXCEL() {
        return isFormatEXCEL;
    }

    public void setFormatEXCEL(boolean formatEXCEL) {
        isFormatEXCEL = formatEXCEL;
    }

    public boolean isWindowsNewline() {
        return isWindowsNewline;
    }

    void setWindowsNewline(boolean windowsNewline) {
        isWindowsNewline = windowsNewline;
    }

    public boolean isLinuxNewline() {
        return isLinuxNewline;
    }

    void setLinuxNewline(boolean linuxNewline) {
        isLinuxNewline = linuxNewline;
    }

    public boolean isOSXNewline() {
        return isOSXNewline;
    }

    void setOSXNewline(boolean OSXNewline) {
        isOSXNewline = OSXNewline;
    }

    public boolean isOldMacNewline() {
        return isOldMacNewline;
    }

    void setOldMacNewline(boolean oldMacNewline) {
        isOldMacNewline = oldMacNewline;
    }

    public boolean isSystemNewline() {
        return isSystemNewline;
    }

    void setSystemNewline(boolean systemNewline) {
        isSystemNewline = systemNewline;
    }

    public String getInputFilespec() {
        return inputFilespec;
    }

    void setInputFilespec(String inputFilespec) {
        this.inputFilespec = inputFilespec;
    }

    public String getOutputFilespec() {
        return outputFilespec;
    }

    void setOutputFilespec(String outputFilespec) {
        this.outputFilespec = outputFilespec;
    }

    public char getColumnSeparator() {
        return columnSeparator;
    }

    void setColumnSeparator(char columnSeparator) {
        this.columnSeparator = columnSeparator;
    }

    public String getNewline() {
        return newline;
    }

    void setNewline(String newline) {
        this.newline = newline;
    }

    private static class OptionConstants {
        private static final String OPT_CONVERT =  "c";
        private static final String LONG_OPT_CONVERT =  "convert";

        private static final String OPT_FORMAT = "f";
        private static final String LONG_OPT_FORMAT = "format";

        private static final String OPT_HELP =  "h";
        private static final String LONG_OPT_HELP =  "help";

        private static final String OPT_INPUT = "i";
        private static final String LONG_OPT_INPUT =  "input";

        private static final String OPT_NEWLINE = "n";
        private static final String LONG_OPT_NEWLINE = "newline";

        private static final String OPT_OUTPUT = "o";
        private static final String LONG_OPT_OUTPUT = "output";

        private static final String OPT_SERVER = "S";

        private static final String OPT_DEBUG = "X";

        private static final String CSV = "CSV";
        private static final String CSV_EXT = ".csv";

        private static final String TSV = "TSV";
        private static final String TSV_EXT = ".tsv";

        private static final char TAB = '\t';
        private static final char COMMA = ',';

        private static final String DEFAULT = "DEFAULT";
        private static final String RFC4180 = "RFC4180";
        private static final String EXCEL = "EXCEL";

        private static final String WINDOWS = "WINDOWS";
        private static final String LINUX = "LINUX";
        private static final String OSX = "OSX";
        private static final String OLDMAC = "OLDMAC";
        private static final String SYSTEM = "SYSTEM";
        private static final String ESCAPE_N = "\n";
        private static final String ESCAPE_R = "\r";
        private static final String ESCAPE_R_ESCAPE_N = "\r\n";
    }

}
