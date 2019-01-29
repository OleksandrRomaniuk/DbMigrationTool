package com.dbbest.databasemanager.connectionbuilder.propertyfilemanager;

import com.dbbest.exceptions.SerializingException;
import com.dbbest.xmlmanager.filemanagers.SerializingManager;

/**
 * The class which writes the connection properties file.
 */
public class ConnectionPropertiesSerializer extends SerializingManager {

    @Override
    public void writeFile(String targetFile) throws SerializingException {

        if (getContainer() == null) {
            throw new SerializingException("The container has not been set. Please set container before evoking the method.");
        } else if (getSerializer() == null) {
            throw new SerializingException("The serializer has not been set. Please set serializer before evoking the method.");
        }
        getSerializer().setContainer(getContainer());
        getSerializer().writeFile(targetFile);
    }
}
