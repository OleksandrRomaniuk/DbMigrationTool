package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.annotations.Loader;
import com.dbbest.databasemanager.loadingmanager.enumtypes.LoaderTypeEnum;
import com.dbbest.exceptions.ConnectionException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

@Loader(LoaderTypeEnum.SchemaLoader)
public class SchemaLoader implements Loaders {

    private TreeContainerBuilder treeContainerBuilder = new TreeContainerBuilder();

    @Override
    public void lazyLoad(Connection connection) throws ConnectionException, ContainerException {
        try {
            connection.getMetaData().getCatalogs();
        } catch (SQLException e) {
            throw new ConnectionException(Level.SEVERE, e, "Can not get the schema name.");
        }
    }

    @Override
    public void detailedLoad(Connection connection) throws ConnectionException, ContainerException {
        try {

            DatabaseMetaData meta = connection.getMetaData();
            ResultSet schemas = meta.getSchemas();
            while (schemas.next()) {
               // String tableSchema = schemas.getMetaData().
                //String tableCatalog = schemas.getString(2); //"TABLE_CATALOG"
                //System.out.println("tableSchema"+tableSchema);
            }

           // connection.getSchema()
        } catch (SQLException e) {
            throw new ConnectionException(Level.SEVERE, e, "Can not get the schema name.");
        }
    }

    @Override
    public void fullLoad(Connection connection) {

    }
}
