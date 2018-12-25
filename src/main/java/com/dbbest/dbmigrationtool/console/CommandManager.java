package com.dbbest.dbmigrationtool.console;

import com.dbbest.dbmigrationtool.exceptions.CommandException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandManager {
    private Invoker invoker = new Invoker();

    public void addCommands(String[] commandLine) throws CommandException {

        List<String> listOfCommands = getListOfCommands(commandLine);

        for (String singleCommand : listOfCommands) {
            addToInvoker(singleCommand);
        }
    }

    public void execute() throws CommandException {
        invoker.execute();
    }

    private List<String> getListOfCommands(String[] commandLine) {
        List<String> commands = new ArrayList();
        int previousFoundBeginingOfCommand = 0;
        for (int i = 0; i < commandLine.length; i++) {
            if (commandLine[i].trim().startsWith("-")) {
                StringBuilder singleCommad = new StringBuilder();
                for (int j = previousFoundBeginingOfCommand; j < previousFoundBeginingOfCommand; j++) {
                    singleCommad.append(commandLine[j] + " ");
                }
                commands.add(singleCommad.toString().trim());
            }
        }
        return commands;
    }

    private boolean matchs(String input, String patternToApply) {
        Pattern pattern = Pattern.compile(patternToApply);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    private void addToInvoker(String singleCommand) throws CommandException {
        String pattern = "-.*\\s.*";

        if (!matchs(singleCommand, pattern)) {
            throw new CommandException("The command " + singleCommand + " was not recognized.");
        }

        String[] singleCommandWords = singleCommand.split("\\s");

        switch (singleCommandWords[0].trim()) {
            case "-read":

                CommandRead commandRead = new CommandRead(singleCommandWords[1].trim());
                invoker.add(commandRead);
                break;

            case "-write":
                CommandRead commandWrite = new CommandRead(singleCommandWords[1].trim());
                invoker.add(commandWrite);
                break;

            case "-searchHorizontalName":
                CommandHorizontalSearchByName commandHorizontalSearchByName = new CommandHorizontalSearchByName(singleCommandWords[1].trim());
                invoker.add(commandHorizontalSearchByName);
                break;

            case "-searchHorizontalValue":
                CommandHorizontalSearchByValue commandHorizontalSearchByValue = new CommandHorizontalSearchByValue(singleCommandWords[1].trim());
                invoker.add(commandHorizontalSearchByValue);
                break;

            case "-searchHorizontalKeyValue":
                CommandHorizontalSearchByKeyValue commandHorizontalSearchByKeyValue = new CommandHorizontalSearchByKeyValue(singleCommandWords[1].trim());
                invoker.add(commandHorizontalSearchByKeyValue);
                break;

            case "-searchVerticalName":
                CommandVerticalSearchByName commandVerticalSearchByName = new CommandVerticalSearchByName(singleCommandWords[1].trim());
                invoker.add(commandVerticalSearchByName);
                break;

            case "-searchVerticalValue":
                CommandVerticalSearchByValue commandVerticalSearchByValue = new CommandVerticalSearchByValue(singleCommandWords[1].trim());
                invoker.add(commandVerticalSearchByValue);
                break;

            case "-searchVerticalKeyValue":
                CommandVerticalSearchByKeyValue commandVerticalSearchByKeyValue = new CommandVerticalSearchByKeyValue(singleCommandWords[1].trim());
                invoker.add(commandVerticalSearchByKeyValue);
                break;
            // You can have any number of case statements.
            default: // Optional
                throw new CommandException("The command " + singleCommand + " was not recognized.");
        }
    }
}
