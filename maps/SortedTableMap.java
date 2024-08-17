package maps;

import java.util.Comparator;

import lists.ArrayList;
import priorityqueue.Entry;

public class SortedTableMap<K,V> extends AbstractSortedMap<K,V> {
    private ArrayList<MapEntry<K,V>> table = new ArrayList<>();
    public SortedTableMap() {
        super();
    }
    public SortedTableMap(Comparator<K> comp) {
        super(comp);
    }
    // returns the minimun index in table[] in which there is a entry with key not minor than k (otherwise by convention high+1)
    private int findIndex(K key, int low, int high) {
        if (high < low)
            return high + 1;
        int mid = (low + high) / 2;
        int comp = compare(key, table.get(mid));
        if(comp == 0)
            return mid;
        else if(comp < 0)
            return findIndex(key, low, mid - 1);
        // else
        return findIndex(key, mid + 1, high);
    }
    // version of findIndex that search in the entire table
    private int findIndex(K key) {
        return findIndex(key, 0, table.size() - 1);
    }
    public int size() {
        return table.size();
    }
    public V get(K key) {
        int j = findIndex(key);
        if(j == size() || compare(key, table.get(j)) != 0)
            return null;    // there is no element with that key
        return table.get(j).getValue();
    }
    public V put(K key, V value) {
        int j = findIndex(key);
        if (j < size() && compare(key, table.get(j)) == 0)  // found a match
            return table.get(j).setValue(value);
        table.add(j, new MapEntry<K,V>(key, value));
        return null;
    }
    public V remove(K key) {
        int j = findIndex(key);
        if(j == size() || compare(key, table.get(j)) != 0)
            return null;    // there is no element with that key
        return table.remove(j).getValue();
    }
    private Entry<K,V> safeEntry(int j) {
        if(j < 0 || j >= table.size())
            return null;
        return table.get(j);
    }
    public Entry<K,V> firstEntry() {
        return safeEntry(0);
    }
    public Entry<K,V> lastEntry() {
        return safeEntry(table.size() - 1);
    }
    // return the entry with the minor key from those not minor than key (or null)
    public Entry<K,V> ceilingEntry(K key) {
        return safeEntry(findIndex(key));
    }
    // return the entry with the max key from those not major than key (or null)
    public Entry<K,V> floorEntry(K key) {
        int j = findIndex(key);
        if (j == size() || !key.equals(table.get(j).getKey()))
            j--;
        return safeEntry(j);
    }
    // returns the entry with the max key from those minor than key (or null)
    public Entry<K,V> lowerEntry(K key) {
        return safeEntry(findIndex(key) - 1);   // the immediately left than ceilingEntry
    }
    // returns the entry with the min key from those major than key (or null)
    public Entry<K,V> higherEntry(K key) {
        int j = findIndex(key);
        if (j < size() && key.equals(table.get(j).getKey()))
            j++; // to the right of the exact match
        return safeEntry(j);
    }

    // snapshot iterator support for entrySet() and subMap()
    private Iterable<Entry<K,V>> snapshot(int startIndex, K stop) {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();
        int j = startIndex;
        while (j < table.size() && (stop == null || compare(stop, table.get(j)) > 0)) {
            buffer.add(table.get(j++));
        }
        return buffer;
    }

    public Iterable<Entry<K,V>> entrySet() {
        return snapshot(0, null);
    }
    public Iterable<Entry<K,V>> subMap(K fromKey, K toKey) {
        return snapshot(findIndex(fromKey), toKey);
    }
}
