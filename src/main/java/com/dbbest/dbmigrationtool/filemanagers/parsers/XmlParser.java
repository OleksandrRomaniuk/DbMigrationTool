package com.dbbest.dbmigrationtool.filemanagers.parsers;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.exceptions.ParsingException;
import com.dbbest.dbmigrationtool.filemanagers.parsers.validator.Validator;
import com.dbbest.dbmigrationtool.filemanagers.parsers.validator.XmlValidator;
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

    @Override
    public Container<String> parse(String targetFileUrl) throws ParsingException {

        Document document = new XmlValidator().validate(targetFileUrl);
        normalize(document);
        Node node = getNode(document);
        Container<String> container = new XmlSingleNodeParser().parse(new Container<String>(), node);
        return container;
    }

    private Node getNode(Document document) throws ParsingException {
        if (document != null) {
            return getRootNode(document);
        } else {
            throw new ParsingException("Can not get the document. The document is null");
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
            throw new ParsingException(exception);
        }
    }
}

