package com.dbbest.consolexmlmanager;

public enum Commands implements DbCommands {
    READ("-read", 1), WRITE("-write", 3), SEARCH("-search", 2);

    private final String command;
    private final int priority;

    @Override
    public String getCommand() {
        return this.command;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    Commands(String command, int priority) {
        this.command = command;
        this.priority = priority;
    }
}
