package com.dbbest.consolexmlmanager;


import com.dbbest.xmlmanager.exceptions.SerializingException;
import com.dbbest.xmlmanager.filemanagers.SerializingManager;
import com.dbbest.xmlmanager.filemanagers.serializers.XmlSerializer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class of the command of writing an xml file from the container.
 */
public class CommandWrite implements Command {

    private static final Logger logger = Logger.getLogger("Command logger");
    private String fileName;
    private Context context = Context.getInstance();
    private final int priority;

    public CommandWrite(String fileName, int priority) {
        this.fileName = fileName;
        this.priority = priority;
    }

    @Override
    public void execute() throws SerializingException {
        SerializingManager<String> serializingManager = new SerializingManager();
        serializingManager.setContainer(context.getBuiltContainer());
        serializingManager.setSerializer(new XmlSerializer());
        serializingManager.writeFile(fileName);
        logger.log(Level.INFO, "The file has been written");
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
