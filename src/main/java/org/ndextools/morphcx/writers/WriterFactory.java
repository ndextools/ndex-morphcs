package org.ndextools.morphcx.writers;

import java.io.PrintStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.ndextools.morphcx.shared.Configuration;
import org.ndextools.morphcx.shared.Utilities;

/**
 * The WriterFactory class creates an output writer appropriate for the application's need,
 */
public final class WriterFactory {
    private final Configuration cfg;

    public WriterFactory(Configuration cfg) throws IllegalArgumentException {
        Utilities.nullReferenceCheck(cfg, Configuration.class.getSimpleName());
        this.cfg = cfg;
    }

    /**
     * The getWriter method creates an output writer appropriate to the needs of the application.
     *
     * @return the writer object to be used for outputting the resulting morphed CX network
     * @throws IllegalStateException
     */
    public final TableWritable getWriter()throws IllegalStateException {
        PrintStream printStream;
        CSVFormat csvFormat;

        try {

            // Determine the output format based on Apache Commons CVSFormat class constants
            Configuration.Operation operation = Configuration.getOperation();
            switch (operation) {
                case CSV:
                    csvFormat = CSVFormat
                            .DEFAULT
                            .withDelimiter(cfg.getColumnSeparator())
                            .withRecordSeparator(cfg.getNewline());
                    System.err.println("** making a CSV file! **");
                    break;
                case TSV:
                default:
                    csvFormat = CSVFormat
                            .TDF
                            .withDelimiter(cfg.getColumnSeparator())
                            .withRecordSeparator(cfg.getNewline());
                    System.err.println("** making a TSV file! **");
                    break;
            }

            // Determine whether the output is to a file or stdout.
            if (cfg.getOutputIsFile()) {
                printStream = new PrintStream(cfg.getOutputFilespec());
                CSVPrinter printer = new CSVPrinter(printStream, csvFormat);
                return new TableToCSV(printer, cfg.getColumnSeparator(), cfg.getNewline());
            } else {
                printStream = new PrintStream(System.out, true);
                CSVPrinter printer = new CSVPrinter(printStream, csvFormat);
                return new TableToCSV(printer, cfg.getColumnSeparator(), cfg.getNewline());
            }
        } catch (Exception e) {
            System.out.println("Error in Generating output; " + e);
        }

        // Execution flow should never reach here because a writer object was not returned to caller.
        String msg = "WriterFactory: Unable to create writer";
        throw new IllegalStateException(msg);
    }

}
