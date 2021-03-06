package com.dbbest.databasemanager.dbmanager.loaders.mysql;

import com.dbbest.databasemanager.dbmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.MySQLAttributeFactory;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.TableColumnAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.ViewAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.queries.MySQLQueries;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * The class-loader of the mysql views.
 */
@LoaderAnnotation(NameConstants.VIEW_COLUMN)
public class ViewColumnLoader extends AbstractLoader {

    /*public ViewColumnLoader(Connection connection) {
        super(connection);
    }*/
    @Override
    public void setConnection(Connection connection) {
        super.setConnection(connection);
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
            String schemaName = (String) viewColumnContainer.getParent().getParent().getParent()
                .getAttributes().get(SchemaAttributes.SCHEMA_NAME);
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
