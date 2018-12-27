package com.dbbest.consolexmlmanager;

import com.dbbest.xmlmanager.container.Container;
import com.dbbest.xmlmanager.container.HorizontalPassageSearchManager;
import java.util.logging.Level;

/**
 * A class which carries out search in key values with horizontal passage.
 */
public class CommandHorizontalSearchByKeyValue extends CommandSearch {

    public CommandHorizontalSearchByKeyValue(String text) {
        super(text);
    }

    @Override
    public void execute() {
        HorizontalPassageSearchManager horSearchManager = new HorizontalPassageSearchManager(getContainerToSearchIn());
        setListOfFoundItems(horSearchManager.searchInKeyValues(getText()));
        logger.log(Level.INFO, "Horizontal passage search has been completed.");
    }
}