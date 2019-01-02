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

}
