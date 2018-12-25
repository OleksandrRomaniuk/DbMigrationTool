package com.dbbest.dbmigrationtool.console;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.containers.HorizontalPassageSearchManager;

import java.util.logging.Level;

public class CommandHorizontalSearchByKeyValue extends CommandSearch {

    public CommandHorizontalSearchByKeyValue(String text, Container<String> containerToSearchIn) {
        super(text, containerToSearchIn);
    }

    @Override
    public void execute() {
        HorizontalPassageSearchManager horSearchManager = new HorizontalPassageSearchManager(getContainerToSearchIn());
        setListOfFoundItems(horSearchManager.searchInKeyValues(getText()));
        logger.log(Level.INFO, "Horizontal passage search has been completed.");
    }
}
