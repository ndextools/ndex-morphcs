package org.ndextools.morphcx.writers;

import java.io.IOException;
import java.util.List;
import org.apache.commons.csv.CSVPrinter;
import org.ndextools.morphcx.shared.Configuration;
import org.ndextools.morphcx.shared.Utilities;

/**
 * Concrete class that formats and writes character separated values to an output stream by using
 * the Apache Commons CSV dependency.
 */
public class TableToCSV implements TableWritable, AutoCloseable {
    private final CSVPrinter printer;
    private final char delimiter;
    private final String newline;

    /**
     * Class constructor
     *
     * @param printer reference to output stream used by CSVPrinter.
     */
    public TableToCSV(final CSVPrinter printer, char delimiter, String newline) throws IllegalArgumentException {
        Utilities.nullReferenceCheck(printer, Configuration .class.getSimpleName());
        Utilities.nullReferenceCheck(newline, Configuration.class.getSimpleName());
        this.printer = printer;
        this.delimiter = delimiter;
        this.newline = newline;

    }

    /**
     * Refer to the corresponding method in the TableWritable interface for more details.
     *
     * @param columns is a list of ordered columns/cells that are output as a single and complete row.
     * @throws IOException likely caused by an IOException when when writing to the underlying output stream.
     */
    @Override
    public void outputRow(final List<String> columns) throws IOException {
        printer.printRecord(columns);
    }

    /**
     * Releases resources associated with AutoClosable interface
     */
    @Override
    public void close() throws IOException {

        try
        {
            if (printer != null) {
                printer.flush();
            }

            if (printer != null) {
                printer.close();
            }

        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * Override of Object.toString()
     *
     * @return contents of class instance variables
     */
    @Override
    public final String toString() {
        String stringDelimiter = Utilities.delimiterToStringConvert(this.delimiter);
        String stringNewline = Utilities.newlineToStringConvert(this.newline);

        return String.format("%s{delimiter=%s, newline=%s}",
                    this.getClass().getSimpleName(),
                    stringDelimiter,
                    stringNewline);
    }
}
