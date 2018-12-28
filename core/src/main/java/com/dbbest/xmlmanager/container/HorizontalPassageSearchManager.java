package com.dbbest.xmlmanager.container;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * An extension of the SearchManager class. Realizes the method search.
 * Search is performed with the horizontal passage.
 */
public class HorizontalPassageSearchManager extends SearchManager {

    public HorizontalPassageSearchManager(Container rootContainer) {
        super(rootContainer);
    }

    @Override
    public List<Container> searchInNames(String enteredText) {
        super.setEnteredText(enteredText);
        List<Container> list = new ArrayList();
        list.add(super.getRootContainer());
        searchElementInContainerByHorizontalPassage(getPredicateByName(), list);
        return super.getListOfFoundElements();
    }

    @Override
    public List<Container> searchInValues(String enteredText) {
        super.setEnteredText(enteredText);
        List<Container> list = new ArrayList();
        list.add(getRootContainer());
        searchElementInContainerByHorizontalPassage(getPredicateByValue(), list);
        return super.getListOfFoundElements();
    }

    @Override
    public List<Container> searchInKeyValues(String attributeKey, String attributeValue) {
        super.setAttributes(attributeKey, attributeValue);
        List<Container> list = new ArrayList();
        list.add(getRootContainer());
        searchElementInContainerByHorizontalPassage(getPredicateByKeyValue(), list);
        return super.getListOfFoundElements();
    }

    private void searchElementInContainerByHorizontalPassage(Predicate<Container> predicate, List<Container> containers) {

        searchInContainers(predicate, containers);

        List allChildrenOfNextLevel = new ArrayList();

        for (Container con : containers) {
            if (con.hasChildren()) {

                DbList<Container> childList = con.getChildren();

                for (Container cont : childList) {

                    allChildrenOfNextLevel.add(cont);
                }
            }
        }

        if (!allChildrenOfNextLevel.isEmpty()) {
            searchElementInContainerByHorizontalPassage(predicate, allChildrenOfNextLevel);
        }
    }
}

