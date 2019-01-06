package com.dbbest.database;

import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.ParsingException;
import com.dbbest.xmlmanager.container.Container;
import com.dbbest.xmlmanager.filemanagers.ParsingManager;
import com.dbbest.xmlmanager.filemanagers.parsers.XmlParser;

/**
 * A class which gets connection properties from a properties file.
 */
public class ConnectionConfiguration {

    private final String defaultConfigFileName = "ConnectionProperties.xml";

    public Container initConfig() throws ParsingException, ContainerException {
        return this.initConfig(null);
    }

    /**
     * A method which initiates connection properties from a files indicated in the parameter
     * or from a default file if nothing was put to the parameter.
     *
     * @param fileName a custom file with connection properties.
     * @return returns the container with connection properties.
     * @throws ParsingException   the exception thrown if the file could not be parsed.
     * @throws ContainerException throws the exception if the container validation found a problem.
     */
    public Container initConfig(String fileName) throws ParsingException, ContainerException {

        ParsingManager parsingManager = new ParsingManager();
        parsingManager.setParser(new XmlParser());

        if (fileName != null && !fileName.trim().isEmpty()) {
            parsingManager.parse(fileName);
        } else {
            parsingManager.parse(defaultConfigFileName);
        }
        return parsingManager.getContainer();
    }

    public String getDefaultConfigFileName() {
        return defaultConfigFileName;
    }
}
