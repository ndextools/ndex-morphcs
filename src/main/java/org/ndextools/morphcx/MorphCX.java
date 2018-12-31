package org.ndextools.morphcx;

import org.apache.commons.cli.HelpFormatter;
import org.ndextools.morphcx.tables.CSVRoot;
import org.ndextools.morphcx.shared.Configuration;

/**
 * The MorphCX class behaves as the MorphCX application entry point.
 *
 * This class provides a backstop to any uncaught exceptions, in which event a stack trace is printed to stderr
 * and a non-zero status code is returned.
 */
public class MorphCX {

    /**
     * The application entry point.
     * @param args command-line parameters (if any) when invoking the application.
     */
    public static void main(final String[] args ) {
        Configuration cfg;

        try {
//            nullReferenceCheck(args, MorphCX.class.getSimpleName());
            cfg = configureApp(args);
            dispatchByOperation(cfg);
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static Configuration configureApp(final String[] args) throws Exception {
        Configuration cfg = new Configuration(args);
        cfg.configure();
        return cfg;
    }

    static void dispatchByOperation(final Configuration cfg) throws Exception {

        if (!cfg.isHelp()) {
            CSVRoot root = new CSVRoot();
            root.execute(cfg);
        }

        return;
    }

    public static void nullReferenceCheck(Object reference, String classname) throws NullPointerException {
        if (reference == null) {
            String msg = String.format("%s: Reference parameter expected !=null, actual=null", classname);
            throw new NullPointerException(msg);
        }
    }

}

