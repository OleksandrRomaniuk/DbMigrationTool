package com.dbbest.dbmigrationtool.console;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.exceptions.SerializingException;
import com.dbbest.dbmigrationtool.filemanagers.SerializingManager;
import com.dbbest.dbmigrationtool.filemanagers.serializers.XmlSerializer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class of the command of writing an xml file from the container.
 */
public class CommandWrite implements Command {

    private static final Logger logger = Logger.getLogger("Command logger");
    private Container containerToWrite;
    private String fileName;
    private String commandLine;

    public CommandWrite(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void execute() throws SerializingException {
        SerializingManager<String> serializingManager = new SerializingManager();
        serializingManager.setContainer(containerToWrite);
        serializingManager.setSerializer(new XmlSerializer());
        serializingManager.writeFile(fileName);
        logger.log(Level.INFO, "The file has been written");
    }

    @Override
    public String getCommandLine() {
        return commandLine;
    }

    @Override
    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }

    public void setContainerToWrite(Container containerToWrite) {
        this.containerToWrite = containerToWrite;
    }
}