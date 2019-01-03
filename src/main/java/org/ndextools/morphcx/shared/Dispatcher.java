package org.ndextools.morphcx.shared;


import org.ndextools.morphcx.tables.CSVRoot;

/**
 * The Dispatcher class is a factory that instantiates the class that will do the morphing.
 */
public class Dispatcher {
    private Configuration cfg;

    public Dispatcher(Configuration cfg) {
        this.cfg = cfg;
    }

    public void dispatch() throws Exception {
        CSVRoot root = new CSVRoot();
        root.execute(cfg);
    }
}
