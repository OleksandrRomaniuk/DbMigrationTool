package com.dbbest.dbmigrationtool.console;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.exceptions.SerializingException;
import com.dbbest.dbmigrationtool.filemanagers.SerializingManager;
import com.dbbest.dbmigrationtool.filemanagers.serializers.XmlSerializer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandWrite implements Command {

    private static final Logger logger = Logger.getLogger("Command logger");

    private Container containerToWrite;
    private String fileName;

    public CommandWrite(String fileName, Container<String> containerToWrite) {
        this.containerToWrite = containerToWrite;
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
}
