package com.dbbest.consolexmlmanager;

import com.dbbest.xmlmanager.container.Container;

import java.sql.Connection;
import java.util.List;

/**
 * Supporting class used to keep all data used by different classes.
 */
public final class Context {

    private String fileName;
    private Container container;
    private String commandLine;
    private static Context instance;
    private List<Container> listOfFoundElements;
    private Connection connection;
    private String schemaName;
    private String dbType;
    private String printedSqlQuery;
    //private Container dbTreeContainer;

    private Context() {
    }

    /**
     * @return returns the instance of the class.
     */
    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
            return instance;
        } else {
            return instance;
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Container getBuiltContainer() {
        return container;
    }

    public void setBuiltContainer(Container builtContainer) {
        this.container = builtContainer;
    }

    public String getCommandLine() {
        return commandLine;
    }

    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }

    public List<Container> getListOfFoundElements() {
        return listOfFoundElements;
    }

    public void setListOfFoundElements(List<Container> listOfFoundElements) {
        this.listOfFoundElements = listOfFoundElements;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }


    public String getPrintedSqlQuery() {
        return printedSqlQuery;
    }

    public void setPrintedSqlQuery(String printedSqlQuery) {
        this.printedSqlQuery = printedSqlQuery;
    }


    public Container getDbTreeContainer() {
        return container;
    }

    public void setDbTreeContainer(Container dbTreeContainer) {
        this.container = dbTreeContainer;
    }
}

