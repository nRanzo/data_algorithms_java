import java.util.NoSuchElementException;

/**
 * ArrayQueue is an implementation of a queue using a fixed-size array.
 * The time complexity for enqueue, dequeue, and size operations is O(1).
 * The main drawback is the O(n) complexity when resizing the array. So problems of this implementation are time 
 * complexity of resize, which is O(n), and the memory waste that you'll likely have when removing a large number 
 * of items. Of course in that case you can resize the array dividing its dimension, but remember that this will 
 * have the same complexity of resizing to add more spaces: O(n). This implementation will divide and double the
 * array dimension when needed.
 *
 * @param <E> the type of elements held in this queue
 */
public class ArrayQueue<E> implements Queue<E> {

    private static final int DEFAULT_CAPACITY = 500;
    private E[] data;
    private int front = 0;      // index of the first element
    private int size = 0;       // current number of elements in the queue

    /**
     * Constructs an empty queue with the default capacity.
     */
    public ArrayQueue() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs an empty queue with the specified capacity.
     *
     * @param capacity the initial capacity of the queue
     */
    @SuppressWarnings("unchecked")
    public ArrayQueue(int capacity) {
        data = (E[]) new Object[capacity];  // safe cast, ignore type safety warning
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return the size of the queue
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns true if the queue is empty.
     *
     * @return true if the queue is empty; false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Inserts the specified element into the queue.
     * If the queue is full, it doubles the capacity of the array.
     *
     * @param e the element to add
     */
    @Override
    public void enqueue(E e) {
        if (size == data.length) {
            resize(2 * data.length);  // double the array size if full, instead of throwing an exception
        }
        int avail = (front + size) % data.length;
        data[avail] = e;
        size++;
    }

    /**
     * Returns, but does not remove, the first element of the queue.
     *
     * @return the first element of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    @Override
    public E first() throws NoSuchElementException {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty");
        return data[front];
    }

    /**
     * Removes and returns the first element of the queue.
     * If the queue size falls below a quarter of the array length, the array is halved in size.
     *
     * @return the element removed from the queue
     * @throws NoSuchElementException if the queue is empty
     */
    @Override
    public E dequeue() throws NoSuchElementException {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty");
        E element = data[front];
        data[front] = null;  // helps Java's garbage collector
        front = (front + 1) % data.length;
        size--;

        // Shrink the array if necessary
        if (size > 0 && size == data.length / 4) {
            resize(data.length / 2);  // halve the array size if too sparse
        }

        return element;
    }

    /**
     * Resizes the internal array to the specified capacity.
     * This method should be used with caution, as it has O(n) complexity.
     *
     * @param newCapacity the new capacity of the array
     */
    private void resize(int newCapacity) {
        @SuppressWarnings("unchecked")
        E[] newData = (E[]) new Object[newCapacity];    // safe cast, ignore type safety warning 
                                                        // you may likely want to double the older capacity
        for (int i = 0; i < size; i++) {
            newData[i] = data[(front + i) % data.length];
        }
        data = newData;
        front = 0;
    }
}
