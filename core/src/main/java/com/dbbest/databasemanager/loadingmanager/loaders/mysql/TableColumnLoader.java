package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@LoaderAnnotation(LoaderPrinterName.TABLE_COLUMN)
public class TableColumnLoader extends AbstractLoader {

    @Override
    public void lazyLoad(Container categoryColumnsContainer) throws DatabaseException, ContainerException {
        try {
            /*if (categoryColumnsContainer.getName() == null || categoryColumnsContainer.getName().trim().isEmpty()) {
                throw new ContainerException(Level.SEVERE, "The container with the column category does not contain the name.");
            }*/

            super.executeLazyLoadTableChildren(categoryColumnsContainer);
            /*
            ResultSet resultSet = connection.getMetaData().getColumns(categoryColumnsContainer.getParent().getParent().getParent().getName(),
                null, categoryColumnsContainer.getParent().getName(), null);

            while (resultSet.next()) {
                Container column = new Container();
                column.setName(resultSet.getString(ColumnAttributes.COLUMN_NAME.getElement()));
                categoryColumnsContainer.addChild(column);
            }*/
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of columns.");
        }
    }

    @Override
    public void detailedLoad(Container columnContainer) throws DatabaseException, ContainerException {

        /*if (columnContainer.getName() == null || columnContainer.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The container of column does not contain the name.");
        }*/

        try {
            super.executeDetailedLoadTableChildren(columnContainer);

            /*String query =
                String.format(MySqlQueriesConstants.ColumnInformationSchemaSelectAll.getQuery(),
                    columnContainer.getParent().getParent().getParent().getParent().getName(),
                    columnContainer.getParent().getParent().getName(),
                    columnContainer.getName());

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                for (ColumnAttributes attributeKey : ColumnAttributes.values()) {
                    columnContainer.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
                }
            }*/
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container categoryColumnsContainer) throws DatabaseException, ContainerException {
        lazyLoad(categoryColumnsContainer);
        List<Container> columns = categoryColumnsContainer.getChildren();
        if (columns != null && !columns.isEmpty()) {
            for (Container column : columns) {
                detailedLoad(column);
            }
        }
    }
}