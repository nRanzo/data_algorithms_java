package lists;

import java.util.NoSuchElementException;

public class DoublyLinkedList<E> {

    private static class Node<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;
        public Node(E e, Node<E> p, Node<E> n) {
            element = e;
            prev = p;
            next = n;
        }
        public E getElement() {
            return element;
        }
        public Node<E> getPrev() {
            return prev;
        }
        public Node<E> getNext() {
            return next;
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

    public DoublyLinkedList() {
        header = new Node<E>(null, null, null); // can't put trailer as a follower here ...
        trailer = new Node<E>(null, header, null);
        header.setNext(trailer);                      // ... so do only now
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E first() {
        if(isEmpty())
            return null;    // or an exception
        return header.getNext().getElement();   // first element is the one immediately after header
    }

    public E last() {
        if(isEmpty())
            return null;    // or an exception
        return trailer.getPrev().getElement();  // last element is the one immediately before trailer
    }

    public void addFirst(E e) {
        addBetween(e, header, header.getNext());
    }
    
    public void addLast(E e) {
        addBetween(e, trailer.getPrev(), trailer);
    }

    public E removeFirst() throws NoSuchElementException {
        if(isEmpty())
            throw new NoSuchElementException();
        return remove(header.getNext());
    }

    public E removeLast() throws NoSuchElementException {
        if(isEmpty())
            throw new NoSuchElementException();
        return remove(trailer.getPrev());
    }

    private void addBetween(E e, Node<E> predecessor, Node<E> successor) {
        Node<E> newest = new Node<>(e, predecessor, successor);
        predecessor.setNext(newest);
        successor.setPrev(newest);
    }

    private E remove(Node<E> e) {
        Node<E> prev = e.getPrev();
        Node<E> next = e.getNext();
        prev.setNext(next);
        next.setPrev(prev);
        size --;
        return e.getElement();
    }
    
}