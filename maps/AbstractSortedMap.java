package maps;

import java.util.Comparator;

import priorityqueue.Entry;
import trees.AbstractBinaryTree;

public abstract class AbstractSortedMap<K, V> extends AbstractBinaryTree<Entry<K,V>> {

    private Comparator<K> comp; // The comparator used for sorting

    /**
     * Default constructor that uses the natural ordering of the keys.
     * Assumes K implements Comparable<K>.
     */
    protected AbstractSortedMap() {
        this.comp = null; // Use natural ordering
    }

    /**
     * Constructor that takes a custom comparator for the keys.
     * 
     * @param comp The comparator to use for key comparisons.
     */
    protected AbstractSortedMap(Comparator<K> comp) {
        this.comp = comp;
    }

    /**
     * Compares two keys using either the custom comparator or the natural ordering.
     * 
     * @param a The first key.
     * @param b The second key.
     * @return A negative integer, zero, or a positive integer if the first key is less than, equal to, or greater than the second key.
     */
    @SuppressWarnings("unchecked")
    protected int compare(K a, K b) {
        if (comp != null) {
            return comp.compare(a, b);
        } else {
            return ((Comparable<K>) a).compareTo(b);
        }
    }

    /**
     * Compares the key of a map entry with a specified key.
     * 
     * @param key The key to compare.
     * @param entry The map entry whose key is compared with the specified key.
     * @return A negative integer, zero, or a positive integer if the key is less than, equal to, or greater than the entry's key.
     */
    protected int compare(K key, Entry<K, V> entry) {
        return compare(key, entry.getKey());
    }
}
