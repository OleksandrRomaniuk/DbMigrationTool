
import com.dbbest.AppContext;
import com.dbbest.consolexmlmanager.CommandManager;
import com.dbbest.consolexmlmanager.Commands;
import com.dbbest.consolexmlmanager.Context;
import com.dbbest.consolexmlmanager.exceptions.CommandException;
import com.dbbest.exceptions.DatabaseException;

import java.util.logging.Level;
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

        Context context = new Context();
        CommandManager commandManager = new CommandManager(context);

        try {
            commandManager.addCommands(args);
        } catch (CommandException e) {
            logger.log(Level.SEVERE, "Failed to add the command: " + e.getMessage());
        }
        try {
            commandManager.execute();
        } catch (CommandException e) {
            logger.log(Level.SEVERE, "The program ended with the error: " + e.getMessage());
        } catch (DatabaseException e) {
            logger.log(Level.SEVERE, "The program ended with the error: " + e.getMessage());
        }
    }
}

