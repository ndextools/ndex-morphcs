package org.ndextools.morphcx.writers;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ndextools.morphcx.tables.POICell;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * This class formats and writes Excel workbooks to an  output stream by using
 * the Apache Commons POI dependency.
 */
public class TableToPOI implements POIWritable<POICell> {
    private final OutputStream outputStream;
    private final XSSFWorkbook workbook;


    /**
     * Class constructor
     *
     * @param outputStream reference to the destination output stream.
     * @param workbook reference to an Excel workbook data structure managed by POI.
     */
    public TableToPOI(OutputStream outputStream, final XSSFWorkbook workbook)
    {
        this.outputStream = outputStream;
        this.workbook = workbook;
    }

    /**
     * Refer to the corresponding method in the POIWritable interface.
     */
    @Override
    public void writeRow(List<POICell> columns) throws Exception {
        try
        {
            for ( POICell column : columns )
            {
                Row row = column.getRow();
                row.createCell(column.getColumnIdx()).setCellValue(column.getText());
            }
        }
        catch (Exception e)
        {
            String msg = this.getClass().getSimpleName() + ": " + e.getMessage();
            throw new IOException(msg);
        }
    }

    /**
     * Refer to the corresponding method in the POIWritable interface.
     */
    @Override
    public void writeAll() throws IOException {
        try
        {
            workbook.write(outputStream);
            workbook.close();
        }
        catch (IOException io)
        {
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
            if (workbook != null)
            {
                workbook.close();
            }
            if (outputStream != null)
            {
                outputStream.flush();
                outputStream.close();
            }
        }
        catch (IOException e)
        {
            throw new IOException(e);
        }
    }

}
