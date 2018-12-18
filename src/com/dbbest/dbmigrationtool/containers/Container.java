package com.dbbest.dbmigrationtool.containers;

import java.util.List;
import java.util.Map;

/**
* An object which contains a structured tree of names and values. The object maps names to values.
* Names and values can be null.
* The object can have attributes and children.
* Attributes consist of names mapped to values.
* The values of the object are parametrized.
 * @param <V1> the type of the object's value
 * @param <V2> the type of the attribute's value
 */
public class Container<V1, V2> {


    private List<Container<V1, V2>> children;
    private String name;
    private V1 value;
    private Map<String, V2> attributes;

    public Container() {
    }

    public Container(String name) {
        this.name = name;
    }

    public Container(String name, V1 value) {
        this(name);
        this.value = value;
    }

    public Container(String name, V1 value, Map<String, V2> attributes) {
        this(name, value);
        this.attributes = attributes;
    }

    /**
     * @param name the name of the element. The name is of the type String.
     * @param value the value of the element, the value is parametrized by V1.
     * @param attributes the map which contains attributes which consist of name-value pairs. The name is of the type String.
     *                   The value is parametrized by V2.
     * @param children represents the list of child elements of the type Container.
     */
    public Container(String name, V1 value, Map<String, V2> attributes, List<Container<V1, V2>> children) {
        this(name, value, attributes);
        this.children = children;
        this.value = value;
        this.attributes = attributes;
    }

    /**
     * The method checks out if the element has children.
     * @return true if the current element has children.
     */
    public boolean hasChildren() {
        if (children != null && !children.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * The method checks out if the element has attributes.
     * @return true if the current element has attributes.
     */
    public boolean hasAttributes() {
        if (attributes != null && !attributes.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * The method checks out if the element has a value.
     * @return true if the current element has a value.
     */
    public boolean hasValue() {
        if (value != null && !value.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * The method checks out if the element has a name.
     * @return true if the current element has a name.
     */
    public boolean hasName() {
        if (name != null && !name.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public V1 getValue() {
        return value;
    }

    public void setValue(V1 value) {
        this.value = value;
    }

    public Map<String, V2> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, V2> attributes) {
        this.attributes = attributes;
    }

    public List<Container<V1, V2>> getChildren() {
        return children;
    }

    public void setChildren(List<Container<V1, V2>> children) {
        this.children = children;
    }
}
