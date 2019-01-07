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
    public void _ShouldOutputColumnHeadersAndColumnDataUsingCommaDelimiter() throws Exception {

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
        } catch (Exception e) {
            throw new Exception(e);
        }

        String output = outContent.toString();
        Assert.assertTrue(output.contains("Header-A,Header-B,Header-C,Header-D"));
        Assert.assertTrue(output.contains("\n"));
        Assert.assertTrue(output.contains("data_2_A,data_2_B,data_2_C,data_2_D"));
    }

        @Test
        public void _ShouldOutputColumnHeadersAndColumnDataUsingTabDelimiter() throws Exception {

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
            } catch (Exception e) {
                throw new Exception(e);
            }

            String output = outContent.toString();
            Assert.assertTrue(output.contains("Header-A\tHeader-B\tHeader-C\tHeader-D"));
            Assert.assertTrue(output.contains("\n"));
            Assert.assertTrue(output.contains("data_2_A\tdata_2_B\tdata_2_C\tdata_2_D"));
        }

    @Test
    public void _ShouldOutputColumnHeadersAndDataRowsUsingWindowsNewline() throws Exception {

        List<String> row1Headers = new ArrayList<>();
        row1Headers.add("Header-A");
        row1Headers.add("Header-B");
        row1Headers.add("Header-C");
        row1Headers.add("Header-D");

        List<String> row2Data = new ArrayList<>();
        row2Data.add("data_A");
        row2Data.add("data_B");
        row2Data.add("data_C");
        row2Data.add("data_D");

        try {
            CSVPrinter printer = new CSVPrinter(ps, CSVFormat.DEFAULT
                    .withRecordSeparator("\r\n")
                    .withDelimiter(COMMA));
            printer.printRecord(row1Headers);
            printer.printRecord(row2Data);
        } catch (Exception e) {
            throw new Exception(e);
        }

        String output = outContent.toString();
        Assert.assertTrue(output.contains("Header-A,Header-B,Header-C,Header-D"));
        Assert.assertTrue(output.contains("\r\n"));
        Assert.assertTrue(output.contains("data_A,data_B,data_C,data_D"));
    }

}
