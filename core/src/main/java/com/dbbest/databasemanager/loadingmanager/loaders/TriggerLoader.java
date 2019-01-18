package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.constants.MySqlQueriesConstants;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderTypeEnum;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.FkAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.SchemaAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.TableAttributes;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.TriggerAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@com.dbbest.databasemanager.loadingmanager.annotations.Loader(LoaderTypeEnum.Trigger)
public class TriggerLoader implements Loader {
    private static final Logger logger = Logger.getLogger("Database logger");

    @Override
    public void lazyLoad(Connection connection, Container categoryTriggers) throws DatabaseException, ContainerException {
        if (categoryTriggers.getName() == null || categoryTriggers.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The container with Trigger category does not contain the name.");
        }
        try {
            String query =
                String.format(MySqlQueriesConstants.TriggerInformationSchemaGetListOfTriggers.getQuery(),
                    categoryTriggers.getParent().getParent().getParent().getName(),
                    categoryTriggers.getParent().getName());
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet triggers = preparedStatement.executeQuery();

            while (triggers.next()) {
                Container triggerContainer = new Container();
                triggerContainer.setName(triggers.getString(TriggerAttributes.TRIGGER_NAME.getElement()));
                categoryTriggers.addChild(triggerContainer);
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Connection connection, Container triggerContainer) throws DatabaseException, ContainerException {
        if (triggerContainer.getName() == null || triggerContainer.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The trigger container does not contain the name.");
        }
        try {
            String query =
                String.format(MySqlQueriesConstants.TriggerInformationSchemaSelectAll.getQuery(),
                    triggerContainer.getParent().getParent().getParent().getParent().getName(),
                    triggerContainer.getParent().getParent().getName(),
                    triggerContainer.getName());
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                for (TriggerAttributes attributeKey : TriggerAttributes.values()) {
                    triggerContainer.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Connection connection, Container categoryTriggers) throws DatabaseException, ContainerException {
        lazyLoad(connection, categoryTriggers);
        List<Container> trigers = categoryTriggers.getChildren();
        if (trigers != null && !trigers.isEmpty()) {
            for (Container triger : trigers) {
                detailedLoad(connection, triger);
            }
        }
    }
}
