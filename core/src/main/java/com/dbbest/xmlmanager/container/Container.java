package com.dbbest.xmlmanager.container;

import com.dbbest.databasemanager.loadingmanager.constants.TagNamesConstants;
import com.dbbest.exceptions.ContainerException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * An object which contains a structured tree of names and values. The object maps names to values.
 * Names and values can be null.
 * The object can have attributes and children.
 * Attributes consist of names mapped to values.
 * The values of the object are parametrized.
 *
 * @param <V> the type of the object's value
 */
public class Container<V> {

    private List<Container<V>> children;
    private String name;
    private V value;
    private Map<String, Object> attributes;
    private Container<V> parent;

    public Container() {
    }

    public Container(String name) {
        this.name = name;
    }

    public Container(String name, V value) {
        this(name);
        this.value = value;
    }

    public Container(String name, V value, Map<String, Object> attributes) {
        this(name, value);
        this.attributes = attributes;
    }

    /**
     * @param name       the name of the element. The name is of the type String.
     * @param value      the value of the element, the value is parametrized by V1.
     * @param attributes the map which contains attributes which consist of name-value pairs. The name is of the type String.
     *                   The value is parametrized by V2.
     * @param children   represents the list of child elements of the type Container.
     */
    public Container(String name, V value, Map<String, Object> attributes, List<Container<V>> children) {
        this(name, value, attributes);
        this.children = children;
        this.value = value;
        this.attributes = attributes;
    }

    /**
     * The method checks out if the element has attributes.
     *
     * @return true if the current element has attributes.
     */
    public boolean hasAttributes() {
        return attributes != null && !attributes.isEmpty();
    }

    /**
     * The method checks out if the element has children.
     *
     * @return true if the current element has children.
     */
    public boolean hasChildren() {
        return children != null && !children.isEmpty();
    }

    /**
     * The method checks out if the element has a value.
     *
     * @return true if the current element has a value.
     */
    public boolean hasValue() {
        return value != null && !value.equals("");
    }

    /**
     * The method checks out if the element has a name.
     *
     * @return true if the current element has a name.
     */
    public boolean hasName() {
        return name != null && !name.equals("");
    }

    public String getName() {
        return name;
    }

    /**
     * @param name the name to be set bto the container.
     * @throws ContainerException the exception to thrown if the name is not null.
     */
    public void setName(String name) throws ContainerException {
        if (this.name != null) {
            throw new ContainerException(Level.SEVERE, "The value is not null.");
        } else {
            this.name = name;
        }
    }

    public V getValue() {
        return value;
    }

    /**
     * @param value the value to be set to the container.
     * @throws ContainerException the exception to be thrown if the value is not null.
     */
    public void setValue(V value) throws ContainerException {
        if (this.value != null) {
            throw new ContainerException(Level.SEVERE, "The value is not null.");
        } else {
            this.value = value;
        }
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to be set.
     * @throws ContainerException the axception to be thrown if the attributes are not null.
     */
    public void setAttributes(Map<String, Object> attributes) throws ContainerException {
        if (this.attributes != null) {
            throw new ContainerException(Level.SEVERE, "The attributes are not null.");
        } else {
            this.attributes = attributes;
        }
    }

    public List<Container<V>> getChildren() {
        return children;
    }

    public Container<V> getChildByName(String childName) throws ContainerException {
        for (Container child: children) {
            if (child.getName().equals(childName)) {
                return child;
            }
        }
        throw new ContainerException(Level.SEVERE, "Can not find the container with the name " + childName);
    }

    /**
     * @param children the list of children to be set.
     * @throws ContainerException the exception to be thrown if the list of children is not null.
     */
    public void setChildren(List<Container<V>> children) throws ContainerException {
        if (this.children != null) {
            throw new ContainerException(Level.SEVERE, "The list of children is not null.");
        } else {
            this.children = children;
        }
    }

    public Container<V> getParent() {
        return parent;
    }

    /**
     * @param parent the parent to be set to the container.
     * @throws ContainerException the exception to be thrown if the parent is not null.
     */
    public void setParent(Container<V> parent) throws ContainerException {
        if (this.parent != null) {
            throw new ContainerException(Level.SEVERE, "The parent of the container is not null.");
        } else {
            this.parent = parent;
        }
    }

    /**
     * Adds a new child for the current element of the tree.
     *
     * @param container the child to be added.
     */
    public void addChild(Container<V> container) {
        if (children == null) {
            children = new ListOfChildren();
        }
        children.add(container);
    }

    /**
     * @return returns true if the current element has a parent.
     */
    public boolean hasParent() {
        return (parent != null);
    }

    /**
     * Adds a new attribute for the current tree element.
     *
     * @param key   the key of the attribute to be added.
     * @param value the value of the attribute to be added.
     */
    public void addAttribute(String key, Object value) {
        if (attributes == null) {
            attributes = new HashMap<String, Object>();
        }
        attributes.put(key, value);
    }

    public List<Container> searchInTreeHorizontalSearchInNames(String textToSearch) {
        return new HorizontalPassageSearchManager(this).searchInNames(textToSearch);
    }

    public List<Container> searchInTreeHorizontalSearchInValues(String textToSearch) {
        return new HorizontalPassageSearchManager(this).searchInValues(textToSearch);
    }

    public List<Container> searchInTreeHorizontalSearchInKeyValues(String attributeKey, String attributeValue) {
        return new HorizontalPassageSearchManager(this).searchInKeyValues(attributeKey, attributeValue);
    }

    public List<Container> searchInTreeVerticalSearchInNames(String textToSearch) {
        return new VerticalPassageSearchManager(this).searchInNames(textToSearch);
    }

    public List<Container> searchInTreeVerticalSearchInValues(String textToSearch) {
        return new VerticalPassageSearchManager(this).searchInValues(textToSearch);
    }

    public List<Container> searchInTreeVerticalSearchInKeyValues(String attributeKey, String attributeValue) {
        return new VerticalPassageSearchManager(this).searchInKeyValues(attributeKey, attributeValue);
    }
}

