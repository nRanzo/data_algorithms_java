public interface PositionalList<E> {

    /**
     * Returns the number of elements in the list.
     * 
     * @return the size of the list as an integer.
     */
    int size();

    /**
     * Checks if the list is empty.
     * 
     * @return true if the list contains no elements, false otherwise.
     */
    boolean isEmpty();

    /**
     * Returns the first position in the list.
     * 
     * @return the Position<E> of the first element, or null if the list is empty.
     */
    Position<E> first();

    /**
     * Returns the last position in the list.
     * 
     * @return the Position<E> of the last element, or null if the list is empty.
     */
    Position<E> last();

    /**
     * Returns the position immediately before the given position in the list.
     * 
     * @param p the position to query.
     * @return the Position<E> before the given position, or null if p is the first position.
     * @throws IllegalArgumentException if the position is invalid.
     */
    Position<E> before(Position<E> p) throws IllegalArgumentException;

    /**
     * Returns the position immediately after the given position in the list.
     * 
     * @param p the position to query.
     * @return the Position<E> after the given position, or null if p is the last position.
     * @throws IllegalArgumentException if the position is invalid.
     */
    Position<E> after(Position<E> p) throws IllegalArgumentException;

    /**
     * Inserts a new element at the beginning of the list and returns its position.
     * 
     * @param e the element to add.
     * @return the Position<E> of the newly added element.
     */
    Position<E> addFirst(E e);

    /**
     * Inserts a new element at the end of the list and returns its position.
     * 
     * @param e the element to add.
     * @return the Position<E> of the newly added element.
     */
    Position<E> addLast(E e);

    /**
     * Inserts a new element immediately before the given position in the list.
     * 
     * @param p the position before which to insert the new element.
     * @param e the element to add.
     * @return the Position<E> of the newly added element.
     * @throws IllegalArgumentException if the position is invalid.
     */
    Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException;

    /**
     * Replaces the element at the given position with a new element and returns the replaced element.
     * 
     * @param p the position whose element is to be replaced.
     * @param e the new element to set.
     * @return the element that was replaced.
     * @throws IllegalArgumentException if the position is invalid.
     */
    E set(Position<E> p, E e) throws IllegalArgumentException;

    /**
     * Removes and returns the element at the given position.
     * 
     * @param p the position of the element to remove.
     * @return the element that was removed.
     * @throws IllegalArgumentException if the position is invalid.
     */
    E remove(Position<E> p) throws IllegalArgumentException;
}
