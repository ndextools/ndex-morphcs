package org.ndextools.morphcx.shared;

import org.ndextools.morphcx.MorphCX;
import org.ndextools.morphcx.tables.TablesRoot;

/**
 * The Dispatcher class is a factory that instantiates and passes control to the class which controls
 * the morphing workflow.
 */
public class Dispatcher {
    private final Configuration cfg;

    /**
     * Default constructor used by the application itself.
     */
    public Dispatcher() {
        this.cfg = MorphCX.appConfig();
        String msg = String.format("%s: expecting MorphCX.appConfig() !=null, actual=%s ",
                Dispatcher.class.getSimpleName(), cfg);
        Utilities.nullReferenceCheck(cfg, msg);
    }

    /**
     * Class constructor to be used by third-party developers who extend this application's API
     *
     * @param conf reference to a Configuration object built by third-party developers
     */
    public Dispatcher(Configuration conf) {
        this.cfg = conf;
        String msg = String.format("%s: expecting cfg !=null, actual=%s ",
                Dispatcher.class.getSimpleName(), cfg);
        Utilities.nullReferenceCheck(cfg, msg);
    }

    /**
     * Passes control to the class doing the morphing.  Can be overridden by third-party developers
     * who extend this application's API
     *
     * @throws Exception base class exception when there is an IO or other processing error
     */
    public void run() throws Exception {

        Configuration.Operation operation = cfg.getOperation();
        switch (operation) {
            case TSV:
            case CSV:
            case EXCEL:
            default:
                TablesRoot root = new TablesRoot();
                root.execute(cfg);
        }
    }
}
