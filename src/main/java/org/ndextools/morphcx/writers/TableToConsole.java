package org.ndextools.morphcx.writers;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

/**
 * Concrete class that formats and writes an ordered unorderedListOfCells of table columns to the console (stdout).
 * A delimiter character is used to separate table columns.
 */
public final class TableToConsole implements TableWritable, AutoCloseable {
    private static PrintStream out;
    private static char delimiter;

    /**
     * Class constructor
     *
     * @param delimiter reference to character used to delimit columns/cells (example: a comma or tab character)
     */
    public TableToConsole(final PrintStream out, final char delimiter) {

        if (out == null) {
            String msg = "TableToConsole: PrintStream reference cannot be a null value";
            throw new IllegalArgumentException(msg);
        }

        this.out = out;
        this.delimiter = delimiter;
    }

    /**
     * Refer to the corresponding method in the TableWriteable interface for more details.
     *
     * @param allColumnsInRow is a list of ordered columns/cell data that will be output
     *                as a single unorderedListOfCells by the writer.
     * @throws IOException
     */
    @Override
    public void outputRow(final List<String> allColumnsInRow) throws IOException {
        String[] columns = new String[allColumnsInRow.size()];
        allColumnsInRow.toArray(columns);

        StringBuilder row = new StringBuilder();
        for (String column : columns) {
            row.append(column);
            row.append(delimiter);
        }
        row.setLength((row.length() - 1 ));

        out.println(row.toString());
    }

    @Override
    public void close() throws IOException {
        if (out != null) {
            out.flush();
            out.close();
        }
    }

}
