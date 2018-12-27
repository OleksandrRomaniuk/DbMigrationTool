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
    private final String patternReadAndWriteCommand = "-\\S*\\s*\\S*\\z";
    private final String patternSearchCommand = "-\\S*\\s*\\S*s*\\S*\\z";

    /**
     * Adds command lines to work out.
     *
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
        for (String element : commandsSplit) {
            if (element.length() > 0) {
                if (matchs("-" + element.trim(), patternReadAndWriteCommand)
                    || matchs("-" + element.trim(), patternSearchCommand)) {
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
        if (!matchs(singleCommand.trim(), patternReadAndWriteCommand)
            || matchs("-" + singleCommand.trim(), patternSearchCommand)) {
            throw new CommandException("The command " + singleCommand + " was not recognized.");
        }
        String[] singleCommandWords = singleCommand.split("\\s");

        if (singleCommandWords[0].trim().equals(Commands.READ.getCommand())) {
            invoker.add(new CommandRead(singleCommandWords[1].trim(), Commands.READ.getPriority()));
        } else if (singleCommandWords[0].trim().equals(Commands.WRITE.getCommand())) {
            invoker.add(new CommandWrite(singleCommandWords[1].trim(), Commands.WRITE.getPriority()));
        } else if (singleCommandWords[0].trim().equals(Commands.SEARCH.getCommand())) {
            invoker.add(new CommandSearch(singleCommandWords[1].trim(),
                singleCommandWords[2].trim(), Commands.SEARCH.getPriority()));
        } else {
            throw new CommandException("The command " + singleCommand + " was not recognized.");
        }
    }

    public Invoker getInvoker() {
        return invoker;
    }
}
