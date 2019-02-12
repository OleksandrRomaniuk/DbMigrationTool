package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.consolexmlmanager.Context;
import com.dbbest.databasemanager.loadingmanager.annotations.mysql.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.TableColumnAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.ViewAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.logging.Level;

/**
 * The class-loader of the mysql views.
 */
@LoaderAnnotation(LoaderPrinterName.VIEW_COLUMN)
public class ViewColumnLoader extends AbstractLoader {

    public ViewColumnLoader(Context context) {
        super(context);
    }

    @Override
    public void lazyLoad(Container viewColumnContainer) throws DatabaseException, ContainerException {
    }

    @Override
    public void detailedLoad(Container viewColumnContainer) throws DatabaseException, ContainerException {
        try {
            String elementName = (String) viewColumnContainer.getAttributes().get(TableColumnAttributes.COLUMN_NAME);
            String tableName = (String) viewColumnContainer.getParent().getAttributes().get(ViewAttributes.TABLE_NAME);
            String listRepresentationOfAttributes = super.listToString(MySQLAttributeFactory.getInstance().getAttributes(this));
            String schemaName = (String) viewColumnContainer.getParent().getAttributes().get(ViewAttributes.TABLE_SCHEMA);
            String query = String.format(MySQLQueries.COLUMNDETAILED, listRepresentationOfAttributes,
                schemaName, tableName, elementName);
            super.executeDetailedLoaderQuery(viewColumnContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container viewColumnContainer) throws DatabaseException, ContainerException {
        this.lazyLoad(viewColumnContainer);
        this.detailedLoad(viewColumnContainer);
    }
}
