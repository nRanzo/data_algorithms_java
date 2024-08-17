package maps;

import java.util.Random;

import lists.ArrayList;
import priorityqueue.Entry;

/**
 * An abstract base class for a hash map implementation using the MAD (Multiply-Add-Divide) method for hash codes.
 * This class manages the common functionality of a hash map, including dynamic resizing, 
 * hash code generation, and abstract bucket operations.
 * 
 * @param <K> The type of the keys.
 * @param <V> The type of the values.
 */
public abstract class AbstractHashMap<K, V> extends AbstractMap<K, V> {
    /** Number of entries in the map. */
    protected int n = 0;

    /** Capacity of the hash table (number of buckets). */
    protected int capacity;

    /** A prime number used for the MAD method. */
    private int prime;

    /** Scaling factor for the MAD method. */
    private long scale;

    /** Shift factor for the MAD method. */
    private long shift;

    /**
     * Default constructor that initializes the hash map with a default capacity of 17.
     */
    public AbstractHashMap() {
        this(17);  // default capacity
    }

    /**
     * Constructor that initializes the hash map with the specified capacity.
     * 
     * @param cap The initial capacity of the hash table.
     */
    public AbstractHashMap(int cap) {
        this(cap, 109345121);  // a default prime number
    }

    /**
     * Constructor that initializes the hash map with the specified capacity and prime number.
     * 
     * @param cap The initial capacity of the hash table.
     * @param p The prime number used in the MAD method.
     */
    public AbstractHashMap(int cap, int p) {
        prime = p;
        capacity = cap;
        Random rand = new Random();
        scale = rand.nextInt(prime - 1) + 1;  // scale is in the range [1, p-1]
        shift = rand.nextInt(prime);          // shift is in the range [0, p)
        createTable();  // initialize the table
    }

    /**
     * Returns the number of entries in the map.
     * 
     * @return The number of entries in the map.
     * 
     * Time Complexity: O(1).
     */
    public int size() {
        return n;
    }

    /**
     * Returns the value associated with the specified key.
     * 
     * @param key The key whose associated value is to be returned.
     * @return The value associated with the specified key, or null if the key is not in the map.
     * 
     * Time Complexity: O(1) on average, assuming a good hash function and low load factor.
     * In the worst case, this can be O(n) if all entries hash to the same bucket.
     */
    public V get(K key) {
        return bucketGet(hashValue(key), key);
    }

    /**
     * Removes the mapping for the specified key from the map, if present.
     * 
     * @param key The key whose mapping is to be removed.
     * @return The previous value associated with the specified key, or null if the key was not in the map.
     * 
     * Time Complexity: O(1) on average, with the same caveats as get().
     */
    public V remove(K key) {
        return bucketRemove(hashValue(key), key);
    }

    /**
     * Associates the specified value with the specified key in the map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     * 
     * @param key The key with which the specified value is to be associated.
     * @param value The value to be associated with the specified key.
     * @return The previous value associated with the specified key, or null if there was no mapping for the key.
     * 
     * Time Complexity: O(1) on average, with the same caveats as get().
     * Resizing the table, if triggered, has a time complexity of O(n).
     */
    public V put(K key, V value) {
        V item = bucketPut(hashValue(key), key, value);
        if (n > capacity / 2) {  // maintain load factor <= 0.5
            resize(2 * capacity - 1);  // resize and use a prime number for optimal performance
        }
        return item;
    }

    /**
     * Computes the hash value for a given key using the MAD method.
     * 
     * @param key The key to hash.
     * @return The hash value for the key.
     * 
     * Time Complexity: O(1).
     */
    private int hashValue(K key) {
        return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
    }

    /**
     * Resizes the hash table to the specified new capacity and rehashes all entries.
     * 
     * @param newCap The new capacity of the hash table.
     * 
     * Time Complexity: O(n), where n is the number of entries in the map.
     * This is because each entry must be rehashed and reinserted into the new table.
     */
    private void resize(int newCap) {
        ArrayList<Entry<K, V>> buffer = new ArrayList<>(n);
        for (Entry<K, V> e : entrySet()) {
            buffer.add(e);
        }
        capacity = newCap;
        createTable();  // recreate the table with the new capacity
        n = 0;          // reset size and reinsert all entries
        for (Entry<K, V> e : buffer) {
            put(e.getKey(), e.getValue());
        }
    }

    /**
     * Creates the table for the hash map. This method must be implemented by concrete subclasses.
     */
    protected abstract void createTable();

    /**
     * Retrieves the value associated with a key in a specific bucket. 
     * This method must be implemented by concrete subclasses.
     * 
     * @param h The hash value of the key.
     * @param k The key to search for.
     * @return The value associated with the key, or null if not found.
     */
    protected abstract V bucketGet(int h, K k);

    /**
     * Associates a value with a key in a specific bucket. 
     * This method must be implemented by concrete subclasses.
     * 
     * @param h The hash value of the key.
     * @param k The key to associate.
     * @param v The value to associate with the key.
     * @return The old value associated with the key, or null if there was no previous value.
     */
    protected abstract V bucketPut(int h, K k, V v);

    /**
     * Removes the entry for a key in a specific bucket. 
     * This method must be implemented by concrete subclasses.
     * 
     * @param h The hash value of the key.
     * @param k The key to remove.
     * @return The value associated with the removed key, or null if not found.
     */
    protected abstract V bucketRemove(int h, K k);
}
