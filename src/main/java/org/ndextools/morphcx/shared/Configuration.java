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
    private String[] args;
    private boolean isHelp;
    private Operation operation;
    private boolean inputIsFile;
    private boolean outputIsFile;
    private Newline newline;
    private boolean isServer;
    private String inputFilename;
    private String outputFilename;
    private char delimiter;
    private String newlineAsString;

    private Utilities util;
    private Options csvParameterOptions;

    public enum Operation {
        TSV, CSV, NOT_SPECIFIED

    }
    enum Newline {
        WINDOWS("\r\n"),
        LINUX("\n"),
        OSX("\n"),
        OLDMAC("\r"),
        SYSTEM( System.getProperty("line.separator") ),
        NOT_SPECIFIED("not_specified");

        private String nl;

        private String getNewlineValueOf() {
            return this.nl;
        }

        Newline(String nl) {
            this.nl = nl;
        }

    }


    /**
     * Class constructor
     */
    public Configuration() {}

    public Configuration configure(String[] args) throws Exception {

        Utilities.nullReferenceCheck(args, Configuration.class.getSimpleName());
        this.args = args;

        CommandLine parsedParams = defineParams();
        resolve(parsedParams);

        if (!isHelp()) {
            preValidationAdjustments();
            validate();
        }
        util = new Utilities(this);
        return this;
    }

    /**
     * This method defines all command-line parameters used when starting this application.
     *
     * @throws ParseException occurs when an invalid parameter exists in the command-line
     */
    private CommandLine defineParams() throws ParseException {

        // Apache Commons CLI - Step #1 of 3: Define all command-line parameters
        setCSVParameterOptions( new Options());

        getCSVParameterOptions().addOption(
                Option.builder(ConfigurationConstants.OPT_CONVERT)
                        .longOpt(ConfigurationConstants.LONG_OPT_CONVERT)
                        .hasArg()
                        .desc("Converts an NDEx CX network to a .csv or .tsv format. < CSV | TSV >  Default: 'TSV'.")
                        .build()
        );
        getCSVParameterOptions().addOption(
                Option.builder(ConfigurationConstants.OPT_HELP)
                        .longOpt(ConfigurationConstants.LONG_OPT_HELP)
                        .desc("Displays this help information.")
                        .build()
        );
        getCSVParameterOptions().addOption(
                Option.builder(ConfigurationConstants.OPT_INPUT)
                        .longOpt(ConfigurationConstants.LONG_OPT_INPUT)
                        .hasArg()
                        .desc("Full input path and file specification. Default: input comes from STDIN rather than a file.")
                        .build()
        );
        getCSVParameterOptions().addOption(
                Option.builder(ConfigurationConstants.OPT_NEWLINE)
                        .longOpt(ConfigurationConstants.LONG_OPT_NEWLINE)
                        .hasArg()
                        .desc("Platform-dependent newline characters. < WINDOWS | LINUX | OSX | OLDMAC | SYSTEM > Default: SYSTEM.")
                        .build()
        );
        getCSVParameterOptions().addOption(
                Option.builder(ConfigurationConstants.OPT_OUTPUT)
                        .longOpt(ConfigurationConstants.LONG_OPT_OUTPUT)
                        .hasArg()
                        .desc("Full output path and file specification. Default: output sent to STDOUT rather than a file.")
                        .build()
        );

        getCSVParameterOptions().addOption(
                Option.builder(ConfigurationConstants.OPT_SERVER)
                        .desc("Program to run as server process. IO is forced to STDIN and STDOUT.")
                        .build()
        );

        // Apache Commons CLI - Step #2 of 3: Parse all command-line parameters
        CommandLine parsed;
        CommandLineParser parser = new DefaultParser();
        try {
            parsed = parser.parse(getCSVParameterOptions(), getArgs());
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
            if (parsed.hasOption(ConfigurationConstants.OPT_HELP)) {
                setIsHelp(true);
            }

            if (parsed.hasOption(ConfigurationConstants.OPT_CONVERT)) {
                String convert = parsed.getOptionValue(ConfigurationConstants.OPT_CONVERT).toUpperCase();
                switch (convert) {
                    case ConfigurationConstants.CONVERT_CSV:
                        setOperation(Operation.CSV);
                        setDelimiter(ConfigurationConstants.COMMA);
                        break;
                    case ConfigurationConstants.CONVERT_TSV:
                        setOperation(Operation.TSV);
                        setDelimiter(ConfigurationConstants.TAB);
                    default:
                        setOperation(Operation.NOT_SPECIFIED);
                        break;
                }
            } else {
                setOperation(Operation.NOT_SPECIFIED);
            }

            if (parsed.hasOption(ConfigurationConstants.OPT_INPUT)) {
                setInputIsFile(true);
                setInputFilename(parsed.getOptionValue(ConfigurationConstants.OPT_INPUT));
            } else {
                setInputFilename("");
            }

            if (parsed.hasOption(ConfigurationConstants.OPT_NEWLINE)) {
                String nl = parsed.getOptionValue(ConfigurationConstants.OPT_NEWLINE).toUpperCase();
                switch (nl) {
                    case ConfigurationConstants.WINDOWS:
                        newline = Newline.WINDOWS;
                        newlineAsString = Newline.WINDOWS.getNewlineValueOf();
                    break;
                    case ConfigurationConstants.LINUX:
                        newline = Newline.LINUX;
                        newlineAsString = Newline.LINUX.getNewlineValueOf();
                    break;
                    case ConfigurationConstants.OSX:
                        newline = Newline.OSX;
                        newlineAsString = Newline.OSX.getNewlineValueOf();
                    break;
                    case ConfigurationConstants.OLDMAC:
                        newline = Newline.OLDMAC;
                        newlineAsString = Newline.OLDMAC.getNewlineValueOf();
                    break;
                    case ConfigurationConstants.SYSTEM:
                        newline = Newline.SYSTEM;
                        newlineAsString = System.getProperty("line.separator");
                    default:
                        newline = Newline.NOT_SPECIFIED;
                        setNewlineAsString("");
                    break;
            }
        } else {
                newline = Newline.NOT_SPECIFIED;
                setNewlineAsString("");
            }

        if (parsed.hasOption(ConfigurationConstants.OPT_OUTPUT)) {
            setOutputIsFile(true);
            setOutputFilename(parsed.getOptionValue(ConfigurationConstants.OPT_OUTPUT));
        } else {
            setOutputFilename("");
        }

        if (parsed.hasOption(ConfigurationConstants.OPT_SERVER)) {
            setIsServer(true);
        }
    }

    private final void preValidationAdjustments() {

        // operation defaults to "-c tsv" when -c or --convert isn't specified in command-line
        if (operation == Operation.NOT_SPECIFIED) {
            setOperation(Operation.TSV);
            setDelimiter(ConfigurationConstants.TAB);
        }

        // newline defaults to "-n system" when -n or --newline isn't specified in command-line
        if (newline == Newline.NOT_SPECIFIED) {
            newline = Newline.SYSTEM;
            newlineAsString = Newline.SYSTEM.getNewlineValueOf();
        }

        // forces stdin and stdout when -S option is specified on the command-line
        if (isServer()) {
            setInputIsFile(false);
            setOutputIsFile(false);
        }
    }

    private final void validate()throws IOException, SecurityException {

        if (inputIsFile()) {
            fileExists(getInputFilename());
        }
    }

    /**
     *  This method checks if a file (not a directory) exists and can be read/written to.
     *
     * @param filename the directory path, name and extension of the file to be accessed.
     * @return a validated file specification, i.e. a file that can be opened.
     * @throws FileNotFoundException occurs when the input file is not found.
     * @throws SecurityException occurs when read-access to the input file is denied.
     */
    final boolean fileExists(String filename)
            throws FileNotFoundException, SecurityException {

        File file = new File(filename);
        if( !file.isFile() ){
            String errMsg = this.getClass().getSimpleName() + ": " + filename + " - file not found";
            throw new FileNotFoundException(errMsg);
        }
        if( !file.canRead() ){
            String errMsg = this.getClass().getSimpleName() + ": " + filename + " - access denied";
            throw new SecurityException(errMsg);
        }

        return true;
    }

    public void printHelpText() {
        String prefix = "java -jar morphcx.jar";
        String header = "where parameter options are:";
        String footer = "";
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(132, prefix, header, getCSVParameterOptions(), footer, true);
    }

    public String[] getArgs() { return this.args; }

    public Options getCSVParameterOptions() {
        return this.csvParameterOptions;
    }

    void setCSVParameterOptions(Options helpOptions) {
        this.csvParameterOptions = helpOptions;
    }

    public boolean isHelp() {
        return isHelp;
    }

    void setIsHelp(boolean value) {
        isHelp = value;
    }

    public Operation getOperation() {
        return this.operation;
    }

    void setOperation(Operation operation) {
        this.operation = operation;
    }

    public boolean inputIsFile() {
        return this.inputIsFile;
    }

    void setInputIsFile(boolean value) {
        this.inputIsFile = value;
    }

    public boolean outputIsFile() {
        return this.outputIsFile;
    }

    void setOutputIsFile(boolean value) {
        this.outputIsFile = value;
    }

    public boolean isServer() {
        return this.isServer;
    }

    void setIsServer(boolean value) {
        this.isServer = value;
    }

    public String getInputFilename() {
        return this.inputFilename;
    }

    void setInputFilename(String filename) {
        this.inputFilename = filename;
    }

    public String getOutputFilename() {
        return this.outputFilename;
    }

    void setOutputFilename(String filename) {
        this.outputFilename = filename;
    }

    public char getDelimiter() {
        return this.delimiter;
    }

    void setDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }

    public String getNewlineAsString() {
        return this.newlineAsString;
    }

    void setNewlineAsString(String newline) {
        this.newlineAsString = newline;
    }

    public static class ConfigurationConstants {
        private static final String OPT_CONVERT =  "c";
        private static final String LONG_OPT_CONVERT =  "convert";

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

        private static final String CONVERT_TSV = "TSV";

        public static final char TAB = '\t';
        public static final char COMMA = ',';

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
                "args=%s\n" + 
                "isHelp=%s, " +
                "operation=%s, " +
                "inputIsFile=%b, " +
                "outputIsFile=%b, " +
                "newline=%s, " +
                "isServer=%b, " +
                "inputFilename=%s, " +
                "outputFilespec=%s, " +
                "delimiter=%s, " +
                "newlineAsString=%s",
                getArgs().toString(),
                isHelp(),
                getOperation().toString(),
                inputIsFile(),
                outputIsFile(),
                Utilities.newlineToDescriptionText(newline),
                isServer(),
                getInputFilename(),
                getOutputFilename(),
                Utilities.delimiterToDescriptionText(getDelimiter()),
                getNewlineAsString()
        );
    }
}
