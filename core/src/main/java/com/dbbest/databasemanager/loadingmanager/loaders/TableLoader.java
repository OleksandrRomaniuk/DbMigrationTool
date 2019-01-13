package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.constants.ConstantListsBuilder;
import com.dbbest.databasemanager.loadingmanager.constants.MySqlQueriesConstants;
import com.dbbest.databasemanager.loadingmanager.constants.TagNamesConstants;
import com.dbbest.databasemanager.loadingmanager.constants.enumsattributes.TableAttributes;
import com.dbbest.exceptions.ConnectionException;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class TableLoader implements Loaders {

    @Override
    public void lazyLoad(Connection connection, Container container) throws ConnectionException, ContainerException {
        try {
            validateContainer(container);
            ResultSet resultSet = connection.getMetaData()
                .getTables(container.getName(), null, "%", new String[] {"TABLE"});

            Container childContainerWithTables = container.getChildByName(TagNamesConstants.Tables.getElement());

            while (resultSet.next()) {
                Container table = new Container();
                table.setName(resultSet.getString(ConstantListsBuilder.TableAttributes.TABLE_NAME.getElement()));
                childContainerWithTables.addChild(table);
            }
        } catch (SQLException e) {
            throw new ConnectionException(Level.SEVERE, e, "Can not get the list of tables.");
        }
    }

    @Override
    public void detailedLoad(Connection connection, Container container) throws ConnectionException, ContainerException {

        try {
            validateContainer(container);

            for (Container tableContainer : getTables(container)) {
                String informationSchemataSelectAllQuery =
                    String.format(MySqlQueriesConstants.TableInformationSchemaSelectAll.getQuery(),
                        container.getName(), tableContainer.getName());

                PreparedStatement preparedStatement = connection.prepareStatement(informationSchemataSelectAllQuery);
                ResultSet resultSet = preparedStatement.executeQuery();

                Container table = new Container();
                table.setName(resultSet.getString(TableAttributes.TABLE_NAME.getElement()));
                tableContainer.addChild(table);

                for (TableAttributes attributeKey : TableAttributes.values()) {
                    table.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
                }
            }
        } catch (SQLException e) {
            throw new ConnectionException(Level.SEVERE, e);
        }

    }

    @Override
    public void fullLoad(Connection connection, Container container) {

    }

    private List<Container> getTables(Container container) throws ConnectionException {
        List<Container> shemaChildren = container.getChildren();
        for (Container child : shemaChildren) {
            if (child.getName().trim().equals(TagNamesConstants.Tables.getElement())) {
                return child.getChildren();
            }
        }
        throw new ConnectionException(Level.SEVERE, "Have not found Tables in the con ainer");
    }

    private void validateContainer(Container container) throws ConnectionException {
        if (container.getName() == null || container.getName().trim().isEmpty()) {
            throw new ConnectionException(Level.SEVERE, "The container does not contain the name.");
        } else {
            List<Container> shemaChildren = container.getChildren();
            boolean result = false;
            for (Container child : shemaChildren) {
                if (child.getName().trim().equals(TagNamesConstants.Tables.getElement())) {
                    result = true;
                }
            }
            if (!result) {
                throw new ConnectionException(Level.SEVERE, "The container does not contain the chile "
                    + TagNamesConstants.Tables.getElement());
            }
        }
    }
}
