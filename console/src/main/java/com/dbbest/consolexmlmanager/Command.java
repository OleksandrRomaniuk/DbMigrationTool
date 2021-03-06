package com.dbbest.consolexmlmanager;

import com.dbbest.consolexmlmanager.exceptions.CommandException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.exceptions.ParsingException;
import com.dbbest.exceptions.SerializingException;

/**
 * This interface represents command for each particular operation with xml.
 */
public interface Command extends Comparable<Command> {
    void execute() throws ParsingException, SerializingException, ContainerException, DatabaseException, CommandException;

    int getPriority();

    @Override
    default int compareTo(Command command) {
        if (this.getPriority() < command.getPriority()) {
            return -1;
        } else if (this.getPriority() > command.getPriority()) {
            return 1;
        } else {
            return 0;
        }
    }
}
