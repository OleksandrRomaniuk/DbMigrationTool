package com.dbbest.xmlmanager.filemanagers;

import com.dbbest.xmlmanager.container.Container;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.exceptions.ParsingException;
import com.dbbest.xmlmanager.filemanagers.parsers.Parser;

/**
 * A manager which performs a parsing of a file using a predefined parser.
 */
public class ParsingManager {
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
    public void parse(String targetFileUrl) throws ParsingException, ContainerException {
        if (parser != null) {
            container = parser.parse(targetFileUrl);
        } else {
            throw new ParsingException("The parser has not been set. Please set a parser before parsing the file.");
        }
    }

    public Container getContainer() {

        return container;
    }
}

