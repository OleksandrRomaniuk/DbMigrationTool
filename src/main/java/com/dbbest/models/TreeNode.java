package com.dbbest.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeNode {
    private String label;
    private String value;
    private boolean checked;
    private boolean expanded;
    private List<TreeNode> children;
    private int checkedState;
    private Map<String, Object> attributes;

    public TreeNode() {
        children = new ArrayList<>();
        attributes = new HashMap<>();
    }

    public TreeNode(String label, String value, boolean checked, boolean expanded, List<TreeNode> children, int checkedState) {
        this.label = label;
        this.value = value;
        this.checked = checked;
        this.expanded = expanded;
        this.children = children;
        this.checkedState = checkedState;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public int getCheckedState() {
        return checkedState;
    }

    public void setCheckedState(int checkedState) {
        this.checkedState = checkedState;
    }
}
