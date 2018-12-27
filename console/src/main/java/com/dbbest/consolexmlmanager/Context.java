package com.dbbest.consolexmlmanager;

import com.dbbest.xmlmanager.container.Container;

import java.util.List;

public class Context {

    private String fileName;
    private Container builtContainer;
    private String commandLine;
    private static Context instance;
    private List<Container> listOfFoundElements;

    private Context() {
    }

    public static Context getInstance() {
        if (instance == null) {
            return new Context();
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
        return builtContainer;
    }

    public void setBuiltContainer(Container builtContainer) {
        this.builtContainer = builtContainer;
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
}

