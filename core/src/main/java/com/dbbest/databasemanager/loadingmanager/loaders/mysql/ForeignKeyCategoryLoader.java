package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.CustomAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class ForeignKeyCategoryLoader extends AbstractLoader {
    public ForeignKeyCategoryLoader(Context context) {
        super(context);
    }

    @Override
    public void lazyLoad(Container foreignKeyCategory) throws DatabaseException, ContainerException {
        if (!foreignKeyCategory.hasName()) {
            foreignKeyCategory.setName(LoaderPrinterName.TABLE_FOREIGN_KEYS);
        }
        foreignKeyCategory.addAttribute(CustomAttributes.IS_CATEGORY, true);
        foreignKeyCategory.addAttribute(CustomAttributes.CHILD_TYPE, LoaderPrinterName.FOREIGN_KEY);
        try {
            String tableName = (String) foreignKeyCategory.getParent().getAttributes().get(TableAttributes.TABLE_NAME);
            String schemaName = (String) foreignKeyCategory.getParent()
                .getAttributes().get(TableAttributes.TABLE_SCHEMA);
            String query = String.format(MySQLQueries.FOREIGNKEYLAZY, schemaName, tableName);
            super.executeLazyLoaderQuery(foreignKeyCategory, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container foreignKeyCategory) throws DatabaseException, ContainerException {
        if (foreignKeyCategory.hasChildren()) {
            for (Container foreignKey : (List<Container>) foreignKeyCategory.getChildren()) {
                new ForeignKeyLoader(super.getContext()).detailedLoad(foreignKey);
            }
        }
    }

    @Override
    public void fullLoad(Container foreignKeyCategory) throws DatabaseException, ContainerException {
        this.lazyLoad(foreignKeyCategory);
        if (foreignKeyCategory.hasChildren()) {
            for (Container foreignKey : (List<Container>) foreignKeyCategory.getChildren()) {
                new ForeignKeyLoader(super.getContext()).fullLoad(foreignKey);
            }
        }
    }
}
