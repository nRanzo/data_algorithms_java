package searchtrees;

import java.util.Comparator;

import lists.Position;
import lists.ArrayList;
import maps.AbstractMap.MapEntry;
import maps.AbstractMap.MapEntry;
import maps.AbstractSortedMap;
import priorityqueue.Entry;
import trees.InvalidPositionException;


// a sorted map implementation that uses a binary search tree
public class TreeMap<K,V> extends AbstractSortedMap<K,V> {
    
    protected BalanceableBT<K,V> tree = new BalanceableBT<>();
    private Comparator<K> comp;

    public TreeMap() {
        super();
        comp = null;
        tree.addRoot(null);
    }

    public TreeMap(Comparator<K> c) {
        super(c);
        comp = c;
        tree.addRoot(null);
    }

    public Position<Entry<K,V>> left(Position<Entry<K,V>> p) {
        try {
            Position<Entry<K,V>> left = tree.left(p);
            return left;
        }
        catch (InvalidPositionException e) {
            return null;
        }
    }

    public Position<Entry<K,V>> right(Position<Entry<K,V>> p) {
        try {
            Position<Entry<K,V>> right = tree.right(p);
            return right;
        }
        catch (InvalidPositionException e) {
            return null;
        }
    }

    public Position<Entry<K,V>> parent(Position<Entry<K,V>> p) {
        try {
            Position<Entry<K,V>> parent = tree.parent(p);
            return parent;
        }
        catch (InvalidPositionException e) {
            return null;
        }
    }

    public boolean isRoot(Position<Entry<K,V>> p) {
        return p == tree.root();
    }


    public int size() {
        return (tree.size() - 1) / 2;   // only internal nodes are actuall entries
    }

    /** Checks whether the key is valid by comparing it with itself. */
    protected boolean checkKey(K key) throws IllegalArgumentException {
        try {
            return (comp.compare(key, key) == 0);
        } catch(ClassCastException e) {
            throw new IllegalArgumentException("Incompatible key");
        }
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

    private void expandExternal(Position<Entry<K,V>> p, Entry<K,V> entry) throws InvalidPositionException {
        tree.set(p, entry);
        tree.addLeft(p, null);
        tree.addRight(p, null);
    }

    public Position<Entry<K,V>> root() {
        return tree.root();
    }

    private Position<Entry<K,V>> treeSearch(Position<Entry<K,V>> p, K key) throws InvalidPositionException {
        if(isExternal(p))
            return p;
        int comp = compare(key, p.getElement());
        if (comp == 0)
            return p;
        else if (comp < 0)
            return treeSearch(left(p), key);
        else
            return treeSearch(right(p), key);
    }

    public V get(K key) throws InvalidPositionException {
        checkKey(key);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        rebalanceAccess(p);
        if(isExternal(p))
            return null;
        return p.getElement().getValue();
    }

    public V put(K key, V value) throws InvalidPositionException {
        checkKey(key);
        Entry<K,V> newEntry = new MapEntry<>(key, value);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if(isExternal(p)) { // key is new
            expandExternal(p, newEntry);
            rebalanceInsert(p);
            return null;
        }
        else {
            V old = p.getElement().getValue();
            set(p, newEntry);
            rebalanceAccess(p);
            return old;
        }
    }

    public V remove(K key) {
        checkKey(key);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if(isExternal(p)) {
            rebalanceAccess(p);
            return null;
        }
        else {
            V old = p.getElement().getValue();
            if(isInternal(left(p)) && isInternal(right(p))) {
                Position<Entry<K,V>> replacement = treeMax(left(p));
                set(p, replacement.getElement());
                p = replacement;
            }
        }
        // now at most one of the children of p is internal
        Position<Entry<K,V>> leaf = (isExternal(left(p)) ? left(p) : right(p));
        Position<Entry<K,V>> sib = sibling(leaf);
        remove(leaf);
        remove(p);
        rebalanceDelete(p);
        return old;
    }

    // returns the position with maximum key in the subtree that has p as its root
    protected Position<Entry<K,V>> treeMax(Position<Entry<K,V>> p) {
        Position<Entry<K,V>> walk = p;
        while(isInternal(walk))
            walk = right(walk);
        return parent(walk);
    }

    // returns the entry with maximum key (or null if the map is empty)
    public Entry<K,V> lastEntry() {
        if(isEmpty())
            return null;
        return treeMax(root()).getElement();
    }

    // returns the entry with the maximum key among those not greater than key
    public Entry<K,V> floorEntry(K key) {
        checkKey(key);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if(isInternal(p))
            return p.getElement();
        while(!isRoot(p)) {
            if(p == right(parent(p)))
                return parent(p).getElement(); // the parent of p has the key sought
            else
                p = parent(p);
        }
        return null;    // there is no key not greater than key specified
    }

    // returns the entry with the maximum key among those minor than key
    public Entry<K,V> lowerEntry(K key) {
        checkKey(key);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if(isInternal(p) && isInternal(left(p)))
            return treeMax(left(p)).getElement();   // predecessor of p
        // else research failed or there is no left son
        while(!isRoot(p)) {
            if(p == right(parent(p)))
                return parent(p).getElement(); // the parent of p has the key sought
            else
                p = parent(p);
        }
        return null;    // there is no key minor than key specified
    }

    // returns a container with all entries in the map
    public Iterable<Entry<K,V>> entrySet() {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(size());
        for (Position<Entry<K,V>> p : tree.inorder())
            if(isInternal(p))
                buffer.add(p.getElement());
        return buffer;
    }

    // returns a container with the entries with key belongs to [fromKey, toKey)
    public Iterable<Entry<K,V>> subMap(K fromKey, K toKey) {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(size());
        if(compare(fromKey, toKey) < 0) // checks if fromKey < toKey
            subMapRecurse(fromKey, toKey, root(), buffer);
        return buffer;
    }

    private void subMapRecurse(K fromKey, K toKey, Position<Entry<K,V>> p, ArrayList<Entry<K,V>> buffer) {
        if(isInternal(p))
            if(compare(p.getElement().getKey(), fromKey) < 0)
                // the key of p is less than fromKey, so the searched entries are on the right
                subMapRecurse(fromKey, toKey, right(p), buffer);
        else {
            subMapRecurse(fromKey, toKey, left(p), buffer);
            if(compare(p.getElement().getKey(), toKey) < 0) {
                buffer.add(p.getElement());
                subMapRecurse(fromKey, toKey, right(p), buffer);
            }
        }
    }
}
