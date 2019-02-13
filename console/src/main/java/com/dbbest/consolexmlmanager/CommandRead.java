package com.dbbest.consolexmlmanager;

import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.ParsingException;
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
    private Context context;
    private final int priority;

    /**
     * @param fileName the file path to the file to read.
     * @param priority the priority of the command.
     * @param context the file context.
     */
    public CommandRead(String fileName, int priority, Context context) {
        this.fileName = fileName;
        this.priority = priority;
        this.context = context;
    }

    @Override
    public void execute() throws ParsingException, ContainerException {
        ParsingManager parsingManager = new ParsingManager();
        parsingManager.setParser(new XmlParser());
        parsingManager.parse(fileName);
        logger.log(Level.INFO, "The file has been parsed");
        context.setBuiltContainer(parsingManager.getContainer());
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
