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
            case Configuration.OptionConstants.TAB:
                stringDelimiter = String.valueOf(Configuration.OptionConstants.TAB);
                break;
            case Configuration.OptionConstants.COMMA:
                stringDelimiter = String.valueOf(Configuration.OptionConstants.COMMA);
                break;
            default:
                int intDelimiter = ch;
                stringDelimiter = String.valueOf(intDelimiter);
                break;
        }
        return stringDelimiter;
    }

    public static String newlineToStringConvert(String str) {
        String stringNewline;
        switch (str) {
            case Configuration.OptionConstants.ESCAPE_R_ESCAPE_N:
                stringNewline = Configuration.OptionConstants.ESCAPE_R_ESCAPE_N;
                break;
            case Configuration.OptionConstants.ESCAPE_N:
                stringNewline = Configuration.OptionConstants.ESCAPE_N;
                break;
            case Configuration.OptionConstants.ESCAPE_R:
                stringNewline = Configuration.OptionConstants.ESCAPE_R;
                break;
            default:
                stringNewline = str;
                break;
        }
        return stringNewline;
    }

}
