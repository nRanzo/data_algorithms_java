package maps;

import java.util.NoSuchElementException;
import lists.ArrayList;
import java.util.Iterator;
import priorityqueue.Entry;

/**
 * An implementation of a map using an unsorted list of key-value pairs.
 * The map does not guarantee any order of the keys.
 * 
 * @param <K> The type of the keys.
 * @param <V> The type of the values.
 */
public class UnsortedTableMap<K, V> extends AbstractMap<K, V> {
    
    /** The underlying data structure that stores the map entries. */
    private ArrayList<MapEntry<K, V>> table = new ArrayList<>();

    /**
     * Constructs an empty map.
     */
    public UnsortedTableMap() {}

    /**
     * Finds the index of a key in the table.
     * 
     * @param key The key to find.
     * @return The index of the key if found; -1 otherwise.
     * 
     * Time Complexity: O(n), where n is the size of the map.
     * This is because the method performs a linear search through the list.
     */
    private int findIndex(K key) {
        int n = table.size();
        for (int j = 0; j < n; j++) {
            if (table.get(j).getKey().equals(key)) {
                return j;
            }
        }
        return -1;  // value to report that the key was not found
    }

    /**
     * Returns the number of entries in the map.
     * 
     * @return The number of entries in the map.
     * 
     * Time Complexity: O(1).
     * This is because the size of the list is directly returned.
     */
    public int size() {
        return table.size();
    }

    /**
     * Returns the value associated with the specified key.
     * 
     * @param key The key whose associated value is to be returned.
     * @return The value associated with the specified key, or null if the key does not exist.
     * 
     * Time Complexity: O(n), where n is the size of the map.
     * The time complexity is due to the linear search performed by findIndex().
     */
    public V get(K key) {
        int j = findIndex(key);
        if (j == -1) // not found
            return null;
        return table.get(j).getValue();
    }

    /**
     * Associates the specified value with the specified key in the map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     * 
     * @param key The key with which the specified value is to be associated.
     * @param value The value to be associated with the specified key.
     * @return The previous value associated with key, or null if there was no mapping for key.
     * 
     * Time Complexity: O(n), where n is the size of the map.
     * The time complexity is due to the linear search performed by findIndex().
     */
    public V put(K key, V value) {
        int j = findIndex(key);
        if (j == -1) {
            table.add(new MapEntry<>(key, value));
            return null;
        } else {
            return table.get(j).setValue(value);
        }
    }

    /**
     * Removes the mapping for the specified key from the map if present.
     * 
     * @param key The key whose mapping is to be removed from the map.
     * @return The previous value associated with key, or null if there was no mapping for key.
     * 
     * Time Complexity: O(n), where n is the size of the map.
     * The time complexity is dominated by the linear search performed by findIndex(),
     * followed by the removal and potential swapping of elements.
     */
    public V remove(K key) {
        int j = findIndex(key);
        int n = size();
        if (j == -1) {
            return null;  // there is no item to remove with that key
        }
        V oldItem = table.get(j).getValue();
        if (j != n - 1) {
            table.set(j, table.get(n - 1));  // move the last entry to the vacant spot
        }
        table.remove(n - 1);  // remove the last entry
        return oldItem;
    }

    /**
     * An iterator over the entries of the map.
     * 
     * Time Complexity: O(1) for hasNext() and next().
     * Each operation on the iterator is constant time, but iterating over the entire map is O(n).
     */
    private class EntryIterator implements Iterator<Entry<K, V>> {
        private int j = 0;

        public boolean hasNext() {
            return j < table.size();
        }

        public Entry<K, V> next() throws NoSuchElementException {
            if (j == table.size())
                throw new NoSuchElementException();
            return table.get(j++);
        }

        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * An iterable that produces iterators over the entries of the map.
     */
    private class EntryIterable implements Iterable<Entry<K, V>> {
        public Iterator<Entry<K, V>> iterator() {
            return new EntryIterator();
        }
    }

    /**
     * Returns an iterable collection of all key-value pairs in the map.
     * 
     * @return An iterable collection of all key-value pairs in the map.
     * 
     * Time Complexity: O(1).
     * This operation simply returns a new EntryIterable object.
     */
    public Iterable<Entry<K, V>> entrySet() {
        return new EntryIterable();
    }
}
