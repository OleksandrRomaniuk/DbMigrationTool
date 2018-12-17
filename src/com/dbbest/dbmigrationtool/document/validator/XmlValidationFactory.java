package com.dbbest.dbmigrationtool.document.validator;

import com.dbbest.dbmigrationtool.exceptions.ParsingException;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XmlValidationFactory {
    private static final Logger logger = Logger.getLogger("Parsing logger");

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
        } catch (ParserConfigurationException exception) {
            logger.log(Level.SEVERE, exception.getMessage(), exception);
            throw new ParsingException(exception);
        } catch (SAXException exception) {
            logger.log(Level.SEVERE, exception.getMessage(), exception);
            throw new ParsingException(exception);
        } catch (IOException exception) {
            logger.log(Level.SEVERE, exception.getMessage(), exception);
            throw new ParsingException(exception);
        }
    }

    private class SimpleErrorHandler implements ErrorHandler {

        public void error(SAXParseException exception) throws SAXException {
            logger.log(Level.SEVERE, exception.getMessage(), exception);
        }

        public void fatalError(SAXParseException exception) throws SAXException {
            logger.log(Level.SEVERE, exception.getMessage(), exception);
        }

        @Override
        public void warning(SAXParseException exception) throws SAXException {
            logger.log(Level.WARNING, exception.getMessage(), exception);
        }
    }
}
