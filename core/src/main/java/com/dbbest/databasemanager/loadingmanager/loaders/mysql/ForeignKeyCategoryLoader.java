package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.mysql.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.ConstraintAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.CustomAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * The class-loader of the foreign key category.
 */
@LoaderAnnotation(LoaderPrinterName.TABLE_FOREIGN_KEYS)
public class ForeignKeyCategoryLoader extends AbstractLoader {
    public ForeignKeyCategoryLoader(Context context) {
        super(context);
    }

    @Override
    public void lazyLoad(Container foreignKeyCategory) throws DatabaseException, ContainerException {
        try {
            String tableName = (String) foreignKeyCategory.getParent().getAttributes().get(TableAttributes.TABLE_NAME);
            String schemaName = (String) foreignKeyCategory.getParent().getParent().getParent()
                .getAttributes().get(SchemaAttributes.SCHEMA_NAME);
            String query = String.format(MySQLQueries.FOREIGNKEYLAZY, schemaName, tableName);
            super.executeLazyLoaderQuery(foreignKeyCategory, query, LoaderPrinterName.FOREIGN_KEY,
                ConstraintAttributes.CONSTRAINT_NAME);
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
        if (foreignKeyCategory.hasChildren()) {
            for (Container foreignKey : (List<Container>) foreignKeyCategory.getChildren()) {
                new ForeignKeyLoader(super.getContext()).fullLoad(foreignKey);
            }
        }
    }
}
