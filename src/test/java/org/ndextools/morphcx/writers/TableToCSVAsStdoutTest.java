package org.ndextools.morphcx.writers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for TableToCSV class when the output target is stdout.
 */
public class TableToCSVAsStdoutTest {
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
    public void _ShouldOutputColumnHeadersUsingCommaDelimiter() {

        List<String> row1Headers = new ArrayList<>();
        row1Headers.add("Header-A");
        row1Headers.add("Header-B");
        row1Headers.add("Header-C");
        row1Headers.add("Header-D");

        List<String> row2Data = new ArrayList<>();
        row2Data.add("data_2_A");
        row2Data.add("data_2_B");
        row2Data.add("data_2_C");
        row2Data.add("data_2_D");

        try {
            CSVPrinter printer = new CSVPrinter(ps, CSVFormat.DEFAULT
                    .withRecordSeparator("\n")
                    .withDelimiter(COMMA));
            printer.printRecord(row1Headers);
            printer.printRecord(row2Data);
        } catch (IOException e) {
            Assert.assertEquals("Header-A" + COMMA +
                    "Header-B" + COMMA +
                    "Header-C" + COMMA +
                    "Header-D\n", outContent.toString());
        }
    }

        @Test
        public void _ShouldOutputColumnHeadersUsingTabDelimiter() {

            List<String> row1Headers = new ArrayList<>();
            row1Headers.add("Header-A");
            row1Headers.add("Header-B");
            row1Headers.add("Header-C");
            row1Headers.add("Header-D");

            List<String> row2Data = new ArrayList<>();
            row2Data.add("data_2_A");
            row2Data.add("data_2_B");
            row2Data.add("data_2_C");
            row2Data.add("data_2_D");

            try {
                CSVPrinter printer = new CSVPrinter(ps, CSVFormat.TDF
                        .withRecordSeparator("\n")
                        .withDelimiter(TAB));
                printer.printRecord(row1Headers);
                printer.printRecord(row2Data);
            } catch (IOException e) {
                Assert.assertEquals("Header-A" + TAB +
                        "Header-B" + TAB +
                        "Header-C" + TAB +
                        "Header-D\n", outContent.toString() );
            }
    }

    @Test
    public void _ShouldOutputColumnHeadersAndDataRowsUsingCommaDelimiter() {

        List<String> row1Headers = new ArrayList<>();
        row1Headers.add("Column-A");
        row1Headers.add("Column-B");
        row1Headers.add("Column-C");
        row1Headers.add("Column-D");

        List<String> row2Data = new ArrayList<>();
        row2Data.add("data_A");
        row2Data.add("data_B");
        row2Data.add("data_C");
        row2Data.add("data_D");

        try {
            CSVPrinter printer = new CSVPrinter(ps, CSVFormat.DEFAULT
                    .withRecordSeparator("\n")
                    .withDelimiter(COMMA));
            printer.printRecord(row1Headers);
            printer.printRecord(row2Data);
        } catch (IOException e) {
            Assert.assertEquals("Column-A" + COMMA +
                    "Column-B" + COMMA +
                    "Column-C" + COMMA +
                    "Column-D\n" + COMMA +
                    "data_A" + COMMA +
                    "data_B" + COMMA +
                    "data_C" + COMMA +
                    "data-D\n", outContent.toString());
        }
    }

    @Test
    public void _ShouldOutputColumnHeadersAndDataRowsUsingTabDelimiter() {

        List<String> row1Headers = new ArrayList<>();
        row1Headers.add("Column-A");
        row1Headers.add("Column-B");
        row1Headers.add("Column-C");
        row1Headers.add("Column-D");

        List<String> row2Data = new ArrayList<>();
        row2Data.add("data_A");
        row2Data.add("data_B");
        row2Data.add("data_C");
        row2Data.add("data_D");

        try {
            CSVPrinter printer = new CSVPrinter(ps, CSVFormat.TDF
                    .withRecordSeparator("\n")
                    .withDelimiter(TAB));
            printer.printRecord(row1Headers);
            printer.printRecord(row2Data);
        } catch (IOException e) {
            Assert.assertEquals("Column-A" + TAB +
                    "Column-B" + TAB +
                    "Column-C" + TAB +
                    "Column-D\n" + TAB +
                    "data_A" + TAB +
                    "data_B" + TAB +
                    "data_C" + TAB +
                    "data-D\n", outContent.toString());
        }
    }

}
