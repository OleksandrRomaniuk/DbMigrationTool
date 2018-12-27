package com.dbbest.consolexmlmanager;


import com.dbbest.xmlmanager.container.Container;
import com.dbbest.xmlmanager.exceptions.ParsingException;
import com.dbbest.xmlmanager.filemanagers.ParsingManager;
import com.dbbest.xmlmanager.filemanagers.parsers.XmlParser;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class of the command of reading an xml file.
 */
public class CommandRead implements Command {

    private static final Logger logger = Logger.getLogger("Command logger");

    private String fileName;
    private Context context = Context.getInstance();

    public CommandRead(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void execute() throws ParsingException {
        ParsingManager parsingManager = new ParsingManager();
        parsingManager.setParser(new XmlParser());
        parsingManager.parse(fileName);
        logger.log(Level.INFO, "The file has been parsed");
        context.setBuiltContainer(parsingManager.getContainer());
    }
}
