package com.dbbest.consolexmlmanager;


import com.dbbest.xmlmanager.container.VerticalPassageSearchManager;

import java.util.logging.Level;

/**
 * A class of the command of search in key values of the container with horizontal passage.
 */
public class CommandVerticalSearchByKeyValue extends CommandSearch {

    public CommandVerticalSearchByKeyValue(String text) {
        super(text);
    }

    @Override
    public void execute() {
        VerticalPassageSearchManager verticalPassageSearchManager = new VerticalPassageSearchManager(getContainerToSearchIn());
        setListOfFoundItems(verticalPassageSearchManager.searchInKeyValues(getText()));
        logger.log(Level.INFO, "Vertical passage search has been completed.");
    }
}
