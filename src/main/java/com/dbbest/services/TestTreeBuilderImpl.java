package com.dbbest.services;

import com.dbbest.consolexmlmanager.CommandManager;
import com.dbbest.consolexmlmanager.Context;
import com.dbbest.consolexmlmanager.LoadTypes;
import com.dbbest.consolexmlmanager.exceptions.CommandException;
import com.dbbest.databasemanager.dbmanager.constants.DatabaseTypes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.SchemaAttributes;
import com.dbbest.exceptions.DatabaseException;
import com.dbbest.xmlmanager.container.Container;
import org.springframework.stereotype.Service;

@Service
public class TestTreeBuilderImpl implements TestTreeBuilder {

    @Override
    public Container build() throws CommandException, DatabaseException {
        Context context = new Context();
        CommandManager commandManager = new CommandManager(context);
        String[] commandLine = new String[7];
        commandLine[0] = "-load";
        commandLine[1] = DatabaseTypes.MYSQL;
        commandLine[2] = "sakila";
        commandLine[3] = "root";
        commandLine[4] = "root";
        commandLine[5] = "sakila";
        commandLine[6] = LoadTypes.LAZY;
        commandManager.addCommands(commandLine);
        commandManager.execute();
        Container root = context.getBuiltContainer();

        System.out.println(root.getAttributes().get(SchemaAttributes.SCHEMA_NAME));
        return root;
    }
}
