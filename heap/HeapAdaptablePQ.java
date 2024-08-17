package heap;

import java.util.Comparator;

import priorityqueue.Entry;

/**
 * An implementation of an adaptable priority queue based on a binary heap.
 * 
 * This class extends the standard heap-based priority queue to support 
 * additional operations such as removal and value replacement of arbitrary 
 * elements. The main feature of this implementation is the use of 
 * AdaptablePQEntry, which tracks the position of each entry in the heap, 
 * allowing efficient updates and removals.
 * 
 * <p>This flexibility comes at the cost of increased implementation 
 * complexity, but it ensures that both insertion and removal operations 
 * remain O(log n), making it suitable for use cases requiring dynamic priority 
 * changes.</p>
 *
 * @param <K> The type of the keys.
 * @param <V> The type of the values.
 */
public class HeapAdaptablePQ<K,V> extends HeapPQ<K,V> {
    
    /**
     * Nested class representing an entry in the adaptable priority queue.
     * 
     * This entry keeps track of its position in the heap, allowing efficient
     * updates and removals.
     *
     * @param <K> The type of the key.
     * @param <V> The type of the value.
     */
    protected static class AdaptablePQEntry<K,V> extends PQEntry<K,V> {
        private int index;  // The index of this entry in the heap.
        
        /**
         * Constructs a new AdaptablePQEntry with the given key, value, and index.
         *
         * @param key   The key associated with this entry.
         * @param value The value associated with this entry.
         * @param j     The index of the entry in the heap.
         */
        public AdaptablePQEntry(K key, V value, int j) {
            super(key, value);
            index = j;
        }

        /**
         * Returns the index of this entry in the heap.
         *
         * @return The index of the entry.
         */
        public int getIndex() {
            return index;
        }

        /**
         * Sets the index of this entry in the heap.
         *
         * @param j The new index of the entry.
         */
        public void setIndex(int j) {
            index = j;
        }
    }

    /**
     * Constructs an empty adaptable priority queue based on a binary heap.
     */
    public HeapAdaptablePQ() {
        super();
    }

    /**
     * Constructs an empty adaptable priority queue using the specified comparator.
     *
     * @param comp The comparator defining the order of keys.
     */
    public HeapAdaptablePQ(Comparator<K> comp) {
        super(comp);
    }

    /**
     * Validates a given entry, ensuring it is properly associated with this priority queue.
     *
     * @param entry The entry to validate.
     * @return The validated AdaptablePQEntry.
     * @throws IllegalArgumentException if the entry is invalid or not associated with this queue.
     */
    protected AdaptablePQEntry<K,V> validate(Entry<K,V> entry) throws IllegalArgumentException {
        if(!(entry instanceof AdaptablePQEntry))
            throw new IllegalArgumentException("Invalid entry");
        AdaptablePQEntry<K,V> locator = (AdaptablePQEntry<K,V>) entry;
        int j = locator.getIndex();
        if(j >= heap.size() || heap.get(j) != locator)
            throw new IllegalArgumentException("Invalid entry");
        return locator;
    }

    /**
     * Swaps two entries in the heap and updates their indices.
     *
     * @param i The index of the first entry.
     * @param j The index of the second entry.
     */
    protected void swap(int i, int j) {
        super.swap(i, j);
        ((AdaptablePQEntry<K,V>) heap.get(i)).setIndex(i);  // index update
        ((AdaptablePQEntry<K,V>) heap.get(j)).setIndex(j);  // index update
    }

    /**
     * Restores the heap property by either moving the entry up or down.
     * 
     * @param j The index of the entry to bubble.
     */
    protected void bubble(int j) {
        if(j > 0 && compare(heap.get(j), heap.get(parent(j))) < 0)
            upheap(j);
        else
            donwheap(j);
    }

    /**
     * Inserts a new entry into the priority queue.
     *
     * @param key   The key associated with the entry.
     * @param value The value associated with the entry.
     * @return The newly created entry.
     * @throws IllegalArgumentException if the key is invalid.
     */
    public Entry<K,V> insert(K key, V value) {
        checkKey(key);
        Entry<K,V> newest = new AdaptablePQEntry<>(key, value, heap.size());
        heap.add(newest);
        upheap(heap.size() - 1);
        return newest;
    }

    /**
     * Removes a given entry from the priority queue.
     *
     * @param entry The entry to remove.
     * @throws IllegalArgumentException if the entry is invalid.
     */
    public void remove(Entry<K,V> entry) throws IllegalArgumentException {
        AdaptablePQEntry<K,V> locator = validate(entry);
        int j = locator.getIndex();
        if(j == heap.size() - 1)            // entry is in the last position
            heap.remove(heap.size() - 1);   // so just delete it
        else {
            swap(j, heap.size() - 1);       // swap with last entry
            heap.remove(heap.size() - 1);   // now delete the entry in last position
            bubble(j);                      // and fix the other entry swapped
        }
    }

    /**
     * Replaces the value associated with a given entry.
     *
     * @param entry The entry to update.
     * @param value The new value.
     * @throws IllegalArgumentException if the entry is invalid.
     */
    protected void replaceValue(Entry<K,V> entry, V value) throws IllegalArgumentException{
        AdaptablePQEntry<K,V> locator = validate(entry);
        locator.setValue(value);
    }
}
