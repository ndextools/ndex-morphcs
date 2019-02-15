package org.ndextools.morphcx.writers;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class TableToPOITest {
    private PrintStream ps;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

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
    public void _ShouldProveExcelWorkbookWithTestSpreadsheetWasCreatedAndImportable() throws Exception {

        // Create a trivial Excel workbook containing a test sheet and row.
        final String cell0 = "Cell-0";
        final String cell1 = "Cell-1";
        final String cell2 = "Cell-2";
        final String cell3 = "Cell-3";
        final String testSheetName = "Test1";

        // Create workbook and populate a row with cells
        try (OutputStream out = new PrintStream(outContent);
             XSSFWorkbook exportWB = new XSSFWorkbook() ) {
            Sheet expSheet = exportWB.createSheet(testSheetName);
            Row expRow = expSheet.createRow(0);
            expRow.createCell(0).setCellValue(cell0);
            expRow.createCell(1).setCellValue(cell1);
            expRow.createCell(2).setCellValue(cell2);
            expRow.createCell(3).setCellValue(cell3);
            exportWB.write(out);
        } catch (Exception io) {
            System.err.println(io);
            Assert.fail();
        }

        // Import Excel worksheet stream into POI worksheet
        ByteArrayInputStream inContent;
        inContent = new ByteArrayInputStream(outContent.toByteArray());
        Workbook impWB = new XSSFWorkbook(inContent);

        // Perform tests
        Sheet impSheet = impWB.getSheetAt(0);
        Assert.assertEquals( impSheet.getSheetName(), testSheetName );

        Row r0 = impSheet.getRow(0);
        Assert.assertEquals( r0.getCell(0).getStringCellValue(), cell0 );
        Assert.assertEquals( r0.getCell(1).getStringCellValue(), cell1 );
        Assert.assertEquals( r0.getCell(2).getStringCellValue(), cell2 );
        Assert.assertEquals( r0.getCell(3).getStringCellValue(), cell3 );
    }

}