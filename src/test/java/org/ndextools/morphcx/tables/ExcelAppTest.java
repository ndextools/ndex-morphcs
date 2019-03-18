package org.ndextools.morphcx.tables;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExcelAppTest {
    private SXSSFWorkbook wb;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private static final String SHEET_NAME = "Captain Ahab";
    private static final List<String> columnHeadings =
            new ArrayList<>( Arrays.asList( "Ishmael", "Starbuck", "Stubb" ) );

    @Before
    public void setUp() throws Exception {
        // Redirect console output to outContent in order to perform JUnit tests.
        PrintStream ps;

        try (PrintStream printStream = new PrintStream(outContent);
             SXSSFWorkbook workbook = new SXSSFWorkbook() )
        {
            this.wb = workbook;
            ps = printStream;
        }
        catch (Exception e) {
            System.setOut(System.out);
            throw new Exception(e);
        }
        System.setOut(ps);

    }

    @After
    public void tearDown() {
        //Redirect console output to system stdout stream.
        System.setOut(System.out);
    }

    @Ignore
    public void initializeSpreadsheets() {
        /*
        // Not implemented because of the requirement to mock the creation of a
        // NiceCXNetwork object.  Besides, the crux of its functionality is validated
        // when testing the addSSInfoElement() method.
        */
    }

    @Test
    public void _Should_populateNetworkTableSheet() {
    }

    @Test
    public void _Should_populateNodeTableSheet() {
    }

    @Test
    public void _Should_populateEdgeTableSheet() {
    }

    @Test
    public void _Should_gatherNetworkTableColumnHeadings() {
    }

    @Test
    public void _Should_gatherNodeTableColumnHeadings() {
    }

    @Test
    public void _Should_gatherEdgeTableColumnHeadings() {
    }

    @Test
    public void _Should_addColumnHeadings() {
    }

    @Test
    public void _Should_makeColumnHeadings() {
    }

    @Test
    public void _Should_toString() {
    }

    @Test
    public void _Should_Add_SheetName_To_New_SpreadsheetInfo_Element() {
        SpreadsheetInfo ssInfo = ExcelApp.addSSInfoElement(wb, SHEET_NAME, columnHeadings);
        Assert.assertEquals(SHEET_NAME, ssInfo.getSheetName());
    }


    @Test
    public void _Should_Add_Column_Headings_To_New_SpreadsheetInfo_Element() {
        SpreadsheetInfo ssInfo = ExcelApp.addSSInfoElement(wb, SHEET_NAME, columnHeadings);
        Assert.assertEquals(columnHeadings, ssInfo.getColumnHeadings());
    }

    @Test
    public void _Should_Add_Workbook_Reference_To_New_SpreadsheetInfo_Element() {
        SpreadsheetInfo ssInfo = ExcelApp.addSSInfoElement(wb, SHEET_NAME, columnHeadings);
        Assert.assertEquals(wb, ssInfo.getWorkbook());
    }

    @Test
    public void _Should_Increment_RowIdx_In_New_SpreadsheetInfo_Element() {
        SpreadsheetInfo ssInfo = ExcelApp.addSSInfoElement(wb, SHEET_NAME, columnHeadings);
        Assert.assertEquals(1, ssInfo.getNextRowIdx());
    }

}