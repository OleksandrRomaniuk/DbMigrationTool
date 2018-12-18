package com.dbbest.dbmigrationtool.filemanagers.serializers;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.exceptions.SerializingException;

/**
 * An interface which is used to writes a tree kept in a container to a file.
 * @param <V1> the type of the value of container which should be written to a file
 * @param <V2> the type of the value of attributes of a container
 */
public interface Serializer<V1, V2> {
    void setContainer(Container<V1, V2> container);

    void writeFile(String targetFileUrl) throws SerializingException;
}
