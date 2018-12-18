package main;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.containers.HorizontalPassageSearchManager;
import com.dbbest.dbmigrationtool.containers.VerticalPassageSearchManager;
import com.dbbest.dbmigrationtool.exceptions.ParsingException;
import com.dbbest.dbmigrationtool.exceptions.SerializingException;
import com.dbbest.dbmigrationtool.filemanagers.ParsingManager;
import com.dbbest.dbmigrationtool.filemanagers.SerializingManager;
import com.dbbest.dbmigrationtool.filemanagers.parsers.XmlParser;
import com.dbbest.dbmigrationtool.filemanagers.serializers.XmlSerializer;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logger.CustomLogger;

/**
 * A class which proceeds console commands and evokes appropriate classes and methods.
 */
public class App {


    private static final Logger logger = Logger.getLogger("App logger");

    /**
     * The method retrieves commands and performs respective procedures.
     *
     * @param args an array of console commands to be processed.
     */
    public static void main(String[] args) {

        try {
            CustomLogger logger = new CustomLogger();
            logger.setup();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

        logger.log(Level.INFO, "The application started");

        try {
            App app = new App();
            Container<String, String> container = null;
            for (int i = 0; i < args.length; i++) {
                if (args[i].startsWith("-")) {
                    if (args[i].equals("-parse")) {
                        container = app.parse(args[++i]);
                    } else if (args[i].equals("-write")) {
                        app.write(args[++i], container);
                    }
                } else if (args[i].equals("-search")) {
                    app.search(args[++i], container);
                } else {
                    logger.log(Level.INFO, "Can not recognize the command.");
                }
            }

        } catch (ParsingException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (SerializingException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

    }

    private Container parse(String fileName) throws ParsingException {
        ParsingManager parsingManager = new ParsingManager();
        parsingManager.setParser(new XmlParser());
        parsingManager.parse(fileName);
        logger.log(Level.INFO, "The file has been parsed");
        return parsingManager.getContainer();
    }

    private void write(String fileName, Container<String, String> container) throws SerializingException {
        SerializingManager<String, String> serializingManager = new SerializingManager();
        serializingManager.setContainer(container);
        serializingManager.setSerializer(new XmlSerializer());
        serializingManager.writeFile(fileName);
        logger.log(Level.INFO, "The file has been written");
    }

    private void search(String text, Container<String, String> container) {
        HorizontalPassageSearchManager horSearchManager = new HorizontalPassageSearchManager(container);
        List<Container<String, String>> listOfFoundItems = horSearchManager.search(text);
        logger.log(Level.INFO, "Horizontal passage search has been completed.");
        VerticalPassageSearchManager verticalPassageSearchManager = new VerticalPassageSearchManager(container);
        List<Container<String, String>> listOfFoundItms = verticalPassageSearchManager.search(text);
        logger.log(Level.INFO, "Vertical passage search has been completed.");
    }
}
