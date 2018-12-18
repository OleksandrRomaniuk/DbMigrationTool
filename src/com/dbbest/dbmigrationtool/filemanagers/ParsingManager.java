package com.dbbest.dbmigrationtool.filemanagers;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.exceptions.ParsingException;
import com.dbbest.dbmigrationtool.filemanagers.parsers.Parser;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;

/**
 * A manager which performs a parsing of a file using a predefined parser.
 */
public class ParsingManager {
    private static final Logger logger = Logger.getLogger("Parsing logger");
    private Parser parser;
    private Container container;

    public void setParser(Parser parser) {

        this.parser = parser;
    }

    /**
     * @param targetFileUrl the URL of a file to parse.
     * @throws ParsingException the method throws a parsing exception
        if any checked exception was identified during the parsing process.
     */
    public void parse(String targetFileUrl) throws ParsingException {
        if (parser != null) {
            Document document = parser.getFileValidator().validate(targetFileUrl);
            container = parser.parse(document);
        } else {
            String message = "The parser has not been set. Please set a parser before parsing the file.";
            logger.log(Level.SEVERE, message);
            throw new ParsingException(message);
        }
    }

    public Container getContainer() {

        return container;
    }
}
