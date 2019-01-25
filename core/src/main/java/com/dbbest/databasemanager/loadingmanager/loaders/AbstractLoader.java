package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractLoader implements Loader {


    protected void executeLazyLoad(Container node, String lazyLoaderQuery) throws SQLException, ContainerException {

        String childName = ((LoaderAnnotation) this.getClass()
            .getAnnotation(LoaderAnnotation.class)).value();

        String attribute = AttributeSingleConstants.NAME_ATTRIBUTE_CONSTANTS.get(childName);

        Connection connection = Context.getInstance().getConnection();

        String schemaName = Context.getInstance().getSchemaName();

        this.executeLazyLoaderQuery(connection, node, lazyLoaderQuery, attribute, childName, schemaName);
    }

    protected void executeDetailedLoad(Connection connection, Container node,
                                       String detailedLoaderQuery, List<String> attributes, String schemaName) throws SQLException {
        executeDetailedLoaderQuery(connection, node, detailedLoaderQuery,
            attributes, schemaName);
    }


    private void executeLazyLoaderQuery(Connection connection, Container node, String lazyLoaderQuery,
                                        String attribute, String childName, String schemaName) throws SQLException, ContainerException {
        String query = String.format(lazyLoaderQuery, attribute, schemaName);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Container childNode = new Container();
            childNode.setName(childName);
            childNode.addAttribute(attribute, resultSet.getString(attribute));
            node.addChild(childNode);
        }
    }

    private void executeDetailedLoaderQuery(Connection connection, Container node, String detailedLoaderQuery,
                                            List<String> attributes, String schemaName) throws SQLException {
        String listOfAttributes = getListOfAttributes(attributes);

        String query = String.format(detailedLoaderQuery, listOfAttributes, schemaName, node.getName());
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
