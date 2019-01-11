package org.ndextools.morphcx.shared;

/**
 * The Utilities class holds methods commonly used across classes.
 */
public class Utilities {

    /**
     * Method to check the safety of null references being passed to application methods.
     * @param reference the reference variable being checked for null condition.
     * @param classname calling method is expected to populate this field with it's simple class name.
     * @throws NullPointerException
     */
    public static void nullReferenceCheck(Object reference, String classname) throws NullPointerException {
        if (reference == null) {
            String msg = String.format("%s: Reference parameter expected !=null, actual=null", classname);
            throw new NullPointerException(msg);
        }
    }

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
                int intDelimiter = ch;
                stringDelimiter = String.valueOf(intDelimiter);
                break;
        }
        return stringDelimiter;
    }

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
