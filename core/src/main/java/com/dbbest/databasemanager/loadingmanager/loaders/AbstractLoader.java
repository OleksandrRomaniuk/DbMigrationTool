package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeListConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.constants.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractLoader implements Loader {

    private Connection connection;
    private String schemaName;
    private String childName;
    private String attribute;

    public AbstractLoader() {
        connection = Context.getInstance().getConnection();
        schemaName = Context.getInstance().getSchemaName();
        childName = ((LoaderAnnotation) this.getClass()
            .getAnnotation(LoaderAnnotation.class)).value();
        attribute = AttributeSingleConstants.NAME_ATTRIBUTE_CONSTANTS.get(childName);
    }

    protected void executeLazyLoad(Container node) throws SQLException, ContainerException {

        String lazyLoaderQuery = MySQLQueries.SQL_QUERIES_LAZY_LOADER.get(childName);

        this.executeLazyLoaderQuery(connection, node, lazyLoaderQuery, attribute, childName, schemaName);
    }

    protected void executeDetailedLoad(Container node) throws SQLException {

        List<String> attributes = AttributeListConstants.getInstance().getMapOfConstants()
            .get(childName);

        String detailedLoaderQuery = MySQLQueries.SQL_QUERIES_DETAIL_LOADER.get(childName);

        this.executeDetailedLoaderQuery(connection, node, detailedLoaderQuery, attributes, schemaName);
    }


    private void executeLazyLoaderQuery(Connection connection, Container node, String lazyLoaderQuery,
                                        String attribute, String childName, String schemaName) throws SQLException, ContainerException {
        String query = String.format(lazyLoaderQuery, schemaName);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Container childNode = new Container();
            childNode.setName(childName);
            childNode.addAttribute(attribute, resultSet.getString(attribute));
            node.addChild(childNode);
        }
    }

    private void executeDetailedLoaderQuery(Connection connection, Container node, String detailedLoaderQuery,
                                            List<String> attributes, String schemaName) throws SQLException {
        String listOfAttributes = getListOfAttributes(attributes);
        String elementName = (String) node.getAttributes().get(attribute);
        String query = String.format(detailedLoaderQuery, listOfAttributes, schemaName, elementName);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            for (String attribute : attributes) {
                node.addAttribute(attribute, resultSet.getString(attribute));
            }
        }
    }

    private String getListOfAttributes(List<String> attributes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String attribute : attributes) {
            stringBuilder.append(attribute + ", ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
