package org.ndextools.morphcx.tables;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

/**
 * List of spreadsheets and associated information belonging to Excel Excel workbook object
 */
public class SpreadsheetInfo {
    private final XSSFWorkbook workbook;
    private final String sheetName;
    private final Sheet sheet;
    private final List<String> columnHeadings;
    private int nextRowIdx;

    public SpreadsheetInfo(XSSFWorkbook workbook,
                           String sheetName,
                           Sheet sheet,
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

    public XSSFWorkbook getWorkbook() { return this.workbook; }

    public String getSheetName() { return this.sheetName; }

    public Sheet getSheet() { return this.sheet; }

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
