package com.dbbest.consolexmlmanager;

import com.dbbest.xmlmanager.container.HorizontalPassageSearchManager;
import com.dbbest.xmlmanager.container.VerticalPassageSearchManager;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The enumeration of commands of different types.
 */
public enum SearchCommands {
    VerticalSearchInNames("v/n"), VerticalSearchInValues("v/v"), VerticalSearchInAttributes("v/a"),
    HorizontalSearchInNames("h/n"), HorizontalSearchInValues("h/v"), HorizontalSearchInAttributes("h/a");

    private static final Logger logger = Logger.getLogger("Command logger");

    private final String searchCommand;

    public String getSearchCommand() {
        return this.searchCommand;
    }

    SearchCommands(String searchCommand) {
        this.searchCommand = searchCommand;
    }

    void executeCommand(String textToSearch, Context context) {

        switch (this) {
            case VerticalSearchInNames:
                VerticalPassageSearchManager verticalPassageSearchManager =
                    new VerticalPassageSearchManager(context.getBuiltContainer());
                context.setListOfFoundElements(verticalPassageSearchManager.searchInNames(textToSearch));
                logger.log(Level.INFO, "Vertical passage search has been completed.");
                break;
            case VerticalSearchInValues:
                VerticalPassageSearchManager verticalPassageSearchManager1 =
                    new VerticalPassageSearchManager(context.getBuiltContainer());
                context.setListOfFoundElements(verticalPassageSearchManager1.searchInValues(textToSearch));
                logger.log(Level.INFO, "Vertical passage search has been completed.");
                break;
            case HorizontalSearchInValues:
                HorizontalPassageSearchManager horSearchManager = new HorizontalPassageSearchManager(context.getBuiltContainer());
                context.setListOfFoundElements(horSearchManager.searchInValues(textToSearch));
                logger.log(Level.INFO, "Horizontal passage search has been completed.");
                break;
            case HorizontalSearchInNames:
                HorizontalPassageSearchManager horSearchManager1 = new HorizontalPassageSearchManager(context.getBuiltContainer());
                context.setListOfFoundElements(horSearchManager1.searchInNames(textToSearch));
                logger.log(Level.INFO, "Horizontal passage search has been completed.");
                break;
            default:
                logger.log(Level.INFO, "The search command is not of the type vertical/horizontal in names/values.");
        }
    }

    void executeCommand(String attributeKey, String attributeValue, Context context) {

        switch (this) {
            case VerticalSearchInAttributes:
                VerticalPassageSearchManager verticalPassageSearchManager3 =
                    new VerticalPassageSearchManager(context.getBuiltContainer());
                context.setListOfFoundElements(verticalPassageSearchManager3.searchInKeyValues(attributeKey, attributeValue));
                logger.log(Level.INFO, "Vertical passage search has been completed.");
                break;
            case HorizontalSearchInAttributes:
                HorizontalPassageSearchManager horSearchManager2 = new HorizontalPassageSearchManager(context.getBuiltContainer());
                context.setListOfFoundElements(horSearchManager2.searchInKeyValues(attributeKey, attributeValue));
                logger.log(Level.INFO, "Horizontal passage search has been completed.");
                break;
            default:
                logger.log(Level.INFO, "The search command is not of the type vertical/horizontal in attributes.");
        }
    }
}
