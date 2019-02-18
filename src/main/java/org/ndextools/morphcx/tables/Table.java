package org.ndextools.morphcx.tables;

import java.util.ArrayList;
import java.util.List;
import org.ndexbio.model.cx.NiceCXNetwork;
import org.ndextools.morphcx.shared.Configuration;
import org.ndextools.morphcx.writers.TableWritable;

/**
 * Parent concrete class for all Tables classes being morphed.
 */
public class Table {
    protected final Configuration cfg;
    protected final NiceCXNetwork niceCX;
    protected final TableWritable exportWriter;

    protected List<String> columnHeadings = new ArrayList<>();
    protected int columnsInTable;
    protected List<Bin> unorderedListOfBins = new ArrayList<>();

    /**
     * Table class constructor
     *
     * @param cfg reference to Configuration class object.
     * @param niceCX reference to NiceCXNetwork class object
     * @param exportWriter reference to output writer class object
     */
    Table(Configuration cfg,
          NiceCXNetwork niceCX,
          TableWritable exportWriter) {
        this.cfg = cfg;
        this.niceCX = niceCX;
        this.exportWriter = exportWriter;
    }

    void addCellToRow(String label, String value) {
        Bin bin = this.new Bin(label, value);
        unorderedListOfBins.add(bin);
    }

    void addCellsToRow(List<Bin> bins) {
        unorderedListOfBins.addAll(bins);
    }

    NiceCXNetwork getNiceCX() { return this.niceCX; }

    List<Bin> getUnorderedListOfBins() { return unorderedListOfBins; }

    /**
     * This method iterates through the list of unordered table cells, picks each cell, and plops that
     * cell into a list of table cells ordered by column heading sequence/index.  This ordered list will
     * becomes the unorderedListOfBins that will be output.
     *
     * @return a list of cells that are ordered by column heading sequence
     */
    List<String> pickAndPlop() {

        // Create a unorderedListOfBins of cell elements, one cell element for each column to output.  This unorderedListOfBins will
        // eventually contain cells arranged in a sequence that matches that of the column headings
        List<String> outList = new ArrayList<>();
        for (int i = 0; i < columnHeadings.size(); i++) {
            outList.add("");
        }

        // Iterate through the unordered cells in the output unorderedListOfBins, and place each unordered cell's
        // value into the column sequence/index associated with its respective column heading.
        for ( Bin bin : unorderedListOfBins) {
            int idx = columnHeadings.indexOf(bin.getCellColumnHeading());
            if (idx < 0) {
                System.err.println("NOT FOUND IN COLUMN HEADINGS");
            } else {
                outList.set(idx, bin.getCellData());
            }
        }

        return outList;
    }

    /**
     * Inner Bin class contains the text of the column heading it is associated with.  The contents
     * is the text to be displayed within a unorderedListOfBins of cells.
     */
    class Bin {

        private final String cellColumnHeading;
        private final String cellData;

        /**
         * Class constructor
         * @param cellColumnHeading contains text of the column heading a cell is associated with.
         * @param cellData contains the text to be be displayed
         */
        Bin(String cellColumnHeading, String cellData) {
            this.cellColumnHeading = cellColumnHeading;
            this.cellData = cellData;
        }

        String getCellColumnHeading() { return this.cellColumnHeading; }

        String getCellData() { return this.cellData; }

        @Override
        public String toString() {
            return String.format("heading='%s', data='%s'", getCellColumnHeading(), getCellData());
        }

    }

}
