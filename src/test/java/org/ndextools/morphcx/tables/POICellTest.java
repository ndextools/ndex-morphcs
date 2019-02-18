package org.ndextools.morphcx.tables;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for POICell class.
 */
public class POICellTest {
    private final String TEXT =
            "The difference between stupidity and genius is that genius has its limits. Albert Einstein";
    private final int COLUMN_IDX = 100;
    private final int ROW_IDX = 0;

    private POICell poiCell;
    private Workbook workbook;
    private Sheet sheet;
    private Row row;

    @Before
    public void _Setup() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet();
        row = sheet.createRow(ROW_IDX);
    }

    @Test
    public void _Should_Return_Row_Class_Reference() {
        poiCell = new POICell(TEXT, COLUMN_IDX, row);
        Assert.assertTrue( poiCell.getRow().equals(row) ? true : false );
    }

    @Test
    public void _Should_Return_Parent_Class_Variables() {
        poiCell = new POICell(TEXT, COLUMN_IDX, row);
        String y = poiCell.getText();
        Assert.assertEquals(TEXT, poiCell.getText());
        Assert.assertEquals(COLUMN_IDX, poiCell.getColumnIdx());
    }

    @Test
    public void _Should_Return_toString_Of_Parent_And_Child_Classes() {
        poiCell = new POICell(TEXT, COLUMN_IDX, row);
        String results = poiCell.toString();
        Assert.assertTrue(results.contains(String.valueOf(COLUMN_IDX)));
        Assert.assertTrue(results.contains("BaseCell{"));
        Assert.assertTrue(results.contains("POICell{"));
    }
}