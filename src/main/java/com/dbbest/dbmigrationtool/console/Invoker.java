package com.dbbest.dbmigrationtool.console;

import com.dbbest.dbmigrationtool.containers.Container;

import java.util.function.Predicate;

public abstract class Invoker {

    private Command command;
    private final Predicate<Container> predicate;

    protected Invoker(Predicate<Container> predicate) {
        this.predicate = predicate;
    }

    public abstract void invokeCommand();
}
