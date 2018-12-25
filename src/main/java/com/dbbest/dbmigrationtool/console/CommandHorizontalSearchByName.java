package com.dbbest.dbmigrationtool.console;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.containers.HorizontalPassageSearchManager;
import com.dbbest.dbmigrationtool.containers.VerticalPassageSearchManager;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class which carries out search in names with horizontal passage.
 */
public class CommandHorizontalSearchByName extends CommandSearch {

    public CommandHorizontalSearchByName(String text) {
        super(text);
    }

    @Override
    public void execute() {
        HorizontalPassageSearchManager horSearchManager = new HorizontalPassageSearchManager(getContainerToSearchIn());
        setListOfFoundItems(horSearchManager.searchInNames(getText()));
        logger.log(Level.INFO, "Horizontal passage search has been completed.");
    }
}
