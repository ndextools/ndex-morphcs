package org.ndextools.morphcx.tables;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ndexbio.model.cx.NiceCXNetwork;

import java.util.List;

public interface Table3D extends Table2D {

    List<SpreadsheetInfo> initializeSpreadsheets(NiceCXNetwork niceCX, XSSFWorkbook wb);

    void processSpreadsheets(NiceCXNetwork niceCX, XSSFWorkbook wb, List<SpreadsheetInfo> spreadsheetInfos);

}