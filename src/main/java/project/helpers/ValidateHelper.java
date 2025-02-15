package project.helpers;


import project.exceptions.InvalidDataException;

public class ValidateHelper {
    public static void throwIfFalse(boolean condition, String errorMessage) {
        if (!condition) {
            throw new InvalidDataException(errorMessage);
        }
    }

    public static void throwIfNull(Object obj, String errorMessage) {
        if (obj == null) {
            throw new InvalidDataException(errorMessage);
        }
    }
}