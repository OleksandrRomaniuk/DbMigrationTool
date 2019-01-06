package com.dbbest.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class of checked exception which is thrown if any checked exceptions were encountered at the connection module.
 */
public class ConnectionException extends Exception {
    private static final Logger logger = Logger.getLogger("Connection logger");

    /**
     * @param exception the exception which is thrown at the connection module.
     */
    public ConnectionException(Exception exception) {

        super(exception);
        logger.log(Level.SEVERE, exception.getMessage(), exception);
    }

    /**
     * @param message the message of the exception thrown.
     */
    public ConnectionException(String message) {

        super(message);
        logger.log(Level.SEVERE, message);
    }

    /**
     * @param level the level of the exception.
     * @param exception the exception which originally was thrown.
     */
    public ConnectionException(Level level, Exception exception) {

        super(exception);
        logger.log(level, exception.getMessage(), exception);
    }

    /**
     * @param level the level of the exception.
     * @param message the message of the exception thrown.
     */
    public ConnectionException(Level level, String message) {

        super(message);
        logger.log(level, message);
    }
}
