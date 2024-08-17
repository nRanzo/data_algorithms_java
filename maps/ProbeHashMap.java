package maps;

import lists.ArrayList;
import priorityqueue.Entry;

/**
 * A hash map implementation using open addressing with linear probing for collision resolution.
 * This implementation provides a concrete version of the abstract AbstractHashMap class,
 * and it uses probing to handle hash collisions.
 * 
 * @param <K> The type of keys maintained by this map.
 * @param <V> The type of mapped values.
 */
public class ProbeHashMap<K, V> extends AbstractHashMap<K, V> {
    
    /** Array of map entries representing the table. Initially, all entries are null. */
    private MapEntry<K, V>[] table;

    /** A special marker entry used to indicate a location in the table where an entry was removed. */
    private MapEntry<K, V> DEFUNCT = new MapEntry<>(null, null);

    /**
     * Constructs a ProbeHashMap with default capacity and prime number.
     */
    public ProbeHashMap() {
        super();
    }

    /**
     * Constructs a ProbeHashMap with the specified capacity and a default prime number.
     * 
     * @param cap The initial capacity of the map.
     */
    public ProbeHashMap(int cap) {
        super(cap);
    }

    /**
     * Constructs a ProbeHashMap with the specified capacity and prime number.
     * 
     * @param cap The initial capacity of the map.
     * @param p The prime number used for the hash function.
     */
    public ProbeHashMap(int cap, int p) {
        super(cap, p);
    }

    /**
     * Creates the table used for the map, initializing all entries to null.
     * This method is called during the construction and resizing of the map.
     */
    @SuppressWarnings("unchecked")
    protected void createTable() {
        table = (MapEntry<K, V>[]) new MapEntry[capacity];
    }

    /**
     * Checks if a slot in the table is available, meaning it is either null or marked as DEFUNCT.
     * 
     * @param j The index of the slot to check.
     * @return true if the slot is available, false otherwise.
     */
    private boolean isAvailable(int j) {
        return (table[j] == null || table[j] == DEFUNCT);
    }

    /**
     * Finds the slot where the key should be stored or retrieved.
     * 
     * @param h The initial hash value.
     * @param k The key to search for.
     * @return The index of the slot containing the key, or the negative of the first available slot if the key is not found.
     */
    private int findSlot(int h, K k) {
        int avail = -1;  // No available slot found yet
        int j = h;
        do {
            if (isAvailable(j)) {
                if (avail == -1)
                    avail = j;       // First available slot found
                if (table[j] == null)  // Empty slot indicates search failure
                    break;
            } else if (table[j].getKey().equals(k)) {
                return j;            // Key found
            }
            j = (j + 1) % capacity;  // Linear probing
        } while (j != h);            // Stops if we circle back to the start
        return -(avail + 1);         // Key not found, return first available slot as a negative value
    }

    /**
     * Retrieves a value from the map corresponding to the given key.
     * 
     * @param h The hash value of the key.
     * @param k The key to retrieve the value for.
     * @return The value associated with the key, or null if the key is not found.
     */
    protected V bucketGet(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0)  // Key not found
            return null;
        return table[j].getValue();
    }

    /**
     * Inserts a key-value pair into the map. If the key already exists, its value is updated.
     * 
     * @param h The hash value of the key.
     * @param k The key to insert or update.
     * @param v The value to associate with the key.
     * @return The old value associated with the key, or null if the key did not exist.
     */
    protected V bucketPut(int h, K k, V v) {
        int j = findSlot(h, k);
        if (j >= 0)      // Key found, update its value
            return table[j].setValue(v);
        table[-(j + 1)] = new MapEntry<>(k, v);  // Insert new entry in the correct index
        n++;
        return null;
    }

    /**
     * Removes the key-value pair from the map for the given key.
     * 
     * @param h The hash value of the key.
     * @param k The key to remove.
     * @return The value associated with the removed key, or null if the key was not found.
     */
    protected V bucketRemove(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0)           // Key not found
            return null;
        V oldItem = table[j].getValue();
        table[j] = DEFUNCT;  // Mark the slot as defunct
        n--;
        return oldItem;
    }

    /**
     * Returns an iterable collection of all key-value entries in the map.
     * 
     * @return An iterable collection of all entries.
     */
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K, V>> buffer = new ArrayList<>();
        for (int h = 0; h < capacity; h++) {
            if (!isAvailable(h))
                buffer.add(table[h]);
        }
        return buffer;
    }
}
