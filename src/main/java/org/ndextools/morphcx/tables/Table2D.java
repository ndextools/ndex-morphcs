package org.ndextools.morphcx.tables;

import java.io.IOException;
import java.util.List;

/**
 * Interface for Table classes
 */
public interface Table2D {

    void morphThisNiceCX() throws IOException;

    List<String> buildColumnHeadings();

}
