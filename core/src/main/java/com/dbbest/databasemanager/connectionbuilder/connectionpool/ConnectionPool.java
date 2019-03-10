package com.dbbest.databasemanager.connectionbuilder.connectionpool;

import com.dbbest.exceptions.DatabaseException;
import java.util.logging.Level;
import javax.sql.DataSource;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * A class which sets up and holds a pool of connections.
 */
public class ConnectionPool {

    private GenericObjectPool connectionPool = null;

    /**
     * @param jdbcDriver the driver of the connection to the DB.
     * @param dbUrl      the url of the DB.
     * @param userName   the loggin used to connect to the DB.
     * @param password   the password used to connect to the DB.
     * @return returns the DataSource object.
     * @throws DatabaseException the connection thrown if connection failed.
     */
    @SuppressWarnings("unused")
    public DataSource setUpPool(String jdbcDriver, String dbUrl, String userName, String password) throws DatabaseException {

        try {
            Class.forName(jdbcDriver);
            connectionPool = new GenericObjectPool();
            connectionPool.setMaxActive(100000000);
            connectionPool.setMaxIdle(100000000);
            connectionPool.setMaxWait(10000000);
            ConnectionFactory cf = new DriverManagerConnectionFactory(dbUrl, userName, password);
            PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, connectionPool, null, null, false, true);
            return new PoolingDataSource(connectionPool);

        } catch (ClassNotFoundException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    public DataSource getDataSource() {
        return new PoolingDataSource(connectionPool);
    }

    public GenericObjectPool getConnectionPool() {
        return connectionPool;
    }
}

