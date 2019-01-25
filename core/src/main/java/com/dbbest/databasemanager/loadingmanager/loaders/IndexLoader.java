package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@LoaderAnnotation(LoaderPrinterName.INDEX)
public class IndexLoader extends AbstractLoader {

    @Override
    public void lazyLoad(Container indexCategory) throws DatabaseException, ContainerException {

        /*if (indexCategory.getName() == null || indexCategory.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The container with Index category does not contain the name.");
        }*/
        try {

            super.executeLazyLoadTableChildren(indexCategory);
            /*String query =
                String.format(MySqlQueriesConstants.IndexesSelectAllName.getQuery(),
                    indexCategory.getParent().getParent().getParent().getName(),
                    indexCategory.getParent().getName());
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Container index = new Container();
                index.setName(resultSet.getString(IndexAttributes.INDEX_NAME.getElement()));
                indexCategory.addChild(index);
            }*/
            /*
            ResultSet indexes = connection.getMetaData().getIndexInfo(null,
                indexCategory.getParent().getParent().getParent().getName(),
                indexCategory.getParent().getName(), false, false);

            while (indexes.next()) {
                Container index = new Container();
                index.setName(indexes.getString(IndexAttributes.INDEX_NAME.getElement()));
                indexCategory.addChild(index);
            }
            */
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container indexContainer) throws DatabaseException, ContainerException {
        try {
            super.executeDetailedLoadTableChildren(indexContainer);

            /*if (indexContainer.getName() == null || indexContainer.getName().trim().isEmpty()) {
                throw new ContainerException(Level.SEVERE, "The index container does not contain the name.");
            }
            String query =
                String.format(MySqlQueriesConstants.IndexInformationSchemaSelectAll.getQuery(),
                    indexContainer.getParent().getParent().getParent().getParent().getName(),
                    indexContainer.getParent().getParent().getName(),
                    indexContainer.getName());
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Container index = new Container();
                index.setName(resultSet.getString(IndexAttributes.INDEX_NAME.getElement()));
                for (IndexAttributes attributeKey : IndexAttributes.values()) {
                    index.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
                }
                indexContainer.addChild(index);
            }*/
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container indexCategory) throws DatabaseException, ContainerException {
        lazyLoad(indexCategory);
        List<Container> indexes = indexCategory.getChildren();
        if (indexes != null && !indexes.isEmpty()) {
            for (Container index : indexes) {
                detailedLoad(index);
            }
        }
    }
}
