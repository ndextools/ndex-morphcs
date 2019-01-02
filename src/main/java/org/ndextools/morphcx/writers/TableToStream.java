package org.ndextools.morphcx.writers;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

/**
 * Concrete class that formats and outputs a single row of a table using the PrintStream class.  It acts as a
 * replacement to the Apache Commons CSV library functions.
 */
public final class TableToStream implements TableWritable, AutoCloseable {
    private final PrintStream out;
    private final char delimiter;
    private final String newline;

    /**
     * Class constructor
     * @param delimiter character used to separate column data (typically comma or tab character)
     * @param newline string of characters used when overriding the JVM line separator system property.
     */
    public TableToStream(final PrintStream out, final char delimiter, final String newline) {
        this.out = out;
        this.delimiter = delimiter;
        this.newline = System.setProperty("line.separator", newline);
    }

    /**
     * Refer to the corresponding method in the TableWriteable interface for more details.
     *
     * @param allColumnsInRow an ordered list of column data that will be output as a single row by the writer.
     * @throws IOException
     */
    @Override
    public void outputRow(final List<String> allColumnsInRow) throws Exception {
        String[] columns = new String[allColumnsInRow.size()];
        allColumnsInRow.toArray(columns);

        StringBuilder row = new StringBuilder();
        for (String column : columns) {
            if (row.length() > 0) {
                row.append(delimiter);
            }
            row.append(column);
        }

        out.println(row.toString());
        if ( out.checkError() ) {
            String msg = String.format("%s: Error when writing to underlying output stream", this.getClass().getSimpleName());
            throw new IOException(msg);
        }

    }

    @Override
    public void close() {
        if (out != null) {
            out.close();
        }
    }

}
