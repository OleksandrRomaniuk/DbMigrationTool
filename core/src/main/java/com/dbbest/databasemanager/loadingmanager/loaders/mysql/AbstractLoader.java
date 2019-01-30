package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeListConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.databasemanager.loadingmanager.constants.queries.MySQLQueries;
import com.dbbest.databasemanager.loadingmanager.loaders.Loader;
import com.dbbest.exceptions.ContainerException;
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

    private Connection connection = Context.getInstance().getConnection();
    private String schemaName = Context.getInstance().getSchemaName();
    private String childName = this.getClass()
        .getAnnotation(LoaderAnnotation.class).value();
    private String attribute  = AttributeSingleConstants.getInstance().getNameAttributes().get(childName);
    private List<String> attributes = AttributeListConstants.getInstance().getConstants().get(childName);
    private String listOfAttributes = getListOfAttributes(attributes);
    private String lazyLoaderQuery = MySQLQueries.getInstance().getSqlQueriesLazyLoader().get(childName);
    private String detailedLoaderQuery = MySQLQueries.getInstance().getSqlQueriesDetailLoader().get(childName);

    protected void executeLazyLoadSchemaChildren(Container node) throws SQLException, ContainerException {
        String query = String.format(lazyLoaderQuery, schemaName);
        this.executeLazyLoaderQuery(node, query);
    }

    protected void executeLazyLoadTableChildren(Container node) throws SQLException, ContainerException {
        String tableName = (String) node.getParent().getAttributes().get(AttributeSingleConstants.TABLE_NAME);
        String query = String.format(lazyLoaderQuery, schemaName, tableName);
        this.executeLazyLoaderQuery(node, query);
    }

    protected void executeLazyLoadViewColumns(Container node) throws SQLException, ContainerException {
        String tableName = (String) node.getAttributes().get(AttributeSingleConstants.TABLE_NAME);
        String query = String.format(lazyLoaderQuery, schemaName, tableName);
        this.executeLazyLoaderQuery(node, query);
    }

    protected void executeLazyLoadProcedureFunctionParameters(Container node) throws SQLException, ContainerException {
        String procedureFunctionName = (String) node.getAttributes().get(attribute);
        String query = String.format(lazyLoaderQuery, schemaName, procedureFunctionName);
        this.executeLazyLoaderQuery(node, query);
    }

    protected void executeLazyLoadTableConstraint(Container node) throws SQLException, ContainerException {
        String tableName = (String) node.getParent().getAttributes().get(AttributeSingleConstants.TABLE_NAME);
        String query = String.format(lazyLoaderQuery, schemaName, tableName);

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Container childNode = new Container();
            childNode.setName(childName);
            node.addChild(childNode);

            for (String attribute : AttributeListConstants.getInstance().getListOfConstraintAttributes()) {
                childNode.addAttribute(attribute, resultSet.getString(attribute));
            }
        }
    }

    protected void executeDetailedLoadSchemaChildren(Container node) throws SQLException {
        String elementName = (String) node.getAttributes().get(attribute);
        String query = String.format(detailedLoaderQuery, listOfAttributes, schemaName, elementName);
        this.executeDetailedLoaderQuery(node, query);
    }

    protected void executeDetailedLoadTableChildren(Container node) throws SQLException {
        String elementName = (String) node.getAttributes().get(attribute);
        String tableName = (String) node.getParent().getParent().getAttributes().get(AttributeSingleConstants.TABLE_NAME);
        String query = String.format(detailedLoaderQuery, listOfAttributes, schemaName, tableName, elementName);
        this.executeDetailedLoaderQuery(node, query);
    }

    protected void executeDetailedLoadTableIndexey(Container node) throws SQLException, ContainerException {
        String elementName = (String) node.getAttributes().get(attribute);
        String tableName = (String) node.getParent().getParent().getAttributes().get(AttributeSingleConstants.TABLE_NAME);
        String query = String.format(detailedLoaderQuery, listOfAttributes, schemaName, tableName, elementName);
        this.executeDetailedLoaderIndexQuery(node, query);
    }

    protected void executeDetailedLoadViewColumns(Container node) throws SQLException {
        String elementName = (String) node.getAttributes().get(attribute);
        String tableName = (String) node.getParent().getAttributes().get(AttributeSingleConstants.TABLE_NAME);
        String query = String.format(detailedLoaderQuery, listOfAttributes, schemaName, tableName, elementName);
        this.executeDetailedLoaderQuery(node, query);
    }

    protected void executeDetailedLoadProcedureFunctionParameter(Container node) throws SQLException {
        String elementName = (String) node.getAttributes().get(attribute);
        String procedureFunctionName = (String) node.getParent().getAttributes()
            .get(AttributeSingleConstants.FUNCTION_PROCEDURE_NAME);
        String query = String.format(detailedLoaderQuery, listOfAttributes, schemaName, procedureFunctionName, elementName);
        this.executeDetailedLoaderQuery(node, query);
    }

    protected void executeDetailedLoadTableConstraint(Container node) throws SQLException, ContainerException {
        String elementName = (String) node.getAttributes().get(attribute);
        String tableName = (String) node.getParent().getParent().getAttributes().get(AttributeSingleConstants.TABLE_NAME);
        String query = String.format(detailedLoaderQuery, listOfAttributes, schemaName, tableName, elementName);
        this.executeDetailedLoaderConstraintQuery(node, query);
    }

    protected void executeSchemaDetailedLoad(Container node) throws SQLException {
        String query = String.format(detailedLoaderQuery, listOfAttributes, schemaName);
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

    private void executeDetailedLoaderIndexQuery(Container node, String query) throws SQLException, ContainerException {

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Container index = new Container();
            index.setName(LoaderPrinterName.INDEX);
            for (String attribute : attributes) {
                index.addAttribute(attribute, resultSet.getString(attribute));
            }
            node.addChild(index);
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

    private void executeDetailedLoaderConstraintQuery(Container node, String query) throws SQLException, ContainerException {

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Container childNode = new Container();
            childNode.setName(childName);
            node.addChild(childNode);
            for (String attribute : attributes) {
                childNode.addAttribute(attribute, resultSet.getString(attribute));
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
