package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.constants.MySqlQueriesConstants;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.SchemaAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.TriggerAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.ViewAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.tags.TableCategoriesTagNameCategories;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class ViewLoader implements Loaders {
    @Override
    public void lazyLoad(Connection connection, Container triggerCategoryContainer) throws DatabaseException, ContainerException {
        if (triggerCategoryContainer.getName() == null || triggerCategoryContainer.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The container of the category Views does not contain the name.");
        }
        try {
            ResultSet views = connection.getMetaData()
                .getTables((String) triggerCategoryContainer.getParent().getAttributes().get(SchemaAttributes.SCHEMA_CATALOG_NAME.getElement()),
                    null, "%", new String[] {"VIEW"});
            while (views.next()) {
                Container view = new Container();
                view.setName(views.getString(ViewAttributes.TABLE_NAME.getElement());
                triggerCategoryContainer.addChild(view);
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Connection connection, Container viewContainer) throws DatabaseException, ContainerException {
        if (viewContainer.getName() == null || viewContainer.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The view container does not contain the name.");
        }

        try {
            String query =
                String.format(MySqlQueriesConstants.ViewInformationSchemaSelectAll.getQuery(),
                    viewContainer.getParent().getParent().getName(),
                    viewContainer.getName());
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            for (ViewAttributes attributeKey : ViewAttributes.values()) {
                viewContainer.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Connection connection, Container tree) {

    }
}
