package com.dbbest.services;

import com.dbbest.databasemanager.connectionbuilder.connectionpool.DriverUrlManager;
import com.dbbest.databasemanager.connectionbuilder.connectionpool.SimpleConnectionBuilder;
import com.dbbest.exceptions.DatabaseException;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = Logger.getLogger("UI logger");

    @Override
    public boolean checkLoginAndPassword(String login, String password, String dbName, String dbType) {

        if(DriverUrlManager.getInstance().getDriver(dbType) == null || DriverUrlManager.getInstance().getDriver(dbType).equals("")) {
           return false;
        }

        try  {
            String queryString = " SELECT user FROM  mysql.user;";
            Connection connection = new SimpleConnectionBuilder().getConnection(dbType, dbName, login, password);
            PreparedStatement ps = connection.prepareStatement(queryString);
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                if (results.getString(1).equals(login)) {
                    return true;
                }
            }
        } catch (DatabaseException | SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return false;
    }
}
