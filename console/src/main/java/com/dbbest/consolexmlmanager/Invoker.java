package com.dbbest.consolexmlmanager;


import com.dbbest.consolexmlmanager.exceptions.CommandException;
import com.dbbest.xmlmanager.exceptions.ParsingException;
import com.dbbest.xmlmanager.exceptions.SerializingException;

import java.util.PriorityQueue;
import java.util.logging.Level;

/**
 * A class which contains all commands and operates with them.
 */
public class Invoker {

    private PriorityQueue<Command> priorityQueueCommands = new PriorityQueue();

    /**
     * @param command the command which will be added.
     */
    public void add(Command command) {
        if (command != null) {
            priorityQueueCommands.add(command);
        }
    }

    public void remove(int index) {
        priorityQueueCommands.remove(index);
    }

    /**
     * @throws CommandException the exception thrown during execution of commands.
     */
    public void execute() throws CommandException {
        for (Command command : priorityQueueCommands) {
            executeCommand(command);
        }
    }

    private void executeCommand(Command command) throws CommandException {

        try {
            command.execute();
        } catch (ParsingException e) {
            e.printStackTrace();
        } catch (SerializingException exception) {
            throw new CommandException(Level.SEVERE, "Command " + command.getClass()
                + " could not be executed. The program stoped execution of programs.");
        }
    }
}
