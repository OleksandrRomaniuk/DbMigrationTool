package com.dbbest.consolexmlmanager;

public enum Commands implements DbCommands {
    READ("-read"), WRITE("-write");

    private String command;

    @Override
    public String getCommand() {
        return this.command;
    }

    Commands(String command) {
        this.command = command;
    }
}
