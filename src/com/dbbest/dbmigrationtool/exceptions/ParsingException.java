package com.dbbest.dbmigrationtool.exceptions;

/**
 * A class of checked exception which is thrown if any checked exceptions were encountered during the parsing of a file.
 */
public class ParsingException extends Exception {

    public ParsingException(Exception exception) {
        super(exception);
    }

    public ParsingException(String message) {
        super(message);
    }
}
