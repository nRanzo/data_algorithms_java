public abstract class AbstractTree<E> implements HTree<E> {
    public boolean isInternal(Position<E> p) throws InvalidPositionException {
        return numChildren(p) > 0;
    }

    public boolean isExternal(Position<E> p) throws InvalidPositionException {
        return numChildren(p) == 0;
    }

    public boolean isRoot(Position<E> p) throws EmptyTreeException, InvalidPositionException {
        if(p == null || !(p instanceof Position<E>))
            throw new InvalidPositionException();
        return p == root();
    }
}
