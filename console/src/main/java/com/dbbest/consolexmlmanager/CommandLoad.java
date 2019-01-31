package com.dbbest.consolexmlmanager;

import com.dbbest.consolexmlmanager.exceptions.CommandException;
import com.dbbest.databasemanager.connectionbuilder.connectionpool.SimpleConnectionBuilder;
import com.dbbest.databasemanager.loadingmanager.LoaderManager;
import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;
import com.dbbest.xmlmanager.container.HorizontalPassageSearchManager;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The command-loader from a databse to the container.
 */
public class CommandLoad implements Command {
    private static final Logger logger = Logger.getLogger("Command logger");

    private Context context = Context.getInstance();
    private final int priority;
    private final String dbType;
    private final String dbName;
    private final String userName;
    private final String password;
    private final String routineID;
    private final String loadType;

    /**
     * @param dbType the type of the databse to load.
     * @param dbName the name of the databse to load.
     * @param userName the username to connect to the database.
     * @param password the password to connect to the database.
     * @param routineID the rooutine id of the node to load.
     * @param loadType 
     * @param priority
     */
    public CommandLoad(String dbType, String dbName, String userName, String password,
                       String routineID, String loadType, int priority) {
        this.dbType = dbType;
        this.dbName = dbName;
        this.userName = userName;
        this.password = password;
        this.priority = priority;
        this.routineID = routineID;
        this.loadType = loadType;
    }

    @Override
    public void execute() throws ContainerException, DatabaseException, CommandException {

        if (context.getDbTreeContainer() == null) {
            Container schemaContainer = new Container();
            schemaContainer.setName(LoaderPrinterName.SCHEMA);
            context.setDbTreeContainer(schemaContainer);
        }
        context.setDbType(dbType);
        SimpleConnectionBuilder simpleConnectionBuilder = new SimpleConnectionBuilder();
        Connection connection = simpleConnectionBuilder.getConnection(dbType, dbName, userName, password);
        context.setConnection(connection);

        HorizontalPassageSearchManager horSearchManager = new HorizontalPassageSearchManager(context.getDbTreeContainer());
        List<Container> targetContainer = horSearchManager.searchInKeyValues(AttributeSingleConstants.ROUTINE_ID, routineID);

        LoaderManager loaderManager = LoaderManager.getInstance();

        switch (loadType.toLowerCase()) {
            case LoadTypes.LAZY:
                loaderManager.loadLazy(targetContainer.get(0));
                break;
            case LoadTypes.DETAIL:
                loaderManager.loadDetails(targetContainer.get(0));
                break;
            case LoadTypes.FULL:
                loaderManager.loadFull(targetContainer.get(0));
                break;
            default:
                throw new CommandException(Level.SEVERE, "The type of loading " + loadType + " has not been recognized.");
        }
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
