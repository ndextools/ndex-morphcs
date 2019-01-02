package org.ndextools.morphcx.writers;

import org.ndextools.morphcx.shared.Configuration;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

/**
 * Concrete class that formats and outputs a single row of a table using the PrintStream class.  It acts as a
 * substitute to using the Apache Commons CSV library functions.  Typically used when debugging this application.
 */
public final class TableToStream implements TableWritable, AutoCloseable {
    private final PrintStream out;
    private final char delimiter;
    private final String newline;

    /**
     * Class constructor
     *
     * @param delimiter character used to separate column data (typically comma or tab character)
     * @param newline string of characters used when overriding the JVM line separator system property.
     */
    public TableToStream(final PrintStream out, final char delimiter, final String newline) {
        this.out = out;
        this.delimiter = delimiter;
        this.newline = newline;
    }

    /**
     * Refer to the corresponding method in the TableWritable interface for more details.
     *
     * @param rowOfColumns is a list of ordered columns/cells that are output as a single and complete row.
     * @throws Exception likely caused by an IOException when when writing to the underlying output stream.
     */
    @Override
    public void outputRow(final List<String> rowOfColumns) throws Exception {
        String[] columns = new String[rowOfColumns.size()];
        rowOfColumns.toArray(columns);

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
            out.flush();
        }
        if (out != null) {
            out.close();
        }
    }

    @Override
    public String toString() {

        String stringDelimiter;
        switch (this.delimiter) {
            case ',':
                stringDelimiter = ",";
                break;
            case '\t':
                stringDelimiter = "\t";
                break;
            default:
                int intDelimiter = this.delimiter;
                stringDelimiter = String.valueOf(intDelimiter);
                break;
        }

        String stringNewline;
        switch (this.newline) {
            case Configuration.OptionConstants.ESCAPE_R_ESCAPE_N:
                stringNewline = Configuration.OptionConstants.ESCAPE_R_ESCAPE_N;
                break;
            case Configuration.OptionConstants.ESCAPE_N:
                stringNewline = Configuration.OptionConstants.ESCAPE_N;
                break;
            case Configuration.OptionConstants.ESCAPE_R:
                stringNewline = Configuration.OptionConstants.ESCAPE_R;
                break;
            default:
                stringNewline = this.newline;
                break;
        }

        return String.format("thisWriter=%s, delimiter='%s', newline='%s'",
                this.getClass().getSimpleName(),
                stringDelimiter,
                stringNewline);
    }

}
