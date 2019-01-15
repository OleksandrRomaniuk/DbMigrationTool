package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.constants.MySqlQueriesConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.FkAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.SchemaAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.tags.TableCategoriesTagNameCategories;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ForeignKeyLoader implements Loaders {
    private static final Logger logger = Logger.getLogger("Database logger");

    @Override
    public void lazyLoad(Connection connection, Container containerOfCategoryFks) throws DatabaseException, ContainerException {

        if (containerOfCategoryFks.getName() == null || containerOfCategoryFks.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The container of the category Foreign keys does not contain the name.");
        }
        try {
            ResultSet foreignKeys = connection.getMetaData().getExportedKeys(
                (String) containerOfCategoryFks.getParent().getParent().getParent().getAttributes().get(SchemaAttributes.SCHEMA_CATALOG_NAME.getElement()),
                (String) containerOfCategoryFks.getParent().getParent().getParent().getAttributes().get(SchemaAttributes.SCHEMA_NAME.getElement()),
                containerOfCategoryFks.getParent().getName());

            while (foreignKeys.next()) {
                Container fkContainer = new Container();
                fkContainer.setName(foreignKeys.getString(FkAttributes.CONSTRAINT_NAME.getElement()));
                containerOfCategoryFks.getChildByName(TableCategoriesTagNameCategories.Indexes.getElement()).addChild(fkContainer);
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Connection connection, Container fkContainer) throws DatabaseException, ContainerException {
        try {
            if (fkContainer.getName() == null || fkContainer.getName().trim().isEmpty()) {
                throw new ContainerException(Level.SEVERE, "The foreign key container does not contain the name");
            }
            String query =
                String.format(MySqlQueriesConstants.ForeignKeyInformationSchemaSelectAll.getQuery(),
                    fkContainer.getParent().getParent().getParent().getParent().getName(),
                    fkContainer.getParent().getParent().getName(),
                    fkContainer.getName());
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            for (FkAttributes attributeKey : FkAttributes.values()) {
                fkContainer.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Connection connection, Container container) {

    }
}
