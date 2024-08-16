package priorityqueue;

import java.util.Comparator;

/**
 * An abstract base class for a priority queue that provides basic functionalities 
 * common to all priority queues.
 *
 * @param <K> the type of keys maintained by this priority queue
 * @param <V> the type of mapped values
 */
public abstract class AbstractPQ<K,V> implements PriorityQueue<K,V> {

    /** Nested PQEntry class representing key-value pairs in the priority queue. */
    protected static class PQEntry<K,V> extends SimpleEntry<K,V> {
        private K k;
        private V v;

        public PQEntry(K key, V value) {
            super(key, value); // Calling the constructor of SimpleEntry
            this.k = key;
            this.v = value;
        }

        @Override
        public K getKey() {
            return k;
        }

        @Override
        public V getValue() {
            return v;
        }

        protected void setKey(K key) {
            k = key;
        }

        protected void setValue(V value) {
            v = value;
        }
    }

    /** The comparator that defines the order between the keys of the priority queue. */
    private Comparator<K> comp;

    /** Constructor that initializes the comparator with a custom comparator. */
    protected AbstractPQ(Comparator<K> c) {
        comp = c;
    }

    /** Constructor that initializes the comparator with a default comparator. */
    protected AbstractPQ() {
        this(new DefaultComparator<K>());
    }

    /** Compares two entries using the comparator. */
    protected int compare(Entry<K,V> a, Entry<K,V> b) {
        return comp.compare(a.getKey(), b.getKey());
    }

    /** Checks whether the key is valid by comparing it with itself. */
    protected boolean checkKey(K key) throws IllegalArgumentException {
        try {
            return (comp.compare(key, key) == 0);
        } catch(ClassCastException e) {
            throw new IllegalArgumentException("Incompatible key");
        }
    }

    /** Returns true if the priority queue is empty. */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /** Returns the number of entries in the priority queue. */
    @Override
    public abstract int size();

    /** Inserts a key-value pair and returns the entry created. */
    @Override
    public abstract Entry<K, V> insert(K key, V value) throws IllegalArgumentException;

    /** Returns (but does not remove) an entry with the minimal key. */
    @Override
    public abstract Entry<K, V> min();

    /** Removes and returns an entry with the minimal key. */
    @Override
    public abstract Entry<K, V> removeMin();
}
