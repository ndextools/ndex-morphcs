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

    public enum Operation {
        TSV, CSV, NOT_SPECIFIED
    }

    enum CSVFormat {
        DEFAULT, TDF, RFC4180, EXCEL, NOT_SPECIFIED
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
                case OptionConstants.FORMAT_EXCEL:
                    setCSVFormat(CSVFormat.EXCEL);
                    break;
                case OptionConstants.FORMAT_RFC4180:
                    setCSVFormat(CSVFormat.RFC4180);
                    break;
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
        }

        if (parsed.hasOption(OptionConstants.OPT_OUTPUT)) {
            setOutputIsFile(true);
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

        if (!outputIsFile) {
            if (operation == Operation.TSV) {
                setOutputFilespec( buildOutputFilespec(getInputFilespec(), OptionConstants.TSV_EXT) );
            } else {
                setOutputFilespec( buildOutputFilespec(getInputFilespec(), OptionConstants.CSV_EXT) );
            }
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

    private static String buildOutputFilespec(String inpFilespec, String extension) {
        int lastDot = inpFilespec.lastIndexOf(".");
        if (lastDot != -1 && lastDot != 0) {
            return inpFilespec.substring(0, lastDot) + extension;
        } else {
            return inpFilespec + extension;
        }

    }

    public static void printHelpText() {
        String prefix = "java -jar morphcx.jar";
        String header = "where parameter options are:";
        String footer = "";
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(132, prefix, header, getHelpOptions(), footer, true);
    }

    public String[] getArgs() { return this.args; }

    public static Options getHelpOptions() { // TODO: 1/3/19 KEEP THIS
        return helpOptions;
    }

    void setHelpOptions(Options helpOptions) { // TODO: 1/3/19 KEEP THIS
        this.helpOptions = helpOptions;
    }

    public boolean isHelp() { // TODO: 1/3/19 KEEP THIS
        return this.isHelp;
    }

    void setIsHelp(boolean value) { // TODO: 1/3/19 KEEP THIS
        this.isHelp = value;
    }

    public static Operation getOperation() {  // TODO: 1/3/19 KEEP THIS
        return Configuration.operation;
    }

    static void setOperation(Operation operation) {  // TODO: 1/3/19 KEEP THIs
        Configuration.operation = operation;
    }

    public static boolean getInputIsFile() {    // TODO: 1/3/19 KEEP THIS
        return Configuration.inputIsFile;
    }

    static void setInputIsFile(boolean value) {     // TODO: 1/3/19 KEEP THIS
        Configuration.inputIsFile = value;
    }

    public boolean getOutputIsFile() {   // TODO: 1/3/19 KEEP THIS
        return Configuration.outputIsFile;
    }

    static void setOutputIsFile(boolean value) {    // TODO: 1/3/19 KEEP THIS
        Configuration.outputIsFile = value;
    }

    public static CSVFormat getCSVFormat() {  // TODO: 1/3/19 KEEP THIS
        return Configuration.csvFormat;
    }

    static void setCSVFormat(CSVFormat csvFormat) {  // TODO: 1/3/19 KEEP THIs
        Configuration.csvFormat = csvFormat;
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
