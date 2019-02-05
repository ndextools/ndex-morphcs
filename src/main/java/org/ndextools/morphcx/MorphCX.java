package org.ndextools.morphcx;

import org.ndextools.morphcx.shared.Dispatcher;
import org.ndextools.morphcx.shared.Configuration;

/**
 * The MorphCX class's main() method is the application's entry point.  Its purpose is to call for the
 * creation of a configuration object, then pass control to the class responsible for dispatching the
 * the class that does the actual morphing operation.
 *
 * This class also provides a backstop to any uncaught exceptions, upon which a stack trace is printed
 * to stderr and a non-zero status code is returned should one occur.
 */
public class MorphCX {
    private static Configuration cfg;

    static {
        cfg = new Configuration();
    }

    /**
     * The application entry point.
     * @param args command-line parameters (if any) when invoking the application.
     */
    public static void main(final String[] args ) {

        try {
            cfg = configureApp(cfg, args);
            dispatchByOperation(cfg);
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    static Configuration configureApp(final Configuration conf, final String[] args) throws Exception {
        return conf.configure(args);
    }

    static void dispatchByOperation(final Configuration cfg) throws Exception {
        if (cfg.isHelp()) {
            cfg.printHelpText();
        } else {
            Dispatcher dispatcher = new Dispatcher();
            dispatcher.run();
        }
    }

    /**
     * Getter method
     * @return reference to a fully-configured Configuration object
     */
    public static Configuration appConfig() { return cfg; }

}
