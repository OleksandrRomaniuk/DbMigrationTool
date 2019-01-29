package com.dbbest.databasemanager.connectionbuilder.propertyfilemanager;

import com.dbbest.databasemanager.connectionbuilder.ConnectionConfiguration;
import com.dbbest.databasemanager.connectionbuilder.ContainerElementsNames;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.exceptions.ParsingException;
import com.dbbest.xmlmanager.container.Container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * The class which gets, adds and removes connection properties.
 */
public class ConnectionPropertiesManager {

    public Map<String, String> getConnectionUrlDriverUserAndPass(String connectionName)
        throws ParsingException, ContainerException, DatabaseException {
        return this.getConnectionUrlDriverUserAndPass(null, connectionName);
    }

    /**
     * @param fileName       the custom file with connection properties.
     * @param connectionName the connection name to which properties should be retrieved.
     * @return returns a map which contains driver (String), url (String), login (String) and password (String).
     * @throws ParsingException    throws the parsing exception if any was thrown at parsing of the file.
     * @throws ContainerException  throws the container exception if any was thrown at validation of the container.
     * @throws DatabaseException throws the connection exception if any problem was encountered
     *                             at retrieving connection properties.
     */
    public Map<String, String> getConnectionUrlDriverUserAndPass(String fileName, String connectionName)
        throws ParsingException, ContainerException, DatabaseException {
        Container connectionProperties;
        Map<String, String> connectionPropertiesMap = new HashMap();
        if (fileName != null && !fileName.trim().isEmpty()) {
            connectionProperties = new ConnectionConfiguration().initConfig(fileName);

        } else {
            connectionProperties = new ConnectionConfiguration().initConfig();
        }

        if (connectionName != null && !connectionName.trim().isEmpty()) {
            Container targetContainer = getTargetConnectionProperties(connectionProperties, connectionName);
            connectionPropertiesMap.put(ContainerElementsNames.DRIVER.getElement(),
                getDriver(targetContainer, connectionName));
            connectionPropertiesMap.put(ContainerElementsNames.URL.getElement(),
                getUrl(targetContainer, connectionName));
            connectionPropertiesMap.put(ContainerElementsNames.LOGIN.getElement(),
                getLogin(targetContainer, connectionName));
            connectionPropertiesMap.put(ContainerElementsNames.PASSWORD.getElement(),
                getPassword(targetContainer, connectionName));
        } else {
            throw new DatabaseException(Level.SEVERE, "The connection name was not entered.");
        }
        return connectionPropertiesMap;
    }

    private Container getTargetConnectionProperties(Container connectionProperties, String connectionName)
        throws DatabaseException {
        List<Container> foundElements = searchconnectionProperties(connectionProperties, connectionName);
        if (foundElements.size() > 1) {
            throw new DatabaseException(Level.SEVERE,
                "The connection properties file contains "
                    + "more than one connections with the name " + connectionName);
        } else if (foundElements.size() == 0) {
            throw new DatabaseException(Level.SEVERE,
                "No such connection was found " + connectionName);
        }
        return foundElements.get(0);
    }

    private String getDriver(Container connectionProperties, String connectionName) throws DatabaseException {
        return getElement(connectionProperties, connectionName, ContainerElementsNames.DRIVER.getElement().trim());
    }

    private String getUrl(Container connectionProperties, String connectionName) throws DatabaseException {
        return getElement(connectionProperties, connectionName, ContainerElementsNames.URL.getElement()).trim();
    }

    private String getLogin(Container connectionProperties, String connectionName) throws DatabaseException {
        return getElement(connectionProperties, connectionName, ContainerElementsNames.LOGIN.getElement().trim());
    }

    private String getPassword(Container connectionProperties, String connectionName) throws DatabaseException {
        return getElement(connectionProperties, connectionName, ContainerElementsNames.PASSWORD.getElement().trim());
    }

    private String getElement(Container connectionProperties, String connectionName, String element) throws DatabaseException {
        List<Container> foundElements = searchconnectionProperties(connectionProperties, element);
        if (foundElements.size() > 1) {
            throw new DatabaseException(Level.SEVERE,
                "The connection " + connectionName + " contains more than one element " + element);
        } else if (foundElements.size() == 0) {
            throw new DatabaseException(Level.SEVERE,
                "The connection " + connectionName + " contains no element " + element);
        } else if (foundElements.get(0).getChildren().size() != 1) {
            throw new DatabaseException(Level.SEVERE,
                "Wrong configuration of the connection " + connectionName
                    + ". Please check the conf file (properties of the connection.)");
        } else {
            return ((Container<String>) foundElements.get(0).getChildren().get(0)).getValue();
        }
    }

    private List<Container> searchconnectionProperties(Container connectionProperties, String element) {

        List<Container> children = connectionProperties.getChildren();
        List<Container> foundElements = new ArrayList();

        for (Container child : children) {
            if (child.getName().trim().toLowerCase().equals(element.trim().toLowerCase())) {
                foundElements.add(child);
            }
        }
        return foundElements;
    }
}
