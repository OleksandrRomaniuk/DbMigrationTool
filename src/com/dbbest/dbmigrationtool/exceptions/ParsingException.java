package com.dbbest.dbmigrationtool.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class of checked exception which is thrown if any checked exceptions were encountered during the parsing of a file.
 */
public class ParsingException extends Exception {

    private static final Logger logger = Logger.getLogger("Parsing logger");

    /**
     * @param exception the exception which is thrown during the parsing process.
     */
    public ParsingException(Exception exception) {

        super(exception);
        logger.log(Level.SEVERE, exception.getMessage(), exception);
    }

    /**
     * @param message the message of the exception thrown.
     */
    public ParsingException(String message) {

        super(message);
        logger.log(Level.SEVERE, message);
    }
}

