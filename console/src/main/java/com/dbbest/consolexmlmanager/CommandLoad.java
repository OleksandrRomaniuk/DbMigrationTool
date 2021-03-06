package com.dbbest.consolexmlmanager;

import com.dbbest.consolexmlmanager.exceptions.CommandException;
import com.dbbest.databasemanager.connectionbuilder.connectionpool.SimpleConnectionBuilder;
import com.dbbest.databasemanager.dbmanager.LoaderManager;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The command-loader from a databse to the container.
 */
public class CommandLoad implements Command {
    private static final Logger logger = Logger.getLogger("Command logger");

    private Context context;
    private final int priority;
    private final String dbType;
    private final String dbName;
    private final String userName;
    private final String password;
    private final String fullPath;
    private final String loadType;

    /**
     * @param dbType   the type of the databse to load.
     * @param dbName   the name of the databse to load.
     * @param userName the username to connect to the database.
     * @param password the password to connect to the database.
     * @param fullPath the rooutine id of the node to load.
     * @param loadType the type of loading (lazy, detailed or full).
     * @param priority priority of the command in the list of commands.
     */
    public CommandLoad(String dbType, String dbName, String userName, String password,
                       String fullPath, String loadType, int priority, Context context) {
        this.dbType = dbType;
        this.dbName = dbName;
        this.userName = userName;
        this.password = password;
        this.priority = priority;
        this.fullPath = fullPath;
        this.loadType = loadType;
        this.context = context;
    }

    @Override
    public void execute() throws ContainerException, DatabaseException, CommandException {

        if (context.getDbTreeContainer() == null) {
            Container schemaContainer = new Container();
            schemaContainer.setName(NameConstants.SCHEMA);
            context.setDbTreeContainer(schemaContainer);
        }

        SimpleConnectionBuilder simpleConnectionBuilder = new SimpleConnectionBuilder();

        Connection connection = simpleConnectionBuilder.getConnection(dbType, dbName, userName, password);

        Container targetContainer = new TreeNavigator(context).getTargetContainer(fullPath);
        if (targetContainer == null) {
            throw new DatabaseException(Level.SEVERE, "Can not find the node with the path: " + fullPath);
        }

        LoaderManager loaderManager = new LoaderManager(connection, dbType);

        switch (loadType.toLowerCase()) {
            case LoadTypes.LAZY:
                loaderManager.loadLazy(targetContainer);
                break;
            case LoadTypes.DETAIL:
                loaderManager.loadDetails(targetContainer);
                break;
            case LoadTypes.FULL:
                loaderManager.loadFull(targetContainer);
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
