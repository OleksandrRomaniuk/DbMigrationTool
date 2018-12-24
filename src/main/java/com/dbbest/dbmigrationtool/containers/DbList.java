package com.dbbest.dbmigrationtool.containers;

/**
 *A list which may contain any element of the parametrized type T.
 */
public interface DbList<T> extends Iterable<T>, ListIterable<T>  {

    /**
     * Checks if the list is empty.
     * @return returns true if the list if empty.
     */
    boolean isEmpty();

    /**
     * Appends the specified element to the end of this list.
     * @param element the specified element to be added.
     */
    void add(T element);

    /**
     * Removes all of the elements from this list.
     */
    void clear();

    /**
     *  Removes the first occurrence of the specified element from this list.
     * @param element the element to be removed.
     * @return true if the element is removed.
     */
    boolean remove(T element);

    /**
     * Returns an array containing all of the elements in this list in proper sequence.
     * @return
     */
    Object[] toArray();

    /**
     * Returns the number of elements in this list.
     * @return the number of elements in this list.
     */
    int size();

    /**
     * Returns true if this list contains the specified element.
     * @param element the element to checked if is contained in the list.
     * @return true if this list contains the specified element.
     */
    boolean contains(T element);

    /**
     * Returns true if this list contains all of the elements of the specified list.
     * @param list the list of elements to be checked if are contained in the list.
     * @return true if this list contains all of the elements of the specified list.
     */
    boolean containsAll(DbList<T> list);

    T item(int index);

    boolean set(T element, int index);
}
