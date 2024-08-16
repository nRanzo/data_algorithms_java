/**
 * A linked list implementation of the {@link PositionalList} interface.
 * <p>
 * This implementation maintains a doubly linked list with a header and a trailer node,
 * allowing for efficient insertion and removal of elements at both ends of the list as well as
 * in-between. It provides methods to add, remove, and access elements at specific positions
 * in the list.
 *
 * @param <E> the type of elements stored in the list
 */

package lists;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedPositionalList<E> implements PositionalList<E> {

    /**
     * A node in the linked positional list, which holds an element and references
     * to its previous and next nodes.
     *
     * @param <E> the type of element stored in the node
     */
    private static class Node<E> implements Position<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;

        public Node(E e, Node<E> p, Node<E> n) {
            element = e;
            prev = p;
            next = n;
        }

        public E getElement() throws IllegalStateException {
            if (next == null)
                throw new IllegalStateException("Position no longer valid");
            return element;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setElement(E e) {
            element = e;
        }

        public void setPrev(Node<E> p) {
            prev = p;
        }

        public void setNext(Node<E> n) {
            next = n;
        }
    }

    private Node<E> header;
    private Node<E> trailer;
    private int size = 0;

    public LinkedPositionalList() {
        header = new Node<>(null, null, null); 
        trailer = new Node<>(null, header, null);
        header.setNext(trailer);                     
    }

    /**
     * Validates the given position to ensure it is a valid node in the list.
     *
     * @param p the position to validate
     * @return the node corresponding to the position
     * @throws IllegalArgumentException if the position is invalid or no longer valid
     */
    private Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node))
            throw new IllegalArgumentException("Invalid position");
        Node<E> node = (Node<E>) p; 
        if (node.getNext() == null)
            throw new IllegalArgumentException("Position no longer valid");
        return node;
    }

    private Position<E> position(Node<E> node) {
        return node;  
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Position<E> first() {
        return position(header.getNext());
    }

    public Position<E> last() {
        return position(trailer.getPrev());
    }

    public Position<E> before(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return position(node.getPrev());
    }

    public Position<E> after(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return position(node.getNext());
    }

    private Position<E> addBetween(E e, Node<E> predec, Node<E> succes) {
        Node<E> newest = new Node<>(e, predec, succes);
        predec.setNext(newest);
        succes.setPrev(newest);
        size++;
        return position(newest);
    }

    public Position<E> addFirst(E e) {
        return addBetween(e, header, header.getNext());
    }

    public Position<E> addLast(E e) {
        return addBetween(e, trailer.getPrev(), trailer);
    }

    public Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return addBetween(e, node.getPrev(), node);
    }

    public Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return addBetween(e, node, node.getNext());
    }

    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E oldItem = node.getElement();
        node.setElement(e);
        return oldItem;
    }

    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        Node<E> predecessor = node.getPrev();
        Node<E> successor = node.getNext();
        predecessor.setNext(successor);
        successor.setPrev(predecessor);
        size--;
        E oldItem = node.getElement();
        node.setElement(null);  
        node.setNext(null);     
        node.setPrev(null);     
        return oldItem;
    }

    public E removeFirst() throws NoSuchElementException {
        if (isEmpty()) throw new NoSuchElementException();
        return remove(first());
    }

    public E removeLast() throws NoSuchElementException {
        if (isEmpty()) throw new NoSuchElementException();
        return remove(last());
    }

    public Iterable<Position<E>> positions() {
        return new PositionIterable();
    }

    private class PositionIterator implements Iterator<Position<E>> {
        private Position<E> cursor = first(); 
        private Position<E> recent = null;    

        public boolean hasNext() {
            return cursor != null;
        }

        public Position<E> next() throws NoSuchElementException {
            if (cursor == null) throw new NoSuchElementException("No more elements");
            recent = cursor;
            cursor = after(cursor);
            return recent;
        }

        public void remove() throws IllegalStateException {
            if (recent == null) throw new IllegalStateException("Nothing to remove");
            LinkedPositionalList.this.remove(recent); 
            recent = null; 
        }
    }

    private class PositionIterable implements Iterable<Position<E>> {
        public Iterator<Position<E>> iterator() {
            return new PositionIterator();
        }
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return new PositionIterator(); 
    }
}
