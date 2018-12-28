package com.dbbest.consolexmlmanager;

import com.dbbest.consolexmlmanager.exceptions.CommandException;
import com.dbbest.xmlmanager.exceptions.ContainerException;
import com.dbbest.xmlmanager.exceptions.ParsingException;
import com.dbbest.xmlmanager.exceptions.SerializingException;

import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class which contains all commands and operates with them.
 */
public class Invoker {

    private static final Logger logger = Logger.getLogger("Command logger");
    private PriorityQueue<Command> priorityQueueCommands = new PriorityQueue();

    /**
     * @param command the command which will be added.
     */
    public void add(Command command) {
        if (command != null) {
            priorityQueueCommands.add(command);
        } else  {
            logger.log(Level.INFO, "Can not add a null command.");
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
        } catch (ParsingException | ContainerException | SerializingException e) {
            throw new CommandException(Level.SEVERE, e.getMessage());
        }
    }
}
