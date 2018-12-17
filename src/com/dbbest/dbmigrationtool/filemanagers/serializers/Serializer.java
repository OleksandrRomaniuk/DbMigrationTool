package com.dbbest.dbmigrationtool.filemanagers.serializers;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.exceptions.SerializingException;

public interface Serializer<V1, V2> {
    void setContainer(Container<V1, V2> container);

    void writeFile(String targetFileUrl) throws SerializingException;
}
