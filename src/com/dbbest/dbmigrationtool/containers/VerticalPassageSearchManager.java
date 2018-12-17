package com.dbbest.dbmigrationtool.containers;

import java.util.ArrayList;
import java.util.List;

public class VerticalPassageSearchManager extends SearchManager {

    public VerticalPassageSearchManager(Container<String, String> rootContainer) {
        super(rootContainer);
    }

    @Override
    public List search(String enteredText) {
        List<Container<String, String>> list = new ArrayList();
        list.add(getRootContainer());
        searchElementInContainerByVerticalPassage(enteredText, list);
        return getListOfFoundElements();
    }

    private void searchElementInContainerByVerticalPassage(String enteredText, List<Container<String, String>> containers) {

        for (Container<String, String> con : containers) {
            checkIfContains(enteredText, con);
            if (con.hasChildren()) {
                searchElementInContainerByVerticalPassage(enteredText, con.getChildren());
            }
        }
    }
}
