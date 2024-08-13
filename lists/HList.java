/**
 * HList interface that defines the methods of a list.
 * Extends the HCollection interface to add list-specific operations.
 * @param <E>
 */
public interface HList<E> extends HCollection<E> {

    /**
     * Inserts the specified element into this list at the specified index.
     *
     * @param index   the index at which to insert the element
     * @param element the element to be inserted
     */
    void add(int index, Object element);

    /**
     * Inserts all elements of the specified collection into this list,
     * starting at the specified index.
     *
     * @param index the index at which to start adding elements
     * @param c     the collection containing the elements to be added
     * @return true if all elements were successfully added, otherwise false
     */
    boolean addAll(int index, HCollection<E> c);

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index the index of the element to return
     * @return the element at the specified position
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     */
    Object get(int index) throws ArrayIndexOutOfBoundsException;

    /**
     * Returns the index of the first occurrence of the specified element in this list,
     * or -1 if this list does not contain the element.
     *
     * @param o the element to search for
     * @return the index of the first occurrence of the element, or -1 if it is not present
     */
    int indexOf(Object o);

    /**
     * Returns the index of the last occurrence of the specified element in this list,
     * or -1 if this list does not contain the element.
     *
     * @param o the element to search for
     * @return the index of the last occurrence of the element, or -1 if it is not present
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    int lastIndexOf(Object o) throws IndexOutOfBoundsException;

    /**
     * Returns an iterator over this list.
     *
     * @return an iterator over this list
     */
    HListIterator<E> listIterator();

    /**
     * Returns an iterator over this list, starting at the specified position.
     *
     * @param index the index at which to start the iteration
     * @return an iterator over this list, starting at the specified position
     */
    HListIterator<E> listIterator(int index);

    /**
     * Removes the element at the specified position in this list.
     *
     * @param index the index of the element to remove
     * @return the element previously at the specified position
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     */
    Object remove(int index) throws ArrayIndexOutOfBoundsException;

    /**
     * Replaces the element at the specified position in this list with the specified element.
     *
     * @param index   the index of the element to replace
     * @param element the element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     */
    Object set(int index, Object element) throws ArrayIndexOutOfBoundsException;

    /**
     * Returns a view of this list between the specified indices.
     *
     * @param fromIndex the starting index (inclusive) of the sublist
     * @param toIndex   the ending index (exclusive) of the sublist
     * @return a view of this list between the specified indices
     * @throws ArrayIndexOutOfBoundsException if the indices are out of range
     */
    HList<E> subList(int fromIndex, int toIndex) throws ArrayIndexOutOfBoundsException;
}
