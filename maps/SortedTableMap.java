package maps;

import java.util.Comparator;

import lists.ArrayList;
import priorityqueue.Entry;

/**
 * A map implementation using a sorted table (array list) as the underlying data structure.
 * This implementation maintains entries sorted by keys, allowing for efficient range queries
 * such as finding entries with keys within a given range.
 *
 * @param <K> The type of keys maintained by this map.
 * @param <V> The type of mapped values.
 */
public class SortedTableMap<K, V> extends AbstractSortedMap<K, V> {

    /** The underlying sorted list of map entries. */
    private ArrayList<MapEntry<K, V>> table = new ArrayList<>();

    /**
     * Constructs an empty `SortedTableMap` with natural ordering of keys.
     */
    public SortedTableMap() {
        super();
    }

    /**
     * Constructs an empty `SortedTableMap` with a custom comparator for ordering keys.
     *
     * @param comp The comparator used to order the keys.
     */
    public SortedTableMap(Comparator<K> comp) {
        super(comp);
    }

    /**
     * Returns the minimum index in the table where the key is not less than the specified key.
     * If no such key exists, returns high+1 by convention.
     *
     * @param key The key to search for.
     * @param low The lower bound of the search range.
     * @param high The upper bound of the search range.
     * @return The index of the first entry not less than the specified key, or high+1 if not found.
     */
    private int findIndex(K key, int low, int high) {
        if (high < low)
            return high + 1;
        int mid = (low + high) / 2;
        int comp = compare(key, table.get(mid));
        if (comp == 0)
            return mid;
        else if (comp < 0)
            return findIndex(key, low, mid - 1);
        // else
        return findIndex(key, mid + 1, high);
    }

    /**
     * Searches the entire table for the specified key and returns its index.
     *
     * @param key The key to search for.
     * @return The index of the key, or the index where it should be inserted.
     */
    private int findIndex(K key) {
        return findIndex(key, 0, table.size() - 1);
    }

    /**
     * Returns the number of entries in the map.
     *
     * @return The number of entries.
     */
    public int size() {
        return table.size();
    }

    /**
     * Returns the value associated with the specified key, or null if no such key exists.
     *
     * @param key The key whose associated value is to be returned.
     * @return The value associated with the key, or null if not found.
     */
    public V get(K key) {
        int j = findIndex(key);
        if (j == size() || compare(key, table.get(j)) != 0)
            return null;    // No element with the specified key
        return table.get(j).getValue();
    }

    /**
     * Inserts a key-value pair into the map, or updates the value if the key already exists.
     *
     * @param key The key to be inserted or updated.
     * @param value The value to associate with the key.
     * @return The previous value associated with the key, or null if no such key existed.
     */
    public V put(K key, V value) {
        int j = findIndex(key);
        if (j < size() && compare(key, table.get(j)) == 0)  // Key found, update value
            return table.get(j).setValue(value);
        table.add(j, new MapEntry<>(key, value));  // Insert new entry
        return null;
    }

    /**
     * Removes the entry for the specified key from the map.
     *
     * @param key The key whose entry is to be removed.
     * @return The value associated with the removed key, or null if no such key existed.
     */
    public V remove(K key) {
        int j = findIndex(key);
        if (j == size() || compare(key, table.get(j)) != 0)
            return null;    // No element with the specified key
        return table.remove(j).getValue();
    }

    /**
     * Safely retrieves the entry at the specified index, or null if the index is out of bounds.
     *
     * @param j The index to retrieve the entry from.
     * @return The entry at the specified index, or null if out of bounds.
     */
    private Entry<K, V> safeEntry(int j) {
        if (j < 0 || j >= table.size())
            return null;
        return table.get(j);
    }

    /**
     * Returns the entry with the smallest key in the map.
     *
     * @return The entry with the smallest key, or null if the map is empty.
     */
    public Entry<K, V> firstEntry() {
        return safeEntry(0);
    }

    /**
     * Returns the entry with the largest key in the map.
     *
     * @return The entry with the largest key, or null if the map is empty.
     */
    public Entry<K, V> lastEntry() {
        return safeEntry(table.size() - 1);
    }

    /**
     * Returns the entry with the smallest key greater than or equal to the specified key.
     *
     * @param key The key to search for.
     * @return The entry with the smallest key greater than or equal to the specified key, or null if no such key exists.
     */
    public Entry<K, V> ceilingEntry(K key) {
        return safeEntry(findIndex(key));
    }

    /**
     * Returns the entry with the largest key less than or equal to the specified key.
     *
     * @param key The key to search for.
     * @return The entry with the largest key less than or equal to the specified key, or null if no such key exists.
     */
    public Entry<K, V> floorEntry(K key) {
        int j = findIndex(key);
        if (j == size() || !key.equals(table.get(j).getKey()))
            j--;
        return safeEntry(j);
    }

    /**
     * Returns the entry with the largest key strictly less than the specified key.
     *
     * @param key The key to search for.
     * @return The entry with the largest key strictly less than the specified key, or null if no such key exists.
     */
    public Entry<K, V> lowerEntry(K key) {
        return safeEntry(findIndex(key) - 1);  // Immediately to the left of ceilingEntry
    }

    /**
     * Returns the entry with the smallest key strictly greater than the specified key.
     *
     * @param key The key to search for.
     * @return The entry with the smallest key strictly greater than the specified key, or null if no such key exists.
     */
    public Entry<K, V> higherEntry(K key) {
        int j = findIndex(key);
        if (j < size() && key.equals(table.get(j).getKey()))
            j++;  // Move to the right of an exact match
        return safeEntry(j);
    }

    /**
     * Returns a snapshot iterator over entries in the specified range.
     *
     * @param startIndex The starting index of the range.
     * @param stop The key at which to stop the iteration (exclusive), or null to iterate through the end.
     * @return An iterable collection of entries in the specified range.
     */
    private Iterable<Entry<K, V>> snapshot(int startIndex, K stop) {
        ArrayList<Entry<K, V>> buffer = new ArrayList<>();
        int j = startIndex;
        while (j < table.size() && (stop == null || compare(stop, table.get(j)) > 0)) {
            buffer.add(table.get(j++));
        }
        return buffer;
    }

    /**
     * Returns an iterable collection of all entries in the map.
     *
     * @return An iterable collection of all entries.
     */
    public Iterable<Entry<K, V>> entrySet() {
        return snapshot(0, null);
    }

    /**
     * Returns an iterable collection of entries with keys in the range [fromKey, toKey).
     *
     * @param fromKey The lower bound (inclusive).
     * @param toKey The upper bound (exclusive).
     * @return An iterable collection of entries in the specified range.
     */
    public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) {
        return snapshot(findIndex(fromKey), toKey);
    }
}
