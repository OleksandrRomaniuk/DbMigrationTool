package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.annotations.Loader;
import com.dbbest.databasemanager.loadingmanager.constants.LoaderTypeEnum;
import com.dbbest.databasemanager.loadingmanager.constants.MySqlQueriesConstants;
import com.dbbest.databasemanager.loadingmanager.constants.TagNamesConstants;
import com.dbbest.databasemanager.loadingmanager.constants.enumsattributes.SchemaAttributes;
import com.dbbest.exceptions.ConnectionException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

@Loader(LoaderTypeEnum.SchemaLoader)
public class SchemaLoader implements Loaders {

    @Override
    public void lazyLoad(Connection connection, Container container) throws ConnectionException, ContainerException {
        try {
            container.setName(connection.getCatalog());

            for (TagNamesConstants schemaChildName : TagNamesConstants.values()) {
                Container child = new Container();
                child.setName(schemaChildName.getElement());
                container.addChild(child);
            }

        } catch (SQLException e) {
            throw new ConnectionException(Level.SEVERE, e, "Can not get the schema name.");
        }
    }

    @Override
    public void detailedLoad(Connection connection, Container container) throws ConnectionException, ContainerException {


        try {
            if (container.getName() == null || container.getName().trim().isEmpty()) {
                throw new ConnectionException(Level.SEVERE, "The container does not contain the name.");
            }
            String informationSchemataSelectAllQuery =
                String.format(MySqlQueriesConstants.INFORMATIONSCHEMASELECTALL.getQuery(), connection.getCatalog());
            PreparedStatement preparedStatement = connection.prepareStatement(informationSchemataSelectAllQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            for (SchemaAttributes attributeKey : SchemaAttributes.values()) {
                container.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
            }

        } catch (SQLException e) {
            throw new ConnectionException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Connection connection, Container container) {

    }
}
