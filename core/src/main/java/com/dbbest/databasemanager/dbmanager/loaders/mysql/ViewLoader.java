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
import java.util.List;
import java.util.logging.Level;

/**
 * The class-loader of the mysql views.
 */
@LoaderAnnotation(NameConstants.VIEW)
public class ViewLoader extends AbstractLoader {
    /*public ViewLoader(Connection connection) {
        super(connection);
    }*/
    @Override
    public void setConnection(Connection connection) {
        super.setConnection(connection);
    }

    @Override
    public void lazyLoad(Container viewContainer) throws DatabaseException, ContainerException {
        try {
            String tableName = (String) viewContainer.getAttributes().get(ViewAttributes.TABLE_NAME);
            String schemaAName = (String) viewContainer.getParent().getParent().getAttributes().get(SchemaAttributes.SCHEMA_NAME);
            String query = String.format(MySQLQueries.COLUMNLAZY, schemaAName, tableName);
            this.executeLazyLoaderQuery(viewContainer, query, NameConstants.VIEW_COLUMN, TableColumnAttributes.COLUMN_NAME);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e, "Can not get the list of columns.");
        }
    }

    @Override
    public void detailedLoad(Container viewContainer) throws DatabaseException, ContainerException {
        try {
            String elementName = (String) viewContainer.getAttributes().get(ViewAttributes.TABLE_NAME);
            String listRepresentationOfAttributes = listToString(MySQLAttributeFactory.getInstance().getAttributes(this));
            String schemaName = (String) viewContainer.getParent().getParent().getAttributes().get(SchemaAttributes.SCHEMA_NAME);
            String query = String.format(MySQLQueries.VIEWDETAILED, listRepresentationOfAttributes, schemaName, elementName);
            super.executeDetailedLoaderQuery(viewContainer, query);
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container viewContainer) throws DatabaseException, ContainerException {
        this.lazyLoad(viewContainer);
        this.detailedLoad(viewContainer);
        if (viewContainer.hasChildren()) {
            for (Container viewColumn : (List<Container>) viewContainer.getChildren()) {
                ViewColumnLoader viewColumnLoader = new ViewColumnLoader();
                viewColumnLoader.setConnection(super.getConnection());
                viewColumnLoader.fullLoad(viewColumn);
            }
        }
    }
}
