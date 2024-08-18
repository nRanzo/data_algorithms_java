package maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lists.ArrayList;

/**
 * A generic HashMultimap implementation that associates multiple values with a single key.
 * 
 * <p>This class provides a way to map keys to a collection of values. Unlike a regular 
 * map where each key maps to a single value, a multimap allows each key to map to 
 * multiple values. Internally, it uses a HashMap where each key maps to a List 
 * of values.</p>
 * 
 * @param <K> The type of keys maintained by this multimap.
 * @param <V> The type of mapped values.
 */
public class HashMultimap<K, V> {
    private Map<K, List<V>> map = new HashMap<>();
    private int total = 0;
    
    /**
     * Constructs an empty HashMultimap.
     */
    public HashMultimap() {}

    /**
     * Returns the total number of key-value pairs in the multimap.
     * 
     * @return The total number of key-value pairs.
     */
    public int size() {
        return total;
    }

    /**
     * Returns whether the multimap is empty.
     * 
     * @return true if the multimap contains no key-value pairs, false otherwise.
     */
    public boolean isEmpty() {
        return total == 0;
    }

    /**
     * Returns the list of values associated with the specified key, or null if the key 
     * is not present in the map.
     * 
     * @param key The key whose associated values are to be returned.
     * @return An iterable containing the values associated with the specified key, 
     *         or null if the key does not exist.
     */
    public Iterable<V> get(K key) {
        List<V> secondList = map.get(key);
        if (secondList == null)
            return null;
        return secondList;
    }

    /**
     * Associates the specified value with the specified key in the multimap.
     * 
     * <p>If the multimap previously contained an association for the key, the new 
     * value is added to the existing list of values. If no association existed, 
     * a new list is created.</p>
     * 
     * @param key The key with which the specified value is to be associated.
     * @param value The value to be associated with the specified key.
     */
    @SuppressWarnings("unchecked")
    public void put(K key, V value) {
        List<V> secondList = map.get(key);
        if (secondList == null) {
            secondList = (List<V>) new ArrayList<V>();
            map.put(key, secondList);
        }
        secondList.add(value);
        total++;
    }

    /**
     * Removes the association of the specified value with the specified key.
     * 
     * <p>If the value is the last one associated with the key, the key is also removed 
     * from the map. If the key is not present, or if the key is present but does not 
     * contain the specified value, the method returns false.</p>
     * 
     * @param key The key whose association is to be removed.
     * @param value The value to be removed from the list associated with the specified key.
     * @return true if the key-value pair was successfully removed, false otherwise.
     */
    public boolean remove(K key, V value) {
        boolean wasRemoved = false;
        List<V> secondList = map.get(key);
        if (secondList != null) {
            wasRemoved = secondList.remove(value);
            if (wasRemoved) {
                total--;
                if (secondList.isEmpty())
                    map.remove(key);    
            }
        }
        return wasRemoved;
    }
}
