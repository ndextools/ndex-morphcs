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
    private static Newline newline;
    private static boolean isServer;
    private static String inputFilename;
    private static String outputFilename;
    private static char delimiter;
    private static String newlineAsString;

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

        if (!Configuration.isHelp()) {
            preValidationAdjustments();
            validate();
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
                        .desc("Platform-dependent newline characters. < WINDOWS | LINUX | OSX | OLDMAC | SYSTEM > Default: SYSTEM.")
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
                Configuration.setIsHelp(true);
            }

            if (parsed.hasOption(OptionConstants.OPT_CONVERT)) {
                String convert = parsed.getOptionValue(OptionConstants.OPT_CONVERT).toUpperCase();
                switch (convert) {
                    case OptionConstants.CONVERT_CSV:
                        Configuration.setOperation(Operation.CSV);
                        Configuration.setDelimiter(OptionConstants.COMMA);
                        System.err.println("Converting to CSV");
                        break;
                    case OptionConstants.CONVERT_TSV:
                        Configuration.setOperation(Operation.TSV);
                        Configuration.setDelimiter(OptionConstants.TAB);
                        System.err.println("Converting to TSV");
                    default:
                        Configuration.setOperation(Operation.NOT_SPECIFIED);
                        break;
                }
            } else {
                Configuration.setOperation(Operation.NOT_SPECIFIED);
            }

            if (parsed.hasOption(OptionConstants.OPT_INPUT)) {
                Configuration.setInputIsFile(true);
                Configuration.setInputFilename(parsed.getOptionValue(OptionConstants.OPT_INPUT));
            } else {
                Configuration.setInputFilename("");
            }

            if (parsed.hasOption(OptionConstants.OPT_NEWLINE)) {
                String newline = parsed.getOptionValue(OptionConstants.OPT_NEWLINE).toUpperCase();
                switch (newline) {
                    case OptionConstants.WINDOWS:
                        Configuration.newline = Newline.WINDOWS;
                        Configuration.newlineAsString = Newline.WINDOWS.getNewlineValueOf();
                    break;
                    case OptionConstants.LINUX:
                        Configuration.newline = Newline.LINUX;
                        Configuration.newlineAsString = Newline.LINUX.getNewlineValueOf();
                    break;
                    case OptionConstants.OSX:
                        Configuration.newline = Newline.OSX;
                        Configuration.newlineAsString = Newline.OSX.getNewlineValueOf();
                    break;
                    case OptionConstants.OLDMAC:
                        Configuration.newline = Newline.OLDMAC;
                        Configuration.newlineAsString = Newline.OLDMAC.getNewlineValueOf();
                    break;
                    case OptionConstants.SYSTEM:
                        Configuration.newline = Newline.SYSTEM;
//                        Configuration.newlineAsString = Newline.SYSTEM.getNewlineValueOf();
                        Configuration.newlineAsString = System.getProperty("line.separator");
                    default:
                        Configuration.newline = Newline.NOT_SPECIFIED;
                        setNewlineAsString("");
                    break;
            }
        } else {
                Configuration.newline = Newline.NOT_SPECIFIED;
                Configuration.setNewlineAsString("");
            }

        if (parsed.hasOption(OptionConstants.OPT_OUTPUT)) {
            Configuration.setOutputIsFile(true);
            Configuration.setOutputFilename(parsed.getOptionValue(OptionConstants.OPT_OUTPUT));
        } else {
            Configuration.setOutputFilename("");
        }

        if (parsed.hasOption(OptionConstants.OPT_SERVER)) {
            Configuration.setIsServer(true);
        }
    }

    private final void preValidationAdjustments() {

        // operation defaults to "-c tsv" when -c or --convert isn't specified in command-line
        if (Configuration.operation == Operation.NOT_SPECIFIED) {
            Configuration.setOperation(Operation.TSV);
            Configuration.setDelimiter(OptionConstants.TAB);
        }

        // newline defaults to "-n system" when -n or --newline isn't specified in command-line
        if (Configuration.newline == Newline.NOT_SPECIFIED) {
            Configuration.newline = Newline.SYSTEM;
            Configuration.newlineAsString = Newline.SYSTEM.getNewlineValueOf();
        }

        // forces stdin and stdout when -S option is specified on the command-line
        if (Configuration.isServer()) {
            Configuration.setInputIsFile(false);
            Configuration.setOutputIsFile(false);
        }
    }

    private final void validate()throws IOException, SecurityException {

        if (Configuration.getInputIsFile()) {
            fileExists(Configuration.getInputFilename());
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

    public String[] getArgs() { return Configuration.args; }

    public static Options getHelpOptions() {
        return Configuration.helpOptions;
    }

    static void setHelpOptions(Options helpOptions) {
        Configuration.helpOptions = helpOptions;
    }

    public static boolean isHelp() {
        return Configuration.isHelp;
    }

    static void setIsHelp(boolean value) {
        Configuration.isHelp = value;
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

    public static boolean getOutputIsFile() {
        return Configuration.outputIsFile;
    }

    static void setOutputIsFile(boolean value) {
        Configuration.outputIsFile = value;
    }

    public static boolean isServer() {
        return Configuration.isServer;
    }

    static void setIsServer(boolean value) {
        Configuration.isServer = value;
    }

    public static String getInputFilename() {
        return Configuration.inputFilename;
    }

    static void setInputFilename(String filename) {
        Configuration.inputFilename = filename;
    }

    public static String getOutputFilename() {
        return Configuration.outputFilename;
    }

    static void setOutputFilename(String filename) {
        Configuration.outputFilename = filename;
    }

    public static char getDelimiter() {
        return Configuration.delimiter;
    }

    static void setDelimiter(char delimiter) {
        Configuration.delimiter = delimiter;
    }

    public static String getNewlineAsString() {
        return Configuration.newlineAsString;
    }

    static void setNewlineAsString(String newline) {
        Configuration.newlineAsString = newline;
    }

    public static class OptionConstants {
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
//                args
                "helpOptions=%b, " +
                        // "operation=%s, " +
                "inputIsFile=%b, " +
                "outputIsFile=%b, " +
//                newline
                "isServer=%b, " +
                "inputFilename=%s, " +
                "outputFilespec=%s, ",
//                delimiter
                "newlineAsString=%s",
//                getargs
                getHelpOptions(),
                // operation
                getInputIsFile(),
                getOutputIsFile(),
//               getNewline
                isServer(),
                getInputFilename(),
                getOutputFilename()
        );
    }
}
