package com.dbbest.databasemanager.connectionbuilder.connectionpool;

import com.dbbest.databasemanager.connectionbuilder.connectionpool.propertyfilemanager.ConnectionPropertiesManager;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.exceptions.ParsingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.cj.util.StringUtils;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;


/**
 * The class which returns the needed connection from the pool or creates a new pool if the required pool does not exist.
 */
public class SimpleConnectionBuilder implements ConnectionBuilder {
    private static HashMap<String, GenericObjectPool> connectionPools = new HashMap();

    public Connection getConnection(String connectionName) throws DatabaseException, ContainerException, ParsingException {
        return getConnection(connectionName, null);
    }

    /**
     * @param connectionName the name of the connection to retrieve.
     * @param fileName       the custom file with connection properties.
     * @return returns the connection searched.
     * @throws DatabaseException  trows the connection exception if connection did not succeed.
     * @throws ParsingException   throws the parsing exception at parsing the connection properties file.
     * @throws ContainerException throws the container exception if a problem was encountered at validation of the container.
     */
    public Connection getConnection(String connectionName, String fileName)
        throws DatabaseException, ParsingException, ContainerException {
        try {
            if (exists(connectionName)) {
                return new PoolingDataSource(connectionPools.get(connectionName)).getConnection();
            } else {
                buildConnectionPoolAndGetConnection(connectionName, fileName);
                return new PoolingDataSource(connectionPools.get(connectionName)).getConnection();
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    /**
     * @param dbType the type of database to connect to.
     * @param dbName the name of database to connect to.
     * @param userName the username to connect to the database.
     * @param password the password to connect to the database.
     * @return returns the connection searched.
     * @throws DatabaseException trows the connection exception if connection did not succeed.
     */
    public Connection getConnection(String dbType, String dbName, String userName, String password) throws DatabaseException {

        try {
            String driver = DriverUrlManager.getInstance().getDriver(dbType);
            String url = DriverUrlManager.getInstance().getUrl(dbType, dbName);
            if(StringUtils.isNullOrEmpty(driver)) {
                throw new DatabaseException(Level.SEVERE, "Can not find the respective driver for the db type: " + dbName);
            } else if(StringUtils.isNullOrEmpty(url)) {
                throw new DatabaseException(Level.SEVERE, "Can not find the respective url for the db type: " + dbName);
            }
            Class.forName(driver);
            return DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }

        /*try {
            if (exists(dbName)) {
                return new PoolingDataSource(connectionPools.get(dbName)).getConnection();
            } else {
                buildConnectionPoolAndGetConnection(dbType, dbName, userName, password);
                return new PoolingDataSource(connectionPools.get(dbName)).getConnection();
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }*/
    }

    private boolean exists(String connectionName) {
        for (Map.Entry<String, GenericObjectPool> entry : connectionPools.entrySet()) {
            if (entry.getKey().equals(connectionName)) {
                return true;
            }
        }
        return false;
    }

    private void buildConnectionPoolAndGetConnection(String dbName, String fileName)
        throws ParsingException, ContainerException, DatabaseException {
        Map<String, String> properties = new ConnectionPropertiesManager()
            .getConnectionUrlDriverUserAndPass(fileName, dbName);
        ConnectionPool connectionPool = new ConnectionPool();
        connectionPool.setUpPool(
            properties.get(ContainerElementsNames.DRIVER.getElement()),
            properties.get(ContainerElementsNames.URL.getElement()),
            properties.get(ContainerElementsNames.LOGIN.getElement()),
            properties.get(ContainerElementsNames.PASSWORD.getElement()));
        connectionPools.put(dbName, connectionPool.getConnectionPool());
    }

    private void buildConnectionPoolAndGetConnection(String dbType, String dbName, String userName, String password)
        throws DatabaseException {
        ConnectionPool connectionPool = new ConnectionPool();
        connectionPool.setUpPool(
            DriverUrlManager.getInstance().getDriver(dbType),
            DriverUrlManager.getInstance().getUrl(dbType, dbName),
            userName,
            password);
        connectionPools.put(dbName, connectionPool.getConnectionPool());
    }

    public HashMap<String, GenericObjectPool> getConnectionPools() {
        return connectionPools;
    }
}
