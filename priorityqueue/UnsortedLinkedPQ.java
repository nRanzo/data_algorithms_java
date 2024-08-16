package priorityqueue;

import java.util.Comparator;
import lists.LinkedPositionalList;
import lists.Position;
import lists.PositionalList;

/**
 * An implementation of a priority queue using linked list, keeping it unsorted.
 * 
 * This class represents a priority queue where elements are stored in an unsorted
 * linked list. It provides methods for inserting elements, retrieving the minimum
 * element, and removing the minimum element.
 * 
 * <p>The time complexities of the key methods are as follows:
 * <ul>
 *   <li>{@code min()} - O(n): This method retrieves the minimum element by scanning
 *       through the entire list. The time complexity is linear due to the unsorted nature
 *       of the list.</li>
 *   <li>{@code findMin()} - O(n): This method finds the position of the minimum element
 *       by iterating through all positions in the list. The time complexity is linear as
 *       it requires a full scan of the unsorted list.</li>
 *   <li>{@code insert(K key, V value)} - O(1): This method adds a new entry to the end
 *       of the list. The insertion operation is constant time since it involves appending
 *       an element to the end of the list.</li>
 *   <li>{@code removeMin()} - O(n): This method removes the minimum element from the
 *       list, which requires first finding the minimum element (O(n) complexity) and
 *       then removing it from the list.</li>
 * </ul>
 * </p>
 * 
 * <p>The priority queue is maintained in an unsorted state, which allows efficient
 * insertions but requires linear time to find and remove the minimum element.</p>
 */

public class UnsortedLinkedPQ<K,V> extends AbstractPQ<K,V> {
    // main container of PQ
    private PositionalList<SimpleEntry<K,V>> list = new LinkedPositionalList<>();

    public UnsortedLinkedPQ() {
        super();
    }

    public UnsortedLinkedPQ(Comparator<K> comp) {
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
        list.addLast(newest);
        return newest;
    }

    public SimpleEntry<K,V> min() throws EmptyPriorityQueueException {
        if(list.isEmpty())
            throw new EmptyPriorityQueueException();
        return findMin().getElement();
    }

    public SimpleEntry<K,V> removeMin() {
        if(list.isEmpty())
            throw new EmptyPriorityQueueException();
        return list.remove(findMin());
    }

    public int size() {
        return list.size();
    }
}
