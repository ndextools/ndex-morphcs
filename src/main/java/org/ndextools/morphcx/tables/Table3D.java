package org.ndextools.morphcx.tables;

import java.io.IOException;
import java.util.List;

/**
 * Interface for Tables classes
 */
public interface Table3D {

    void morphThisCX() throws IOException;

    List<String> buildColumnHeadings();

}
