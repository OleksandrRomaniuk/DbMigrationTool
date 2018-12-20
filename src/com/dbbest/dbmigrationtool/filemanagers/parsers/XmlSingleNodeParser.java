package com.dbbest.dbmigrationtool.filemanagers.parsers;

import com.dbbest.dbmigrationtool.containers.Container;
import com.dbbest.dbmigrationtool.containers.DbList;
import com.dbbest.dbmigrationtool.containers.ListOfChildren;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.CharacterData;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A class which carries out the parsing of a single node.
 * It is used by other classes to parse and create an appropriate container based on a node.
 * The class accepts the following three types of nodes: Element_node, Cdata_section_node and Text_node.
 */
public class XmlSingleNodeParser {

    /**
     * A method which triggers the process of parsing of a node.
     * @return the method returns the object on which it was evoked. From the returned object the container can be got.
     */
    public Container<String> parse(Container<String> container, Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            setElementNode(container, node);
        } else if (node.getNodeType() == Node.CDATA_SECTION_NODE) {
            setCdataNode(container, node);
        } else if (node.getNodeType() == Node.TEXT_NODE) {
            setTextNode(container, node);
        }

        return container;
    }

    /**
     * A method which triggers the process of parsing of a node.
     * @return the method returns the object on which it was evoked. From the returned object the container can be got.
     */
    public Container<String> parse(Container<String> container, Container<String> parentContainer, Node node) {
        setParentContainer(container, parentContainer);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            setElementNode(container, node);
        } else if (node.getNodeType() == Node.CDATA_SECTION_NODE) {
            setCdataNode(container, node);
        } else if (node.getNodeType() == Node.TEXT_NODE) {
            setTextNode(container, node);
        }

        return container;
    }

    private void setElementNode(Container<String> container, Node node) {

        setContainerName(container, node);
        setContainerValue(container, node.getNodeValue());
        setAttributes(container, node);
        setListOfBoxedChildren(container, node);
    }

    private void setTextNode(Container<String> container, Node node) {
        setContainerValue(container, node.getTextContent());
    }

    private void setCdataNode(Container<String> container, Node node) {
        setContainerValue(container, ((CharacterData) node).getData());
    }

    private void setParentContainer(Container<String> container, Container<String> parentContainer) {
        if (parentContainer != null) {
            container.setParent(parentContainer);
        }
    }

    private void setContainerName(Container<String> container, Node node) {
        if (node.getNodeName() != null) {
            container.setName(node.getNodeName());
        }
    }

    private void setContainerValue(Container<String> container, String value) {
        if (value != null) {
            container.setValue(value);
        }
    }

    private void setAttributes(Container<String> container, Node node) {
        if (node.hasChildNodes()) {
            NamedNodeMap attributesMap = node.getAttributes();
            Map<String, Object> attributes = new HashMap();
            for (int i = 0; i < attributesMap.getLength(); i++) {
                Node atrNode = attributesMap.item(i);
                attributes.put(atrNode.getNodeName(), atrNode.getNodeValue());
            }
            container.setAttributes(attributes);
        }
    }

    private void setListOfBoxedChildren(Container<String> container, Node node) {
        if (node.hasChildNodes()) {
            DbList listOfChildren = new ListOfChildren();
            NodeList childList = node.getChildNodes();
            for (int i = 0; i < childList.getLength(); i++) {
                Node child = childList.item(i);
                Container<String> childContainer = new XmlSingleNodeParser().parse(new Container<String>(), container, child);

                listOfChildren.add(childContainer);
                container.setChildren(listOfChildren);
            }
        }
    }
}

