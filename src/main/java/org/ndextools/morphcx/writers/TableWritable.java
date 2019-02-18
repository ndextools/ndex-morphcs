package org.ndextools.morphcx.writers;

import java.util.List;

/**
 * Interface used by Table Writer classes.
 */
public interface TableWritable extends AutoCloseable {

    /**
     * The outputRow method outputs the row of a table.
     *
     * @param columns is a list of ordered columns/cells that are output as a single and complete row.
     * @throws Exception base class exception when there is an IO or other processing error.
     */
    void outputRow(List<String> columns) throws Exception;  // TODO: eventually replace by writeRow()

}
