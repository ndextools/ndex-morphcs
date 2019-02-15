package org.ndextools.morphcx.writers;

import java.io.PrintStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.ndextools.morphcx.shared.Configuration;
import org.ndextools.morphcx.shared.Utilities;

/**
 * The WriterFactory class initializes an output writer appropriate for the application's need,
 */
public final class WriterFactory {
    private final Configuration cfg;

    public WriterFactory(Configuration cfg) throws IllegalArgumentException {
        Utilities.nullReferenceCheck(cfg, Configuration.class.getSimpleName());
        this.cfg = cfg;
    }

    /**
     * The getCSVWriter method initializes an output writer appropriate for the application's need,
     *
     * @return the writer object to be used for outputting the resulting morphed CX network
     * @throws Exception base class exception when there is an IO or other processing error
     */
    public final TableWritable getCSVWriter() throws Exception {
        PrintStream printStream;
        CSVFormat csvFormat;

        try
        {

            // Determine the output format based on Apache Commons CVSFormat class constants
            Configuration.Operation operation = cfg.getOperation();
            switch (operation) {
                case CSV:
                    csvFormat = CSVFormat
                            .DEFAULT
                            .withDelimiter(cfg.getDelimiter())
                            .withRecordSeparator(cfg.getNewlineAsString());
                    break;
                case TSV:
                default:
                    csvFormat = CSVFormat
                            .TDF
                            .withDelimiter(cfg.getDelimiter())
                            .withRecordSeparator(cfg.getNewlineAsString());
                    break;
            }

            // Determine whether the output is to a file or stdout.
            if (cfg.outputIsFile()) {
                printStream = new PrintStream(cfg.getOutputFilename());
                CSVPrinter printer = new CSVPrinter(printStream, csvFormat);
                return new TableToCSV(printer, cfg.getDelimiter(), cfg.getNewlineAsString());
            } else {
                printStream = new PrintStream(System.out, true);
                CSVPrinter printer = new CSVPrinter(printStream, csvFormat);
                return new TableToCSV(printer, cfg.getDelimiter(), cfg.getNewlineAsString());
            }

        }
        catch (Exception e) {
            String msg = this.getClass().getSimpleName() + ": " + e.getMessage();
            throw new Exception(msg);
        }
    }

}
