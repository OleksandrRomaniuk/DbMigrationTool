package com.dbbest.dbmigrationtool.console;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.exceptions.CommandException;
import com.dbbest.dbmigrationtool.exceptions.ParsingException;
import com.dbbest.dbmigrationtool.exceptions.SerializingException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Invoker {

    private List<Command> commandList = new ArrayList();
    private Container containerBuilt;

    public void add(Command command) {
        commandList.add(command);
    }

    public void remove(int index) {
        commandList.remove(index);
    }

    public void execute() throws CommandException {
        for (Command command : commandList) {
            executeCommand(command);
        }
    }

    private void executeCommand(Command command) throws CommandException {
        try {

            command.execute();
            if (command instanceof CommandRead) {
                containerBuilt = ((CommandRead) command).getBuiltContainer();
            } else if (command instanceof CommandWrite) {
                if (containerBuilt != null) {
                    ((CommandWrite) command).setContainerToWrite(containerBuilt);
                } else {
                    throw new CommandException(Level.SEVERE, "No Container found to serialize.");
                }
            } else if (command instanceof CommandSearch) {
                if (containerBuilt != null) {
                    ((CommandSearch) command).setContainerToSearchIn(containerBuilt);
                } else {
                    throw new CommandException(Level.SEVERE, "No Container found to search in.");
                }
            }
        } catch (ParsingException | SerializingException exception) {
            throw new CommandException(Level.SEVERE, "Command " + command.getCommandLine() + " could not be executed. The program stoped execution of programs.");
        }
    }
}
