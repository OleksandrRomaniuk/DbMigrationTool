package com.dbbest.xmlmanager.container;

/**
 *Implements List Iterator.
 * @param <T> the type of elements of the list.
 */
public interface ListIterable<T> {

    ListIterator<T> listIterator();

}
