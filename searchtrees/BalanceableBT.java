package searchtrees;

import lists.Position;
import priorityqueue.Entry;
import trees.InvalidPositionException;
import trees.LinkedBinaryTree;

/**
 * A binary tree implementation that supports balancing operations, suitable for building 
 * balanced search trees like AVL trees or red-black trees.
 * 
 * <p>This class extends LinkedBinaryTree to include operations for node rotation 
 * and restructuring, essential for maintaining balance in search trees. It uses 
 * auxiliary data stored in each node to assist with balancing operations.</p>
 * 
 * @param <K> The type of keys maintained by this tree.
 * @param <V> The type of values associated with the keys.
 */
public class BalanceableBT<K,V> extends LinkedBinaryTree<Entry<K,V>> {

    /**
     * A specialized node class that includes an auxiliary integer to support tree balancing.
     * 
     * @param <E> The element type stored in the node.
     */
    protected static class BSTNode<E> extends Node<E> {
        private int aux = 0;

        /**
         * Constructs a new BSTNode with the given element, parent, left child, and right child.
         *
         * @param e The element to be stored in the node.
         * @param parent The parent node.
         * @param leftChild The left child node.
         * @param rightChild The right child node.
         */
        BSTNode(E e, Node<E> parent, Node<E> leftChild, Node<E> rightChild) {
            super(e, parent, leftChild, rightChild);
        }

        /**
         * Returns the auxiliary value stored in the node.
         * 
         * @return The auxiliary value.
         */
        public int getAux() {
            return aux;
        }

        /**
         * Sets a new auxiliary value for the node.
         * 
         * @param newAux The new auxiliary value.
         */
        public void setAux(int newAux) {
            aux = newAux;
        }
    }

    /**
     * Returns the auxiliary value associated with the given position.
     * 
     * @param p The position whose auxiliary value is to be retrieved.
     * @return The auxiliary value.
     * @throws ClassCastException If the position is not of type `BSTNode`.
     */
    public int getAux(Position<Entry<K,V>> p) {
        return ((BSTNode<Entry<K,V>>) p).getAux();
    }

    /**
     * Sets a new auxiliary value for the node at the given position.
     * 
     * @param p The position whose auxiliary value is to be set.
     * @param newAux The new auxiliary value.
     * @throws ClassCastException If the position is not of type `BSTNode`.
     */
    public void setAux(Position<Entry<K,V>> p, int newAux) {
        ((BSTNode<Entry<K,V>>) p).setAux(newAux);
    }

    /**
     * Creates a new node to be inserted into the tree.
     * 
     * @param e The element to be stored in the node.
     * @param parent The parent node.
     * @param left The left child node.
     * @param right The right child node.
     * @return A new `BSTNode` instance.
     */
    protected Node<Entry<K,V>> createNode(Entry<K,V> e, Node<Entry<K,V>> parent, 
                                          Node<Entry<K,V>> left, Node<Entry<K,V>> right) {
        return new BSTNode<>(e, parent, left, right);
    }

    /**
     * Relinks a parent node with a child node in the specified direction.
     * 
     * @param parent The parent node.
     * @param child The child node.
     * @param makeLeftChild If true, the child becomes the left child of the parent; otherwise, it becomes the right child.
     */
    private void relink(Node<Entry<K,V>> parent, Node<Entry<K,V>> child, boolean makeLeftChild) {
        child.setParent(parent);
        if (makeLeftChild)
            parent.setLeft(child);
        else
            parent.setRight(child);
    }

    /**
     * Rotates the subtree rooted at the given position around its parent node.
     * 
     * @param p The position of the node to be rotated.
     * @throws InvalidPositionException If the position is invalid or cannot be rotated.
     */
    public void rotate(Position<Entry<K,V>> p) throws InvalidPositionException {
        Node<Entry<K,V>> x = validate(p);
        Node<Entry<K,V>> y = x.getParent();
        Node<Entry<K,V>> z = y.getParent();
        if (z == null) {
            root = x;       // x becomes the root
            x.setParent(null);
        } else {
            relink(z, x, y == z.getLeft());
        }
        // Rotate x and y, adjusting the subtree
        if (x == y.getLeft()) {
            relink(y, x.getRight(), true);  // The right child of x becomes the left child of y
            relink(x, y, false);            // y becomes the right child of x
        } else {
            relink(y, x.getLeft(), false);  // The left child of x becomes the right child of y
            relink(x, y, true);             // y becomes the left child of x
        }
    }

    /**
     * Restructures the subtree rooted at the given position using rotations.
     * 
     * <p>This method is used to restore balance in the tree after insertions or deletions. 
     * Depending on the configuration of the node and its ancestors, a single or double rotation 
     * is performed.</p>
     * 
     * @param x The position of the node to be restructured.
     * @return The position of the new root of the restructured subtree.
     * @throws InvalidPositionException If the position is invalid.
     */
    public Position<Entry<K,V>> restructure(Position<Entry<K,V>> x) throws InvalidPositionException {
        Position<Entry<K,V>> y = parent(x);
        Position<Entry<K,V>> z = parent(y);
        if ((x == right(y)) == (y == right(z))) {    // Single rotation case, y is the new root of subtree
            rotate(y);
            return y;
        } else {                                     // Double rotation case, x is the new root of subtree
            rotate(x);
            rotate(x);
            return x;
        }
    }
}
