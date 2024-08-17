package stack_queue;

/**
 * LinkedCircularQueue implementation that uses a CircularQueue as its internal storage.
 * This class delegates all queue operations to an instance of CircularQueue.
 *
 * @param <E> the type of elements held in this queue
 */
public class LinkedCircularQueue<E> implements CircularQueue<E> {

    private CircularQueue<E> circularQueue;

    public LinkedCircularQueue() {
        circularQueue = new CircularQueueImpl<>();
    }

    public LinkedCircularQueue(int capacity) {
        circularQueue = new CircularQueueImpl<>(capacity);
    }

    @Override
    public int size() {
        return circularQueue.size();
    }

    @Override
    public boolean isEmpty() {
        return circularQueue.isEmpty();
    }

    @Override
    public void enqueue(E e) {
        circularQueue.enqueue(e);
    }

    @Override
    public E first() {
        return circularQueue.first();
    }

    @Override
    public E dequeue() {
        return circularQueue.dequeue();
    }

    @Override
    public void rotate() {
        circularQueue.rotate();
    }
}
