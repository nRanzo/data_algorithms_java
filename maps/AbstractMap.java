package maps;

import java.util.Iterator;

import priorityqueue.Entry;

/**
 * An abstract base class providing some common functionality for map implementations.
 * This class implements the Map interface and provides utility methods for checking if the map is empty,
 * as well as for generating iterables for the keys, values, and entries of the map.
 * 
 * @param <K> The type of keys maintained by this map.
 * @param <V> The type of mapped values.
 */
public abstract class AbstractMap<K, V> implements Map<K, V> {

    /**
     * Checks if the map is empty.
     * 
     * @return true if the map contains no entries, false otherwise.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Factory method to create a new {@link Entry} instance.
     * <p>
     * This method allows subclasses to create instances of {@link MapEntry} 
     * without directly accessing the {@code MapEntry} class, maintaining 
     * encapsulation within the {@code maps} package. The method is protected 
     * so that only subclasses within the hierarchy can invoke it.
     * </p>
     *
     * @param key   the key to be stored in the entry
     * @param value the value to be stored in the entry
     * @return a new {@code Entry} instance containing the specified key and value
     */
    protected Entry<K, V> createMapEntry(K key, V value) {
        return new MapEntry<>(key, value);
    }

    /**
     * A concrete implementation of the Entry interface to represent a key-value pair.
     * 
     * @param <K> The type of keys maintained by this entry.
     * @param <V> The type of value associated with the key.
     */
    protected static class MapEntry<K, V> implements Entry<K, V> {
        private K k; // key
        private V v; // value

        /**
         * Constructs a MapEntry with the specified key and value.
         * 
         * @param key The key associated with this entry.
         * @param value The value associated with this entry.
         */
        public MapEntry(K key, V value) {
            k = key;
            v = value;
        }

        /**
         * Returns the key of this entry.
         * 
         * @return The key.
         */
        public K getKey() {
            return k;
        }

        /**
         * Returns the value of this entry.
         * 
         * @return The value.
         */
        public V getValue() {
            return v;
        }

        /**
         * Sets the key for this entry.
         * 
         * @param key The new key.
         */
        protected void setKey(K key) {
            k = key;
        }

        /**
         * Sets the value for this entry, returning the old value.
         * 
         * @param value The new value.
         * @return The old value associated with the key.
         */
        protected V setValue(V value) {
            V old = v;
            v = value;
            return old;
        }
    }

    /**
     * A private inner class that implements an iterator for the keys of the map.
     */
    private class KeyIterator implements Iterator<K> {
        private Iterator<Entry<K, V>> entries = entrySet().iterator();

        /**
         * Checks if there are more keys to iterate over.
         * 
         * @return true if there are more keys, false otherwise.
         */
        public boolean hasNext() {
            return entries.hasNext();
        }

        /**
         * Returns the next key in the iteration.
         * 
         * @return The next key.
         */
        public K next() {
            return entries.next().getKey();
        }

        /**
         * Unsupported operation. Throws an exception if called.
         * 
         * @throws UnsupportedOperationException if called.
         */
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * A private inner class that implements an iterable for the keys of the map.
     */
    private class KeyIterable implements Iterable<K> {

        /**
         * Returns an iterator over the keys in this map.
         * 
         * @return An iterator over the keys.
         */
        public Iterator<K> iterator() {
            return new KeyIterator();
        }
    }

    /**
     * Returns an iterable collection of all keys in the map. 
     * 
     * @return An iterable collection of keys.
     */
    public Iterable<K> keySet() {
        return new KeyIterable();
    }

    /**
     * A private inner class that implements an iterator for the values of the map.
     */
    private class ValueIterator implements Iterator<V> {
        private Iterator<Entry<K, V>> entries = entrySet().iterator();

        /**
         * Checks if there are more values to iterate over.
         * 
         * @return true if there are more values, false otherwise.
         */
        public boolean hasNext() {
            return entries.hasNext();
        }

        /**
         * Returns the next value in the iteration.
         * 
         * @return The next value.
         */
        public V next() {
            return entries.next().getValue();
        }

        /**
         * Unsupported operation. Throws an exception if called.
         * 
         * @throws UnsupportedOperationException if called.
         */
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * A private inner class that implements an iterable for the values of the map.
     */
    private class ValueIterable implements Iterable<V> {

        /**
         * Returns an iterator over the values in this map.
         * 
         * @return An iterator over the values.
         */
        public Iterator<V> iterator() {
            return new ValueIterator();
        }
    }

    /**
     * Returns an iterable collection of all values in the map.
     * 
     * @return An iterable collection of values.
     */
    public Iterable<V> values() {
        return new ValueIterable();
    }
}
