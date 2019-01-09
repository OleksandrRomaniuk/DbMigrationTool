package com.dbbest.xmlmanager.filemanagers.serializers;

import com.dbbest.xmlmanager.container.Container;
import com.dbbest.xmlmanager.container.DbList;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * This class works out a single container of a tree. It transforms a container to an element of xml document.
 */
public class XmlNodeBuilder {
    private Document document;
    private Element parentElement;
    private Container<String> container;

    public XmlNodeBuilder(Document document, Container<String> container) {
        this.document = document;
        this.container = container;
    }

    /**
     * @param document the document retrieved from the xml serializer to put elements got from the container.
     * @param container the container which is treated by the node builder.
     * @param parentElement the element created from the parent container.
     */
    public XmlNodeBuilder(Document document, Container<String> container, Element parentElement) {
        this.document = document;
        this.container = container;
        this.parentElement = parentElement;
    }

    /**
     * This method triggers the process of getting data from a container and creating a respective element.
     * @return the object on which the method was evoked.
     */
    public XmlNodeBuilder build() {
        if (parentElement == null) {
            parentElement = setElementNode();
            document.appendChild(parentElement);
        } else {
            if (isElementNode(container)) {
                parentElement.appendChild(setElementNode());
            } else if (isTextNode(container)) {
                parentElement.appendChild(setTextElement());
            } else if (isCdataNode(container)) {
                parentElement.appendChild(setCdataNode());
            }
        }
        return this;
    }

    private boolean isElementNode(Container<String> container) {
        return container.hasName();
    }

    private boolean isTextNode(Container<String> container) {
        return !container.hasName() && !meetCdataRegex(container.getValue())
            && !container.hasAttributes() && !container.hasChildren();
    }

    private boolean isCdataNode(Container<String> container) {
        return !container.hasName() && meetCdataRegex(container.getValue())
            && !container.hasAttributes() && !container.hasChildren();
    }

    public Document getDocument() {
        return document;
    }

    private boolean meetCdataRegex(String nodeValue) {
        return matchs(nodeValue);
    }

    private boolean matchs(String input) {
        Pattern pattern = Pattern.compile("<|&|\\'.*\\'|\\\".*\\\"");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    private Element setElementNode() {
        Element elementNode = document.createElement(container.getName());
        if (container.hasValue()) {
            elementNode.setNodeValue(container.getValue());
        }
        if (container.hasAttributes()) {
            setNodeAttributes(elementNode);
        }
        if (container.hasChildren()) {
            setChildNodes(elementNode);
        }
        return elementNode;
    }

    private Text setTextElement() {
        Text textNode = document.createTextNode(container.getValue());
        return textNode;
    }

    private CDATASection setCdataNode() {
        CDATASection cdataSection = document.createCDATASection(container.getValue());
        return cdataSection;
    }

    private void setNodeAttributes(Element element) {
        if (container.hasAttributes()) {
            Map<String, Object> attributes = container.getAttributes();
            for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                element.setAttribute(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
    }

    private void setChildNodes(Element element) {
        List childContainers = container.getChildren();
        for (Object childContainer : childContainers) {
            new XmlNodeBuilder(document, (Container<String>) childContainer, element).build();
        }
    }
}

