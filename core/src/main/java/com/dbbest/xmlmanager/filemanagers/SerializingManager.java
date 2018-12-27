package com.dbbest.xmlmanager.filemanagers;

import com.dbbest.xmlmanager.container.Container;
import com.dbbest.xmlmanager.exceptions.SerializingException;
import com.dbbest.xmlmanager.filemanagers.serializers.Serializer;

import java.io.File;

/**
 * A manager which performs reading a container and writing a file with a predefined serializer.
 * @param <V> the type of the value of containers.
 */
public class SerializingManager<V> {
    private Container<V> container;
    private Serializer<V> serializer;

    public void setContainer(Container<V> container) {

        this.container = container;
    }

    public void setSerializer(Serializer<V> serializer) {

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
            throw new SerializingException("The container has not been set. Please set container before evoking the method.");
        } else if (serializer == null) {
            throw new SerializingException("The serializer has not been set. Please set serializer before evoking the method.");
        } else if (new File(targetFile).exists()) {
            throw new SerializingException("Such file already exists. Please enter another name.");
        }
        serializer.setContainer(container);
        serializer.writeFile(targetFile);
    }
}


