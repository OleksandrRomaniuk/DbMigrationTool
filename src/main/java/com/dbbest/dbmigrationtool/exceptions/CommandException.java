package com.dbbest.dbmigrationtool.exceptions;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandException extends Exception {

    private static final Logger logger = Logger.getLogger("Command logger");

    public CommandException(Exception exception) {

        super(exception);
        logger.log(Level.SEVERE, exception.getMessage(), exception);
    }

    public CommandException(String message) {

        super(message);
        logger.log(Level.SEVERE, message);
    }

    public CommandException(Level level, Exception exception) {

        super(exception);
        logger.log(level, exception.getMessage(), exception);
    }

    public CommandException(Level level, String message) {

        super(message);
        logger.log(level, message);
    }

}
