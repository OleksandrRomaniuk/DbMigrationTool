package com.dbbest.consolexmlmanager;

import com.dbbest.exceptions.SerializingException;
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
    private Context context;
    private final int priority;

    /**
     * @param fileName the file path to the file to read.
     * @param priority the priority of the command.
     * @param context the file context.
     */
    public CommandWrite(String fileName, int priority, Context context) {
        this.fileName = fileName;
        this.priority = priority;
        this.context = context;
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
