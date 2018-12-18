package com.dbbest.dbmigrationtool.filemanagers;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.exceptions.SerializingException;
import com.dbbest.dbmigrationtool.filemanagers.serializers.Serializer;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A manager which performs reading a container and writing a file with a predefined serializer.
 * @param <V1> the type of the value of containers.
 * @param <V2> the type of the value of attributes.
 */
public class SerializingManager<V1, V2> {
    private static final Logger logger = Logger.getLogger("Serializing logger");
    private Container<V1, V2> container;
    private Serializer<V1, V2> serializer;

    public void setContainer(Container<V1, V2> container) {

        this.container = container;
    }

    public void setSerializer(Serializer<V1, V2> serializer) {

        this.serializer = serializer;
    }

    /**
     * A method which triggers the process of serialization.
     * @param targetFile the url of a file to write.
     * @throws SerializingException will be thrown
     if any checked exceptions are encountered during the serialization process.
     */
    public void writeFile(String targetFile) throws SerializingException {

        if (container == null) {
            String message = "The container has not been set. Please set container before evoking the method.";
            logger.log(Level.SEVERE, message);
            throw new SerializingException(message);
        } else if (serializer == null) {
            String message = "The serializer has not been set. Please set serializer before evoking the method.";
            logger.log(Level.SEVERE, message);
            throw new SerializingException(message);
        } else if (new File(targetFile).exists()) {
            String message = "Such file already exists. Please enter another name.";
            logger.log(Level.SEVERE, message);
            throw new SerializingException(message);
        }
        serializer.setContainer(container);
        serializer.writeFile(targetFile);
    }
}


