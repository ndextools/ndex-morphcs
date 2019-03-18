package org.ndextools.morphcx.tables;

import org.apache.poi.xssf.streaming.SXSSFRow;

/**
 * POICell class extends the BaseCell class, and encapsulates stateful methods and data needed by
 * Apache Commons POI dependencies.
 */
public class POICell  extends BaseCell {
    private final SXSSFRow row;

    public POICell(String text, int columnIdx, SXSSFRow row) {
        super(text, columnIdx);
        this.row = row;
    }

    public SXSSFRow getRow() { return row; }

    @Override
    public String toString() {
        return super.toString() +
                ", POICell{" +
                "row=" + row +
                '}';
    }
}
