package com.dbbest.dbmigrationtool.exceptions;

public class ParsingException extends Exception {

    public ParsingException(Exception exception) {
        super(exception);
    }

    public ParsingException(String message) {
        super(message);
    }
}
