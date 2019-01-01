package org.ndextools.morphcx.writers;

import java.io.IOException;
import java.util.List;

/**
 * Interface used by Table Writer classes.
 */
public interface TableWritable extends AutoCloseable {

    /**
     * The outputRow method outputs the unorderedListOfCells of columns/cells. The data contained in the
     * columns/cells are expected to be aligned with their corresponding column headers
     * which are output in first unorderedListOfCells.
     *
     * @param columns is a list of ordered columns/cell data that will be output
     *                as a single unorderedListOfCells by the writer.
     * @throws IOException
     */
    void outputRow(List<String> columns) throws Exception;

}
