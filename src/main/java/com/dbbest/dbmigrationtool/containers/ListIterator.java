package com.dbbest.dbmigrationtool.containers;

import java.util.Iterator;

/**
 * An interface which does navigation in a list.
 */
public interface ListIterator<T> extends Iterator  {

    /**
     * @return returns true if this list iterator has more elements when traversing the list in the reverse direction.
     */
    boolean hasPrevious();

    /**
     * @return returns the previous element in the list and moves the cursor position backwards.
     */
    T previous();

    /**
     * Replaces the last element returned by next or previous with the specified element.
     * @param element the element which will be put.
     */
    void set(T element);

    /**
     * Removes from the list the last element that was returned by next or previous.
     */
    void remove();
}
