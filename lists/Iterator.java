package lists;

/**
 * HIterator interface that defines the basic methods of an iterator.
 */
public interface Iterator<E> {

    /**
     * Checks if there are more elements available in the iterator.
     *
     * @return true if there are more elements, otherwise false
     */
    boolean hasNext();

    /**
     * Returns the next element in the iterator.
     *
     * @return the next element in the iterator
     * @throws ArrayIndexOutOfBoundsException if there are no more elements
     */
    Object next() throws ArrayIndexOutOfBoundsException;

    /**
     * Removes the last element returned by the iterator from the underlying collection.
     * Typically called after {@code next()}, it can be called at most once
     * per call to {@code next()}.
     */
    void remove();
}
