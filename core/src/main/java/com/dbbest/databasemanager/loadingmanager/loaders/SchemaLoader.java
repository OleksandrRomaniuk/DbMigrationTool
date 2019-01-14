package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.annotations.Loader;
import com.dbbest.databasemanager.loadingmanager.constants.LoaderTypeEnum;
import com.dbbest.databasemanager.loadingmanager.constants.MySqlQueriesConstants;
import com.dbbest.databasemanager.loadingmanager.constants.tags.SchemaCategoriesTagNameConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.SchemaAttributes;
import com.dbbest.exceptions.DatabaseException;
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
    public void lazyLoad(Connection connection, Container container) throws DatabaseException, ContainerException {
        try {
            container.setName(connection.getCatalog());

            for (SchemaCategoriesTagNameConstants schemaChildName : SchemaCategoriesTagNameConstants.values()) {
                Container child = new Container();
                child.setName(schemaChildName.getElement());
                container.addChild(child);
            }

        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the schema name.");
        }
    }

    @Override
    public void detailedLoad(Connection connection, Container tree) throws DatabaseException, ContainerException {


        try {
            if (new ContainerValidator().ifContainerContainsSchemaName(tree)) {
                String informationSchemataSelectAllQuery =
                    String.format(MySqlQueriesConstants.INFORMATIONSCHEMASELECTALL.getQuery(), connection.getCatalog());
                PreparedStatement preparedStatement = connection.prepareStatement(informationSchemataSelectAllQuery);
                ResultSet schemaAttributes = preparedStatement.executeQuery();

                for (SchemaAttributes attributeKey : SchemaAttributes.values()) {
                    tree.addAttribute(attributeKey.getElement(), schemaAttributes.getString(attributeKey.getElement()));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Connection connection, Container container) {

    }
}
