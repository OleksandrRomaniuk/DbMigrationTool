package com.dbbest.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class of checked exception which is thrown if any checked exceptions were encountered during the serialization process.
 */
public class SerializingException extends Exception {

    private static final Logger logger = Logger.getLogger("Serilising logger");

    /**
     * @param exception the exception which is thrown during the serialising process.
     */
    public SerializingException(Exception exception) {

        super(exception);
        logger.log(Level.SEVERE, exception.getMessage(), exception);
    }

    /**
     * @param message the message of the exception thrown.
     */
    public SerializingException(String message) {

        super(message);
        logger.log(Level.SEVERE, message);
    }

    /**
     * @param level the level of severity of the exception.
     * @param exception the exception originally thrown.
     */
    public SerializingException(Level level, Exception exception) {

        super(exception);
        logger.log(level, exception.getMessage(), exception);
    }

    /**
     * @param message the message of the exception thrown.
     */
    public SerializingException(Level level, String message) {

        super(message);
        logger.log(level, message);
    }
}

