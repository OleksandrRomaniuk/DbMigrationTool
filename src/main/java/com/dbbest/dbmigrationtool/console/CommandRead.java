package com.dbbest.dbmigrationtool.console;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.exceptions.ParsingException;
import com.dbbest.dbmigrationtool.filemanagers.ParsingManager;
import com.dbbest.dbmigrationtool.filemanagers.parsers.XmlParser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandRead implements Command {

    private static final Logger logger = Logger.getLogger("Command logger");

    private String fileName;
    private Container builtContainer;
    private String commandLine;

    public CommandRead(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void execute() throws ParsingException {
        ParsingManager parsingManager = new ParsingManager();
        parsingManager.setParser(new XmlParser());
        parsingManager.parse(fileName);
        logger.log(Level.INFO, "The file has been parsed");
        builtContainer = parsingManager.getContainer();
    }

    public Container getBuiltContainer() {
        return builtContainer;
    }

    @Override
    public String getCommandLine() {
        return commandLine;
    }

    @Override
    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }
}
