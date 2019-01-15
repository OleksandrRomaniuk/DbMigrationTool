package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.constants.attributes.FkAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.SchemaAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.tags.TableCategoriesTagNameCategories;
import com.dbbest.databasemanager.loadingmanager.support.ContainerValidator;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TriggerLoader implements Loaders {
    private static final Logger logger = Logger.getLogger("Database logger");

    @Override
    public void lazyLoad(Connection connection, Container categoryTriggers) throws DatabaseException, ContainerException {
        if (categoryTriggers.getName() == null || categoryTriggers.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The container with Triger category does not contain the name.");
        }
        try {
            ResultSet foreignKeys = connection.getMetaData().getExportedKeys(
                (String) categoryTriggers.getParent().getParent().getParent().getAttributes().get(SchemaAttributes.SCHEMA_CATALOG_NAME.getElement()),
                (String) categoryTriggers.getParent().getParent().getParent().getAttributes().get(SchemaAttributes.SCHEMA_NAME.getElement()),
                categoryTriggers.getParent().getName());

            while (foreignKeys.next()) {
                Container fkContainer = new Container();
                fkContainer.setName(foreignKeys.getString(FkAttributes.CONSTRAINT_NAME.getElement()));
                categoryTriggers.addChild(fkContainer);
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Connection connection, Container tree) throws DatabaseException, ContainerException {

    }

    @Override
    public void fullLoad(Connection connection, Container tree) {

    }
}
