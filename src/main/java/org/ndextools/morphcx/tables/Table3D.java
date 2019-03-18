package org.ndextools.morphcx.tables;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.ndexbio.model.cx.NiceCXNetwork;

import java.util.List;

public interface Table3D extends Table2D {

    List<SpreadsheetInfo> initializeSpreadsheets(NiceCXNetwork niceCX, SXSSFWorkbook wb);

    void processSpreadsheets(NiceCXNetwork niceCX, SXSSFWorkbook wb, List<SpreadsheetInfo> spreadsheetInfos);

}