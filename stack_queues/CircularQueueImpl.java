/**
 * CircularQueue implementation based on an array. This implementation efficiently handles the queue operations 
 * and maintains constant time complexity for enqueue, dequeue, and rotate operations, while dynamically resizing 
 * the underlying array to optimize memory usage.
 *
 * @param <E> the type of elements held in this queue
 */
public class CircularQueueImpl<E> implements CircularQueue<E> {

    private static final int DEFAULT_CAPACITY = 10;
    private E[] data;
    private int front = 0;
    private int size = 0;

    public CircularQueueImpl() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public CircularQueueImpl(int capacity) {
        data = (E[]) new Object[capacity];  // Safe cast, ignore type safety warning
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void enqueue(E e) throws IllegalStateException {
        if (size == data.length) {
            resize(2 * data.length);  // Double the array size if full
        }
        int avail = (front + size) % data.length;
        data[avail] = e;
        size++;
    }

    @Override
    public E first() {
        if (isEmpty()) {
            return null;  // Returning null if the queue is empty
        }
        return data[front];
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            return null;  // Returning null if the queue is empty
        }
        E answer = data[front];
        data[front] = null;  // Allow garbage collection
        front = (front + 1) % data.length;
        size--;
        if (size > 0 && size == data.length / 4) {
            resize(data.length / 2);  // Shrink the array if it's 1/4 full
        }
        return answer;
    }

    @Override
    public void rotate() {
        if (!isEmpty()) {
            front = (front + 1) % data.length;
        }
    }

    /**
     * Resizes the underlying array to a new capacity.
     *
     * @param capacity the new capacity of the array
     */
    @SuppressWarnings("unchecked")

    private void resize(int capacity) {
        E[] newData = (E[]) new Object[capacity];   // safe cast, ignore type safety warning
        for (int i = 0; i < size; i++) {
            newData[i] = data[(front + i) % data.length];
        }
        data = newData;
        front = 0;  // Reset front to 0
    }
}
