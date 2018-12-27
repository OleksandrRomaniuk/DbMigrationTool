package com.dbbest.consolexmlmanager;

import com.dbbest.xmlmanager.container.HorizontalPassageSearchManager;
import java.util.logging.Level;

/**
 * A class which carries out search in values with horizontal passage.
 */
public class CommandHorizontalSearchByValue extends CommandSearch {


    public CommandHorizontalSearchByValue(String text) {
        super(text);
    }

    @Override
    public void execute() {
        HorizontalPassageSearchManager horSearchManager = new HorizontalPassageSearchManager(getContainerToSearchIn());
        setListOfFoundItems(horSearchManager.searchInValues(getText()));
        logger.log(Level.INFO, "Horizontal passage search has been completed.");
    }
}
