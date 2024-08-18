package searchtrees;

import java.util.Comparator;

import lists.Position;
import priorityqueue.Entry;

public class AVLTreeMap<K,V> extends TreeMap<K,V> {
    
    // constructs an empty map using natural ordering between keys
    public AVLTreeMap() {
        super();
    }

    // constructs an empty map by sorting the keys with the given comparator
    public AVLTreeMap(Comparator<K> comp) {
        super(comp);
    }

    protected int height(Position<Entry<K,V>> p) {
        return tree.getAux(p);
    }

    // recalculates the height of the given position based on the heights of its children
    protected void recomputeHeight(Position<Entry<K,V>> p) {
        tree.setAux(p, 1 + Math.max(height(left(p)), height(right(p))));
    }

    // returns true if and only if p has a balance factor of 1, 0, or -1
    protected boolean isBalanced(Position<Entry<K,V>> p) {
        return Math.abs(height(left(p)) - height(right(p))) <= 1;
    }

    // returns the child of p whose height is no less than that of the other child
    protected Position<Entry<K,V>> tallerChild(Position<Entry<K,V>> p) {
        if(height(left(p)) > height(right(p)))
            return left(p);
        if(height(left(p)) < height(right(p)))
            return right(p);
        if(isRoot(p))
            return left(p);     // or right(p), it makes no difference
        if (p == left(parent(p)))
            return left(p);
        else
            return right(p);
    }

    // auxiliary method used to restore balance after an insertion or removal operation. It goes up the path 
    // from p, performing a backhoe restructuring when it finds an imbalance, continuing to the end.
    protected void rebalance(Position<Entry<K,V>> p) {
        int oldHeight, newHeight;
        do {
            oldHeight = height(p);
            if(!isBalanced(p)) {
                p = restructure(tallerChild(tallerChild(p)));
                recomputeHeight(left(p));
                recomputeHeight(right(p));
            }
            recomputeHeight(p);
            newHeight = height(p);
            p = parent(p);
        } while (oldHeight != newHeight && p != null);
    }

    // overrides the TreeMap method that is invoked after an insertion
    protected void rebalanceInsert(Position<Entry<K,V>> p) {
        rebalance(p);
    }

    // overrides the TreeMap method that is invoked after a removal
    protected void rebalanceDelete(Position<Entry<K,V>> p) {
        rebalance(parent(p));
    }
}
