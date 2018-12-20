package com.dbbest.dbmigrationtool.filemanagers.parsers.validator;

import com.dbbest.dbmigrationtool.exceptions.ParsingException;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * The class carries out a check that an xml is built according to generally accepted rules.
 * An xml file is parsed while validating it.
 */
public class XmlValidationFactory {

    /**
     * The method validates and parses an xml file. If the file is validated and parsed without exceptions a document is returned.
     * @param file an xml file to be parsed
     * @return the document got from parsing of the file
     * @throws ParsingException is thrown if any exceptions of validation or parsing were identified
     */
    public Document validateAndGetDocument(File file) throws ParsingException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setNamespaceAware(true);
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new SimpleErrorHandler());
            Document document = builder.parse(file);
            document.normalize();
            return document;
        } catch (ParserConfigurationException | SAXException | IOException exception) {
            throw new ParsingException(exception);
        }
    }

    private class SimpleErrorHandler implements ErrorHandler {

        public void error(SAXParseException exception) throws SAXException {
        }

        public void fatalError(SAXParseException exception) throws SAXException {
        }

        @Override
        public void warning(SAXParseException exception) throws SAXException {
        }
    }
}

