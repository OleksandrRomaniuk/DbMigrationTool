package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.mysql.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.CustomAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.NameAttributes;
import com.dbbest.databasemanager.loadingmanager.loaders.Loader;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * The abstract loader class with realized loaders for all child loaders.
 */
public abstract class AbstractLoader implements Loader {

    private Context context;

    private AbstractLoader() {
    }

    public AbstractLoader(Context context) {
        this.context = context;
        connection = context.getConnection();
    }

    private Connection connection;

    protected void executeLazyLoaderQuery(Container node, String query, String loaderName) throws SQLException, ContainerException {
        //System.out.println(query);
        String attribute = NameAttributes.getNameAttributesMap().get(loaderName);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Container childNode = new Container();
            childNode.setName(loaderName);
            childNode.addAttribute(attribute, resultSet.getString(attribute));
            childNode.addAttribute(CustomAttributes.IS_CATEGORY, false);
            node.addChild(childNode);
        }
    }

    protected void executeDetailedLoaderQuery(Container node, String query) throws SQLException, DatabaseException {
        List<String> attributes = MySQLAttributeFactory.getInstance().getAttributes(this);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            for (String attribute : attributes) {
                node.addAttribute(attribute, resultSet.getString(attribute));
            }
        }
    }

    protected String listToString(List<String> attributes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String attribute : attributes) {
            stringBuilder.append(attribute + ", ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public Connection getConnection() {
        return connection;
    }

    public Context getContext() {
        return context;
    }
}
