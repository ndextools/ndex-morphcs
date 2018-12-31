package org.ndextools.morphcx.writers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;

/**
 * Unit tests for TableToConsole class.
 */
public class TableToConsoleTest {
    private PrintStream ps;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private List<String> orderedColumnValues;
    private final char COMMA = ',';
    private final char TAB = '\t';


    @Before
    public void setup() {
        // Redirect console output to outContent in order to perform JUnit tests.
        ps = new PrintStream(outContent);
        System.setOut(ps);

        // List containing all columns to be printed to the (redirected) console.
        orderedColumnValues = new ArrayList<>();
    }

    @After
    public void breakdown() {
        //Redirect console output to system stdout stream.
        System.setOut(System.out);
    }


    @Test
    public void _ShouldFormatRowAsOneColumnAndNoDelimiter() throws IOException {
        orderedColumnValues.add("column_1");
        TableToConsole tc = new TableToConsole(ps, COMMA);
        tc.outputRow(orderedColumnValues);
        Assert.assertEquals("column_1\n", outContent.toString() );
    }

    @Test
    public void _ShouldFormatRowAsTwoColumnsWithSeparatingCommaDelimiter() throws IOException {
        orderedColumnValues.add("column_1");
        orderedColumnValues.add("column_2");
        TableToConsole tc = new TableToConsole(ps, COMMA);
        tc.outputRow(orderedColumnValues);
        Assert.assertEquals("column_1" + COMMA +
                "column_2\n", outContent.toString() );
    }

    @Test
    public void _ShouldFormatRowAsTwoColumnsWithSeparatingTabDelimiter() throws IOException {
        orderedColumnValues.add("column_A");
        orderedColumnValues.add("column_B");
        TableToConsole tc = new TableToConsole(ps, TAB);
        tc.outputRow(orderedColumnValues);
        Assert.assertEquals("column_A" + TAB +
                "column_B\n", outContent.toString() );
    }

    @Test
    public void _ShouldFormatRowAsThreeColumnsWithSeparatingCommaDelimiter() throws IOException {
        orderedColumnValues.add("column_X");
        orderedColumnValues.add("column_Y");
        orderedColumnValues.add("column_Z");
        TableToConsole tc = new TableToConsole(ps, COMMA);
        tc.outputRow(orderedColumnValues);
        Assert.assertEquals("column_X" + COMMA +
                "column_Y" + COMMA +
                "column_Z\n", outContent.toString() );
    }

}