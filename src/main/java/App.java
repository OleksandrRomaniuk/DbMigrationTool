
import com.dbbest.consolexmlmanager.CommandManager;
import com.dbbest.consolexmlmanager.Commands;
import com.dbbest.consolexmlmanager.exceptions.CommandException;

import java.util.logging.Logger;

/**
 * A class which proceeds console commands and evokes appropriate classes and methods.
 */
public final class App {

    private App() {}

    private static final Logger logger = Logger.getLogger("App logger");

    /**
     * The method retrieves commands and performs respective procedures.
     * @param args an array of console commands to be processed.
     */
    public static void main(String[] args) {

        CommandManager commandManager = new CommandManager();
        try {
            commandManager.addCommands(args);
        } catch (CommandException e) {
            e.printStackTrace();
        }
        try {
            commandManager.execute();
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }
}

