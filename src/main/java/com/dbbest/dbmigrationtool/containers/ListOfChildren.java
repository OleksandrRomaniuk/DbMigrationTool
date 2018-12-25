package com.dbbest.dbmigrationtool.containers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Resizable implementation of the dbList and ListIterable interfaces.
 * @param <T> the type of elements of the list.
 */
public class ListOfChildren<T> implements DbList<T>, ListIterable<T> {


    private T[] list;
    private int currentIndicator = 0;
    private boolean trigger = false;
    private int lastReturned = 0;

    public ListOfChildren() {
        list = (T[]) new Object[] {null};
    }

    @Override
    public void add(T element) {
        if (element != null) {
            addNewElement(element);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return returns true if the list has no elements.
     */
    public boolean isEmpty() {

        if (list == null || list.length == 0) {
            return true;
        } else {
            for (int i = 0; i < list.length; i++) {
                return !(list[i] != null);
            }
        }
        return true;
    }

    private void addNewElement(T element) {

        if (list[0] == null) {
            list[0] = element;
        } else {
            T[] temp = list;
            list = (T[]) new Object[list.length + 1];

            for (int i = 0; i < list.length; i++) {
                if (i < temp.length) {
                    list[i] = (T) temp[i];
                } else {
                    list[i] = element;
                    break;
                }
            }
        }
    }

    @Override
    public void clear() {
        list = (T[]) new Object[1];
    }

    @Override
    public boolean remove(T element) {
        T[] temp;
        int skipper = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(element)) {
                temp = (T[]) new Object[list.length - 1];
                for (int j = 0; j < list.length; j++) {
                    if (j != i) {
                        temp[j - skipper] = list[j];
                    } else {
                        skipper = 1;
                    }
                }
                list = (T[]) temp;
                return true;
            }
        }

        return false;
    }

    @Override
    public Object[] toArray() {
        /*(Class<T[]> instanceClass) throws NoSuchMethodException, IllegalAccessException,
        InvocationTargetException, InstantiationException {
        T[] test = instanceClass.getDeclaredConstructor(instanceClass).newInstance();
        test[0] = (T) 1;*/

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
    public boolean contains(T element) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(DbList element) {
        for (int i = 0; i < list.length; i++) {
            int counter = 0;
            for (int j = 0; j < element.size(); j++) {
                if (list[i].equals(element.item(j))) {
                    counter++;
                }
            }
            if (counter == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public T item(int index) {
        return (T) list[index];
    }

    @Override
    public boolean set(T element, int index) {
        if (index < size()) {
            list[index] = element;
            return true;
        } else {
            return false;
        }
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
    public Iterator<T> iterator() {
        return new IteratorImpl<T>();
    }

    private class IteratorImpl<T> implements Iterator<T> {

        @Override
        public boolean hasNext() { // returns true if the iteration has more elements
            return currentIndicator < size();
        }

        @Override
        public T next() { // returns the next element in the iteration
            trigger = true;
            if (currentIndicator < 0) {
                currentIndicator = 0;
            }
            lastReturned = currentIndicator;
            return (T) list[currentIndicator++];
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
    public ListIterator<T> listIterator() {
        return new ListIteratorImpl<T>();
    }

    private class ListIteratorImpl<T1> extends IteratorImpl implements ListIterator<T1> {

        @Override
        public boolean hasPrevious() {
            return currentIndicator > -1;
        }

        @Override
        public T1 previous() {
            trigger = true;
            if (currentIndicator > (size() - 1)) {
                currentIndicator = (size() - 1);
            }
            lastReturned = currentIndicator;
            return (T1) list[currentIndicator--];
        }

        @Override
        public void set(T1 element) {
            ListOfChildren.this.set((T) element, lastReturned);

        }
    }
}

