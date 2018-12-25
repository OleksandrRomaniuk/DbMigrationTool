package com.dbbest.dbmigrationtool.console;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.containers.VerticalPassageSearchManager;

import java.util.logging.Level;

public class CommandVerticalSearchByKeyValue extends CommandSearch {

    public CommandVerticalSearchByKeyValue(String text, Container<String> containerToSearchIn) {
        super(text, containerToSearchIn);
    }

    @Override
    public void execute() {
        VerticalPassageSearchManager verticalPassageSearchManager = new VerticalPassageSearchManager(getContainerToSearchIn());
        setListOfFoundItems(verticalPassageSearchManager.searchInKeyValues(getText()));
        logger.log(Level.INFO, "Vertical passage search has been completed.");
    }
}
