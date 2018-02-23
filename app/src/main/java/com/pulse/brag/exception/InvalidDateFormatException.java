package com.pulse.brag.exception;

/**
 * Created by alpesh.rathod on 2/2/2018.
 */

public class InvalidDateFormatException extends Exception {

    public InvalidDateFormatException(String message) {
        super(message);
    }

    public InvalidDateFormatException() {
        super("InvalidDateFormatException");
    }
}
