package org.ndextools.morphcx;

import org.ndextools.morphcx.shared.Dispatcher;
import org.ndextools.morphcx.shared.Configuration;

/**
 * The MorphCX class's main method is the application's entry point.  Its simple function is to call for
 * the creation of a configuration object, then passes the object to the class responsible for dispatching
 * the class that does the actual morphing operation.
 *
 * This class also provides a backstop to any uncaught exceptions, in which event a stack trace is printed
 * to stderr and a non-zero status code is returned.
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

    /**
     * A private-protected method that is also used as a hook for unit testing
     *
     * @param conf reference to a template Configuration object
     * @param args reference to the command-line parameters when this application was invoked
     * @return a reference to a fully-configured Configuration option
     * @throws Exception used as a backstop to any uncaught exceptions
     */
    static Configuration configureApp(final Configuration conf, final String[] args) throws Exception {
        return conf.configure(args);
    }

    static void dispatchByOperation(final Configuration cfg) throws Exception {
        if (cfg.isHelp()) {
            cfg.printHelpText();
        } else {
            Dispatcher dispatcher = new Dispatcher();
            dispatcher.dispatch();
        }
    }

    public static Configuration appConfig() {
        return cfg;
    }
}
