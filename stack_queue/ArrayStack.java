package stack_queue;

import java.util.EmptyStackException;

/**
 * ArrayStack is an implementation of a stack using a dynamic array.
 * The time complexity for push, pop, and top operations is O(1) on average, but resizing has a time complexity 
 * of O(n). This implementation has dynamic resizing in push and pop operations when needed
 *
 * @param <E> the type of elements held in this stack
 */
public class ArrayStack<E> implements Stack<E> {

    private static final int DEFAULT_CAPACITY = 500; // arbitrary initial size
    private E[] data;
    private int top = -1; // index of the top element, -1 indicates an empty stack

    /**
     * Constructs an empty stack with the default capacity.
     */
    public ArrayStack() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs an empty stack with the specified capacity.
     *
     * @param capacity the initial capacity of the stack
     */
    @SuppressWarnings("unchecked")
    public ArrayStack(int capacity) {
        data = (E[]) new Object[capacity];  // safe cast, ignore type safety warning
    }

    /**
     * Returns the number of elements in the stack.
     *
     * @return the size of the stack
     */
    @Override
    public int size() {
        return top + 1;
    }

    /**
     * Returns true if the stack is empty.
     *
     * @return true if the stack is empty; false otherwise
     */
    @Override
    public boolean isEmpty() {
        return top == -1; // top being -1 indicates an empty stack
    }

    /**
     * Pushes an element onto the top of the stack.
     * If the stack is full, it doubles the capacity of the array.
     *
     * @param e the element to push
     */
    @Override
    public void push(E e) {
        if (size() == data.length) {
            resize(2 * data.length);  // double the array size if full
        }
        data[++top] = e; // pre-increment top and store the element
    }

    /**
     * Returns, but does not remove, the element at the top of the stack.
     *
     * @return the top element of the stack
     * @throws EmptyStackException if the stack is empty
     */
    @Override
    public E top() throws EmptyStackException {
        if (isEmpty())
            throw new EmptyStackException(); // throws an exception instead of returning null
        return data[top]; // return the top element without removing it
    }

    /**
     * Removes and returns the element at the top of the stack.
     * If the stack size falls below a quarter of the array length, the array is halved in size.
     *
     * @return the element removed from the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    @Override
    public E pop() throws EmptyStackException {
        if (isEmpty())
            throw new EmptyStackException(); // throws an exception instead of returning null
        E element = data[top];
        data[top--] = null; // decrement top and clear the reference for GC

        // Shrink the array if necessary
        if (size() > 0 && size() == data.length / 4) {
            resize(data.length / 2);  // halve the array size if too sparse
        }

        return element;
    }

    /**
     * Resizes the internal array to the specified capacity.
     * This method has O(n) complexity.
     *
     * @param newCapacity the new capacity of the array
     */
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity];    // safe cast, ignore type safety warning
        System.arraycopy(data, 0, newData, 0, size());
        data = newData;
    }
}
