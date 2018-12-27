package com.dbbest.consolexmlmanager;

public enum SearchCommands implements SearchCommandsInterface {
    VerticalSearchInNames("v/n"), VerticalSearchInValues("v/v"), VerticalSearchInAttributes("v/a"),
    HorizontalSearchInNames("h/n"), HorizontalSearchInValues("h/v"), HorizontalSearchInAttributes("h/a");

    private final String command;

    @Override
    public String getCommand() {
        return this.command;
    }

    SearchCommands(String command) {
        this.command = command;
    }

}
