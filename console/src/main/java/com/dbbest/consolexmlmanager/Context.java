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
    private List<Container> listOfFoundElements;

    public Context() {
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

    public Container getDbTreeContainer() {
        return container;
    }

    public void setDbTreeContainer(Container dbTreeContainer) {
        this.container = dbTreeContainer;
    }
}

