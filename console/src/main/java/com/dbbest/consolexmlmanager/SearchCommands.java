package com.dbbest.consolexmlmanager;

import com.dbbest.xmlmanager.container.Container;
import com.dbbest.xmlmanager.container.VerticalPassageSearchManager;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum SearchCommands implements SearchCommandsInterface {
    VerticalNameSearch("v/n"), VerticalValueSearch("v/v");

    private static final Logger logger = Logger.getLogger("Command logger");

    @Override
    public List<Container> getListOfFoundContainers() {
        return null;
    }

    SearchCommands() {
        switch ("v/n") {
            case "v/n":
                VerticalPassageSearchManager verticalPassageSearchManager = new VerticalPassageSearchManager(getContainerToSearchIn());
                setListOfFoundItems(verticalPassageSearchManager.searchInNames(getText()));
                logger.log(Level.INFO, "Vertical passage search has been completed.");
        }
    }

    void setVerticalNameSearch(){

    }
}
