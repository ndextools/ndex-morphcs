package org.ndextools.morphcx;

import org.ndextools.morphcx.shared.Dispatcher;
import org.ndextools.morphcx.shared.Configuration;
import org.ndextools.morphcx.tables.CSVRoot;

/**
 * The MorphCX class's main method is the application's entry point.  Its simple function is to call for
 * the creation of a configuration object, then passes it to the class responsible for dispatching the class
 * that does the actual morphing operation.
 *
 * This class also provides a backstop to any uncaught exceptions, in which event a stack trace is printed
 * to stderr and a non-zero status code is returned.
 */
public class MorphCX {

    /**
     * The application entry point.
     * @param args command-line parameters (if any) when invoking the application.
     */
    public static void main(final String[] args ) {
        Configuration cfg;

        try {
            cfg = configureApp(args);
            dispatchByOperation(cfg);
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    static Configuration configureApp(final String[] args) throws Exception {
        Configuration cfg = new Configuration(args);
        cfg.configure();
        return cfg;
    }

    static void dispatchByOperation(final Configuration cfg) throws Exception {
        if (cfg.isHelp()) {
            cfg.printHelpText();
        } else {
            Dispatcher dispatcher = new Dispatcher(cfg);
            dispatcher.dispatch();
        }
        return;
    }

}

