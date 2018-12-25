package com.dbbest.dbmigrationtool.console;

import java.util.Map;

public class CommandManager {
    private Map<String, Command> commands;
    private String[] commandLine;

    public CommandManager(String[] commandLine) {
        this.commandLine = commandLine;
    }

    public void execute() {

    }
}
