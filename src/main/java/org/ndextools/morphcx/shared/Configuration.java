package org.ndextools.morphcx.shared;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.cli.*;

/**
 * The Configuration class validates command-line parameters used when starting this application, and
 * finalizes the runtime configuration used by the application.
 */
public final class Configuration {
    private static String[] args;
    private static Options helpOptions;
    private static boolean isHelp;
    private static Operation operation;
    private static boolean inputIsFile;
    private static boolean outputIsFile;
    public static CSVFormat csvFormat;
    private static boolean isServer;

    private boolean isWindowsNewline = false;
    private boolean isLinuxNewline = false;
    private boolean isOSXNewline = false;
    private boolean isOldMacNewline = false;
    private boolean isSystemNewline = false;

    private String inputFilespec;
    private String outputFilespec;
    private char columnSeparator;
    private String newline;

    public enum Operation {
        TSV, CSV, NOT_SPECIFIED
    }

    enum CSVFormat {
        DEFAULT, TDF, NOT_SPECIFIED
    }

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

        Utilities.nullReferenceCheck(this.args, Configuration.class.getSimpleName());

        CommandLine parsedParams = defineParams();
        resolve(parsedParams);

        if (!isHelp()) {
            validate();
            postValidationAdjustments();
        }
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
                Option.builder(OptionConstants.OPT_FORMAT)
                        .longOpt(OptionConstants.LONG_OPT_FORMAT)
                        .hasArg()
                        .desc("Output format as defined by the Apache Commons CSV library. Other options: RFC4180 and EXCEL. < DEFAULT | TDF | RFC4180 | EXCEL >  Default: 'DEFAULT'.")
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
                Option.builder(OptionConstants.OPT_SERVER)
                        .desc("Program to run as server process. IO is forced to STDIN and STDOUT.")
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

        if (parsed.hasOption(OptionConstants.OPT_CONVERT)) {
            String convert = parsed.getOptionValue(OptionConstants.OPT_CONVERT).toUpperCase();
            switch (convert) {
                case OptionConstants.CONVERT_CSV:
                    setOperation(Operation.CSV);
                    setColumnSeparator(OptionConstants.COMMA);
                    break;
                case OptionConstants.CONVERT_TSV:
                    setOperation(Operation.TSV);
                    setColumnSeparator(OptionConstants.TAB);
                default:
                    setOperation(Operation.NOT_SPECIFIED);
                    break;
            }
        } else {
            setOperation(Operation.TSV);
            setColumnSeparator(OptionConstants.TAB);
        }

        if (parsed.hasOption(OptionConstants.OPT_FORMAT)) {
            String format = parsed.getOptionValue(OptionConstants.OPT_FORMAT).toUpperCase();
            switch (format) {
                case OptionConstants.FORMAT_DEFAULT:
                    setCSVFormat(CSVFormat.DEFAULT);
                    break;
                case OptionConstants.FORMAT_TDF:
                    setCSVFormat(CSVFormat.TDF);
                    break;
                default:
                    setCSVFormat(CSVFormat.NOT_SPECIFIED);
                    break;
            }
        } else {
            setCSVFormat(CSVFormat.NOT_SPECIFIED);
        }

        if (parsed.hasOption(OptionConstants.OPT_INPUT)) {
            setInputIsFile(true);
            setInputFilespec(parsed.getOptionValue(OptionConstants.OPT_INPUT));
        } else {
            setInputFilespec("");
        }

        if (parsed.hasOption(OptionConstants.OPT_OUTPUT)) {
            setOutputIsFile(true);
            setOutputFilespec(parsed.getOptionValue(OptionConstants.OPT_OUTPUT));
        } else {
            setOutputFilespec("");
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

        if (parsed.hasOption(OptionConstants.OPT_SERVER)) {
            setIsServer(true);
        }

    }

    private final void postValidationAdjustments() {

        // operation defaults to "-c tsv" when -c or --convert isn't specified in command-line
        if (operation == Operation.NOT_SPECIFIED) {
            setOperation(Operation.TSV);
            setColumnSeparator(OptionConstants.TAB);
        }

        // csvFormat defaults to "-f tdf" when -f or --format isn't specified in command-line
        if (csvFormat == CSVFormat.NOT_SPECIFIED) {
            setCSVFormat(CSVFormat.TDF);
            setColumnSeparator(OptionConstants.TAB);
        }

        // forces stdin and stdout when -S option is specified on the command-line
        if (isServer()) {
            setInputIsFile(false);
            setOutputIsFile(false);
        }
    }

    private final void validate()throws IOException, SecurityException {

        if (getInputIsFile()) {
            fileExists(getInputFilespec());
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

    public static void printHelpText() {
        String prefix = "java -jar morphcx.jar";
        String header = "where parameter options are:";
        String footer = "";
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(132, prefix, header, getHelpOptions(), footer, true);
    }

    public String[] getArgs() { return this.args; }

    public static Options getHelpOptions() {
        return helpOptions;
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

    public static Operation getOperation() {
        return Configuration.operation;
    }

    static void setOperation(Operation operation) {
        Configuration.operation = operation;
    }

    public static boolean getInputIsFile() {
        return Configuration.inputIsFile;
    }

    static void setInputIsFile(boolean value) {
        Configuration.inputIsFile = value;
    }

    public boolean getOutputIsFile() {
        return Configuration.outputIsFile;
    }

    static void setOutputIsFile(boolean value) {
        Configuration.outputIsFile = value;
    }

    public static CSVFormat getCSVFormat() {
        return Configuration.csvFormat;
    }

    static void setCSVFormat(CSVFormat csvFormat) {
        Configuration.csvFormat = csvFormat;
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

    public boolean isServer() {
        return Configuration.isServer;
    }

    void setIsServer(boolean value) {
        Configuration.isServer = value;
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

    public static class OptionConstants {
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

        private static final String CONVERT_CSV = "CSV";
        private static final String CSV_EXT = ".csv";

        private static final String CONVERT_TSV = "TSV";
        private static final String TSV_EXT = ".tsv";

        public static final char TAB = '\t';
        public static final char COMMA = ',';

        private static final String FORMAT_DEFAULT = "DEFAULT";
        private static final String FORMAT_RFC4180 = "RFC4180";
        private static final String FORMAT_EXCEL = "EXCEL";
        private static final String FORMAT_TDF = "TDF";

        public static final String WINDOWS = "WINDOWS";
        public static final String LINUX = "LINUX";
        public static final String OSX = "OSX";
        public static final String OLDMAC = "OLDMAC";
        public static final String SYSTEM = "SYSTEM";
        public static final String ESCAPE_N = "\n";
        public static final String ESCAPE_R = "\r";
        public static final String ESCAPE_R_ESCAPE_N = "\r\n";
    }

    @Override
    public String toString() {

        // TODO: 1/3/19 FINISH 
        return String.format(
                "helpOptions=%b, " +
                // "operation=%s, " +
                "inputIsFile=%b, " +
                "outputIsFile=%b, " +
                // "cvsFormat=%s, " +
                "inputFilespec=%s, " +
                "outputFilespec=%s, ",
                //"columnSeparator=%s",
                //"newline=%s",
                getHelpOptions(),
                // operation
                getInputIsFile(),
                getOutputIsFile(),
                // csvFormat
                getInputFilespec(),
                getOutputFilespec()
                );
    }
}
