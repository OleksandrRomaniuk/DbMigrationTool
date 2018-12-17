package com.dbbest.dbmigrationtool.exceptions;

public class SerializingException extends Exception {
    public SerializingException(Exception exception) {
        super(exception);
    }

    public SerializingException(String message) {
        super(message);
    }
}
