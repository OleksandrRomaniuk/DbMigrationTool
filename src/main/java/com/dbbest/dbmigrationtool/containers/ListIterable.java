package com.dbbest.dbmigrationtool.containers;

/**
 *Implements List Iterator.
 * @param <T> the type of elements of the list.
 */
public interface ListIterable<T> {

    ListIterator<T> listIterator();

}
