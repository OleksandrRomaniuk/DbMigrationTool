package com.dbbest.consolexmlmanager;

import com.dbbest.databasemanager.dbmanager.PrinterManager;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The command print to execute printing of an element to an sql query.
 */
public class CommandPrint implements Command {

    private static final Logger logger = Logger.getLogger("Command logger");

    private Context context;
    private final int priority;
    private final String dbType;
    private final String fullPath;

    /**
     * @param dbType the type of the database (mysql, mssql etc.).
     * @param fullPath the identifier of the element to print.
     * @param priority the priority of the command in the list of commands.
     */
    public CommandPrint(String dbType, String fullPath, int priority, Context context) {
        this.dbType = dbType;
        this.fullPath = fullPath;
        this.priority = priority;
        this.context = context;
    }


    @Override
    public void execute() throws ContainerException, DatabaseException {
        if (context.getDbTreeContainer() == null) {
            throw new DatabaseException(Level.SEVERE, "The tree container has not been set.");
        }
        Container targetContainer = new TreeNavigator(context).getTargetContainer(fullPath);
        PrinterManager printerManager = new PrinterManager(dbType);
        context.setSqlQuery(printerManager.print(targetContainer));
    }

    @Override
    public int getPriority() {
        return priority;
    }


}
