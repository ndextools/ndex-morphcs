package org.ndextools.morphcx.shared;

import static org.ndextools.morphcx.shared.Configuration.ConfigurationConstants.*;

/**
 * The Utilities class holds methods commonly used across this application's classes.
 */
public class Utilities {
    private Configuration cfg;

    /**
     * Class constructor
     *
     * @param cfg a reference to a fully-configured Configuration object
     */
    public Utilities(Configuration cfg) {
        this.cfg = cfg;
    }

    /**
     * Method to check the safety of null references being passed to application methods.
     *
     * @param reference the reference variable being checked for null condition.
     * @param classname calling method is expected to populate this field with it's simple class name.
     * @throws NullPointerException occurs when the passed object reference is null
     */
    public static void nullReferenceCheck(Object reference, String classname) throws NullPointerException {
        if (reference == null) {
            String msg = String.format("%s: Reference parameter expected !=null, actual=null", classname);
            throw new NullPointerException(msg);
        }
    }

    /**
     * Translates the CSV delimiter character to a text string
     *
     * @param ch delimter character to translate into string text
     * @return string text representing a comma or tab delimiter character
     */
    public static String delimiterToStringConvert(char ch) {

        String stringDelimiter;
        switch (ch) {
            case TAB:
                stringDelimiter = "\t";
                break;
            case COMMA:
                stringDelimiter = ",";
                break;
            default:
                stringDelimiter = String.valueOf(ch);
                break;
        }
        return stringDelimiter;
    }

    /**
     * Translates the CSV delimiter character to a text string description
     *
     * @param ch delimter character to translate into st a text description
     * @return string text description of a comma or tab delimiter character
     */
    public static String delimiterToDescriptionText(char ch) {

        String text = "";
        switch (ch) {
            case TAB:
                text = "TAB_CHARACTER";
                break;
            case COMMA:
                text = "COMMA_CHARACTER";
                break;
        }
        return text;
    }

    /**
     * Translates the CSV newline constant to unviewable string text such as \n and \r
     *
     * @param str newline string to translate into string text
     * @return the platform-dependent newline a writer will use
     */
    public static String newlineToStringConvert(String str) {
        String stringNewline;
        switch (str) {
            case ESCAPE_R_ESCAPE_N:
                stringNewline = ESCAPE_R_ESCAPE_N;
                break;
            case ESCAPE_N:
                stringNewline = ESCAPE_N;
                break;
            case ESCAPE_R:
                stringNewline = ESCAPE_R;
                break;
            default:
                stringNewline = str;
                break;
        }
        return stringNewline;
    }

    public static String newlineToDescriptionText(Configuration.Newline nl) {
        String text = "";
        switch (nl) {
            case WINDOWS:
                text = WINDOWS;
                break;
            case LINUX:
                text = LINUX;
                break;
            case OSX:
                text = OSX;
                break;
            case OLDMAC:
                text = OLDMAC;
                break;
            case SYSTEM:
                text = SYSTEM;
                break;
            case NOT_SPECIFIED:
                text = Configuration.Newline.NOT_SPECIFIED.toString();
                break;
        }
        return text;
    }

    /**
     * Translates the CSV newline string constant to descriptive text
     *
     * @param str unviewable newline string to translate into descriptive text
     * @return the platform-dependent newline a writer will use
     */
    public static String newlineStringToDescriptionText(String str) {
        String viewableString = "";
        switch (str) {
            case ESCAPE_R_ESCAPE_N:
                viewableString = "ESCAPE_R_ESCAPE_N";
                break;
            case ESCAPE_N:
                viewableString = "ESCAPE_N";
                break;
            case ESCAPE_R:
                viewableString = "ESCAPE_R";
                break;
            default:
                viewableString = "''";
                break;
        }
        return viewableString;
    }


}
