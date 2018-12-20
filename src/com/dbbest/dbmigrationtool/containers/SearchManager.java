package com.dbbest.dbmigrationtool.containers;

import java.util.Map;
import java.util.function.Predicate;

/**
 * The SearchManager is the abstract base class of all classes which perform searches in trees which are saved in the container.
 */
public abstract class SearchManager {

    private Container rootContainer;

    private DbList listOfFoundElements = new ListOfChildren();

    private String enteredText;

    private Predicate<Container> predicateByName = container -> container.hasName()
        && container.getName().toLowerCase().contains(enteredText.toLowerCase());

    private Predicate<Container> predicateByValue = container -> container.hasValue()
        && String.valueOf(container.getValue()).toLowerCase().contains(enteredText.toLowerCase());

    private Predicate<Container> predicateByKeyValue = new Predicate<Container>() {
        @Override
        public boolean test(Container container) {
            if (container.hasAttributes()) {
                for (Object entry : container.getAttributes().entrySet()) {
                    if (((String)((Map.Entry)entry).getKey()).toLowerCase().contains(enteredText.toLowerCase())) {
                        return true;
                    }
                }
            }
            return false;
        }
    };

    SearchManager(Container<String> rootContainer) {
        this.rootContainer = rootContainer;
    }

    public Container getRootContainer() {
        return rootContainer;
    }

    protected DbList getListOfFoundElements() {
        return listOfFoundElements;
    }

    protected void addFoundItemToList(Container container) {
        listOfFoundElements.add(container);
    }

    public abstract DbList searchInNames(String enteredText);

    public abstract DbList searchInValues(String enteredText);

    public abstract DbList searchInKeyValues(String enteredText);

    public String getEnteredText() {
        return enteredText;
    }

    public void setEnteredText(String enteredText) {
        this.enteredText = enteredText;
    }

    protected Predicate<Container> getPredicateByName() {
        return predicateByName;
    }

    protected Predicate<Container> getPredicateByValue() {
        return predicateByValue;
    }

    protected Predicate<Container> getPredicateByKeyValue() {
        return predicateByKeyValue;
    }
}

