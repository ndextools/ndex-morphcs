package org.ndextools.morphcx.writers;

import java.io.OutputStream;
import java.util.List;

public class TableToPOI implements TableWritable, AutoCloseable {
    private final OutputStream workbook;

    public TableToPOI(final OutputStream workbook) {
        this.workbook = workbook;
    }

    /**
     * @param columns is a list of ordered columns/cells that are output as a single and complete row.
     * @throws Exception base class exception when there is an IO or other processing error
     */
    @Override
    public void outputRow(List<String> columns) throws Exception {

    }

    /**
     * Releases resources associated with AutoClosable interface
     */
    @Override
    public void close() throws Exception {
        if (workbook != null) {
            workbook.flush();
        }
        if (workbook != null) {
            workbook.close();
        }
    }
}
