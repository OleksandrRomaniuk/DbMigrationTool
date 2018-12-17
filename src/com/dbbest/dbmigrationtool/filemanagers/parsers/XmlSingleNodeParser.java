package com.dbbest.dbmigrationtool.filemanagers.parsers;

import com.dbbest.dbmigrationtool.containers.Container;
import org.w3c.dom.CharacterData;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlSingleNodeParser {

    private Container<String, String> container;
    private Node node;

    public XmlSingleNodeParser(Container<String, String> container, Node node) {
        this.container = container;
        this.node = node;
    }

    public Container<String, String> getContainer() {

        return container;
    }

    public Node getNode() {

        return node;
    }

    public XmlSingleNodeParser parse() {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            setElementNode();
        } else if (node.getNodeType() == Node.CDATA_SECTION_NODE) {
            setCdataNode();
        } else if (node.getNodeType() == Node.TEXT_NODE) {
            setTextNode();
        }

        return this;
    }

    private void setElementNode() {
        setContainerName();
        setContainerValue(node.getNodeValue());
        setAttributes();
        setListOfBoxedChildren(node);
    }

    private void setTextNode() {

        setContainerValue(node.getTextContent());
    }

    private void setCdataNode() {

        setContainerValue(((CharacterData) node).getData());
    }

    private void setContainerName() {
        if (node.getNodeName() != null) {
            System.out.println("Node name - " + node.getNodeName() + " - " + node.getNodeType());
            container.setName(node.getNodeName());
        }
    }

    private void setContainerValue(String value) {
        if (value != null) {
            System.out.println("Node value - " + node.getNodeValue());
            container.setValue(value);
        }
    }

    private void setAttributes() {
        if (node.hasChildNodes()) {
            NamedNodeMap attributesMap = node.getAttributes();
            Map<String, String> attributes = new HashMap();
            for (int i = 0; i < attributesMap.getLength(); i++) {
                Node atrNode = attributesMap.item(i);
                System.out.println("Node attributes: " + atrNode.getNodeName() + " - " + atrNode.getNodeValue());
                attributes.put(atrNode.getNodeName(), atrNode.getNodeValue());
            }
            container.setAttributes(attributes);
        }
    }

    private void setListOfBoxedChildren(Node node) {
        if (node.hasChildNodes()) {
            List<Container<String, String>> listOfChildren = new ArrayList();
            NodeList childList = node.getChildNodes();
            for (int i = 0; i < childList.getLength(); i++) {
                Node child = childList.item(i);
                Container<String, String> childContainer = new XmlSingleNodeParser(new Container<String, String>(), child)
                    .parse()
                    .getContainer();

                listOfChildren.add(childContainer);
                container.setChildren(listOfChildren);
            }
        }
    }
}
