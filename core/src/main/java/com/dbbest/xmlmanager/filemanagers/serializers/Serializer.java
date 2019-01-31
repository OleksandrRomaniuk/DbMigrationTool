package com.dbbest.xmlmanager.filemanagers.serializers;

import com.dbbest.exceptions.SerializingException;
import com.dbbest.xmlmanager.container.Container;

/**
 * An interface which is used to writes a tree kept in a container to a file.
 * @param <V1> the type of the value of container which should be written to a file
 */
public interface Serializer<V1> {
    void setContainer(Container<V1> container);

    void writeFile(String targetFileUrl) throws SerializingException;
}

