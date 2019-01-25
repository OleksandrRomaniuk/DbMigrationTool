package com.dbbest.databasemanager.loadingmanager.loaders;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.MySqlQueriesConstants;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.StoredProceduresAndFunctionsAttributes;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@LoaderAnnotation(LoaderPrinterName.FUNCTION)
public class FunctionLoader extends AbstractLoader {
    @Override
    public void lazyLoad(Container functionCategoryContainer) throws DatabaseException, ContainerException {

        /*if (functionCategoryContainer.getName() == null || functionCategoryContainer.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The container of the category Functions does not contain the name.");
        }*/


        try {

            super.executeLazyLoad(functionCategoryContainer);
            /*
            ResultSet functions = connection.getMetaData().getFunctions((String) functionCategoryContainer
                .getParent().getAttributes().get(SchemaAttributes.SCHEMA_NAME.getElement()), null, null);
            while (functions.next()) {
                Container storedProcedure = new Container();
                storedProcedure.setName(functions.getString(StoredProceduresAndFunctionsAttributes.SPECIFIC_NAME.getElement()));
                functionCategoryContainer.addChild(storedProcedure);
            }
            */
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container functionContainer) throws DatabaseException, ContainerException {
        /*if (functionContainer.getName() == null || functionContainer.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The view container does not contain the name.");
        }*/

        try {
            super.executeDetailedLoad(functionContainer);
            /*
            String query =
                String.format(MySqlQueriesConstants.StoredProceduresAndFunctionsSchemaSelectAll.getQuery(),
                    functionContainer.getParent().getParent().getName(),
                    "FUNCTION",
                    functionContainer.getName());
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                for (StoredProceduresAndFunctionsAttributes attributeKey : StoredProceduresAndFunctionsAttributes.values()) {
                    functionContainer.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
                }
            }*/
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container functionsCategoryContainer) throws DatabaseException, ContainerException {
        lazyLoad(functionsCategoryContainer);
        List<Container> functions = functionsCategoryContainer.getChildren();
        if (functions != null && !functions.isEmpty()) {
            for (Container function : functions) {
                detailedLoad(function);
                new ProcedureFunctionParameteresLoader().fullLoad(function);
            }
        }
    }
}
