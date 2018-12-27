package com.dbbest.consolexmlmanager.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class of checked exception which is thrown if any checked exceptions were encountered during commands work.
 */
public class CommandException extends Exception {

    private static final Logger logger = Logger.getLogger("Command logger");

    /**
     * @param exception the exception which is thrown at a command.
     */
    public CommandException(Exception exception) {

        super(exception);
        logger.log(Level.SEVERE, exception.getMessage(), exception);
    }

    /**
     * @param message the message of the exception thrown.
     */
    public CommandException(String message) {

        super(message);
        logger.log(Level.SEVERE, message);
    }

    /**
     * @param level the level of exception thrown.
     * @param exception the original exception thrown.
     */
    public CommandException(Level level, Exception exception) {

        super(exception);
        logger.log(level, exception.getMessage(), exception);
    }

    /**
     * @param level the level of exception thrown.
     * @param message the message of the exception thrown.
     */
    public CommandException(Level level, String message) {

        super(message);
        logger.log(level, message);
    }

}
