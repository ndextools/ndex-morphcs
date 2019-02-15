package org.ndextools.morphcx.writers;

public interface POIWritable extends AutoCloseable {

    void outputAll() throws Exception;

}
