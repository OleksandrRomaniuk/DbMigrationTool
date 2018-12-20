package com.dbbest.dbmigrationtool.containers;

import com.dbbest.dbmigrationtool.exceptions.ContainerException;

import java.util.HashMap;
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

    private DbList children;
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
    public Container(String name, V value, Map<String, Object> attributes, DbList children) {
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

    public DbList getChildren() {
        return children;
    }

    public void setChildren(DbList children) {
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
        return  (parent != null);
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

    /**
     * Checks if the tree is valid.
     */
    public void treeIsValid() throws ContainerException {
        for (Object child : this.getChildren()) {
            if (!((Container) child).hasParent()) {
                throw new ContainerException("The child has no parent.");
            }
            else if (!((Container) child).getParent().equals(this)) {
                throw new ContainerException("The parent of the child is not equal to its actual parent.");
            }
        }
        if (this.getName() == null && this.getValue() == null) {
            throw new ContainerException("Both name and value of the container are null.");
        }

        for (Map.Entry<String, Object> entry: this.getAttributes().entrySet()) {
            if (entry.getKey() == null && entry.getValue() == null) {
                throw new ContainerException("Both the key and value of the container are null.");
            }
        }
    }
}
