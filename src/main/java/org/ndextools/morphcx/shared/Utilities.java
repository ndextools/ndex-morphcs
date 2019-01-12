package org.ndextools.morphcx.shared;

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
            case Configuration.ConfigurationConstants.TAB:
                stringDelimiter = String.valueOf(Configuration.ConfigurationConstants.TAB);
                break;
            case Configuration.ConfigurationConstants.COMMA:
                stringDelimiter = String.valueOf(Configuration.ConfigurationConstants.COMMA);
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
            case Configuration.ConfigurationConstants.TAB:
                text = String.valueOf(Configuration.ConfigurationConstants.TAB);
                break;
            case Configuration.ConfigurationConstants.COMMA:
                text = String.valueOf(Configuration.ConfigurationConstants.COMMA);
                break;
        }
        return text;
    }

    /**
     * Translates the CSV newline constant to a string text
     *
     * @param str newline string to translate into string text
     * @return the platform-dependent newline a writer will use
     */
    public static String newlineToStringConvert(String str) {
        String stringNewline;
        switch (str) {
            case Configuration.ConfigurationConstants.ESCAPE_R_ESCAPE_N:
                stringNewline = Configuration.ConfigurationConstants.ESCAPE_R_ESCAPE_N;
                break;
            case Configuration.ConfigurationConstants.ESCAPE_N:
                stringNewline = Configuration.ConfigurationConstants.ESCAPE_N;
                break;
            case Configuration.ConfigurationConstants.ESCAPE_R:
                stringNewline = Configuration.ConfigurationConstants.ESCAPE_R;
                break;
            default:
                stringNewline = str;
                break;
        }
        return stringNewline;
    }

    // TODO: 1/10/19 finish 
    public static String newlineToDescriptionText(Configuration.Newline nl) {
        String text = "";
//        switch (nl) {
//            case
//        }
        return "?"; 
    }
}
