package priorityqueue;

/** 
 * Interface that defines an Entry: a couple of key and value.
 */
public interface Entry<K,V> {
    K getKey();
    V getValue();
}