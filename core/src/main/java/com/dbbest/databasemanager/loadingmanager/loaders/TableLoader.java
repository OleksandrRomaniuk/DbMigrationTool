package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeListConstants;
import com.dbbest.databasemanager.loadingmanager.constants.queries.MySQLQueries;
import com.dbbest.databasemanager.loadingmanager.constants.tags.TableCategoriesTagNameCategories;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@LoaderAnnotation(LoaderPrinterName.TABLE)
public class TableLoader extends AbstractLoader {

    @Override
    public void lazyLoad(Container categoryTablesContainer) throws DatabaseException, ContainerException {
        try {
            super.executeLazyLoad(categoryTablesContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of tables.");
        }
    }

    @Override
    public void detailedLoad(Container tableContainer) throws DatabaseException {
        try {
            super.executeDetailedLoad(tableContainer);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container categoryTablesContainer) throws DatabaseException, ContainerException {
        lazyLoad(categoryTablesContainer);
        List<Container> tables = categoryTablesContainer.getChildren();
        if (tables != null && !tables.isEmpty()) {
            for (Container table : tables) {
                detailedLoad(table);
                new TableColumnLoader().fullLoad(table.getChildByName(TableCategoriesTagNameCategories.Columns.getElement()));
                new IndexLoader().fullLoad(table.getChildByName(TableCategoriesTagNameCategories.Indexes.getElement()));
                new ForeignKeyLoader().fullLoad(table.getChildByName(TableCategoriesTagNameCategories.Foreign_Keys.getElement()));
                new TriggerLoader().fullLoad(table.getChildByName(TableCategoriesTagNameCategories.Triggers.getElement()));
                new ConstraintsLoader().fullLoad(table.getChildByName(TableCategoriesTagNameCategories.ConstraintCategory.getElement()));
            }
        }
    }
}
