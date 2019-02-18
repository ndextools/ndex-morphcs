package org.ndextools.morphcx.tables;

/**
 * BaseCell class acts as a container containing stateful data. The data is intended for placement
 * where a table's row and column intersect.
 */
public class BaseCell {
    private final String text;
    private final int columnIdx;

    public BaseCell(String text, int columnIdx) {
        this.text = text;
        this.columnIdx = columnIdx;
    }

    public String getText() { return text; }

    public int getColumnIdx() { return columnIdx; }

    @Override
    public String toString() {
        return "BaseCell{" +
                "text='" + text + '\'' +
                ", columnIdx=" + columnIdx +
                '}';
    }
}
