package com.dbbest.dbmigrationtool.console;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.containers.VerticalPassageSearchManager;

import java.util.logging.Level;

public class CommandVerticalSearchByValue extends CommandSearch {

    public CommandVerticalSearchByValue(String text) {
        super(text);
    }

    @Override
    public void execute() {
        VerticalPassageSearchManager verticalPassageSearchManager = new VerticalPassageSearchManager(getContainerToSearchIn());
        setListOfFoundItems(verticalPassageSearchManager.searchInValues(getText()));
        logger.log(Level.INFO, "Vertical passage search has been completed.");
    }
}
