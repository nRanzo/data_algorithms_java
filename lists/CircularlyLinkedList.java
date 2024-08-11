/*  The CircularlyLinkedList class provides O(1) complexity for addFirst and rotate operations due to its 
    constant-time pointer updates and maintained tail reference. For addLast, it achieves O(1) complexity 
    if the tail is updated accordingly, unlike a singly linked list which requires O(n) time to traverse 
    and find the end. Traversal operations in a circularly linked list take O(n) time, similar to singly 
    and doubly linked lists, but without the bidirectional traversal capability of the doubly linked list. 
    Thus, while the circularly linked list is efficient for certain operations, it lacks the flexibility of 
    bidirectional traversal found in doubly linked lists. */
    
import java.util.NoSuchElementException;

public class CircularlyLinkedList<E> {

    // private class nested to SinglyLinkedList
    private static class Node<E> {
        private E element;
        private Node<E> next;
        public Node(E e, Node<E> n){
            element = e;
            next = n;
        }
        public E getElement() {
            return element;
        }
        public Node<E> getNext() {
            return next;
        }
        public void setNext(Node<E> n) {
            next = n;
        }
    }

    // instance variabiles
    private Node<E> tail = null;
    private int size = 0;

    public CircularlyLinkedList() {}

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E first() {
        if(isEmpty())   
            return null;    // no nodes are in the list
        return tail.getNext().getElement();  // first node is the successor of tail
    }

    public E last() {
        if(isEmpty())   
            return null;            // no nodes are in the list
        return tail.getElement();   // first node is the successor of tail
    }

    public void rotate() {
        if(tail != null)
            tail = tail.getNext();  // after which first node becomes the last
        // else do nothing
    }

    public void addFirst(E e) {
        if(isEmpty()) {
            tail = new Node<>(e, null);
            tail.setNext(tail);     // close the circle
        }
        else {
            Node<E> newest = new Node<>(e, tail.getNext());
            tail.setNext(newest);
        }
        size++;
    }

    public void addLast(E e) {
        addFirst(e);    // always re-use code if possible, here we can
        tail = tail.getNext();
    }

    public E removeFirst() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty; cannot remove element.");
        }
        Node<E> head = tail.getNext();
        if(head == tail)    // right now list has only one node
            tail = null;    // so after removal list will be empty and so tail must be null
        else
            tail.setNext(head.getNext());
        size--;
        return head.getElement();
    }

}