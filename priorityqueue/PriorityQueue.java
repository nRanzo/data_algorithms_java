package priorityqueue;

public interface PriorityQueue<K,V> {
    int size();
    boolean isEmpty();
    Entry<K,V> insert(K kery, V value) throws IllegalArgumentException;
    Entry<K,V> min() throws EmptyPriorityQueueException;
    Entry<K,V> removeMin() throws EmptyPriorityQueueException;
}
