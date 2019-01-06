package com.dbbest.database;

import com.dbbest.exceptions.ConnectionException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.ParsingException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.sql.DataSource;

/**
 * The class which returns the needed connection from the pool or creates a new pool if the required pool does not exist.
 */
public class SimpleConnectionBuilder implements ConnectionBuilder {

    private HashMap<String, DataSource> connectionPools = new HashMap();

    public Connection getConnection(String connectionName) throws ConnectionException, ContainerException, ParsingException {
        return getConnection(connectionName, null);
    }

    /**
     * @param connectionName the name of the connection to retrieve.
     * @param fileName the custom file with connection properties.
     * @return returns the connection searched.
     * @throws ConnectionException trows the connection exception if connection did not succeed.
     * @throws ParsingException throws the parsing exception at parsing the connection properties file.
     * @throws ContainerException throws the container exception if a problem was encountered at validation of the container.
     */
    public Connection getConnection(String connectionName, String fileName)
        throws ConnectionException, ParsingException, ContainerException {
        try {
            if (exists(connectionName)) {
                return connectionPools.get(connectionName).getConnection();
            } else {
                buildConnectionPoolAndGetConnection(connectionName, fileName);
                return connectionPools.get(connectionName).getConnection();
            }
        } catch (SQLException e) {
            throw new ConnectionException(Level.SEVERE, e);
        }


    }

    private boolean exists(String connectionName) {
        for (Map.Entry<String, DataSource> entry : connectionPools.entrySet()) {
            if (entry.getKey().equals(connectionName)) {
                return true;
            }
        }
        return false;
    }

    private void buildConnectionPoolAndGetConnection(String connectionName, String fileName)
        throws ParsingException, ContainerException, ConnectionException {
        ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration();
        Container connectionProperties = connectionConfiguration.initConfig();
        List<Container> foundElements = searchConnectionProperty(connectionProperties, connectionName);
        if (!foundElements.isEmpty() && foundElements.size() == 1) {
            Container connectionProperty = foundElements.get(0);
            Map<String, String> properties = new ConnectionPropertiesManager()
                .getConnectionUrlDriverUserAndPass(fileName, connectionName);
            new ConnectionPool().setUpPool(ContainerElementsNames.DRIVER.getElement(),
                ContainerElementsNames.URL.getElement(), ContainerElementsNames.LOGIN.getElement(),
                ContainerElementsNames.PASSWORD.getElement());
        }
    }

    private List<Container> searchConnectionProperty(Container connectionProperties, String connectionName) {
        List<Container> foundElements = new ArrayList();
        List<Container> children = connectionProperties.getChildren();
        for (Container child: children) {
            if (child.getName().trim().toLowerCase().equals(connectionName.trim().toLowerCase())) {
                foundElements.add(child);
            }
        }
    }
}
