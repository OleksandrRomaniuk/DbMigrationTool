package com.dbbest.database;

import com.dbbest.exceptions.ConnectionException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.ParsingException;
import com.dbbest.xmlmanager.container.Container;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * The class which gets, adds and removes connection properties.
 */
public class ConnectionPropertiesManager {

    public Map<String, String> getConnectionUrlDriverUserAndPass(String connectionName)
        throws ParsingException, ContainerException, ConnectionException {
        return this.getConnectionUrlDriverUserAndPass(null, connectionName);
    }

    /**
     * @param fileName the custom file with connection properties.
     * @param connectionName the connection name to which properties should be retrieved.
     * @return returns a map which contains driver (String), url (String), login (String) and password (String).
     * @throws ParsingException throws the parsing exception if any was thrown at parsing of the file.
     * @throws ContainerException throws the container exception if any was thrown at validation of the container.
     * @throws ConnectionException throws the connection exception if any problem was encountered
     *      at retrieving connection properties.
     */
    public Map<String, String> getConnectionUrlDriverUserAndPass(String fileName, String connectionName)
        throws ParsingException, ContainerException, ConnectionException {
        Container connectionProperties;
        Map<String, String> connectionPropertiesMap = new HashMap<>();
        if (fileName != null && !fileName.trim().isEmpty()) {
            connectionProperties = new ConnectionConfiguration().initConfig();
        } else {
            connectionProperties = new ConnectionConfiguration().initConfig(fileName);
        }

        if (connectionName != null && !connectionName.trim().isEmpty()) {
            Container targetContainer = getTargetConnectionProperties(connectionProperties, connectionName);
            connectionPropertiesMap.put(ContainerElementsNames.DRIVER.getElement(),
                getDriver(connectionProperties, connectionName));
            connectionPropertiesMap.put(ContainerElementsNames.URL.getElement(), getUrl(connectionProperties, connectionName));
            connectionPropertiesMap.put(ContainerElementsNames.LOGIN.getElement(),
                getLogin(connectionProperties, connectionName));
            connectionPropertiesMap.put(ContainerElementsNames.PASSWORD.getElement(),
                getPassword(connectionProperties, connectionName));
        } else {
            throw new ConnectionException(Level.SEVERE, "The connection name was not entered.");
        }
    }

    private Container getTargetConnectionProperties(Container connectionProperties, String connectionName)
        throws ConnectionException {
        List<Container> targetContainer = connectionProperties.searchInTreeHorizontalSearchInNames(connectionName);
        if (targetContainer.size() > 1) {
            throw new ConnectionException(Level.SEVERE,
                "The connection properties file contains"
                    + "more than one connections with the name " + connectionName);
        } else if (targetContainer.size() == 0) {
            throw new ConnectionException(Level.SEVERE,
                "No such connection was found " + connectionName);
        }
        return targetContainer.get(0);
    }

    private String getDriver(Container connectionProperties, String connectionName) throws ConnectionException {
        return getElement(connectionProperties, connectionName, ContainerElementsNames.DRIVER.getElement());
    }

    private String getUrl(Container connectionProperties, String connectionName) throws ConnectionException {
        return getElement(connectionProperties, connectionName, ContainerElementsNames.URL.getElement());
    }

    private String getLogin(Container connectionProperties, String connectionName) throws ConnectionException {
        return getElement(connectionProperties, connectionName, ContainerElementsNames.LOGIN.getElement());
    }

    private String getPassword(Container connectionProperties, String connectionName) throws ConnectionException {
        return getElement(connectionProperties, connectionName, ContainerElementsNames.PASSWORD.getElement());
    }

    private String getElement(Container connectionProperties, String connectionName, String element) throws ConnectionException {
        List<Container> elementContainer = connectionProperties.searchInTreeHorizontalSearchInNames(element);
        if (elementContainer.size() > 1) {
            throw new ConnectionException(Level.SEVERE,
                "The connection " + connectionName + " contains more than one element.");
        } else if (elementContainer.size() == 0) {
            throw new ConnectionException(Level.SEVERE,
                "The connection " + connectionName + " contains no element.");
        } else if (elementContainer.get(0).getChildren().size() != 1) {
            throw new ConnectionException(Level.SEVERE,
                "Wrong configuration of the connection " + connectionName
                    + ". Please check the conf file (properties of the connection.)");
        } else {
            return ((Container<String>) elementContainer.get(0).getChildren().get(0)).getValue();
        }
    }
}
