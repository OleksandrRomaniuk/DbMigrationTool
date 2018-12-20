package com.dbbest.dbmigrationtool.containers;

import java.util.Iterator;

/**
 * Resizable implementation of the dbList and ListIterable interfaces.
 */
public class ListOfChildren implements DbList, ListIterable {

    private Object[] list;
    private int currentIndicator = 0;
    private boolean trigger = false;
    private int lastReturned = 0;

    public ListOfChildren() {
        list = new Object[]{null};
    }

    @Override
    public void add(Object element) {
        if (element != null) {
            addNewElement(element);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public boolean isEmpty() {
        return (list == null || list.length == 0);
    }

    private void addNewElement(Object element) {

        if (list[0] == null) {
            list[0] = element;
        } else {
            Object[] temp = list;
            list = new Object [list.length + 1];

            for (int i = 0; i < list.length; i++) {
                if (i < temp.length) {
                    list[i] = temp[i];
                } else {
                    list[i] = element;
                    break;
                }
            }
        }
    }

    @Override
    public void clear() {
        list = new Object[1];
    }

    @Override
    public boolean remove(Object element) {
        Object[] temp;
        int skipper = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(element)) {
                temp = new Object[list.length - 1];
                for (int j = 0; j < list.length; j++) {
                    if (j != i) {
                        temp[j - skipper] = list[j];
                    } else {
                        skipper = 1;
                    }
                }
                list = temp;
                return true;
            }
        }

        return false;
    }

    @Override
    public Object[] toArray() {
        Object[] temp = new Object[size()];
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null) {
                temp[i] = list[i];
            } else {
                return temp;
            }
        }
        return temp;
    }

    @Override
    public int size() {
        return list.length;
    }

    @Override
    public boolean contains(Object element) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(DbList element) {
        boolean contains = true;
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(element.toArray()[i])) {
                contains = true;
            }
        }
        return contains;
    }

    @Override
    public String toString() {
        if (list[0] != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.length; i++) {
                if (list[i] != null) {
                    sb.append(list[i].toString() + ";");
                }
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    @Override
    public Iterator iterator() {
        IteratorImpl iterator = new IteratorImpl();

        return iterator;
    }

    private class IteratorImpl implements Iterator {

        @Override
        public boolean hasNext() { // returns true if the iteration has more elements
            return currentIndicator < size();
        }

        @Override
        public Object next() { // returns the next element in the iteration
            trigger = true;
            if (currentIndicator < 0) {
                currentIndicator = 0;
            }
            lastReturned = currentIndicator;
            return list[currentIndicator++];
        }

        @Override
        public void remove() { // removes from the underlying collection the last element returned by this
            if (trigger) {
                if (currentIndicator < 0) {
                    ListOfChildren.this.remove(list[0]);
                } else if (currentIndicator > (size() - 1)) {
                    ListOfChildren.this.remove(list[size() - 1]);
                } else {
                    ListOfChildren.this.remove(list[currentIndicator]);
                }
                trigger = false;

            } else {
                throw new IllegalStateException();
            }
        }
    }

    @Override
    public ListIterator listIterator() {
        return new ListIteratorImpl();
    }

    private class ListIteratorImpl extends IteratorImpl implements ListIterator {

        @Override
        public boolean hasPrevious() {
            return currentIndicator > -1;
        }

        @Override
        public Object previous() {
            trigger = true;
            if (currentIndicator > (size() - 1)) {
                currentIndicator = (size() - 1);
            }
            lastReturned = currentIndicator;
            return list[currentIndicator--];
        }

        @Override
        public void set(Object element) {
            list[lastReturned] = element;

        }
    }
}

