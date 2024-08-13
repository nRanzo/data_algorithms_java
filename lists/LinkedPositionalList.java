import java.util.NoSuchElementException;

public class LinkedPositionalList<E> implements PositionalList<E> {

    private static class Node<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;
        public Node(E e, Node<E> p, Node<E> n) {
            element = e;
            prev = p;
            next = n;
        }

        public E getElement() throws IllegalStateException {
            // by convention null refers to nodes that are no longer valid
            if (next == null)
                throw new IllegalStateException("Position no longer valid");
            return element;
        }
        public Node<E> getPrev() {
            return prev;
        }
        public Node<E> getNext() {
            return next;
        }
        public void setElement(E e) {
            element = e;
        }
        public void setPrev(Node<E> p) {
            prev = p;
        }
        public void setNext(Node<E> n) {
            next = n;
        }
    }

    // instance variables
    private Node<E> header;
    private Node<E> trailer;
    private int size = 0;

    public LinkedPositionalList() {
        header = new Node<E>(null, null, null); // can't put trailer as a follower here ...
        trailer = new Node<E>(null, header, null);
        header.setNext(trailer);                      // ... so do only now
    }

    @SuppressWarnings("unchecked")
    private Node<E> validate(Object object) throws IllegalArgumentException {
        if(!(object instanceof Node))
            throw new IllegalArgumentException("Invalid position received");
        Node<E> node = (Node<E>) object; // safe cast, ignore type safety warn
        if(node.getNext() == null)
            throw new IllegalArgumentException("position no longer valid");
        return node;
    }

    private Position<E> position(Node<E> node) {
        if(node == header || node == trailer)
            return null;        // header and can't be passed to a user
        return position(node);  // much better choice than a cast
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Position<E> first() {
        if(isEmpty())
            return null;    // or an exception
        return position(header.getNext());   // first element is the one immediately after header
    }

    public Position<E> last() {
        if(isEmpty())
            return null;    // or an exception
        return position(trailer.getPrev());  // last element is the one immediately before trailer
    }

    @Override
    public Position<E> before(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return position(node.getPrev());
    }

    @Override
    public Position<E> after(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return position(node.getNext());
    }

    private Position<E> addBetween(E e, Node<E> predec, Node<E> succes) {
        Node<E> newest = new Node<>(e, predec, succes);
        predec.setNext(newest);
        succes.setPrev(predec);
        size++;
        return position(newest);
    }

    public Position<E> addFirst(E e) {
        return addBetween(e, header, header.getNext());
    }
    
    public Position<E> addLast(E e) {
        return addBetween(e, trailer.getPrev(), trailer);
    }

    public Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return addBetween(e, node.getPrev(), node);
    }

    public Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return addBetween(e, node, node.getNext());
    }

    public E get(Object object) throws IllegalArgumentException {
        Node<E> node = validate(object);
        return node.getElement();
    }    

    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E oldItem = node.getElement();
        node.setElement(e);
        return oldItem;
    }

    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        Node<E> predecessor = node.getPrev();
        Node<E> successor = node.getNext();
        predecessor.setNext(successor);
        successor.setPrev(predecessor);
        size--;
        E oldItem = node.getElement();
        node.setElement(null);  // helps Java's garbage collector
        node.setNext(null);     // no longer a valid node
        node.setPrev(null);     // no longer a valid node
        return oldItem;
    }

    public E removeFirst() throws NoSuchElementException {
        if (isEmpty()) throw new NoSuchElementException();
        return remove(position(header.getNext()));
    }
    
    public E removeLast() throws NoSuchElementException {
        if (isEmpty()) throw new NoSuchElementException();
        return remove(position(trailer.getPrev()));
    }

}