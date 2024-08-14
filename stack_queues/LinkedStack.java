/* Demonstration that you can re-use the code you have already made with ease. */

import java.util.NoSuchElementException;

/**
 * LinkedStack is a stack implementation using a singly linked list.
 * This implementation has O(1) time complexity for push, pop, and top operations.
 *
 * @param <E> the type of elements held in this stack
 */
public class LinkedStack<E> implements Stack<E> {

    // Composition: Using SinglyLinkedList as the underlying data structure
    private SinglyLinkedList<E> list = new SinglyLinkedList<>();

    /**
     * Constructs an empty LinkedStack.
     */
    public LinkedStack() {
        // No need for additional initialization
    }

    /**
     * Returns the number of elements in the stack.
     *
     * @return the size of the stack
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * Returns true if the stack is empty.
     *
     * @return true if the stack is empty; false otherwise
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Pushes an element onto the top of the stack.
     *
     * @param e the element to push
     */
    @Override
    public void push(E e) {
        list.addFirst(e); // O(1) operation
    }

    /**
     * Returns, but does not remove, the element at the top of the stack.
     *
     * @return the top element of the stack
     * @throws NoSuchElementException if the stack is empty, this ensures consistent error handling and prevents returning null, which can lead to subtle bugs.
     */
    @Override
    public E top() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return list.first(); // O(1) operation
    }

    /**
     * Removes and returns the element at the top of the stack.
     *
     * @return the element removed from the top of the stack
     * @throws NoSuchElementException if the stack is empty, this ensures consistent error handling and prevents returning null, which can lead to subtle bugs.
     */
    @Override
    public E pop() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return list.removeFirst(); // O(1) operation
    }
}
