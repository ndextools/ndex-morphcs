package org.ndextools.morphcx.tables;

import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;

/**
 * List of spreadsheets and associated information belonging to Excel Excel workbook object
 */
public class SpreadsheetInfo {
    private final SXSSFWorkbook workbook;
    private final String sheetName;
    private final SXSSFSheet sheet;
    private final List<String> columnHeadings;
    private int nextRowIdx;

    public SpreadsheetInfo(SXSSFWorkbook workbook,
                           String sheetName,
                           SXSSFSheet sheet,
                           List<String> columnHeadings,
                           int nextRowIdx) {
        this.workbook = workbook;
        this.sheetName = sheetName;
        this.sheet = sheet;
        this.columnHeadings = columnHeadings;
        this.nextRowIdx = nextRowIdx;
    }

    /**
     * Searches the ordered column header list for a column header identical to the
     * passed argument. If a match is found, the index into the list is returned.
     * Otherwise an IllegalArgumentException is thrown.
     *
     * @param headingText contains the column heading we want to find a match for.
     * @return the column index where a match was found.
     */
    public int findColumnIdx(String headingText) throws IllegalArgumentException {

        if (!columnHeadings.contains(headingText)) {
            String msg = String.format(
                "SpreadsheetInfo: Column heading \'%s\' not found in list: %s",
                   headingText, getColumnHeadings() );
            throw new IllegalStateException(msg);
        }

        return columnHeadings.indexOf(headingText);
    }

    public void incrementNextRowIdx() {
        this.nextRowIdx = getNextRowIdx() + 1;
    }

    public SXSSFWorkbook getWorkbook() { return this.workbook; }

    public String getSheetName() { return this.sheetName; }

    public SXSSFSheet getSheet() { return this.sheet; }

    public List<String> getColumnHeadings() { return this.columnHeadings; }

    public int getNextRowIdx() { return this.nextRowIdx; }


    @Override
    public String toString() {
        return String.format(
            "SpreadsheetInfo{" +
            "workbook=%s, " +
            "sheetName=\'%s\', " +
            "sheet=%s, " +
            "columnHeadings=%s" +
            "lastRowIdx=%d",
            "}",
            getWorkbook() == null ? "null" : getWorkbook().toString(),
            getSheetName(),
            getSheet() == null ? "null" : getSheet().toString(),
            getColumnHeadings().toString(),
            getNextRowIdx()
        );
    }

}
