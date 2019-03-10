package com.dbbest.utils;

import com.dbbest.databasemanager.dbmanager.constants.CustomAttributes;
import com.dbbest.databasemanager.dbmanager.constants.mysql.annotations.NameConstants;
import com.dbbest.databasemanager.dbmanager.constants.mysql.attributes.*;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeNodeBuilder {

    public Container getTreeNode(Container root) throws ContainerException {
        String label = this.getLabel(root);
        String value = "/" + label;
        this.buildCurrentNode(root, label, value);
        return root;
    }

    private void buildCurrentNode(Container container, String label, String value) throws ContainerException {
        container.setChecked(false);
        container.setExpanded(true);
        container.setLabel(label);
        //container.addAttribute(CustomAttributes.CATEGORY, container.getName());
        container.setValue(value);
        this.buildChildren(container, value);
    }

    private void buildChildren(Container container, String value) throws ContainerException {

        if (container.hasChildren()) {
            List<Container> containerChildren = container.getChildren();
            for (int i = 0; i < containerChildren.size(); i++) {
                String label = this.getLabel(containerChildren.get(i));
                String childValue = value + "/" + label;
                this.buildCurrentNode(containerChildren.get(i), label, childValue);
            }
        } else {
            container.setChildren(new ArrayList<>(0));
        }
    }

    private String getLabel(Container container) {

        if (container.hasAttributes()) {
            if ((boolean) container.getAttributes().get(CustomAttributes.IS_CATEGORY)) {
                return container.getName();
            } else {
                String nameAttribute = this.getNameAttribute(container.getName());
                return (String) container.getAttributes().get(nameAttribute);
            }
        } else {
            return "Schemas";
        }
    }

    private String getNameAttribute(String nodeName) {
        Map<String, String> nameAttributes = new HashMap();
        nameAttributes.put(NameConstants.SCHEMA, SchemaAttributes.SCHEMA_NAME);
        nameAttributes.put(NameConstants.TABLE, TableAttributes.TABLE_NAME);
        nameAttributes.put(NameConstants.VIEW, ViewAttributes.TABLE_NAME);
        nameAttributes.put(NameConstants.STORED_PROCEDURE, FunctionAttributes.FUNCTION_PROCEDURE_NAME);
        nameAttributes.put(NameConstants.FUNCTION, FunctionAttributes.FUNCTION_PROCEDURE_NAME);
        nameAttributes.put(NameConstants.VIEW_COLUMN, TableColumnAttributes.COLUMN_NAME);
        nameAttributes.put(NameConstants.TABLE_COLUMN, TableColumnAttributes.COLUMN_NAME);
        nameAttributes.put(NameConstants.TABLE_INDEXES, IndexAttributes.INDEX_NAME);
        nameAttributes.put(NameConstants.FOREIGN_KEY, ForeignKeyAttributes.FUNCTION_PROCEDURE_NAME);
        nameAttributes.put(NameConstants.TRIGGER, TriggerAttributes.TRIGGER_NAME);
        nameAttributes.put(NameConstants.CONSTRAINT, ConstraintAttributes.CONSTRAINT_NAME);
        nameAttributes.put(NameConstants.INDEX, IndexAttributes.INDEX_NAME);
        nameAttributes.put(NameConstants.PROCEDURE_FUNCTION_PARAMETER, FunctionProcedureParameterAttributes.PROC_FUNC_PARAMETER_NAME);
        return nameAttributes.get(nodeName);
    }
}
