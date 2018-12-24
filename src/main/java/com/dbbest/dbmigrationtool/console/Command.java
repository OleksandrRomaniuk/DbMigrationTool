package com.dbbest.dbmigrationtool.console;

import com.dbbest.dbmigrationtool.exceptions.ParsingException;
import com.dbbest.dbmigrationtool.exceptions.SerializingException;

public interface Command {
    void execute() throws ParsingException, SerializingException;
}
