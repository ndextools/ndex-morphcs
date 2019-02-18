package org.ndextools.morphcx.writers;

import java.util.List;

/**
 * Interface used by POI Writer classes.
 */
public interface POIWritable<T> extends AutoCloseable {

    /**
     * Outputs the Excel workbook object to a designated output stream.
     *
     * @throws Exception likely caused by an Exception when when writing to the underlying output stream.
     */
    void writeAll() throws Exception;

    /**
     * The outputRow method outputs the row of a table.
     *
     * @param columns is a list of ordered columns/cells that are output as a single and complete row.
     * @throws Exception base class exception when there is an IO or other processing error.
     */
    void writeRow(List<T> columns) throws Exception;


}
