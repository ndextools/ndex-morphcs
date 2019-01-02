package org.ndextools.morphcx.writers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;

/**
 * Unit tests for TableToStream class.
 */
public class TableToStreamTest {
    private PrintStream ps;
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private List<String> orderedColumnValues;
    private final char COMMA = ',';
    private final char TAB = '\t';
    private final String NEWLINE = System.getProperty("line.separator");


    @Before
    public void setup() {
        // Redirect console output to outputStream in order to perform JUnit tests.
        ps = new PrintStream(outputStream);
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
    public void _ShouldFormatRowAsOneColumnAndNoDelimiter() throws Exception {
        orderedColumnValues.add("column_1");
        TableToStream tc = new TableToStream(ps, COMMA, NEWLINE);
        tc.outputRow(orderedColumnValues);
        Assert.assertEquals("column_1\n", outputStream.toString() );
    }

    @Test
    public void _ShouldFormatRowAsTwoColumnsWithSeparatingCommaDelimiter() throws Exception {
        orderedColumnValues.add("column_1");
        orderedColumnValues.add("column_2");
        TableToStream tc = new TableToStream(ps, COMMA, NEWLINE);
        tc.outputRow(orderedColumnValues);
        Assert.assertEquals("column_1" + COMMA +
                "column_2\n", outputStream.toString() );
    }

    @Test
    public void _ShouldFormatRowAsTwoColumnsWithSeparatingTabDelimiter() throws Exception {
        orderedColumnValues.add("column_A");
        orderedColumnValues.add("column_B");
        TableToStream tc = new TableToStream(ps, TAB, NEWLINE);
        tc.outputRow(orderedColumnValues);
        Assert.assertEquals("column_A" + TAB +
                "column_B\n", outputStream.toString() );
    }

    @Test
    public void _ShouldFormatRowAsThreeColumnsWithSeparatingCommaDelimiter() throws Exception {
        orderedColumnValues.add("column_X");
        orderedColumnValues.add("column_Y");
        orderedColumnValues.add("column_Z");
        TableToStream tc = new TableToStream(ps, COMMA, NEWLINE);
        tc.outputRow(orderedColumnValues);
        Assert.assertEquals("column_X" + COMMA +
                "column_Y" + COMMA +
                "column_Z\n", outputStream.toString() );
    }

}