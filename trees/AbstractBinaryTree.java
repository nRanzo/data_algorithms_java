public abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E> {
    
    public Position<E> sibling(Position<E> p) throws InvalidPositionException {
        Position<E> parent = parent(p);
        if(parent == null)
            return null;
        if(p == left(parent))
            return right(parent);
        return left(parent); // else not needed
    }

    public int numChildren(Position<E> p) throws InvalidPositionException {
        int count = 0;
        if(left(p) != null)
            count++;
        if(right(p) != null)
            count++;
        return count;
    }

    public Iterable<Position<E>> children(Position<E> p) throws InvalidPositionException {
        List<Position<E>> snapshot =  new ArrayList<Position<E>>(2);

        if(left(p) != null)
            snapshot.add(left(p));
        if(right(p) != null)
            snapshot.add(right(p));
        return snapshot;
    }

    /**
     * Recursively performs an in-order traversal of the subtree rooted at the given position.
     * 
     * <p>This method traverses the left subtree, visits the current node, and then traverses the right subtree, 
     * adding each visited position to the provided snapshot list. It is called internally by the 
     * {@code inorder()} method to gather the positions in in-order sequence.</p>
     * 
     * @param p the position representing the root of the subtree to traverse.
     * @param snapshot the list that accumulates the positions in in-order sequence.
     * @throws InvalidPositionException if the position {@code p} is invalid.
     */
    private void inorderSubtree(Position<E> p, List<Position<E>> snapshot) throws InvalidPositionException {
        // Traverse the left subtree
        if (left(p) != null)
            inorderSubtree(left(p), snapshot);

        // Visit the node after the left subtree
        snapshot.add(p);

        // Traverse the right subtree
        if (right(p) != null)
            inorderSubtree(right(p), snapshot);
    }

    /**
     * Returns an iterable collection of positions representing the in-order traversal of the tree.
     * 
     * @return an iterable collection of positions in in-order sequence.
     * @throws InvalidPositionException if any position in the tree is invalid.
     */
    public Iterable<Position<E>> inorder() throws InvalidPositionException {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty())
            inorderSubtree(root(), snapshot);
        return snapshot;
    }

    /**
     * Returns an iterable collection of all positions in the tree using in-order traversal.
     * 
     * @return an iterable collection of all positions in the tree.
     * @throws IllegalStateException if an InvalidPositionException occurs during traversal.
     */
    public Iterable<Position<E>> positions() throws IllegalStateException {
        try {
            return inorder();
        } catch (InvalidPositionException e) {
            throw new IllegalStateException("Can't iterate over positions", e);
        }
    }

}
