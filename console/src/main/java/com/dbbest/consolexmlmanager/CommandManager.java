package com.dbbest.consolexmlmanager;

import com.dbbest.consolexmlmanager.exceptions.CommandException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class which manages work with commands.
 */
public class CommandManager {

    private Invoker invoker = new Invoker();

    /**
     * Adds command lines to work out.
     * @param commandLine command line which should be worked out.
     * @throws CommandException exception thrown during treatment of the commandline.
     */
    public void addCommands(String[] commandLine) throws CommandException {

        List<String> listOfCommands = getListOfCommands(commandLine);

        for (String singleCommand : listOfCommands) {
            addToInvoker(singleCommand);
        }
    }

    public void execute() throws CommandException {
        invoker.execute();
    }

    private List<String> getListOfCommands(String[] commandLine) throws CommandException {
        List<String> commands = new ArrayList();
        StringBuilder cl = new StringBuilder();

        for (String element : commandLine) {
            cl.append(element + " ");
        }

        String[] commandsSplit = cl.toString().trim().split("-");
        String pattern = "-\\S*\\s*\\S*\\z";
        for (String element : commandsSplit) {
            if (element.length() > 0) {
                if (matchs("-" + element.trim(), pattern)) {
                    commands.add("-" + element.trim());
                } else {
                    throw new CommandException("Incorrect command format.");
                }

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
        String pattern = "-\\S*\\s*\\S*\\z";
        if (!matchs(singleCommand.trim(), pattern)) {
            throw new CommandException("The command " + singleCommand + " was not recognized.");
        }
        String[] singleCommandWords = singleCommand.split("\\s");

        switch (singleCommandWords[0].trim()) {
            case "-read":
                CommandRead commandRead = new CommandRead(singleCommandWords[1].trim());
                commandRead.setCommandLine(singleCommand);
                invoker.add(commandRead);
                break;

            case "-write":
                CommandWrite commandWrite = new CommandWrite(singleCommandWords[1].trim());
                invoker.add(commandWrite);
                commandWrite.setCommandLine(singleCommand);
                break;

            case "-searchHorizontalName":
                CommandHorizontalSearchByName commandHorizontalSearchByName =
                    new CommandHorizontalSearchByName(singleCommandWords[1].trim());
                invoker.add(commandHorizontalSearchByName);
                commandHorizontalSearchByName.setCommandLine(singleCommand);
                break;

            case "-searchHorizontalValue":
                CommandHorizontalSearchByValue commandHorizontalSearchByValue =
                    new CommandHorizontalSearchByValue(singleCommandWords[1].trim());
                invoker.add(commandHorizontalSearchByValue);
                commandHorizontalSearchByValue.setCommandLine(singleCommand);
                break;

            case "-searchHorizontalKeyValue":
                CommandHorizontalSearchByKeyValue commandHorizontalSearchByKeyValue =
                    new CommandHorizontalSearchByKeyValue(singleCommandWords[1].trim());
                invoker.add(commandHorizontalSearchByKeyValue);
                commandHorizontalSearchByKeyValue.setCommandLine(singleCommand);
                break;

            case "-searchVerticalName":
                CommandVerticalSearchByName commandVerticalSearchByName =
                    new CommandVerticalSearchByName(singleCommandWords[1].trim());
                invoker.add(commandVerticalSearchByName);
                commandVerticalSearchByName.setCommandLine(singleCommand);
                break;

            case "-searchVerticalValue":
                CommandVerticalSearchByValue commandVerticalSearchByValue =
                    new CommandVerticalSearchByValue(singleCommandWords[1].trim());
                invoker.add(commandVerticalSearchByValue);
                commandVerticalSearchByValue.setCommandLine(singleCommand);
                break;

            case "-searchVerticalKeyValue":
                CommandVerticalSearchByKeyValue commandVerticalSearchByKeyValue =
                    new CommandVerticalSearchByKeyValue(singleCommandWords[1].trim());
                invoker.add(commandVerticalSearchByKeyValue);
                commandVerticalSearchByKeyValue.setCommandLine(singleCommand);
                break;

            default:
                throw new CommandException("The command " + singleCommand + " was not recognized.");
        }
    }

    public Invoker getInvoker() {
        return invoker;
    }
}
