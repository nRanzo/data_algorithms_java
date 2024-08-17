import lists.Position;

public interface BinaryTree<E> extends Tree<E> {
    // If p has a left child, returns its position, otherwise null
    Position<E> left(Position<E> p) throws InvalidPositionException;

    // If p has a right child, returns its position, otherwise null
    Position<E> right(Position<E> p) throws InvalidPositionException;

    // if p has a parent, and it's not the only child of that parent, return the other child's Position
    Position<E> sibling(Position<E> p) throws InvalidPositionException;
}
