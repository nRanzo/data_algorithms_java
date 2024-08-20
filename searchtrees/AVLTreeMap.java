package searchtrees;

import java.util.Comparator;

import lists.Position;
import priorityqueue.Entry;
import trees.InvalidPositionException;

/**
 * AVLTreeMap is an implementation of a map using an AVL tree, a self-balancing
 * binary search tree. In an AVL tree, the heights of the two child subtrees of 
 * any node differ by at most one. If at any time they differ by more than one, 
 * rebalancing is performed to restore this property.
 * 
 * <p>An AVL tree ensures that the tree remains balanced after each insertion and 
 * deletion. The height balance property means that for every internal position p,
 * the heights of the left and right subtrees differ by at most one. This guarantees
 * that the height of the tree remains logarithmic in the number of nodes, ensuring
 * efficient search, insertion, and deletion operations.</p>
 *
 * <p>This implementation extends the {@link TreeMap} class and provides additional
 * methods for maintaining balance during updates (insertions and deletions).</p>
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class AVLTreeMap<K,V> extends TreeMap<K,V> {
    
    /**
     * Constructs an empty map using the natural ordering of keys.
     */
    public AVLTreeMap() {
        super();
    }

    /**
     * Constructs an empty map using the given comparator to order keys.
     *
     * @param comp the comparator that will be used to order the keys
     */
    public AVLTreeMap(Comparator<K> comp) {
        super(comp);
    }

    /**
     * Returns the height of the subtree rooted at the given position.
     *
     * @param p the position in the tree
     * @return the height of the subtree rooted at p
     */
    protected int height(Position<Entry<K,V>> p) {
        return tree.getAux(p);
    }

    /**
     * Recalculates and sets the height of the given position based on the
     * heights of its children.
     *
     * @param p the position whose height needs to be recalculated
     * @throws InvalidPositionException if the position is invalid
     */
    protected void recomputeHeight(Position<Entry<K,V>> p) throws InvalidPositionException {
        tree.setAux(p, 1 + Math.max(height(left(p)), height(right(p))));
    }

    /**
     * Checks if the node at the given position is balanced. A node is balanced if
     * the heights of its left and right children differ by at most one.
     *
     * @param p the position to check for balance
     * @return true if the node is balanced, false otherwise
     * @throws InvalidPositionException if the position is invalid
     */
    protected boolean isBalanced(Position<Entry<K,V>> p) throws InvalidPositionException {
        return Math.abs(height(left(p)) - height(right(p))) <= 1;
    }

    /**
     * Returns the child of the given position that has a height no less than
     * that of the other child. If the children have equal heights, the method
     * returns the left child by default.
     *
     * @param p the position whose taller child is to be returned
     * @return the child of p with the greater height
     * @throws InvalidPositionException if the position is invalid
     */
    protected Position<Entry<K,V>> tallerChild(Position<Entry<K,V>> p) throws InvalidPositionException {
        if (height(left(p)) > height(right(p)))
            return left(p);
        if (height(left(p)) < height(right(p)))
            return right(p);
        if (isRoot(p))
            return left(p);  // Either child can be returned since they are equal
        if (p == left(parent(p)))
            return left(p);
        else
            return right(p);
    }

    /**
     * Restores the balance of the tree after an insertion or deletion operation.
     * It traverses the path from the given position to the root, performing 
     * necessary rotations to correct imbalances.
     *
     * @param p the position to start rebalancing from
     * @throws InvalidPositionException if the position is invalid
     */
    protected void rebalance(Position<Entry<K,V>> p) throws InvalidPositionException {
        int oldHeight, newHeight;
        do {
            oldHeight = height(p);
            if (!isBalanced(p)) {
                p = restructure(tallerChild(tallerChild(p)));
                recomputeHeight(left(p));
                recomputeHeight(right(p));
            }
            recomputeHeight(p);
            newHeight = height(p);
            p = parent(p);
        } while (oldHeight != newHeight && p != null);
    }

    /**
     * Rebalances the tree after an insertion operation.
     *
     * @param p the position where insertion occurred
     * @throws InvalidPositionException if the position is invalid
     */
    protected void rebalanceInsert(Position<Entry<K,V>> p) throws InvalidPositionException {
        rebalance(p);
    }

    /**
     * Rebalances the tree after a deletion operation.
     *
     * @param p the position where deletion occurred
     * @throws InvalidPositionException if the position is invalid
     */
    protected void rebalanceDelete(Position<Entry<K,V>> p) throws InvalidPositionException {
        rebalance(parent(p));
    }

    /**
     * Performs a tree rotation to restore balance at a given unbalanced node.
     * The method determines the appropriate rotation (single or double) based on
     * the structure of the tree at the node and its children.
     *
     * @param x the child of the child of the node that is unbalanced (the "grandchild").
     * @return the new root of the subtree after restructuring.
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
     * @param p the node around which to perform the rotation.
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
