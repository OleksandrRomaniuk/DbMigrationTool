package com.dbbest.consolexmlmanager;


import com.dbbest.consolexmlmanager.exceptions.CommandException;
import com.dbbest.xmlmanager.container.Container;
import com.dbbest.xmlmanager.exceptions.ParsingException;
import com.dbbest.xmlmanager.exceptions.SerializingException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * A class which contains all commands and operates with them.
 */
public class Invoker {

    private List<Command> commandList = new ArrayList();

    /**
     * @param command the command which will be added.
     */
    public void add(Command command) {
        if (command != null) {
            commandList.add(command);
        }
    }

    public void remove(int index) {
        commandList.remove(index);
    }

    /**
     * @throws CommandException the exception thrown during execution of commands.
     */
    public void execute() throws CommandException {
        for (Command command : commandList) {
            executeCommand(command);
        }
    }

    private void executeCommand(Command command) throws CommandException {
        try {

            if (command instanceof CommandRead) {
                command.execute();
                containerBuilt = ((CommandRead) command).getBuiltContainer();
            } else if (command instanceof CommandWrite) {
                if (containerBuilt != null) {
                    ((CommandWrite) command).setContainerToWrite(containerBuilt);
                    command.execute();
                } else {
                    throw new CommandException(Level.SEVERE, "No Container found to serialize.");
                }
            } else if (command instanceof CommandSearch) {
                if (containerBuilt != null) {
                    ((CommandSearch) command).setContainerToSearchIn(containerBuilt);
                    command.execute();
                    listOfFoundElements = ((CommandSearch) command).getListOfFoundItems();
                } else {
                    throw new CommandException(Level.SEVERE, "No Container found to search in.");
                }
            }
        } catch (ParsingException | SerializingException exception) {
            throw new CommandException(Level.SEVERE, "Command " + command.getCommandLine()
                + " could not be executed. The program stoped execution of programs.");
        }
    }

    public Container getContainerBuilt() {
        return containerBuilt;
    }

    public List<Container> getListOfFoundElements() {
        return listOfFoundElements;
    }
}
