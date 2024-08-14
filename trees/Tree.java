import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tree<E> extends AbstractTree<E> {

    private TreeNode<E> root;  // Root node of the tree
    private int size;  // Number of nodes in the tree

    public Tree() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public Position<E> root() throws EmptyTreeException {
        if (root == null) {
            throw new EmptyTreeException();
        }
        return root;
    }

    @Override
    public Position<E> parent(Position<E> p) throws InvalidPositionException {
        TreeNode<E> node = validate(p);
        if (node.getParent() == null) {
            throw new InvalidPositionException("The position has no parent.");
        }
        return node.getParent();
    }

    @Override
    public Iterable<Position<E>> children(Position<E> p) throws InvalidPositionException {
        TreeNode<E> node = validate(p);
        return new ArrayList<>(node.getChildren());
    }

    @Override
    public int numChildren(Position<E> p) throws InvalidPositionException {
        TreeNode<E> node = validate(p);
        return node.getChildren().size();
    }

    @Override
    public Iterator<E> iterator() {
        List<E> elements = new ArrayList<>();
        for (Position<E> position : positions()) {
            elements.add(position.getElement());
        }
        return elements.iterator();
    }

    @Override
    public Iterable<Position<E>> positions() {
        List<Position<E>> positions = new ArrayList<>();
        if (!isEmpty()) {
            preOrderTraversal(root, positions);
        }
        return positions;
    }

    @Override
    public E replace(Position<E> p, E element) throws InvalidPositionException {
        TreeNode<E> node = validate(p);
        return node.replaceElement(element);
    }

    // Helper method to validate the position and cast to TreeNode<E>
    private TreeNode<E> validate(Position<E> p) throws InvalidPositionException {
        if (!(p instanceof TreeNode)) {
            throw new InvalidPositionException("Invalid position type.");
        }
        return (TreeNode<E>) p;
    }

    // Helper method for pre-order traversal
    private void preOrderTraversal(TreeNode<E> node, List<Position<E>> positions) {
        positions.add(node);  // Visit the node first (pre-order)
        for (TreeNode<E> child : node.getChildren()) {
            preOrderTraversal(child, positions);  // Recur for each child
        }
    }

    // Method to add a root to an empty tree
    public Position<E> addRoot(E element) throws IllegalStateException {
        if (root != null) {
            throw new IllegalStateException("Tree already has a root.");
        }
        root = new TreeNode<>(element, null);
        size = 1;
        return root;
    }

    // Method to add a child to a specific parent node
    public Position<E> addChild(Position<E> p, E element) throws InvalidPositionException {
        TreeNode<E> parent = validate(p);
        TreeNode<E> child = new TreeNode<>(element, parent);
        parent.addChild(child);
        size++;
        return child;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
