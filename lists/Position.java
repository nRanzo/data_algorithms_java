public interface Position<E> {

    /**
     * Returns the item memorized in this position
     * 
     * @return the item saved
     * @throws IllegalStateException if position is no longer valid
     */
    E getElement() throws IllegalStateException;
    Position<E> getPrev();
    Position<E> getNext();
}