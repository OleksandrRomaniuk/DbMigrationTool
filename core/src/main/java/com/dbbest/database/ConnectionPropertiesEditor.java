package com.dbbest.database;

import com.dbbest.exceptions.ConnectionException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.ParsingException;
import com.dbbest.exceptions.SerializingException;
import com.dbbest.xmlmanager.container.Container;
import com.dbbest.xmlmanager.filemanagers.SerializingManager;
import com.dbbest.xmlmanager.filemanagers.serializers.XmlSerializer;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class which adds or removes connection properties.
 */
public class ConnectionPropertiesEditor {
    private static final Logger logger = Logger.getLogger("Connection logger");

    public boolean remove(String connectionName)
        throws ParsingException, ContainerException, ConnectionException, SerializingException {
        return remove(null, connectionName);
    }

    /**
     * @param fileName the custom connection properties file.
     * @param connectionName the connection name.
     * @return returns true if the element was successfully removed.
     * @throws ParsingException throws the parsing exception at parsing the connection properties file.
     * @throws ContainerException throws the container exception at removing the element.
     * @throws ConnectionException throws the connection exception at validation transaction.
     * @throws SerializingException throws the parsing exception at writing the connection properties file.
     */
    public boolean remove(String fileName, String connectionName)
        throws ParsingException, ContainerException, ConnectionException, SerializingException {
        Container connectionProperties;
        if (fileName != null && !fileName.trim().isEmpty()) {
            connectionProperties = new ConnectionConfiguration().initConfig(fileName);
            return removeElement(connectionProperties, connectionName, fileName, null);
        } else {
            connectionProperties = new ConnectionConfiguration().initConfig();
            return removeElement(connectionProperties, connectionName, fileName,
                new ConnectionConfiguration().getDefaultConfigFileName());
        }
    }

    private boolean removeElement(Container connectionProperties, String connectionName,
                                  String fileName, String defaultConfigFileName)
        throws ConnectionException, SerializingException {

        List<Container> foundProperties = connectionProperties.searchInTreeHorizontalSearchInNames(connectionName);
        if (foundProperties.size() > 1) {
            throw new ConnectionException("There are several connections with the same name "
                + connectionName + " in the connection properties file.");
        } else if (foundProperties.size() < 1) {
            logger.log(Level.SEVERE, "The connection with the name " + connectionName + " was not found.");
            return false;
        } else {
            connectionProperties.getChildren().remove(foundProperties.get(0));
            SerializingManager serializingManager = new SerializingManager();
            serializingManager.setSerializer(new XmlSerializer());
            serializingManager.setContainer(connectionProperties);
            serializingManager.writeFile((fileName != null && !fileName.trim().isEmpty()) ? fileName : defaultConfigFileName);
            return true;
        }
    }

    public boolean add(String connectionName, String url, String driver, String login, String password)
        throws ParsingException, ContainerException, ConnectionException, SerializingException {
        return this.add(null, connectionName, url, driver, login, password);
    }

    /**
     * @param fileName the custom connection properties file.
     * @param connectionName the connection name.
     * @param url the url of the connection.
     * @param driver the driver of the connection.
     * @param login the login name of the connection.
     * @param password the password of the connection.
     * @return returns true if the element was successfully added.
     * @throws ParsingException throws the parsing exception at parsing the connection properties file.
     * @throws ContainerException throws the container exception at removing the element.
     * @throws ConnectionException throws the connection exception at validation transaction.
     * @throws SerializingException throws the parsing exception at writing the connection properties file.
     */
    public boolean add(String fileName, String connectionName, String url, String driver, String login, String password)
        throws ParsingException, ContainerException, ConnectionException, SerializingException {
        Container connectionProperties;
        if (fileName != null && !fileName.trim().isEmpty()) {
            connectionProperties = new ConnectionConfiguration().initConfig(fileName);
            return addElement(connectionProperties, connectionName, url, driver, login, password, fileName, null);
        } else {
            connectionProperties = new ConnectionConfiguration().initConfig();
            return addElement(connectionProperties, connectionName, url, driver, login, password, fileName,
                new ConnectionConfiguration().getDefaultConfigFileName());
        }
    }

    private boolean addElement(Container connectionProperties, String connectionName, String url, String driver,
                               String login, String password, String fileName, String defaultConfigFileName)
                                throws ConnectionException, SerializingException, ContainerException {
        List<Container> foundProperties = connectionProperties.searchInTreeHorizontalSearchInNames(connectionName);
        if (foundProperties.size() > 0) {
            throw new ConnectionException("A connection with the name "
                + connectionName + " already exists.");
        } else {
            Container connectionPropertiesContainer = buildNewConnectionPropertiesContainer(connectionName,
                url, driver, login, password);
            connectionProperties.addChild(connectionPropertiesContainer);
            SerializingManager serializingManager = new SerializingManager();
            serializingManager.setSerializer(new XmlSerializer());
            serializingManager.setContainer(connectionProperties);
            serializingManager.writeFile((fileName != null && !fileName.trim().isEmpty()) ? fileName : defaultConfigFileName);
            return true;
        }
    }

    private Container buildNewConnectionPropertiesContainer(String connectionName, String url, String driver,
                                                            String login, String password) throws ContainerException {
        Container connectionPropertiesContainer = new Container();
        connectionPropertiesContainer.setName(connectionName);
        connectionPropertiesContainer.addChild(getElementContainer(driver, ContainerElementsNames.DRIVER.getElement()));
        connectionPropertiesContainer.addChild(getElementContainer(url, ContainerElementsNames.URL.getElement()));
        connectionPropertiesContainer.addChild(getElementContainer(login, ContainerElementsNames.LOGIN.getElement()));
        connectionPropertiesContainer.addChild(getElementContainer(password, ContainerElementsNames.PASSWORD.getElement()));
        return connectionPropertiesContainer;
    }

    private Container getElementContainer(String elementValue, String elementName) throws ContainerException {
        Container elementContainer = new Container();
        elementContainer.setName(elementName);
        Container child = new Container();
        child.setValue(elementValue);
        elementContainer.addChild(child);
        return elementContainer;
    }
}
