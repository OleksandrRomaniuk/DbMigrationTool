package com.dbbest.dbmigrationtool.containers;

import java.util.Arrays;
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
    public DbList searchInNames(String enteredText) {
        DbList list = new ListOfChildren();
        list.add(getRootContainer());
        searchElementInContainerByVerticalPassage(getPredicateByName(), list);
        return getListOfFoundElements();
    }

    @Override
    public DbList searchInValues(String enteredText) {
        DbList list = new ListOfChildren();
        list.add(getRootContainer());
        searchElementInContainerByVerticalPassage(getPredicateByValue(), list);
        return getListOfFoundElements();
    }

    @Override
    public DbList searchInKeyValues(String enteredText) {
        DbList list = new ListOfChildren();
        list.add(getRootContainer());
        searchElementInContainerByVerticalPassage(getPredicateByKeyValue(), list);
        return getListOfFoundElements();
    }

    private void searchElementInContainerByVerticalPassage(Predicate<Container> predicate, DbList containers) {

        Container[] listOfFoundItems = (Container[]) Arrays.stream((Container[]) containers.toArray()).filter(predicate).toArray();
        for (Container container : listOfFoundItems) {
            addFoundItemToList(container);
        }

        for (Object con : containers) {

            if (((Container) con).hasChildren()) {
                searchElementInContainerByVerticalPassage(predicate, (((Container) con).getChildren()));
            }
        }
    }
}

