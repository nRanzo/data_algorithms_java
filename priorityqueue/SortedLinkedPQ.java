package priorityqueue;

import java.util.Comparator;
import lists.LinkedPositionalList;
import lists.Position;
import lists.PositionalList;

/**
 * An implementation of a priority queue using a sorted linked list.
 * 
 * This class represents a priority queue where elements are maintained in a
 * sorted order within a linked list. It provides methods for inserting elements,
 * retrieving the minimum element, and removing the minimum element.
 * 
 * <p>The time complexities of the key methods are as follows:
 * <ul>
 *   <li>{@code min()} - O(1): This method retrieves the minimum element directly from
 *       the front of the sorted list. The time complexity is constant because the
 *       minimum element is always at the beginning of the list.</li>
 *   <li>{@code findMin()} - O(1): This method finds the minimum element, which is always
 *       at the front of the sorted list. The time complexity is constant as it directly
 *       accesses the first position in the list.</li>
 *   <li>{@code insert(K key, V value)} - O(n): This method inserts a new entry into the
 *       sorted list. It requires finding the correct position in the list to maintain
 *       the sorted order, which involves scanning the list backwards from the end. The
 *       time complexity is linear due to the need to traverse the list to find the
 *       appropriate insertion point.</li>
 *   <li>{@code removeMin()} - O(1): This method removes the minimum element from the
 *       front of the sorted list. The time complexity is constant as it directly removes
 *       the first element in the list.</li>
 * </ul>
 * </p>
 * 
 * <p>The priority queue is maintained in a sorted state, which allows for constant-time
 * retrieval and removal of the minimum element, but requires linear time for insertions
 * to preserve the sorted order.</p>
 */

public class SortedLinkedPQ<K,V> extends AbstractPQ<K,V> {
    // main container of PQ
    private PositionalList<SimpleEntry<K,V>> list = new LinkedPositionalList<>();

    public SortedLinkedPQ() {
        super();
    }

    public SortedLinkedPQ(Comparator<K> comp) {
        super(comp);
    }
    public Position<SimpleEntry<K,V>> findMin() {
        Position<SimpleEntry<K,V>> small = list.first();
        for(Position<SimpleEntry<K,V>> walk : list.positions())
            if(compare(walk.getElement(), small.getElement()) < 0)
                small = walk;
        return small;
    }

    public SimpleEntry<K,V> insert(K key, V value) {
        checkKey(key);
        SimpleEntry<K,V> newest = new PQEntry<>(key, value);
        Position<SimpleEntry<K,V>> walk = list.last();
        // proceeds backwards looking for a minor key
        while (walk != null && compare(newest, walk.getElement()) < 0)
            walk = list.before(walk);
        if(walk == null)
            list.addFirst(newest);  // the new key is the minor one
        else
            list.addAfter(walk, newest);
        return newest;
    }

    public SimpleEntry<K,V> min() throws EmptyPriorityQueueException {
        if(list.isEmpty())
            throw new EmptyPriorityQueueException();
        return list.first().getElement();
    }

    public SimpleEntry<K,V> removeMin() {
        if(list.isEmpty())
            throw new EmptyPriorityQueueException();
        return list.remove(list.first());
    }

    public int size() {
        return list.size();
    }
}
