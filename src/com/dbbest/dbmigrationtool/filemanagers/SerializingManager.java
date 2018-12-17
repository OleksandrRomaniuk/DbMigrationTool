package com.dbbest.dbmigrationtool.filemanagers;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.exceptions.SerializingException;
import com.dbbest.dbmigrationtool.filemanagers.serializers.Serializer;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

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

