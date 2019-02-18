package org.ndextools.morphcx.tables;

import org.apache.poi.ss.usermodel.Row;

/**
 * POICell class extends the BaseCell class, and encapsulates stateful methods and data needed by
 * Apache Commons POI dependencies.
 */
public class POICell  extends BaseCell {
    private final Row row;

    public POICell(String text, int columnIdx, Row row) {
        super(text, columnIdx);
        this.row = row;
    }

    public Row getRow() { return row; }

    @Override
    public String toString() {
        return super.toString() +
                ", POICell{" +
                "row=" + row +
                '}';
    }
}
