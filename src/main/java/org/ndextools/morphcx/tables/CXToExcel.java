package org.ndextools.morphcx.tables;

import org.apache.poi.ss.usermodel.Workbook;
import org.ndexbio.model.cx.NiceCXNetwork;
import org.ndextools.morphcx.shared.Configuration;

import java.io.IOException;
import java.util.List;

public class CXToExcel implements Table3D {
    private final Configuration cfg;
    private final NiceCXNetwork niceCX;
    private final Workbook writer;

    public CXToExcel(Configuration cfg, NiceCXNetwork niceCX, Workbook writer) {
        this.cfg = cfg;
        this.niceCX = niceCX;
        this.writer = writer;
        System.err.println("Inside CXToExcel constructor.");
    }

    @Override
    public void morphThisCX() throws IOException {

    }

    @Override
    public List<String> buildColumnHeadings() {
        return null;  // TODO: 2/7/19 CANT BE NULL!
    }
}
