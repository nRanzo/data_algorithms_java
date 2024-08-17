package stack_queue;

/*  The Queue interface defines the queue ADT, characterized by the first-in first-out principle for 
    managing insertions and removals */

public interface Queue<E> {
    
    int size();
    boolean isEmpty();
    void enqueue(E e);     // inserts an item at the end of the queue
    E first();          // inspects the first item, without removing it
    E dequeue();        // remove and returns the first item
}
