package priorityqueue;

/**
 * A simple implementation of the Entry interface, representing a key-value pair.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
public class SimpleEntry<K, V> implements Entry<K, V> {

    private K key;
    private V value;

    /**
     * Constructs an entry with the specified key and value.
     *
     * @param key the key associated with this entry
     * @param value the value associated with this entry
     */
    public SimpleEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key stored in this entry.
     *
     * @return the key stored in this entry
     */
    @Override
    public K getKey() {
        return key;
    }

    /**
     * Returns the value stored in this entry.
     *
     * @return the value stored in this entry
     */
    @Override
    public V getValue() {
        return value;
    }
}
