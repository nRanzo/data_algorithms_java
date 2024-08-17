package maps;

import lists.ArrayList;

import priorityqueue.Entry;

/**
 * Implementation of a hash map using separate chaining with an array of UnsortedTableMap instances as buckets.
 * Each bucket is an UnsortedTableMap, which handles collisions by storing multiple entries with the same hash value.
 * 
 * @param <K> The type of the keys.
 * @param <V> The type of the values.
 */
public class ChainHashMap<K, V> extends AbstractHashMap<K, V> {

    /** Array of buckets, where each bucket is an UnsortedTableMap instance. */
    private UnsortedTableMap<K, V>[] table;

    /**
     * Default constructor that initializes the hash map with a default capacity.
     */
    public ChainHashMap() {
        super();
    }

    /**
     * Constructor that initializes the hash map with the specified capacity.
     * 
     * @param cap The initial capacity of the hash map.
     */
    public ChainHashMap(int cap) {
        super(cap);
    }

    /**
     * Constructor that initializes the hash map with the specified capacity and prime number.
     * 
     * @param cap The initial capacity of the hash map.
     * @param p The prime number used in the MAD method.
     */
    public ChainHashMap(int cap, int p) {
        super(cap, p);
    }

    /**
     * Creates the table of buckets. Each bucket is initially null and will be instantiated as needed.
     * This method must be called during initialization and when resizing.
     */
    @SuppressWarnings("unchecked")
    protected void createTable() {
        table = (UnsortedTableMap<K, V>[]) new UnsortedTableMap[capacity]; // safe cast
    }

    /**
     * Retrieves the value associated with the specified key from the bucket at the given hash index.
     * 
     * @param h The hash value that determines the bucket.
     * @param k The key to search for.
     * @return The value associated with the specified key, or null if the key is not found.
     * 
     * Time Complexity: O(1) on average, assuming a uniform distribution of keys and a low load factor.
     * In the worst case, when all keys hash to the same bucket, the time complexity is O(n).
     */
    protected V bucketGet(int h, K k) {
        UnsortedTableMap<K, V> bucket = table[h];
        if (bucket == null)
            return null;
        return bucket.get(k);
    }

    /**
     * Associates the specified value with the specified key in the bucket at the given hash index.
     * If the key is already present, the old value is replaced.
     * 
     * @param h The hash value that determines the bucket.
     * @param k The key to associate.
     * @param v The value to associate with the key.
     * @return The previous value associated with the specified key, or null if there was no mapping for the key.
     * 
     * Time Complexity: O(1) on average. The time complexity depends on the bucket size, 
     * which is expected to be small if the load factor is maintained.
     */
    protected V bucketPut(int h, K k, V v) {
        UnsortedTableMap<K, V> bucket = table[h];
        if (bucket == null)
            bucket = table[h] = new UnsortedTableMap<>();
        int oldSize = bucket.size();
        V item = bucket.put(k, v);
        n += (bucket.size() - oldSize);
        return item;
    }

    /**
     * Removes the mapping for the specified key from the bucket at the given hash index.
     * 
     * @param h The hash value that determines the bucket.
     * @param k The key whose mapping is to be removed.
     * @return The value associated with the removed key, or null if there was no mapping for the key.
     * 
     * Time Complexity: O(1) on average. The time complexity depends on the bucket size.
     */
    protected V bucketRemove(int h, K k) {
        UnsortedTableMap<K, V> bucket = table[h];
        if (bucket == null)
            return null;
        int oldSize = bucket.size();
        V item = bucket.remove(k);
        n -= (oldSize - bucket.size());
        return item;
    }

    /**
     * Returns an iterable collection of all key-value entries in the hash map.
     * 
     * @return An iterable collection of all entries in the map.
     * 
     * Time Complexity: O(n), where n is the total number of entries in the map.
     */
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K, V>> buffer = new ArrayList<>();
        for (int h = 0; h < capacity; h++) {
            if (table[h] != null) {
                for (Entry<K, V> entry : table[h].entrySet()) {
                    buffer.add(entry);
                }
            }
        }
        return buffer;
    }
}
