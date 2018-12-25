package com.dbbest.dbmigrationtool.console;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.exceptions.ParsingException;
import com.dbbest.dbmigrationtool.exceptions.SerializingException;

public interface Command {
    void execute() throws ParsingException, SerializingException;

    void setCommandLine(String commandLine);

    String getCommandLine();

}
