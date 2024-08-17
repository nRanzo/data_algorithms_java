package stack_queue;

import lists.SinglyLinkedList;

/* Demonstration that you can re-use the code you have already made with ease. */

/**
 * A generic queue implementation based on a singly linked list.
 * This implementation provides O(1) time complexity for all its primary operations on worst case.
 * So same time complexity of SinglyLinkedList, that's because this simple implmentation is purely
 * based on SinglyLinkedList.
 *
 * @param <E> the type of elements held in this queue
 */

public class LinkedQueue<E> {

    private SinglyLinkedList<E> list = new SinglyLinkedList<>();

    // Default constructor
    public LinkedQueue() {}

    /**
     * Returns the number of elements in the queue.
     *
     * @return the size of the queue
     */
    public int size() {
        return list.size();
    }

    /**
     * Returns true if the queue is empty.
     *
     * @return true if the queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Adds an element to the end of the queue.
     *
     * @param e the element to add
     */
    public void enqueue(E e) {
        list.addLast(e);
    }

    /**
     * Returns the first element in the queue without removing it.
     *
     * @return the first element, or null if the queue is empty
     */
    public E first() {
        return list.first();
    }

    /**
     * Removes and returns the first element in the queue.
     *
     * @return the removed element, or null if the queue is empty
     */
    public E dequeue() {
        return list.removeFirst();
    }
}
