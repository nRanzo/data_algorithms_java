package heap;

import java.util.Comparator;

import priorityqueue.Entry;

public class HeapAdaptablePQ<K,V> extends HeapPQ<K,V> {
    
    protected static class AdaptablePQEntry<K,V> extends PQEntry<K,V> {
        private int index;
        
        public AdaptablePQEntry(K key, V value, int j) {
            super(key, value);
            index = j;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int j) {
            index = j;
        }
    }

    public HeapAdaptablePQ() {
        super();
    }

    public HeapAdaptablePQ(Comparator<K> comp) {
        super(comp);
    }

    protected AdaptablePQEntry<K,V> validate(Entry<K,V> entry) throws IllegalArgumentException {
        if(!(entry instanceof AdaptablePQEntry))
            throw new IllegalArgumentException("Invalid entry");
        AdaptablePQEntry<K,V> locator = (AdaptablePQEntry<K,V>) entry;
        int j = locator.getIndex();
        if(j >= heap.size() || heap.get(j) != locator)
            throw new IllegalArgumentException("Invalid entry");
        return locator;
    }

    protected void swap(int i, int j) {
        super.swap(i, j);
        ((AdaptablePQEntry<K,V>) heap.get(i)).setIndex(i);  // index update
        ((AdaptablePQEntry<K,V>) heap.get(j)).setIndex(j);  // index update
    }

    protected void bubble(int j) {
        if(j > 0 && compare(heap.get(j), heap.get(parent(j))) < 0)
            upheap(j);
        else
            donwheap(j);
    }

    public Entry<K,V> insert(K key, V value) {
        checkKey(key);
        Entry<K,V> newest = new AdaptablePQEntry<>(key, value, heap.size());
        heap.add(newest);
        upheap(heap.size() - 1);
        return newest;
    }

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

    protected void replaceValue(Entry<K,V> entry, V value) throws IllegalArgumentException{
        AdaptablePQEntry<K,V> locator = validate(entry);
        locator.setValue(value);
    }
}
