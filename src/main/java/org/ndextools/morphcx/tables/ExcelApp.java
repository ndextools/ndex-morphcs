package org.ndextools.morphcx.tables;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ndexbio.model.cx.NiceCXNetwork;
import org.ndextools.morphcx.shared.Configuration;
import org.ndextools.morphcx.writers.TableToPOI;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExcelApp implements Table3D {
    private final Configuration cfg;
    private final NiceCXNetwork niceCX;
    private final TableToPOI writer;
    private final OutputStream outStream;
    private final XSSFWorkbook wb;

    public ExcelApp(Configuration cfg,
                    NiceCXNetwork niceCX,
                    TableToPOI writer,
                    XSSFWorkbook wb,
                    OutputStream outStream) {
        this.cfg = cfg;
        this.niceCX = niceCX;
        this.writer = writer;
        this.outStream = outStream;
        this.wb = wb;
    }

    @Override
    public void morphThisCX() throws IOException {

        Sheet sheet1 = wb.createSheet("Mike 1");
        Row row = sheet1.createRow(0);
        row.createCell(0).setCellValue("Cell-0");
        row.createCell(1).setCellValue("Cell-1");
        row.createCell(2).setCellValue("Cell-2");
        row.createCell(3).setCellValue("Cell-3");
        writer.outputAll();

    }

    @Override
    public List<String> buildColumnHeadings() {
        return null;  // TODO: 2/7/19 CANT BE NULL!
    }
}
