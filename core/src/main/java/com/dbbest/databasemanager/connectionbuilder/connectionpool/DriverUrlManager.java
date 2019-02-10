package com.dbbest.databasemanager.connectionbuilder.connectionpool;

import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoadersPrinterDatabaseTypes;

import java.util.HashMap;
import java.util.Map;

/**
 * The class which builds the url and driver identifier.
 */
public final class DriverUrlManager {
    private static DriverUrlManager instance;
    private Map<String, String> drivers = new HashMap();
    private Map<String, String> urls = new HashMap();

    private DriverUrlManager() {}

    /**
     * @return returns the instance of the class.
     */
    public static DriverUrlManager getInstance() {
        if (instance == null) {
            instance = new DriverUrlManager();
        }
        instance.initializeDrivers();
        instance.initializeUrls();
        return instance;
    }

    public String getDriver(String dbType) {
        return drivers.get(dbType);
    }

    public String getUrl(String dbType, String userName) {
        return urls.get(dbType) + userName;
    }

    private void initializeDrivers() {
        drivers.put(LoadersPrinterDatabaseTypes.MYSQL, "com.mysql.cj.jdbc.Driver");
    }

    private void initializeUrls() {
        urls.put(LoadersPrinterDatabaseTypes.MYSQL, "jdbc:mysql://localhost:3306/");
    }
}
