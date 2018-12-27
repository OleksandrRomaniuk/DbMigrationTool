package com.dbbest.consolexmlmanager;

import com.dbbest.xmlmanager.exceptions.ParsingException;
import com.dbbest.xmlmanager.exceptions.SerializingException;

/**
 * This interface represents command for each particular operation with xml.
 */
public interface Command {
    void execute() throws ParsingException, SerializingException;
}
