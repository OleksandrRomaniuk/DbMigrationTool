package com.dbbest.dbmigrationtool.console;

import com.dbbest.dbmigrationtool.containers.Container;

import java.util.logging.Logger;

public abstract class CommandSearch implements Command {

    private static final Logger logger = Logger.getLogger("Command logger");
    private Container<String> containerToSearchIn;
    private String text;

    public CommandSearch(String text, Container<String> containerToSearchIn) {
        this.text = text;
        this.containerToSearchIn = containerToSearchIn;
    }

    @Override
    public abstract void execute();
}
