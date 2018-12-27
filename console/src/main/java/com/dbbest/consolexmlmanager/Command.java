package com.dbbest.consolexmlmanager;

import com.dbbest.xmlmanager.exceptions.ContainerException;
import com.dbbest.xmlmanager.exceptions.ParsingException;
import com.dbbest.xmlmanager.exceptions.SerializingException;

/**
 * This interface represents command for each particular operation with xml.
 */
public interface Command extends Comparable<Command> {
    void execute() throws ParsingException, SerializingException, ContainerException;

    int getPriority();

    @Override
    default int compareTo(Command command) {
        if (this.getPriority() < command.getPriority()) {
            return 1;
        } else if (this.getPriority() > command.getPriority()) {
            return -1;
        } else {
            return 0;
        }
    }
}
