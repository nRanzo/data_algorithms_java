import java.util.Iterator;

public interface Tree<E> extends Iterable<E> {

    Position<E> root() throws InvalidPositionException;
    Position<E> parent(Position<E> p) throws InvalidPositionException, IndexOutOfBoundsException;
    Iterable<Position<E>> children(Position<E> p) throws InvalidPositionException;
    int numChildren(Position<E> p) throws InvalidPositionException;
    boolean isRoot(Position<E> p) throws EmptyTreeException, InvalidPositionException;
    boolean isInternal(Position<E> p) throws InvalidPositionException;
    boolean isExternal(Position<E> p) throws InvalidPositionException;
    Iterator<E> iterator();
    Iterable<Position<E>> positions() throws InvalidPositionException, EmptyTreeException;
    E replace(Position<E> p, E element) throws InvalidPositionException;
    int size();
    boolean isEmpty();
}
