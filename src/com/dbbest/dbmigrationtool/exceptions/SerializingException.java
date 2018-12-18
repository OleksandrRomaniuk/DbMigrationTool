package com.dbbest.dbmigrationtool.exceptions;

/**
 * A class of checked exception which is thrown if any checked exceptions were encountered during the serialization process.
 */
public class SerializingException extends Exception {
    public SerializingException(Exception exception) {
        super(exception);
    }

    public SerializingException(String message) {
        super(message);
    }
}
