package com.dbbest.database;

import com.dbbest.exceptions.ConnectionException;
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

    private static GenericObjectPool gPool = null;

    /**
     * @param jdbcDriver the driver of the connection to the DB.
     * @param dbUrl      the url of the DB.
     * @param userName   the loggin used to connect to the DB.
     * @param password   the password used to connect to the DB.
     * @return returns the DataSource object.
     * @throws ConnectionException the connection thrown if connection failed.
     */
    @SuppressWarnings("unused")
    public DataSource setUpPool(String jdbcDriver, String dbUrl, String userName, String password) throws ConnectionException {

        try {
            Class.forName(jdbcDriver);
            gPool = new GenericObjectPool();
            gPool.setMaxActive(5);
            gPool.setMaxIdle(5);
            gPool.setMaxWait(10000);
            ConnectionFactory cf = new DriverManagerConnectionFactory(dbUrl, userName, password);
            PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, gPool, null, null, false, true);
            return new PoolingDataSource(gPool);

        } catch (ClassNotFoundException e) {
            throw new ConnectionException(Level.SEVERE, e);
        }
    }

    public DataSource getDataSource() {
        return new PoolingDataSource(gPool);
    }

    public GenericObjectPool getConnectionPool() {
        return gPool;
    }
}

