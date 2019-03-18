package org.ndextools.morphcx.writers;

//import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ndextools.morphcx.tables.POICell;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TableToPOITest {
    private PrintStream ps;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private final int SHEET_TAB_0 = 0;
    private final int SHEET_TAB_1 = 1;
    private final String SHEETNAME_TEST_A = "Test A";
    private final String SHEETNAME_TEST_B = "Test B";
    private final int ROW_0 = 0;
    private final int ROW_1 = 1;
    private final int COLUMN_IDX_0 = 0;
    private final int COLUMN_IDX_1 = 1;
    private final int COLUMN_IDX_2 = 2;
    private final int COLUMN_IDX_3 = 3;
    private final String CELL_0_TEXT = "cell zero";
    private final String CELL_1_TEXT = "cell one";
    private final String CELL_2_TEXT = "cell two";
    private final String CELL_3_TEXT = "cell three";

    private List<POICell> cells = new ArrayList<>();
    private SXSSFSheet sheet;
    private SXSSFRow row;

    @Before
    public void setup() {
        // Redirect console output to outContent in order to perform JUnit tests.
        ps = new PrintStream(outContent);
        System.setOut(ps);
    }

    @After
    public void breakdown() {
        //Redirect console output to system stdout stream.
        System.setOut(System.out);
    }

    @Test
    public void _Should_Validate_POI_Output_Can_Be_Unit_Tested() throws Exception
    // Simply a test of the testing process used by this class.
    {
        try ( OutputStream out = new PrintStream(outContent);
              SXSSFWorkbook exportWB = new SXSSFWorkbook() )
        {
            // Create workbook and populate it with a row with cells.
            SXSSFSheet expSheet = exportWB.createSheet(SHEETNAME_TEST_A);
            SXSSFRow expRow = expSheet.createRow(ROW_0);
            expRow.createCell(COLUMN_IDX_0).setCellValue(CELL_0_TEXT);
            expRow.createCell(COLUMN_IDX_1).setCellValue(CELL_1_TEXT);
            expRow.createCell(COLUMN_IDX_2).setCellValue(CELL_2_TEXT);
            expRow.createCell(COLUMN_IDX_3).setCellValue(CELL_3_TEXT);

            // Output to a ByteArrayOutputStream.
            exportWB.write(out);
        }
        catch (Exception e) {
            Assert.fail();
        }

        // Import Excel worksheet stream from a ByteArrayOutputStream into POI worksheet
        ByteArrayInputStream inContent;
        inContent = new ByteArrayInputStream(outContent.toByteArray());
        XSSFWorkbook inpWB = new XSSFWorkbook(inContent);

        // Perform tests
        Sheet impSheet = inpWB.getSheetAt(SHEET_TAB_0);
        Row inpRow = impSheet.getRow(ROW_0);
        Assert.assertEquals( SHEETNAME_TEST_A, impSheet.getSheetName() );
        Assert.assertEquals( CELL_0_TEXT, inpRow.getCell(0).getStringCellValue() );
        Assert.assertEquals( CELL_1_TEXT, inpRow.getCell(1).getStringCellValue() );
        Assert.assertEquals( CELL_2_TEXT, inpRow.getCell(2).getStringCellValue() );
        Assert.assertEquals( CELL_3_TEXT, inpRow.getCell(3).getStringCellValue() );
    }

    @Test
    public void _Should_Output_Workbook_Using_WriteAll_Method() throws Exception
    {
        try ( OutputStream out = new PrintStream(outContent);
              SXSSFWorkbook exportWB = new SXSSFWorkbook() )
        {
            TableToPOI poiWriter = new TableToPOI(out, exportWB);

            // Create workbook and populate it with a row with cells.
            SXSSFSheet expSheet = exportWB.createSheet(SHEETNAME_TEST_A);
            SXSSFRow expRow = expSheet.createRow(ROW_1);
            expRow.createCell(COLUMN_IDX_0).setCellValue(CELL_0_TEXT);
            expRow.createCell(COLUMN_IDX_1).setCellValue(CELL_1_TEXT);
            expRow.createCell(COLUMN_IDX_2).setCellValue(CELL_2_TEXT);
            expRow.createCell(COLUMN_IDX_3).setCellValue(CELL_3_TEXT);

            // Output to a ByteArrayOutputStream.
            poiWriter.writeAll();
        }
        catch (Exception e) {
            Assert.fail();
        }

        // Import Excel worksheet stream from a ByteArrayOutputStream into POI worksheet
        ByteArrayInputStream inContent;
        inContent = new ByteArrayInputStream(outContent.toByteArray());
        XSSFWorkbook impWB = new XSSFWorkbook(inContent);

        // Perform tests
        Sheet impSheet = impWB.getSheetAt(SHEET_TAB_0);
        Row inpRow = impSheet.getRow(ROW_1);
        Assert.assertEquals( SHEETNAME_TEST_A, impSheet.getSheetName() );
        Assert.assertEquals( CELL_0_TEXT, inpRow.getCell(0).getStringCellValue() );
        Assert.assertEquals( CELL_1_TEXT, inpRow.getCell(1).getStringCellValue() );
        Assert.assertEquals( CELL_2_TEXT, inpRow.getCell(2).getStringCellValue() );
        Assert.assertEquals( CELL_3_TEXT, inpRow.getCell(3).getStringCellValue() );
    }

    @Test
    public void _Should_Write_Cells_Using_WriteRow_Method() throws Exception {

        // Create workbook and populate a row with cells
        try (OutputStream out = new PrintStream(outContent);
             SXSSFWorkbook exportWB = new SXSSFWorkbook() )
        {
            TableToPOI poiWriter = new TableToPOI(out, exportWB);

            // create two spreadsheets and populate them with a row of cells.
            sheet = exportWB.createSheet(SHEETNAME_TEST_A);
            row = sheet.createRow(ROW_0);
            cells.add( new POICell(CELL_0_TEXT, COLUMN_IDX_0, row) );
            cells.add( new POICell(CELL_1_TEXT, COLUMN_IDX_1, row) );
            cells.add( new POICell(CELL_2_TEXT, COLUMN_IDX_2, row) );
            cells.add( new POICell(CELL_3_TEXT, COLUMN_IDX_3, row) );

            sheet = exportWB.createSheet(SHEETNAME_TEST_B);
            row = sheet.createRow(ROW_1);
            cells.add( new POICell(CELL_0_TEXT, COLUMN_IDX_3, row) );
            cells.add( new POICell(CELL_1_TEXT, COLUMN_IDX_2, row) );
            cells.add( new POICell(CELL_2_TEXT, COLUMN_IDX_1, row) );
            cells.add( new POICell(CELL_3_TEXT, COLUMN_IDX_0, row) );

            // Output to a ByteArrayOutputStream.
            poiWriter.writeRow(cells);
            poiWriter.writeAll();
        }
        catch (Exception e) {
            Assert.fail();
        }

        // Import Excel worksheet stream from a ByteArrayOutputStream into POI worksheet
        ByteArrayInputStream inContent;
        inContent = new ByteArrayInputStream(outContent.toByteArray());
        XSSFWorkbook impWB = new XSSFWorkbook(inContent);

        // Perform tests
        Sheet impSheet;
        Row impRow;

        impSheet = impWB.getSheetAt(SHEET_TAB_0);
        impRow = impSheet.getRow(ROW_0);
        Assert.assertEquals( impSheet.getSheetName(), SHEETNAME_TEST_A);
        Assert.assertEquals(CELL_0_TEXT, impRow.getCell(COLUMN_IDX_0).getStringCellValue());
        Assert.assertEquals(CELL_1_TEXT, impRow.getCell(COLUMN_IDX_1).getStringCellValue());
        Assert.assertEquals(CELL_2_TEXT, impRow.getCell(COLUMN_IDX_2).getStringCellValue());
        Assert.assertEquals(CELL_3_TEXT, impRow.getCell(COLUMN_IDX_3).getStringCellValue());

        impSheet = impWB.getSheetAt(SHEET_TAB_1);
        impRow = impSheet.getRow(ROW_1);
        Assert.assertEquals( impSheet.getSheetName(), SHEETNAME_TEST_B);
        Assert.assertEquals(CELL_3_TEXT, impRow.getCell(COLUMN_IDX_0).getStringCellValue());
        Assert.assertEquals(CELL_2_TEXT, impRow.getCell(COLUMN_IDX_1).getStringCellValue());
        Assert.assertEquals(CELL_1_TEXT, impRow.getCell(COLUMN_IDX_2).getStringCellValue());
        Assert.assertEquals(CELL_0_TEXT, impRow.getCell(COLUMN_IDX_3).getStringCellValue());
    }

}
