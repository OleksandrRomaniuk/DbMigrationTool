package com.dbbest.dbmigrationtool.console;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.containers.HorizontalPassageSearchManager;
import com.dbbest.dbmigrationtool.exceptions.ParsingException;
import com.dbbest.dbmigrationtool.exceptions.SerializingException;

import java.util.logging.Level;

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
