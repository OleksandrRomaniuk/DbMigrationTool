package com.dbbest.dbmigrationtool.filemanagers.parsers;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.document.validator.Validator;
import com.dbbest.dbmigrationtool.document.validator.XmlValidator;
import com.dbbest.dbmigrationtool.exceptions.ParsingException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A class which implements the interface Parser and realizes the whole cycle of parsing.
 * from the validation of a file to creating a tree.
 */
public class XmlParser implements Parser {

    private static final Logger logger = Logger.getLogger("Parsing logger");

    private Container<String, String> container;
    private Validator validator;

    public XmlParser() {
    }

    @Override
    public Container<String, String> getContainer() {

        return container;
    }

    @Override
    public Validator getFileValidator() {
        if (validator == null) {
            validator = new XmlValidator();
        }
        return validator;
    }

    @Override
    public Container<String, String> parse(Document document) throws ParsingException {

        normalize(document);
        Node node = getNode(document);
        container = new XmlSingleNodeParser(new Container<String, String>(), node)
            .parse()
            .getContainer();
        return container;
    }

    private Node getNode(Document document) throws ParsingException {
        if (document != null) {
            return getRootNode(document);
        } else {
            String message = "Can not get the docuemnt. The document is null";
            logger.log(Level.SEVERE, message);
            throw new ParsingException(message);
        }
    }

    private Node getRootNode(Document document) {

        return document.getDocumentElement();
    }

    private Document normalize(Document document) throws ParsingException {
        try {
            XPathFactory xfact = XPathFactory.newInstance();
            XPath xpath = xfact.newXPath();

            NodeList empty = (NodeList) xpath.evaluate("//text()[normalize-space(.) = '']",
                document, XPathConstants.NODESET);

            for (int i = 0; i < empty.getLength(); i++) {
                Node node = empty.item(i);
                node.getParentNode().removeChild(node);
            }
            return document;
        } catch (XPathExpressionException exception) {
            logger.log(Level.SEVERE, exception.getMessage());
            throw new ParsingException(exception);
        }
    }
}
