package org.ndextools.morphcx.tables;

import java.io.IOException;
import java.util.List;

public interface Table2D {

    void morphThisCX() throws IOException;

    List<String> makeColumnHeadings();

}
