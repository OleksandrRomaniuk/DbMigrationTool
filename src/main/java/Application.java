
import com.dbbest.consolexmlmanager.CommandManager;
import com.dbbest.consolexmlmanager.Context;
import com.dbbest.consolexmlmanager.exceptions.CommandException;
import com.dbbest.exceptions.DatabaseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.logging.Level;
import java.util.logging.Logger;


@Configuration
@SpringBootApplication
@ComponentScan
@EnableScheduling
/**
 * A class which proceeds console commands and evokes appropriate classes and methods.
 */
public class Application {

    private Application() {}

    private static final Logger logger = Logger.getLogger("Application logger");

    /**
     * The method retrieves commands and performs respective procedures.
     * @param args an array of console commands to be processed.
     */
    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        initInitialState(context);

        Context appContext = new Context();
        CommandManager commandManager = new CommandManager(appContext);

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

    private static void initInitialState(ConfigurableApplicationContext context) {

    }

}

