import java.util.NoSuchElementException;

/**
 * A doubly-linked list implementation of the HDeque interface.
 * This class provides constant time O(1) performance for insertions
 * and removals at both ends of the deque.
 *
 * @param <E> the type of elements held in this deque
 */
public class LinkedDeque<E> implements Deque<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    private static class Node<E> {
        E element;
        Node<E> prev;
        Node<E> next;

        Node(E element, Node<E> prev, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }

    /**
     * Constructs an empty deque.
     */
    public LinkedDeque() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns the number of elements in this deque.
     *
     * @return the number of elements in this deque
     * Time complexity: O(1)
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns true if this deque contains no elements.
     *
     * @return true if this deque contains no elements
     * Time complexity: O(1)
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Retrieves, but does not remove, the first element of this deque.
     *
     * @return the first element of this deque
     * @throws NoSuchElementException if this deque is empty
     * Time complexity: O(1)
     */
    @Override
    public E first() throws NoSuchElementException{
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return head.element;
    }

    /**
     * Retrieves, but does not remove, the last element of this deque.
     *
     * @return the last element of this deque
     * @throws NoSuchElementException if this deque is empty
     * Time complexity: O(1)
     */
    @Override
    public E last() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return tail.element;
    }

    /**
     * Inserts the specified element at the front of this deque.
     *
     * @param e the element to add
     * Time complexity: O(1)
     */
    @Override
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e, null, head);
        if (isEmpty()) {
            tail = newNode;
        } else {
            head.prev = newNode;
        }
        head = newNode;
        size++;
    }

    /**
     * Inserts the specified element at the end of this deque.
     *
     * @param e the element to add
     * Time complexity: O(1)
     */
    @Override
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e, tail, null);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    /**
     * Retrieves and removes the first element of this deque.
     *
     * @return the head of this deque
     * @throws NoSuchElementException if this deque is empty
     * Time complexity: O(1)
     */
    @Override
    public E removeFirst() throws NoSuchElementException{
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        E element = head.element;
        head = head.next;
        size--;
        if (isEmpty()) {
            tail = null;
        } else {
            head.prev = null;
        }
        return element;
    }

    /**
     * Retrieves and removes the last element of this deque.
     *
     * @return the tail of this deque
     * @throws NoSuchElementException if this deque is empty
     * Time complexity: O(1)
     */
    @Override
    public E removeLast() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        E element = tail.element;
        tail = tail.prev;
        size--;
        if (isEmpty()) {
            head = null;
        } else {
            tail.next = null;
        }
        return element;
    }
}