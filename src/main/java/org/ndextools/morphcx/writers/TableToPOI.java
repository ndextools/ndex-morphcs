package org.ndextools.morphcx.writers;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;

public class TableToPOI implements POIWritable, AutoCloseable {
    private final OutputStream outputStream;
    private final XSSFWorkbook workbook;

    public TableToPOI(OutputStream outputStream, final XSSFWorkbook workbook) {
        this.outputStream = outputStream;
        this.workbook = workbook;
    }

    /**
     * Outputs the Excel workbook object to a designated output stream.
     *
     * @throws IOException likely caused by an IOException when when writing to the underlying output stream.
     */
    @Override
    public void outputAll() throws IOException {
        try {
            workbook.write(outputStream);
        } catch (IOException io) {
            throw new IOException(io);
        }
    }

    /**
     * Releases resources associated with AutoClosable interface
     */
    @Override
    public void close() throws IOException {
        try
        {
            if (workbook != null) {
                workbook.close();
            }

            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }

        } catch (IOException e) {
            throw new IOException(e);
        }
    }

}
