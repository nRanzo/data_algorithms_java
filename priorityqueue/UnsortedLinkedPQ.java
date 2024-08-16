package priorityqueue;

import java.util.Comparator;

import lists.LinkedPositionalList;
import lists.Position;
import lists.PositionalList;

public class UnsortedLinkedPQ<K,V> extends AbstractPQ<K,V> {
    // main container of pq
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
