package com.dbbest.dbmigrationtool.console;

import com.dbbest.dbmigrationtool.exceptions.ParsingException;
import com.dbbest.dbmigrationtool.exceptions.SerializingException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Invoker {

    private static final Logger logger = Logger.getLogger("Command logger");

    private Command command;
    private List<Command> commandList = new ArrayList();

    public void add(Command command) {
        commandList.add(command);
    }

    public void remove(int index) {
        commandList.remove(index);
    }

    public void execute() {
        for (Command command : commandList) {
            if (executeCommand(command)) {
                continue;
            } else {
                break;
            }
        }

    }

    private boolean executeCommand(Command command) {
        try {
            command.execute();
        } catch (ParsingException | SerializingException exception) {
            logger.log(Level.SEVERE, "Command " + command.getCommandLine() + " could not be executed. The program stoped execution of programs.");
            return false;
        }
        return true;
    }
}
