package com.dbbest.dbmigrationtool.containers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *An extension of the SearchManager class. Realizes the method search.
 * Search is performed with the horizontal passage.
 */
public class HorizontalPassageSearchManager extends SearchManager {

    public HorizontalPassageSearchManager(Container rootContainer) {
        super(rootContainer);
    }

    @Override
    public DbList searchInNames(String enteredText) {
        DbList list = new ListOfChildren();
        list.add(getRootContainer());
        searchElementInContainerByHorizontalPassage(getPredicateByName(), list);
        return getListOfFoundElements();
    }

    @Override
    public DbList searchInValues(String enteredText) {
        DbList list = new ListOfChildren();
        list.add(getRootContainer());
        searchElementInContainerByHorizontalPassage(getPredicateByValue(), list);
        return getListOfFoundElements();
    }

    @Override
    public DbList searchInKeyValues(String enteredText) {
        DbList list = new ListOfChildren();
        list.add(getRootContainer());
        searchElementInContainerByHorizontalPassage(getPredicateByKeyValue(), list);
        return getListOfFoundElements();
    }

    private void searchElementInContainerByHorizontalPassage(Predicate<Container> predicate, DbList containers) {
        DbList allChildrenOfNextLevel = new ListOfChildren();

        Container[] listOfFoundItems = (Container[]) Arrays.stream((Container[])containers.toArray()).filter(predicate).toArray();

        for (Container container:listOfFoundItems) {
            addFoundItemToList(container);
        }

        for (Object con : containers) {

            if (((Container) con).hasChildren()) {
                for (Object cont : ((Container) con).getChildren()) {
                    allChildrenOfNextLevel.add(cont);
                }
            }
        }

        if (!allChildrenOfNextLevel.isEmpty()) {
            searchElementInContainerByHorizontalPassage(predicate, allChildrenOfNextLevel);
        }
    }
}

