package com.dbbest.xmlmanager.container;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * The SearchManager is the abstract base class of all classes which perform searches in trees which are saved in the container.
 */
public abstract class SearchManager {

    private Container rootContainer;

    private List<Container> listOfFoundElements = new ArrayList();

    private String enteredText;
    private String attributeKey;
    private String attributeValue;

    private Predicate<Container> predicateByName = new Predicate<Container>() {
        @Override
        public boolean test(Container container) {
            if (!container.hasLabel()) {
                return false;
            } else {
                return (String.valueOf(container.getName()).toLowerCase().contains(enteredText.toLowerCase()));
            }
        }
    };

    //container -> container.hasLabel()&& container.getName().toLowerCase().contains(enteredText.toLowerCase());

    private Predicate<Container> predicateByValue = new Predicate<Container>() {
        @Override
        public boolean test(Container container) {
            return (container.hasValue() && String.valueOf(container.getValue()).toLowerCase().equals(enteredText.toLowerCase()));
        }
    };
    //container -> container.hasValue() && String.valueOf(container.getValue()).toLowerCase().contains(enteredText.toLowerCase());

    private Predicate<Container> predicateByKeyValue = new Predicate<Container>() {
        @Override
        public boolean test(Container container) {
            if (container.hasAttributes()) {
                Map<String, Object> attr = container.getAttributes();
                for (Map.Entry<String, Object> entry: attr.entrySet()) {
                    if (entry.getKey().toLowerCase().equals(attributeKey.toLowerCase())
                        && entry.getValue().toString().toLowerCase().equals(attributeValue.toLowerCase())) {
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

    protected List<Container> getListOfFoundElements() {
        return listOfFoundElements;
    }

    protected void addFoundItemToList(Container container) {
        listOfFoundElements.add(container);
    }

    public abstract List<Container> searchInNames(String enteredText);

    public abstract List<Container> searchInValues(String enteredText);

    public abstract List<Container> searchInKeyValues(String attributeKey, String attributeValue);

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

    protected void searchInContainers(Predicate<Container> predicate, List<Container> containers) {
        containers.stream().filter(predicate).forEach(this::addFoundItemToList);
    }

    public void setAttributes(String attributeKey, String attributeValue) {
        this.attributeKey = attributeKey;
        this.attributeValue = attributeValue;
    }
}

