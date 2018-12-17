package com.dbbest.dbmigrationtool.containers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class SearchManager {

    private Container<String, String> rootContainer;

    private List<Container<String, String>> listOfFoundElements = new ArrayList();

    SearchManager(Container<String, String> rootContainer) {
        this.rootContainer = rootContainer;
    }

    public Container<String, String> getRootContainer() {
        return rootContainer;
    }

    protected List<Container<String, String>> getListOfFoundElements() {
        return listOfFoundElements;
    }

    public abstract List search(String enteredText);

    protected void checkIfContains(String enteredText, Container<String, String> con) {
        if (con.hasName() && con.getName().toLowerCase().contains(enteredText.toLowerCase())) {
            listOfFoundElements.add(con);
        } else if (con.hasValue() && con.getValue().toLowerCase().contains(enteredText.toLowerCase())) {
            listOfFoundElements.add(con);
        } else if (con.hasAttributes() && checkIfAttrubutesContain(enteredText, con)) {
            listOfFoundElements.add(con);
        }
    }

    protected boolean checkIfAttrubutesContain(String enteredText, Container<String, String> con) {
        Map<String, String> attributes = con.getAttributes();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            if (entry.getKey().toLowerCase().contains(enteredText.toLowerCase())
                || entry.getValue().toLowerCase().contains(enteredText.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
