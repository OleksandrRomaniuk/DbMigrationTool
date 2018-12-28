package com.dbbest.xmlmanager.container;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * An extension of the SearchManager class. Realizes the method search.
 * Search is performed with the vertical passage.
 */
public class VerticalPassageSearchManager extends SearchManager {

    public VerticalPassageSearchManager(Container rootContainer) {
        super(rootContainer);
    }

    @Override
    public List searchInNames(String enteredText) {
        super.setEnteredText(enteredText);
        List list = new ArrayList();
        list.add(getRootContainer());
        searchElementInContainerByVerticalPassage(getPredicateByName(), list);
        return getListOfFoundElements();
    }

    @Override
    public List searchInValues(String enteredText) {
        super.setEnteredText(enteredText);
        List list = new ArrayList();
        list.add(getRootContainer());
        searchElementInContainerByVerticalPassage(getPredicateByValue(), list);
        return getListOfFoundElements();
    }

    @Override
    public List searchInKeyValues(String attributeKey, String attributeValue) {
        super.setAttributes(attributeKey, attributeValue);
        List list = new ArrayList();
        list.add(getRootContainer());
        searchElementInContainerByVerticalPassage(getPredicateByKeyValue(), list);
        return getListOfFoundElements();
    }

    private void searchElementInContainerByVerticalPassage(Predicate<Container> predicate, List<Container> containers) {

        searchInContainers(predicate, containers);

        for (Container con : containers) {
            if (con.hasChildren()) {
                List<Container> children = new ArrayList();
                DbList<Container> childList = con.getChildren();
                for (Container child: childList) {
                    children.add(child);
                }
                searchElementInContainerByVerticalPassage(predicate, children);
            }
        }
    }
}

