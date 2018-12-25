package com.dbbest.dbmigrationtool.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class of checked exception which is thrown if any checked exceptions were encountered during the validation of the tree.
 */
public class ContainerException extends Exception {
    private static final Logger logger = Logger.getLogger("Container logger");

    /**
     * @param exception the exception which is thrown at the container.
     */
    public ContainerException(Exception exception) {

        super(exception);
        logger.log(Level.SEVERE, exception.getMessage(), exception);
    }

    /**
     * @param message the message of the exception thrown.
     */
    public ContainerException(String message) {

        super(message);
        logger.log(Level.SEVERE, message);
    }

    public ContainerException(Level level, Exception exception) {

        super(exception);
        logger.log(level, exception.getMessage(), exception);
    }

    public ContainerException(Level level, String message) {

        super(message);
        logger.log(level, message);
    }
}
