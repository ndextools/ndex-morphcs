package org.ndextools.morphcx.writers;

import java.io.IOException;
import java.util.List;
import org.apache.commons.csv.CSVPrinter;


/**
 * Concrete class that formats and writes character separated values to an output stream by using
 * the Apache Commons CSV dependency.
 */
public class TableToCSV implements TableWritable, AutoCloseable {
    private static CSVPrinter printer;

    /**
     * Class constructor
     *
     * @param printer reference to output stream used by CSVPrinter.
     */
    public TableToCSV(final CSVPrinter printer) throws IllegalArgumentException {

        if (printer == null) {
            String msg = "TableToCSV: CVSPrinter reference cannot be a null value";
            throw new IllegalArgumentException(msg);
        }

        this.printer = printer;
    }

    /**
     * Refer to the corresponding method in the TableWritable interface for more details.
     *
     * @param columns is a list of ordered columns/cell data that will be output
     *                as a single unorderedListOfCells by the writer.
     * @throws IOException
     */
    @Override
    public void outputRow(final List<String> columns) throws IOException {
        printer.printRecord(columns);
    }

    @Override
    public void close() throws IOException {
        if (printer != null) {
            printer.flush();
            printer.close();
        }
    }

}
