package com.dbbest.consolexmlmanager;

import com.dbbest.consolexmlmanager.exceptions.CommandException;

/**
 * The enumeration of commands.
 */
public enum Commands {
    READ("-read", 1),
    WRITE("-write", 3),
    SEARCH("-search", 2);

    private final String stringCommand;
    private int priority;

    Commands(String stringCommand, int priority) {
        this.stringCommand = stringCommand;
        this.priority = priority;
    }

    Command getCommand(String singleCommand) throws CommandException {
        String[] singleCommandWords = singleCommand.split("\\s");

        switch (this) {
            case READ:
                return new CommandRead(singleCommandWords[1].trim(), this.getPriority());
            case WRITE:
                return new CommandWrite(singleCommandWords[1].trim(), this.getPriority());
            case SEARCH:
                if (singleCommandWords.length == 3) {
                    return new CommandSearch(singleCommandWords[1].trim(),
                        singleCommandWords[2].trim(), this.getPriority());
                } else if (singleCommandWords.length == 4) {
                    return new CommandSearch(singleCommandWords[1].trim(),
                        singleCommandWords[2].trim(),singleCommandWords[3].trim(), this.getPriority());
                } else {
                    throw new CommandException("The command was not recognized: " + singleCommand);
                }
            default:
                throw new CommandException("The command was not recognized."  + singleCommand);
        }
    }

    public int getPriority() {
        return priority;
    }
}
