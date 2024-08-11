/**
 * The SinglyLinkedList<E> class provides efficient O(1) operations for adding or removing elements 
 * at the beginning or end, but requires O(n) time for accessing or searching elements. Unlike circular or 
 * doubly linked lists, it lacks backward traversal capability and can complicate operations like removing 
 * the last element. While memory-efficient due to storing only a single reference per node, this design 
 * choice may be less optimal for applications needing more flexible or bidirectional navigation.
 *
 * @param <E> the type of elements held in this list
 */
public class SinglyLinkedList<E> {

    // Private static nested class to represent nodes in the list
    private static class Node<E> {
        private E element;
        private Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }

    // Instance variables for the list
    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;

    // Default constructor
    public SinglyLinkedList() {}

    /**
     * Returns the number of elements in the list.
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if the list is empty.
     *
     * @return true if the list is empty; false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the first element in the list.
     *
     * @return the first element, or null if the list is empty
     */
    public E first() {
        if (isEmpty()) return null;  // Could also throw an exception if null values are allowed in the list
        return head.getElement();
    }

    /**
     * Returns the last element in the list.
     *
     * @return the last element, or null if the list is empty
     */
    public E last() {
        if (isEmpty()) return null;  // Could also throw an exception if null values are allowed in the list
        return tail.getElement();
    }

    /**
     * Adds an element to the front of the list.
     *
     * @param element the element to add
     */
    public void addFirst(E element) {
        head = new Node<>(element, head);
        if (size == 0)
            tail = head;
        size++;
    }

    /**
     * Adds an element to the end of the list.
     *
     * @param element the element to add
     */
    public void addLast(E element) {
        Node<E> newest = new Node<>(element, null); // Create a new node as the tail
        if (isEmpty()) {
            head = newest;
        } else {
            tail.setNext(newest);
        }
        tail = newest;
        size++;
    }

    /**
     * Removes and returns the first element in the list.
     *
     * @return the removed element, or null if the list is empty
     */
    public E removeFirst() {
        if (isEmpty()) return null;  // Could also throw an exception if null values are allowed in the list
        E element = head.getElement();
        head = head.getNext();
        size--;
        if (isEmpty())
            tail = null;  // Avoid memory leaks
        return element;
    }

    /**
     * Creates and returns a deep copy of the list.
     *
     * @return a clone of the list
     * @throws CloneNotSupportedException if the list cannot be cloned
     */
    @Override
    public SinglyLinkedList<E> clone() throws CloneNotSupportedException {
        @SuppressWarnings("unchecked")
        SinglyLinkedList<E> other = (SinglyLinkedList<E>) super.clone(); // Shallow copy of instance variables
        if (!isEmpty()) {
            other.head = new Node<>(head.getElement(), null);
            Node<E> walk = head.getNext();
            Node<E> otherTail = other.head;
            while (walk != null) {
                Node<E> newest = new Node<>(walk.getElement(), null);
                otherTail.setNext(newest);
                otherTail = newest;
                walk = walk.getNext();
            }
            other.tail = otherTail;
        }
        return other;
    }

    /**
     * Compares the specified object with this list for equality.
     *
     * @param o the object to compare with
     * @return true if the specified object is equal to this list
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof SinglyLinkedList)) return false;
        SinglyLinkedList<?> other = (SinglyLinkedList<?>) o;
        if (size != other.size()) return false;
        // scanning every element in search of a difference
        Node<E> walkA = head;
        Node<?> walkB = other.head;
        while (walkA != null) {
            if (!walkA.getElement().equals(walkB.getElement())) return false;
            walkA = walkA.getNext();
            walkB = walkB.getNext();
        }
        // at this point no more elements can be scanned, and no difference was met
        return true;
    }
}
