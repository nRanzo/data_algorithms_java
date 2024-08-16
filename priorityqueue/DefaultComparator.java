package priorityqueue;

import java.util.Comparator;

public class DefaultComparator<E> implements Comparator<E> {
    @SuppressWarnings("unchecked")
    public int compare(E a, E b) throws ClassCastException {
        // Check if both objects are instances of Comparable
        if (a instanceof Comparable && b instanceof Comparable) {
            return ((Comparable<E>) a).compareTo(b);    
        } else {
            throw new ClassCastException("Objects are not comparable: " + a.getClass() + " and " + b.getClass());
        }
    }
}
