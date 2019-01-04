package org.ndextools.morphcx.writers;

import java.io.PrintStream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.ndextools.morphcx.shared.Configuration;

/**
 * The WriterFactory class creates an output writer appropriate for the application's need,
 */
public final class WriterFactory {
    private final Configuration cfg;

    public WriterFactory(Configuration cfg) throws IllegalArgumentException {

        if (cfg == null) {
            String msg = "WriterFactory: Configuration reference cannot be a null value";
            throw new IllegalArgumentException(msg);
        }

        this.cfg = cfg;
    }

    /**
     * The getWriter method creates an output writer appropriate to the needs of the application.
     *
     * @return the writer object to be used for outputting the resulting morphed CX network
     * @throws IllegalStateException
     */
    public final TableWritable getWriter()throws IllegalStateException {
        CSVFormat csvFormat;
        PrintStream printStream;

        boolean streamAsFile = (cfg.getInputIsFile()) ? true : false;
        boolean streamAsStdout = (cfg.getInputIsFile()) ? false : true;

        try {

            // Determine the output format based on Apache Commons CVSFormat class constants
            // TODO: 1/3/19  NEED TO SOLVE HOW TO DO!             
//            if (Configuration.getCSVFormat() == CSVFormat.DEFAULT)
            if (getCfg().isFormatDefault()) {
                csvFormat = CSVFormat
                    .DEFAULT
                    .withDelimiter(cfg.getColumnSeparator())
                    .withRecordSeparator(cfg.getNewline());
            } else if (getCfg().isFormatTDF()) {
                csvFormat = CSVFormat
                    .TDF
                    .withDelimiter(cfg.getColumnSeparator())
                    .withRecordSeparator(cfg.getNewline());
            } else if (getCfg().isFormatEXCEL()) {
                csvFormat = CSVFormat
                        .EXCEL
                        .withDelimiter(cfg.getColumnSeparator())
                        .withRecordSeparator(cfg.getNewline());
            } else if (getCfg().isFormatRFC4180()) {
                csvFormat = CSVFormat
                        .RFC4180
                        .withDelimiter(cfg.getColumnSeparator())
                        .withRecordSeparator(cfg.getNewline());
            } else {
                csvFormat = CSVFormat
                        .DEFAULT
                        .withDelimiter(cfg.getColumnSeparator())
                        .withRecordSeparator(cfg.getNewline());
            }

            // values below fetched and passed to writer for use in class @override toString()
            char csvDelimiter = csvFormat.getDelimiter();
            String csvNewline = csvFormat.getRecordSeparator();

            // Determine whether the output is to a file or stdout.
            if (streamAsStdout)
            {
                printStream = new PrintStream(System.out, true);
                CSVPrinter printer = new CSVPrinter(printStream, csvFormat);
                return new TableToCSV(printer, csvDelimiter, csvNewline);
            }
            else if (streamAsFile)
            {
                printStream = new PrintStream(cfg.getOutputFilespec());
                CSVPrinter printer = new CSVPrinter(printStream, csvFormat);
                return new TableToCSV(printer, csvDelimiter, csvNewline);
            }
            else if (streamAsStdout)
            {
                printStream = new PrintStream(System.out, true);
                // TODO: 1/1/19 line separator property is temporary.
                return new TableToStream(printStream, cfg.getColumnSeparator(), System.getProperty("line.separator"));
            }
            else {
                String msg = "WriterFactory: Unable to create writer";
                throw new IllegalStateException(msg);
            }
        }
        catch (Exception e) {
            System.out.println("Error in Generating output; " + e);
        }

        // Execution flow should never reach here because a writer object was not returned to caller.
        String msg = "WriterFactory: Unable to create writer";
        throw new IllegalStateException(msg);
    }

    private Configuration getCfg() {
        return this.cfg;
    }
}
