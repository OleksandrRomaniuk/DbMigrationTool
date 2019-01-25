package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.MySqlQueriesConstants;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.ColumnAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@LoaderAnnotation(LoaderPrinterName.VIEW_COLUMN)
public class ViewColumnLoader implements Loader {

    @Override
    public void lazyLoad(Connection connection, Container viewContainer) throws DatabaseException, ContainerException {
        try {
            if (viewContainer.getName() == null || viewContainer.getName().trim().isEmpty()) {
                throw new ContainerException(Level.SEVERE, "The view container does not contain the name.");
            }

            ResultSet resultSet = connection.getMetaData().getColumns(viewContainer.getParent().getParent().getName(),
                null, viewContainer.getName(), null);

            while (resultSet.next()) {
                Container column = new Container();
                column.setName(resultSet.getString(ColumnAttributes.COLUMN_NAME.getElement()));
                viewContainer.addChild(column);
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of columns.");
        }
    }

    @Override
    public void detailedLoad(Connection connection, Container viewColumnContainer) throws DatabaseException, ContainerException {
        if (viewColumnContainer.getName() == null || viewColumnContainer.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The container of column does not contain the name.");
        }

        try {
            String query =
                String.format(MySqlQueriesConstants.ColumnInformationSchemaSelectAll.getQuery(),
                    viewColumnContainer.getParent().getParent().getParent().getName(),
                    viewColumnContainer.getParent().getName(),
                    viewColumnContainer.getName());

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                for (ColumnAttributes attributeKey : ColumnAttributes.values()) {
                    viewColumnContainer.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Connection connection, Container viewContainer) throws DatabaseException, ContainerException {
        lazyLoad(connection, viewContainer);
        List<Container> columns = viewContainer.getChildren();
        if (columns != null && !columns.isEmpty()) {
            for (Container column : columns) {
                detailedLoad(connection, column);
            }
        }
    }
}
