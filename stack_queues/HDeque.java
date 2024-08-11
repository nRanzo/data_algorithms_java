/** 
 * Interface that defines a double queue: a container of items with insertions and removals 
 * at both ends. This is way simplified compared to java.util.Deque.
 */
public interface HDeque<E> {

    int size();
    boolean isEmpty();
    E first();
    E last();
    void addFirst(E e);
    void addLast(E e);
    E removeFirst();
    E removeLast();
}