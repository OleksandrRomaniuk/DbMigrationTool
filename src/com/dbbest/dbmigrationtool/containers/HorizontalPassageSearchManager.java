package com.dbbest.dbmigrationtool.containers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HorizontalPassageSearchManager extends SearchManager {

    public HorizontalPassageSearchManager(Container<String, String> rootContainer) {
        super(rootContainer);
    }

    @Override
    public List search(String enteredText) {

        List<Container<String, String>> list = new ArrayList();
        list.add(getRootContainer());
        searchElementInContainerByHoriaontalPassage(enteredText, list);
        return getListOfFoundElements();
    }

    private void searchElementInContainerByHoriaontalPassage(String enteredText, List<Container<String, String>> containers) {
        List<Container<String, String>> allChildrenOfNextLevel = new LinkedList();

        for (Container<String, String> con : containers) {
            checkIfContains(enteredText, con);
            if (con.hasChildren()) {
                for (Container<String, String> cont : con.getChildren()) {
                    allChildrenOfNextLevel.add(cont);
                }
            }
        }

        if (!allChildrenOfNextLevel.isEmpty()) {
            searchElementInContainerByHoriaontalPassage(enteredText, allChildrenOfNextLevel);
        }
    }
}
