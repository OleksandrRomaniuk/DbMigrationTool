package com.dbbest.consolexmlmanager;


import com.dbbest.xmlmanager.container.HorizontalPassageSearchManager;

import java.util.logging.Level;

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
