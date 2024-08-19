package maps;

import java.util.Comparator;

import lists.Position;
import priorityqueue.Entry;
import trees.InvalidPositionException;

/**
 * An abstract base class to assist in creating sorted map implementations.
 * This class provides a comparator and methods for comparing keys and entries.
 * It does not handle tree structures directly; that is left to concrete subclasses.
 */
public abstract class AbstractSortedMap<K, V> extends AbstractMap<K,V> {
    
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

    /**
     * Placeholder for rebalancing after access. Should be implemented in subclasses (e.g., for splay trees).
     */
    protected void rebalanceAccess(Position<Entry<K,V>> p) throws InvalidPositionException {
        // No operation in the base class; intended to be overridden by subclasses
    }
    
    /**
     * Placeholder for rebalancing after insertion. Should be implemented in subclasses (e.g., for AVL or Red-Black trees).
     */
    protected void rebalanceInsert(Position<Entry<K,V>> p) throws InvalidPositionException {
        // No operation in the base class; intended to be overridden by subclasses
    }
    
    /**
     * Placeholder for rebalancing after deletion. Should be implemented in subclasses if needed.
     */
    protected void rebalanceDelete(Position<Entry<K,V>> p) throws InvalidPositionException {
        // No operation in the base class; intended to be overridden by subclasses
    }

    // Additional common map methods can be added here (e.g., get, put, remove), 
    // which can be utilized by subclasses like TreeMap.
}
