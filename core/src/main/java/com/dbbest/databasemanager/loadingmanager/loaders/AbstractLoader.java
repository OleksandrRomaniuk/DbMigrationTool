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
    private String listOfAttributes;
    private List<String> attributes;
    private String lazyLoaderQuery;
    private String detailedLoaderQuery;

    public AbstractLoader() {
        connection = Context.getInstance().getConnection();
        schemaName = Context.getInstance().getSchemaName();
        childName = ((LoaderAnnotation) this.getClass()
            .getAnnotation(LoaderAnnotation.class)).value();
        attribute = AttributeSingleConstants.NAME_ATTRIBUTE_CONSTANTS.get(childName);
        attributes = AttributeListConstants.getInstance().getMapOfConstants()
            .get(childName);
        listOfAttributes = getListOfAttributes(attributes);
        lazyLoaderQuery = MySQLQueries.getInstance().getSqlQueriesLazyLoader().get(childName);
        detailedLoaderQuery = MySQLQueries.getInstance().getSqlQueriesDetailLoader().get(childName);
    }

    protected void executeSchemaDetailedLoad(Container node) throws SQLException {
        String query = String.format(detailedLoaderQuery, listOfAttributes, schemaName);
        this.executeDetailedLoaderQuery(node, query);
    }

    protected void executeLazyLoadSchemaChildren(Container node) throws SQLException, ContainerException {
        String query = String.format(lazyLoaderQuery, schemaName);
        this.executeLazyLoaderQuery(node, query);
    }

    protected void executeDetailedLoadSchemaChildren(Container node) throws SQLException {
        String elementName = (String) node.getAttributes().get(attribute);
        String query = String.format(detailedLoaderQuery, listOfAttributes, schemaName, elementName);
        this.executeDetailedLoaderQuery(node, query);
    }

    protected void executeLazyLoadTableChildren(Container node) throws SQLException, ContainerException {
        String tableName = (String) node.getParent().getAttributes().get(attribute);
        String query = String.format(lazyLoaderQuery, schemaName, tableName);
        this.executeLazyLoaderQuery(node, query);
    }

    protected void executeDetailedLoadTableChildren(Container node) throws SQLException {
        String elementName = (String) node.getAttributes().get(attribute);
        String tableName = (String) node.getParent().getParent().getAttributes().get(attribute);
        String query = String.format(detailedLoaderQuery, listOfAttributes, schemaName, tableName, elementName);
        this.executeDetailedLoaderQuery(node, query);
    }

    private void executeLazyLoaderQuery(Container node, String query) throws SQLException, ContainerException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Container childNode = new Container();
            childNode.setName(childName);
            childNode.addAttribute(attribute, resultSet.getString(attribute));
            node.addChild(childNode);
        }
    }

    private void executeDetailedLoaderQuery(Container node, String query) throws SQLException {
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
