package com.dbbest.xmlmanager.container;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private DbList<Container<V>> children;
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
    public Container(String name, V value, Map<String, Object> attributes, DbList<Container<V>> children) {
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

    public void setName(String name) {
        this.name = name;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public DbList<Container<V>> getChildren() {
        return children;
    }

    public void setChildren(DbList<Container<V>> children) {
        this.children = children;
    }

    public Container<V> getParent() {
        return parent;
    }

    public void setParent(Container<V> parent) {
        this.parent = parent;
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
