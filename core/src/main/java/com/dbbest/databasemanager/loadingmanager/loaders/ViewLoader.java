package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@LoaderAnnotation(LoaderPrinterName.VIEW)
public class ViewLoader extends AbstractLoader {
    @Override
    public void lazyLoad(Container viewCategoryContainer) throws DatabaseException, ContainerException {
        /*if (viewCategoryContainer.getName() == null || viewCategoryContainer.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The container of the category Views does not contain the name.");
        }*/
        try {
            super.executeLazyLoadSchemaChildren(viewCategoryContainer);
            /*ResultSet views = connection.getMetaData()
                .getTables((String) viewCategoryContainer.getParent().getAttributes().get(SchemaAttributes.SCHEMA_NAME.getElement()),
                    null, "%", new String[] {"VIEW"});
            while (views.next()) {
                Container view = new Container();
                view.setName(views.getString(ViewAttributes.TABLE_NAME.getElement()));
                viewCategoryContainer.addChild(view);
            }*/
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container viewContainer) throws DatabaseException, ContainerException {
        /*if (viewContainer.getName() == null || viewContainer.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The view container does not contain the name.");
        }*/

        try {
            super.executeDetailedLoadSchemaChildren(viewContainer);
            /*String query =
                String.format(MySqlQueriesConstants.ViewInformationSchemaSelectAll.getQuery(),
                    viewContainer.getParent().getParent().getName(),
                    viewContainer.getName());
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                for (ViewAttributes attributeKey : ViewAttributes.values()) {
                    viewContainer.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
                }
            }*/
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container viewCategoryContainer) throws DatabaseException, ContainerException {
        lazyLoad(viewCategoryContainer);
        List<Container> views = viewCategoryContainer.getChildren();
        if (views != null && !views.isEmpty()) {
            for (Container view : views) {
                detailedLoad(view);
                new ViewColumnLoader().fullLoad(view);
            }
        }
    }
}
