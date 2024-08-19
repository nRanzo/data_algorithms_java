package searchtrees;

import java.util.Comparator;

import lists.Position;
import priorityqueue.Entry;
import trees.InvalidPositionException;

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
    protected void recomputeHeight(Position<Entry<K,V>> p) throws InvalidPositionException {
        tree.setAux(p, 1 + Math.max(height(left(p)), height(right(p))));
    }

    // returns true if and only if p has a balance factor of 1, 0, or -1
    protected boolean isBalanced(Position<Entry<K,V>> p) throws InvalidPositionException {
        return Math.abs(height(left(p)) - height(right(p))) <= 1;
    }

    // returns the child of p whose height is no less than that of the other child
    protected Position<Entry<K,V>> tallerChild(Position<Entry<K,V>> p) throws InvalidPositionException {
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
    protected void rebalance(Position<Entry<K,V>> p) throws InvalidPositionException {
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
    protected void rebalanceInsert(Position<Entry<K,V>> p) throws InvalidPositionException {
        rebalance(p);
    }

    // overrides the TreeMap method that is invoked after a removal
    protected void rebalanceDelete(Position<Entry<K,V>> p) throws InvalidPositionException {
        rebalance(parent(p));
    }

    /**
     * Performs a tree rotation to restore balance at a given unbalanced node.
     * The method determines the appropriate rotation (single or double) based on
     * the structure of the tree at the node and its children.
     * 
     * @param x The child of the child of the node that is unbalanced (the "grandchild").
     * @return The new root of the subtree after restructuring.
     * @throws InvalidPositionException if any positions involved are invalid.
     */
    protected Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) throws InvalidPositionException {
        Position<Entry<K, V>> y = parent(x);
        Position<Entry<K, V>> z = parent(y);

        if ((x == right(y)) == (y == right(z))) {  // Case 1 or Case 2: single rotation
            rotate(y);  // Perform a single rotation
            return y;   // y becomes the new root of the subtree
        } else {  // Case 3 or Case 4: double rotation
            rotate(x);  // Perform a single rotation on x
            rotate(x);  // Perform a single rotation on x (after the first rotation)
            return x;   // x becomes the new root of the subtree
        }
    }

    /**
     * Performs a single rotation (left or right) depending on the child configuration.
     * 
     * @param p The node around which to perform the rotation.
     * @throws InvalidPositionException if any positions involved are invalid.
     */
    private void rotate(Position<Entry<K, V>> p) throws InvalidPositionException {
        Position<Entry<K, V>> parent = parent(p);
        Position<Entry<K, V>> grandparent = parent(parent);

        // Determine the direction of rotation
        if (p == left(parent)) {
            if (parent == left(grandparent)) {
                // Perform a right rotation
                tree.rotate(parent);
            } else {
                // Perform a left-right rotation
                tree.rotate(p);
                tree.rotate(p);
            }
        } else {
            if (parent == right(grandparent)) {
                // Perform a left rotation
                tree.rotate(parent);
            } else {
                // Perform a right-left rotation
                tree.rotate(p);
                tree.rotate(p);
            }
        }
    }
}
