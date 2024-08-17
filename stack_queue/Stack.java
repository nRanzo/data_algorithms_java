package stack_queue;

/*  Collection of objects that are inserted and deleted according to the last-in first-out principle. 
    Although it serves a similar purpose, this interface is different from java.util.Stack */

interface Stack<E> {

    int size();
    boolean isEmpty();
    void push(E e); // to insert an item at the top of the list
    E top();        // inspect the element on top of the list, without removing it
    E pop();        // remove and obtain the element on top of the list
}