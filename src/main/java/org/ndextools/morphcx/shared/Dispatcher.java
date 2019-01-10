package org.ndextools.morphcx.shared;


import org.ndextools.morphcx.tables.CSVRoot;

/**
 * The Dispatcher class is a factory that instantiates the class that will do the morphing.
 */
public class Dispatcher {
    private Configuration cfg;

    /**
     * Dispatches the class that does the dispatching the class that does the actual morphing operation.
     *
     * @param cfg
     */
    public Dispatcher(Configuration cfg) {
        this.cfg = cfg;
    }

    public void dispatch() throws Exception {

        // Only --convert tsv & --convert csv are valid at this time!
        Configuration.Operation operation = cfg.getOperation();
        switch (operation) {
            case CSV:
            case TSV:
            default:
                CSVRoot root = new CSVRoot();
                root.execute(cfg);
                break;
        }
    }
}
