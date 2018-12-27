package com.dbbest.consolexmlmanager;


import com.dbbest.xmlmanager.container.HorizontalPassageSearchManager;
import com.dbbest.xmlmanager.container.VerticalPassageSearchManager;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An abstract class of the command of search in the container.
 */
public class CommandSearch implements Command {

    protected static final Logger logger = Logger.getLogger("Command logger");
    private String textToSearch;
    private String searchType;
    private Context context = Context.getInstance();

    public CommandSearch(String text, String searchType) {
        this.textToSearch = text;
        this.searchType = searchType;
    }

    public void execute() {

    };

    private void horizontalSearcnInNames() {
        HorizontalPassageSearchManager horSearchManager = new HorizontalPassageSearchManager(context.getBuiltContainer());
        context.setListOfFoundElements(horSearchManager.searchInNames(textToSearch));
        logger.log(Level.INFO, "Horizontal passage search has been completed.");
    }

    private void horizontalSearcnInValues() {
        HorizontalPassageSearchManager horSearchManager = new HorizontalPassageSearchManager(context.getBuiltContainer());
        context.setListOfFoundElements(horSearchManager.searchInValues(textToSearch));
        logger.log(Level.INFO, "Horizontal passage search has been completed.");
    }

    private void horizontalSearcnInAttributes() {
        HorizontalPassageSearchManager horSearchManager = new HorizontalPassageSearchManager(context.getBuiltContainer());
        context.setListOfFoundElements(horSearchManager.searchInKeyValues(textToSearch));
        logger.log(Level.INFO, "Horizontal passage search has been completed.");
    }

    private void  verticalSearcnInNames() {
        VerticalPassageSearchManager verticalPassageSearchManager = new VerticalPassageSearchManager(context.getBuiltContainer());
        context.setListOfFoundElements(verticalPassageSearchManager.searchInNames(textToSearch));
        logger.log(Level.INFO, "Vertical passage search has been completed.");
    }

    private void verticalSearcnInValues() {
        VerticalPassageSearchManager verticalPassageSearchManager = new VerticalPassageSearchManager(context.getBuiltContainer());
        context.setListOfFoundElements(verticalPassageSearchManager.searchInValues(textToSearch));
        logger.log(Level.INFO, "Vertical passage search has been completed.");
    }

    private void verticalSearcnInAttributes() {
        VerticalPassageSearchManager verticalPassageSearchManager = new VerticalPassageSearchManager(context.getBuiltContainer());
        context.setListOfFoundElements(verticalPassageSearchManager.searchInKeyValues(textToSearch));
        logger.log(Level.INFO, "Vertical passage search has been completed.");
    }
}
