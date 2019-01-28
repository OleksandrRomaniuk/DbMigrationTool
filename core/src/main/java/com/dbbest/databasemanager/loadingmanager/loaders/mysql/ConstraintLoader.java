package com.dbbest.databasemanager.loadingmanager.loaders.mysql;

import com.dbbest.databasemanager.loadingmanager.annotations.LoaderAnnotation;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@LoaderAnnotation(LoaderPrinterName.CONSTRAINT)
public class ConstraintLoader extends AbstractLoader {
    @Override
    public void lazyLoad(Container constraintCategoryContainer) throws DatabaseException, ContainerException {

        try {
            super.executeLazyLoadTableConstraint(constraintCategoryContainer);
            /*
            String query =
                String.format(MySqlQueriesConstants.TableConstraintsSelectAll.getQuery(),
                    constraintCategoryContainer.getParent().getParent().getParent().getName(),
                    constraintCategoryContainer.getParent().getName());
            Connection connection = Context.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Container constraintContainer = new Container();
                constraintContainer.setName(resultSet.getString(TableConstraintAttributes.CONSTRAINT_NAME.getElement()));
                constraintCategoryContainer.addChild(constraintContainer);

                for (TableConstraintAttributes attributeKey : TableConstraintAttributes.values()) {
                    constraintContainer.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
                }
            }*/
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void detailedLoad(Container constraintContainer) throws DatabaseException, ContainerException {

        /*if (constraintContainer.getName() == null || constraintContainer.getName().trim().isEmpty()) {
            throw new ContainerException(Level.SEVERE, "The container of the constraint category does not contain the name.");
        }*/

        try {

            super.executeDetailedLoadTableConstraint(constraintContainer);
            /*
            String query =
                String.format(MySqlQueriesConstants.KeyColumnUSageConstarintsSelectAll.getQuery(),
                    constraintContainer.getParent().getParent().getParent().getParent().getName(),
                    constraintContainer.getParent().getParent().getName(),
                    constraintContainer.getName());
            Connection connection = Context.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Container constraint = new Container();
                constraint.setName(resultSet.getString(FkAttributes.CONSTRAINT_NAME.getElement()));
                constraintContainer.addChild(constraint);

                for (FkAttributes attributeKey : FkAttributes.values()) {
                    constraint.addAttribute(attributeKey.getElement(), resultSet.getString(attributeKey.getElement()));
                }
            }*/
        } catch (SQLException e) {
            throw new DatabaseException(Level.SEVERE, e);
        }
    }

    @Override
    public void fullLoad(Container constraintCategoryContainer) throws DatabaseException, ContainerException {
        lazyLoad(constraintCategoryContainer);
        List<Container> constraintContainers = constraintCategoryContainer.getChildren();
        for (Container constraint : constraintContainers) {
            detailedLoad(constraint);
        }
    }
}
