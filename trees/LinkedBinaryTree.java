/*  In this implementation I use a convention to identify nodes that used to belong to the tree but now 
    no longer do. When a node leaves the tree, it is not deleted, but a reference of thath node is assigned 
    to its parent. By doing that, even if its element is preserved, the parent node (and not even the children)
    can be inspected by the deleted node */

import java.util.Iterator;

public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    protected static class Node<E> implements Position<E> {
        private E element;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;

        public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild) {
            element = e;
            parent = above;
            left = leftChild;
            right = leftChild;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getParent() {
            return parent;
        }

        public Node<E> getLeft() {
            return left;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setElement(E e) {
            element = e;
        }

        public void setParent(Node<E> newParent) {
            parent = newParent;
        }

        public void setLeft(Node<E> newLeft) {
            left = newLeft;
        }

        public void setRight(Node<E> newRight) {
            right = newRight;
        }
    } 

    private class ElementIterator implements Iterator<E> {
        Iterator<Position<E>> posIterator;
        public ElementIterator() {
            try {
                posIterator = positions().iterator();
            } catch (InvalidPositionException | EmptyTreeException e) {
                throw new RuntimeException("Unexpected exception: ", e);
            }
        }
        public boolean hasNext() {
            return posIterator.hasNext();
        }
        public E next() {
            return posIterator.next().getElement();
        }
        public void remove() {
            posIterator.remove();
        }
        @SuppressWarnings("unused") // may be used if you need to
        public Iterator<E> iterator() {
            return new ElementIterator();
        }
    }

    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    protected Node<E> root = null;
    protected int size = 0;

    public LinkedBinaryTree() {}

    protected Node<E> validate(Position<E> p) throws InvalidPositionException {
        if(!(p instanceof Node))
            throw new InvalidPositionException("p not an instance of Node");
        Node<E> node = (Node<E>) p;     // ignore type safety
        if(node.getParent() == node)    // by convention node has been removed
            throw new InvalidPositionException("p is no longer in the tree");
        return node;
    }

    public Position<E> addRoot(E e) throws IllegalStateException {
        if(!isEmpty())
            throw new IllegalStateException("Can't add root since tree is not empty");
        root = createNode(e, null, null, null);
        size = 1;   // or ++ is the same
        return root;
    }

    public Position<E> addLeft(Position<E> p, E e) throws IllegalStateException, InvalidPositionException {
        Node<E> parent = validate(p);
        if(parent.getLeft() != null)
            throw new IllegalStateException("p has already a left child, can't add another one");
        Node<E> child = createNode(e, parent, null, null);
        parent.setLeft(child);
        return child;
    }

    public Position<E> addRight(Position<E> p, E e) throws IllegalStateException, InvalidPositionException {
        Node<E> parent = validate(p);
        if(parent.getRight() != null)
            throw new IllegalStateException("p has already a right child, can't add another one");
        Node<E> child = createNode(e, parent, null, null);
        parent.setRight(child);
        return child;
    }

    public E set(Position<E> p, E e) throws InvalidPositionException {
        Node<E> node = validate(p);
        E temp = node.getElement();
        node.setElement(e);
        return temp;
    }

    // connects to binary trees by adding their roots as childs of a Position
    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws InvalidPositionException {
        Node<E> node = validate(p);
        if(isInternal(p))
            throw new InvalidPositionException("p must be a leaf to be a root");
        // size = 0; I'm not sure if we need to reset size
        size += t1.size() + t2.size(); 
        if(!(t1.isEmpty())){
            t1.root.setParent(node);
            node.setLeft(t1.root);
            t1.size = 0;    // no more needed
        }
        if(!(t2.isEmpty())){
            t2.root.setParent(node);
            node.setRight(t2.root);
            t2.size = 0;    // no more needed
        }
    }

    public boolean hasLeft(Position<E> p) throws InvalidPositionException {
        Node<E> node = validate(p);
        return node.getLeft() != null;
    }
    
    public boolean hasRight(Position<E> p) throws InvalidPositionException {
        Node<E> node = validate(p);
        return node.getRight() != null;
    }    

    @Override
    public Position<E> left(Position<E> p) throws InvalidPositionException {
        Node<E> node = validate(p);
        return node.getLeft();
    }

    @Override
    public Position<E> right(Position<E> p) throws InvalidPositionException {
        Node<E> node = validate(p);
        return node.getRight();
    }

    @Override
    public Position<E> root() throws EmptyTreeException {
        if(isEmpty())
            throw new EmptyTreeException("Tree has no nodes");
        return (Position<E>)root;
    }

    @Override
    public Position<E> parent(Position<E> p) throws InvalidPositionException {
        Node<E> node = validate(p);
        return node.getParent();
    }

    @Override
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    @Override
    public Iterable<Position<E>> positions() throws InvalidPositionException, EmptyTreeException {
        return preorder();
    }

    public Iterable<Position<E>> preorder() throws InvalidPositionException, EmptyTreeException {
        List<Position<E>> snapshot = new ArrayList<>();
        if(!isEmpty())
            preOrderSubtree(root(), snapshot);
        return snapshot;
    }

    private void preOrderSubtree(Position<E> p, List<Position<E>> snapList) throws InvalidPositionException {
        snapList.add(p);
        for(Position<E> c : children(p))
            preOrderSubtree(c, snapList);
    }

    public E remove(Position<E> p) throws IllegalStateException, InvalidPositionException {
        Node<E> node = validate(p);
        if(numChildren(p) == 2)
            throw new IllegalStateException("p has two children, can't be removed");
        Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());
        if(child != null)
            child.setParent(node.getParent());
        if(node == root)
            root = child;
        else{
            Node<E> parent = node.getParent();
            if(node == parent.getLeft())
                parent.setLeft(child);
            else
                parent.setRight(child);
        }
        size--;
        E temp = node.getElement();
        node.setElement(null);
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node);   // convention for ex nodes
        return temp;
    }

    @Override
    public E replace(Position<E> p, E element) throws InvalidPositionException {
        if(!(p instanceof Node))
            throw new InvalidPositionException("p not an instance of Node");
        Node<E> node = (Node<E>) p;  // ignore type safety
        E temp = node.getElement();
        node.setElement(element);
        return temp;
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
