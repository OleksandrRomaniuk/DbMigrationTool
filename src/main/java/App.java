import com.dbbest.dbmigrationtool.containers.*;
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

        DbList<Container> list = new ListOfChildren();
        Container con1 = new Container();
        con1.setName("child1");
        Container con2 = new Container();
        con1.setName("child2");
        list.add(con1);
        list.add(con1);
        for (Object container: list.toArray())
            System.out.println(((Container)container).getName());
/*
        try {
            CustomLogger logger = new CustomLogger();
            logger.setup();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

        logger.log(Level.INFO, "The application started");

        try {
            App app = new App();
            Container<String> container = null;
            for (int i = 0; i < args.length; i++) {
                if (args[i].startsWith("-")) {
                    if (args[i].equals("-parse")) {
                        container = app.parse(args[++i]);
                    } else if (args[i].equals("-write")) {
                        app.write(args[++i], container);
                    }
                } else if (args[i].equals("-search")) {
                    //app.search(args[++i], container);
                } else {
                    logger.log(Level.INFO, "Can not recognize the command.");
                }
            }

        } catch (ParsingException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (SerializingException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
*/
    }

    private Container parse(String fileName) throws ParsingException {
        System.out.println("parse " + fileName);
        ParsingManager parsingManager = new ParsingManager();
        parsingManager.setParser(new XmlParser());
        parsingManager.parse(fileName);
        logger.log(Level.INFO, "The file has been parsed");
        return parsingManager.getContainer();
    }

    private void write(String fileName, Container<String> container) throws SerializingException {
        System.out.println("write " + fileName);
        SerializingManager<String> serializingManager = new SerializingManager();
        serializingManager.setContainer(container);
        serializingManager.setSerializer(new XmlSerializer());
        serializingManager.writeFile(fileName);
        logger.log(Level.INFO, "The file has been written");
    }
/**
    private void search(String text, Container<String> container) {
        HorizontalPassageSearchManager horSearchManager = new HorizontalPassageSearchManager(container);
        DbList listOfFoundItems = horSearchManager.search(text);
        logger.log(Level.INFO, "Horizontal passage search has been completed.");
        VerticalPassageSearchManager verticalPassageSearchManager = new VerticalPassageSearchManager(container);
        DbList listOfFoundItms = verticalPassageSearchManager.search(text);
        logger.log(Level.INFO, "Vertical passage search has been completed.");
    }*/
}

