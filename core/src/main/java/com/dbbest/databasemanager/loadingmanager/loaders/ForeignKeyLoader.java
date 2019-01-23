package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.MySqlQueriesConstants;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterTypeEnum;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.FkAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.SchemaAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@LoaderAnnotation(LoaderPrinterTypeEnum.Foreign_Key)
public class ForeignKeyLoader implements Loader {
    private static final Logger logger = Logger.getLogger("Database logger");

    @Override
    public void lazyLoad(Connection connection, Container containerOfCategoryFks) throws DatabaseException, ContainerException {

        if (containerOfCategoryFks.getName() == null || containerOfCategoryFks.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The container of the category Foreign keys does not contain the name.");
        }
        try {
            ResultSet foreignKeys = connection.getMetaData().getImportedKeys(
                (String) containerOfCategoryFks.getParent().getParent().getParent().getAttributes().get(SchemaAttributes.SCHEMA_NAME.getElement()),
                (String) containerOfCategoryFks.getParent().getParent().getParent().getAttributes().get(SchemaAttributes.SCHEMA_NAME.getElement()),
                containerOfCategoryFks.getParent().getName());

            while (foreignKeys.next()) {
                Container fkContainer = new Container();
                fkContainer.setName(foreignKeys.getString("FK_NAME"));
                containerOfCategoryFks.addChild(fkContainer);
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Connection connection, Container fkContainer) throws DatabaseException, ContainerException {
        if (fkContainer.getName() == null || fkContainer.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The foreign key container does not contain the name");
        }
        try {
            String query =
                String.format(MySqlQueriesConstants.ForeignKeyInformationSchemaSelectAll.getQuery(),
                    fkContainer.getParent().getParent().getParent().getParent().getName(),
                    fkContainer.getParent().getParent().getName(),
                    fkContainer.getName());
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                for (FkAttributes attributeKey : FkAttributes.values()) {
                    fkContainer.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Connection connection, Container containerOfCategoryFks) throws DatabaseException, ContainerException {
        lazyLoad(connection, containerOfCategoryFks);
        List<Container> foreignKeys = containerOfCategoryFks.getChildren();
        if (foreignKeys != null && !foreignKeys.isEmpty()) {
            for (Container fk : foreignKeys) {
                detailedLoad(connection, fk);
            }
        }
    }
}
