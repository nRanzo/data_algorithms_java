package heap;

import java.util.Comparator;

import lists.ArrayList;
import priorityqueue.AbstractPQ;
import priorityqueue.SimpleEntry;
import priorityqueue.Entry;


public class HeapPQ<K,V> extends AbstractPQ<K,V> {
    private ArrayList<Entry<K,V>> heap = new ArrayList<>();

    public HeapPQ() {
        super();
    }

    public HeapPQ(Comparator<K> comp) {
        super(comp);
    }

    public HeapPQ(K[] keys, V[] values) {
        super();
        for (int j = 0; j < Math.min(keys.length, values.length); j++)
            heap.add(new PQEntry<K,V>(keys[j], values[j]));
        heapify();
    }

    public HeapPQ(Comparator<K> comp, K[] keys, V[] values) {
        super(comp);
        for (int j = 0; j < Math.min(keys.length, values.length); j++)
            heap.add(new PQEntry<K,V>(keys[j], values[j]));
        heapify();
    }

    protected void heapify() {
        int startIndex = parent(size() - 1);    // starts from the parent of the last entry
        for (int j = startIndex; j >= 0; j--)   // continue to the root
            donwheap(j);
    }

    protected int parent(int j) {
        return (j-1) / 2;   // divisione con troncamento
    }

    protected int left(int j) {
        return 2 * j + 1;
    }

    protected int right(int j) {
        return 2 * j + 2;
    }

    protected boolean hasLeft(int j) {
        return left(j) < heap.size();
    }

    protected boolean hasRight(int j) {
        return right(j) < heap.size();
    }

    protected void swap(int i, int j) throws IllegalArgumentException{
        try {
            Entry<K,V> temp = (Entry<K,V>) heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Index not valid, can't swap");
        }

    }

    protected void upheap(int j) {
        while(j > 0){   // goes on until root
            int p = parent(j);
            if(compare(heap.get(j), heap.get(p)) >= 0)
                break;
            swap(j, p);
            j = p;      // continues from the parent's position
        }
    }

    protected void donwheap(int j) {
        while (hasLeft(j)) {            // goes down until a break
            int leftIndex = left(j);
            int smallChildIndex = leftIndex;    // right can still be minor
            if(hasRight(j)) {
                int rightIndex = right(j);
                if(compare(heap.get(leftIndex), heap.get(rightIndex)) > 0)
                    smallChildIndex = rightIndex;   // right child is minor
            }
            if(compare(heap.get(smallChildIndex), heap.get(j)) >= 0)
                break;  // sorting done
            swap(smallChildIndex, j);
            j = smallChildIndex;
        }
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public Entry<K,V> min() {
        if(heap.isEmpty())
            return null;
        return heap.get(0);
    }

    public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {
        checkKey(key);
        SimpleEntry<K,V> newest = new PQEntry<>(key, value);
        heap.add(newest);           // adds to the end of the list
        upheap(heap.size() - 1);    // runs up-heap for the newly added entry
        return newest;
    }

    public Entry<K,V> removeMin() {
        if(heap.isEmpty())
            return null;
        Entry<K,V> olditem = (SimpleEntry<K,V>) heap.get(0);
        swap(0, heap.size() - 1);     // move the minimum entry to the end
        heap.remove(heap.size() - 1);   // so now you can remove it
        donwheap(0);
        return olditem;
    }
}
