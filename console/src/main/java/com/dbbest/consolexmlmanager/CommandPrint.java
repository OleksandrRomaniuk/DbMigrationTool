package com.dbbest.consolexmlmanager;

import com.dbbest.databasemanager.loadingmanager.PrinterManager;
import com.dbbest.databasemanager.loadingmanager.constants.mysql.attributes.AttributeSingleConstants;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;
import com.dbbest.xmlmanager.container.HorizontalPassageSearchManager;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The command print to execute printing of an element to an sql query.
 */
public class CommandPrint implements Command {

    private static final Logger logger = Logger.getLogger("Command logger");

    private Context context = Context.getInstance();
    private final int priority;
    private final String dbType;
    private final String routineID;

    /**
     * @param dbType the type of the database (mysql, mssql etc.).
     * @param routineID the identifier of the element to print.
     * @param priority the priority of the command in the list of commands.
     */
    public CommandPrint(String dbType, String routineID, int priority) {
        this.dbType = dbType;
        this.routineID = routineID;
        this.priority = priority;
    }


    @Override
    public void execute() throws ContainerException, DatabaseException {
        if (context.getDbTreeContainer() == null) {
            throw new DatabaseException(Level.SEVERE, "The tree container has not been set.");
        }
        context.setDbType(dbType);
        HorizontalPassageSearchManager horSearchManager = new HorizontalPassageSearchManager(context.getDbTreeContainer());
        List<Container> targetContainer = horSearchManager.searchInKeyValues(AttributeSingleConstants.ROUTINE_ID, routineID);
        PrinterManager printerManager = PrinterManager.getInstance();
        if (targetContainer != null && !targetContainer.isEmpty()) {
            context.setPrintedSqlQuery(printerManager.print(targetContainer.get(0)));
        } else {
            logger.log(Level.INFO, "No elements were found by the routine ID " + routineID);
        }
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
