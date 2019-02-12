package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.mysql.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TableAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TableColumnAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.logging.Level;

/**
 * The class-loader of the table columns.
 */
@LoaderAnnotation(LoaderPrinterName.TABLE_COLUMN)
public class TableColumnLoader extends AbstractLoader {

    public TableColumnLoader(Context context) {
        super(context);
    }

    @Override
    public void lazyLoad(Container columnContainer) throws DatabaseException, ContainerException {
    }

    @Override
    public void detailedLoad(Container columnContainer) throws DatabaseException, ContainerException {
        try {
            String elementName = (String) columnContainer.getAttributes().get(TableColumnAttributes.COLUMN_NAME);
            String tableName = (String) columnContainer.getParent().getParent()
                .getAttributes().get(TableAttributes.TABLE_NAME);
            String detailedLoaderQuery = MySQLQueries.COLUMNDETAILED;
            String listRepresentationOfAttributes = super.listToString(MySQLAttributeFactory.getInstance().getAttributes(this));
            String query = String.format(detailedLoaderQuery, listRepresentationOfAttributes,
                columnContainer.getParent().getParent().getAttributes().get(TableAttributes.TABLE_SCHEMA), tableName, elementName);
            super.executeDetailedLoaderQuery(columnContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container columnContainer) throws DatabaseException, ContainerException {
        this.lazyLoad(columnContainer);
        this.detailedLoad(columnContainer);
    }
}
