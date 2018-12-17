package com.dbbest.dbmigrationtool.containers;

import java.util.List;
import java.util.Map;

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

    public Container(String name, V1 value, Map<String, V2> attributes, List<Container<V1, V2>> children) {
        this(name, value, attributes);
        this.children = children;
        this.value = value;
        this.attributes = attributes;
    }

    public boolean hasChildren() {
        if (children != null && !children.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasAttributes() {
        if (attributes != null && !attributes.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasValue() {
        if (value != null && !value.equals("")) {
            return true;
        } else {
            return false;
        }
    }

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
