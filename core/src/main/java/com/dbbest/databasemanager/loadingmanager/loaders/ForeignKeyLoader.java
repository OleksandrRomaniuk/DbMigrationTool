package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@LoaderAnnotation(LoaderPrinterName.FOREIGN_KEY)
public class ForeignKeyLoader extends AbstractLoader {

    @Override
    public void lazyLoad(Container containerOfCategoryFks) throws DatabaseException, ContainerException {

        /*if (containerOfCategoryFks.getName() == null || containerOfCategoryFks.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The container of the category Foreign keys does not contain the name.");
        }*/
        try {
            super.executeLazyLoadTableChildren(containerOfCategoryFks);
/*
            ResultSet foreignKeys = connection.getMetaData().getImportedKeys(
                (String) containerOfCategoryFks.getParent().getParent().getParent().getAttributes().get(SchemaAttributes.SCHEMA_NAME.getElement()),
                (String) containerOfCategoryFks.getParent().getParent().getParent().getAttributes().get(SchemaAttributes.SCHEMA_NAME.getElement()),
                containerOfCategoryFks.getParent().getName());

            while (foreignKeys.next()) {
                Container fkContainer = new Container();
                fkContainer.setName(foreignKeys.getString("FK_NAME"));
                containerOfCategoryFks.addChild(fkContainer);
            }
*/
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container fkContainer) throws DatabaseException, ContainerException {
        /*if (fkContainer.getName() == null || fkContainer.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The foreign key container does not contain the name");
        }*/
        try {
            super.executeDetailedLoadTableChildren(fkContainer);
            /*String query =
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
            }*/
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container containerOfCategoryFks) throws DatabaseException, ContainerException {
        lazyLoad(containerOfCategoryFks);
        List<Container> foreignKeys = containerOfCategoryFks.getChildren();
        if (foreignKeys != null && !foreignKeys.isEmpty()) {
            for (Container fk : foreignKeys) {
                detailedLoad(fk);
            }
        }
    }
}
