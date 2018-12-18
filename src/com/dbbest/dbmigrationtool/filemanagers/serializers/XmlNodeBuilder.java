package com.dbbest.dbmigrationtool.filemanagers.serializers;

import com.dbbest.dbmigrationtool.containers.Container;
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
    private Container<String, String> container;

    public XmlNodeBuilder(Document document, Container<String, String> container) {
        this.document = document;
        this.container = container;
    }

    /**
     * @param document the document retrieved from the xml serializer to put elements got from the container.
     * @param container the container which is treated by the node builder.
     * @param parentElement the element created from the parent container.
     */
    public XmlNodeBuilder(Document document, Container<String, String> container, Element parentElement) {
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

    public Element getParentElement() {
        return parentElement;
    }

    private boolean isElementNode(Container<String, String> container) {
        if (container.hasName()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isTextNode(Container<String, String> container) {
        if (!container.hasName() && !meetCdataRegex(container.getValue())
            && !container.hasAttributes() && !container.hasChildren()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isCdataNode(Container<String, String> container) {
        if (!container.hasName() && meetCdataRegex(container.getValue())
            && !container.hasAttributes() && !container.hasChildren()) {
            return true;
        } else {
            return false;
        }
    }

    public Document getDocument() {
        return document;
    }

    private boolean meetCdataRegex(String nodeValue) {
        if (matchs(nodeValue)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean matchs(String input) {
        Pattern pattern = Pattern.compile("<|&|\\'.*\\'|\\\".*\\\"");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    private Element setElementNode() {
        System.out.println(container.getName());
        Element elementNode = document.createElement(container.getName());
        if (container.hasValue()) {
            System.out.println(container.getValue());
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
        System.out.println(container.getValue());
        Text textNode = document.createTextNode(container.getValue());
        return textNode;
    }

    private CDATASection setCdataNode() {
        System.out.println(container.getValue());
        CDATASection cdataSection = document.createCDATASection(container.getValue());
        return cdataSection;
    }

    private void setNodeAttributes(Element element) {
        if (container.hasAttributes()) {
            Map<String, String> attributes = container.getAttributes();
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                element.setAttribute(entry.getKey(), entry.getValue());
            }
        }
    }

    private void setChildNodes(Element element) {
        List<Container<String, String>> childContainers = container.getChildren();
        for (Container<String, String> childContainer : childContainers) {
            new XmlNodeBuilder(document, childContainer, element).build();
        }
    }
}
