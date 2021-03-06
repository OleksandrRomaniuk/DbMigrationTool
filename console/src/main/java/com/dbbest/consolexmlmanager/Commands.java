package com.dbbest.consolexmlmanager;

import com.dbbest.consolexmlmanager.exceptions.CommandException;

/**
 * The enumeration of commands.
 */
public enum Commands {
    READ("-read", 1),
    WRITE("-write", 4),
    SEARCH("-search", 3),
    LOAD("-load", 2),
    PRINT("-print", 5);


    private final String stringCommand;
    private int priority;

    Commands(String stringCommand, int priority) {
        this.stringCommand = stringCommand;
        this.priority = priority;
    }

    Command getCommand(String singleCommand, Context context) throws CommandException {
        String[] singleCommandWords = singleCommand.split("\\s");

        switch (this) {
            case READ:
                return new CommandRead(singleCommandWords[1].trim(), this.getPriority(), context);
            case WRITE:
                return new CommandWrite(singleCommandWords[1].trim(), this.getPriority(), context);
            case SEARCH:
                if (singleCommandWords.length == 3) {
                    return new CommandSearch(singleCommandWords[1].trim(),
                        singleCommandWords[2].trim(), this.getPriority(), context);
                } else if (singleCommandWords.length == 4) {
                    return new CommandSearch(singleCommandWords[1].trim(),
                        singleCommandWords[2].trim(),singleCommandWords[3].trim(), this.getPriority(), context);
                } else {
                    throw new CommandException("The command was not recognized: " + singleCommand);
                }
            case PRINT:
                return new CommandPrint(singleCommandWords[1].trim(), singleCommandWords[2].trim(), this.getPriority(), context);
            case LOAD:
                return new CommandLoad(singleCommandWords[1].trim(), singleCommandWords[2].trim(), singleCommandWords[3].trim(),
                    singleCommandWords[4].trim(), singleCommandWords[5].trim(), singleCommandWords[6].trim(),
                    this.getPriority(), context);
            default:
                throw new CommandException("The command was not recognized."  + singleCommand);
        }
    }

    public int getPriority() {
        return priority;
    }
}
