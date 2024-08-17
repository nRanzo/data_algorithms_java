package lists;

import java.util.Iterator;

/**
 * Interface that defines the basic methods of a collection.
 * @param <E>
 */
public interface Collection<E> extends Iterable<E> {

    /**
     * Adds an element to the collection.
     *
     * @param o the element to add
     * @return true if the element was successfully added, otherwise false
     */
    boolean add(Object o);

    /**
     * Adds all elements of the specified collection to this collection.
     *
     * @param c the collection containing the elements to add
     * @return true if all elements were successfully added, otherwise false
     */
    boolean addAll(Collection<E> c);

    /**
     * Removes all elements from the collection.
     */
    void clear();

    /**
     * Checks if the collection contains the specified element.
     *
     * @param o the element to search for in the collection
     * @return true if the collection contains the element, otherwise false
     */
    boolean contains(Object o);

    /**
     * Checks if the collection contains all elements of the specified collection.
     *
     * @param c the collection containing the elements to search for
     * @return true if the collection contains all elements, otherwise false
     */
    boolean containsAll(Collection<E> c);

    /**
     * Compares this collection with the specified object for equality.
     *
     * @param o the object to compare with
     * @return true if the collections are equal, otherwise false
     */
    boolean equals(Object o);

    /**
     * Returns the hash code of the collection.
     *
     * @return the hash code of the collection
     */
    int hashCode();

    /**
     * Checks if the collection is empty.
     *
     * @return true if the collection is empty, otherwise false
     */
    boolean isEmpty();

    /**
     * Returns an iterator over the collection.
     *
     * @return an iterator over the collection
     */
    Iterator<E> iterator();

    /**
     * Removes the specified element from the collection.
     *
     * @param o the element to remove
     * @return true if the element was successfully removed, otherwise false
     */
    boolean remove(Object o);

    /**
     * Removes all elements of the specified collection from this collection.
     *
     * @param c the collection containing the elements to remove
     * @return true if all elements were successfully removed, otherwise false
     */
    boolean removeAll(Collection<E> c);

    /**
     * Retains only the elements in this collection that are also contained in the specified collection.
     *
     * @param c the collection containing the elements to retain
     * @return true if the collection was modified, otherwise false
     * @throws ArrayIndexOutOfBoundsException if an exception occurs during the operation
     */
    boolean retainAll(Collection<E> c) throws ArrayIndexOutOfBoundsException;

    /**
     * Returns the number of elements in the collection.
     *
     * @return the number of elements in the collection
     */
    int size();

    /**
     * Returns an array containing all elements in the collection.
     *
     * @return an array containing all elements in the collection
     */
    Object[] toArray();

    /**
     * Returns an array containing all elements in the collection,
     * using the specified array if it is sufficiently large;
     * otherwise, it creates a new one of type Object[].
     *
     * @param a the array in which to place the collection's elements
     * @return an array containing all elements in the collection
     */
    Object[] toArray(Object[] a);
}
