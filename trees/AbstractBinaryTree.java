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
}
