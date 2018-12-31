package org.ndextools.morphcx.writers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for TableToCSV class when the output target is a file.
 */
public class TableToCSVAsFileTest {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private List<String> orderedColumnValues;
    private final char COMMA = ',';
    private final char TAB = '\t';


    @Before
    public void setup() {
        // Redirect console output to outContent in order to perform JUnit tests.
        System.setOut(new PrintStream(outContent, true));

        // List containing all columns to be printed to the (redirected) console.
        orderedColumnValues = new ArrayList<>();
    }

    @After
    public void breakdown() {
        //Redirect console output to system stdout stream.
        System.setOut(System.out);
    }


    @Test
    public void _ShouldOutputHeadersAndColumns() {

        try {
//            String SAMPLE_CSV_FILE = "./sample.tables";
//            BufferedWriter writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE));
            PrintStream printStream = new PrintStream(System.out);
            CSVPrinter printer = new CSVPrinter(printStream, CSVFormat.DEFAULT
                    .withRecordSeparator("\n")
                    .withDelimiter(COMMA));
//            CSVPrinter printer = CSVFormat.DEFAULT.print(System.out);
            List<String> headers1 = new ArrayList<>();
            headers1.add("Header-1");
            headers1.add("Header-2");
            headers1.add("Header-3");
            headers1.add("Header-4");
            String[] arrayHeaders = new String[headers1.size()];
            arrayHeaders = headers1.toArray(arrayHeaders);
//            System.out.println("header1=" + headers1);
            List<String> headers2 = new ArrayList<>();
            headers2.add("col_1");
            headers2.add("col_2");
            headers2.add("col_3");
            headers2.add("col_4");
            String[] arrayColumns = new String[headers2.size()];
            arrayColumns = headers2.toArray(arrayColumns);
            printer.printRecord(arrayHeaders);
            printer.printRecord(arrayColumns);
        } catch (IOException e) {

        }
    }

}
