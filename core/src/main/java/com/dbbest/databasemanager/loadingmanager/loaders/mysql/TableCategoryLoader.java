package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.mysql.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.CustomAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@LoaderAnnotation(LoaderPrinterName.TABLES)
public class TableCategoryLoader extends AbstractLoader {
    public TableCategoryLoader(Context context) {
        super(context);
    }

    @Override
    public void lazyLoad(Container tableCategory) throws DatabaseException, ContainerException {
        try {
            String query = String.format(MySQLQueries.TABLELAZY,
                tableCategory.getParent().getAttributes().get(SchemaAttributes.SCHEMA_NAME));
            super.executeLazyLoaderQuery(tableCategory, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of tables.");
        }
    }

    @Override
    public void detailedLoad(Container tableCategory) throws DatabaseException, ContainerException {
        if (tableCategory.hasChildren()) {
            for (Container table : (List<Container>) tableCategory.getChildren()) {
                new TableLoader(super.getContext()).detailedLoad(table);
            }
        }
    }

    @Override
    public void fullLoad(Container tableCategory) throws DatabaseException, ContainerException {
        this.lazyLoad(tableCategory);
        if (tableCategory.hasChildren()) {
            for (Container table : (List<Container>) tableCategory.getChildren()) {
                new TableLoader(super.getContext()).fullLoad(table);
            }
        }
    }
}
