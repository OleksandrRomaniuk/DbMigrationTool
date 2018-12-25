package com.dbbest.dbmigrationtool.console;

import com.dbbest.dbmigrationtool.containers.Container;

import java.util.List;
import java.util.logging.Logger;

/**
 * An abstract class of the command of search in the container.
 */
public abstract class CommandSearch implements Command {

    protected static final Logger logger = Logger.getLogger("Command logger");
    private Container<String> containerToSearchIn;
    private String text;
    private List<Container> listOfFoundItems;
    private String commandLine;

    public CommandSearch(String text) {
        this.text = text;
    }

    public abstract void execute();

    public Container<String> getContainerToSearchIn() {
        return containerToSearchIn;
    }

    public String getText() {
        return text;
    }

    public List<Container> getListOfFoundItems() {
        return listOfFoundItems;
    }

    public void setListOfFoundItems(List<Container> listOfFoundItems) {
        this.listOfFoundItems = listOfFoundItems;
    }

    @Override
    public String getCommandLine() {
        return commandLine;
    }

    @Override
    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }

    public void setContainerToSearchIn(Container<String> containerToSearchIn) {
        this.containerToSearchIn = containerToSearchIn;
    }
}
