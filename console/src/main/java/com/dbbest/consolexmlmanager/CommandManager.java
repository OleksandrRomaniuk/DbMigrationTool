package com.dbbest.consolexmlmanager;

import com.dbbest.consolexmlmanager.exceptions.CommandException;
import com.dbbest.exceptions.DatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class which manages work with commands.
 */
public class CommandManager {

    private Invoker invoker = new Invoker();
    private Context context;
    private final String commandPattern = "-(((\\S+\\s+){1,3})|((\\S+\\s+){6}))\\S+\\s*\\z";

    public CommandManager(Context context) {
        this.context = context;
    }

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

    public void execute() throws CommandException, DatabaseException {
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
                if ((matchs("-" + element.trim(), commandPattern))) {
                    commands.add("-" + element.trim());
                } else {
                    throw new CommandException("Incorrect command format: -" + element.trim());
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

        if (!matchs(singleCommand.trim(), commandPattern)) {
            throw new CommandException("The command " + singleCommand + " was not recognized.");
        }

        String[] singleCommandWords = singleCommand.split("\\s");

        invoker.add(Commands.valueOf(singleCommandWords[0].trim().substring(1).toUpperCase())
            .getCommand(singleCommand.trim(), context));
    }

    public Invoker getInvoker() {
        return invoker;
    }

    public Context getContext() {
        return context;
    }
}
