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

    @SuppressWarnings("unchecked")
    protected void swap(int i, int j) throws IllegalArgumentException{
        try {
            SimpleEntry<?,?> temp = (SimpleEntry<?,?>) heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Index not valid, can't swap");
        }

    }

    protected void uphead(int j) {
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
}
