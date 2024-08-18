import maps.SortedTableMap;
import priorityqueue.Entry;

/**
 * A class that manages a database of entries representing cost-performance pairs.
 * 
 * <p>This class is designed to store and retrieve the best performance available 
 * for a given cost limit. Each entry in the database is a (cost, performance) pair, 
 * and the database maintains only non-dominated entries, meaning entries where 
 * no other entry has both lower cost and higher performance.</p>
 *
 * <p>The underlying data structure is a `SortedTableMap`, which provides efficient 
 * lookup and update operations based on cost. However, if a more efficient 
 * implementation is desired, a skip list could be used to reduce the expected 
 * time complexity of the `add` operation.</p>
 */
public class CostPerformanceDB {
    private SortedTableMap<Integer, Integer> map = new SortedTableMap<>();

    /**
     * Constructs an empty cost-performance database.
     */
    public CostPerformanceDB() {}

    /**
     * Returns the entry (cost, performance) with the maximum performance that 
     * has a cost not exceeding the specified limit.
     * 
     * <p>If no such entry exists (i.e., all entries have a cost greater than 
     * the specified limit), this method returns `null`.</p>
     *
     * @param cost The maximum cost limit.
     * @return The entry with the best performance for a cost not greater than 
     *         the specified limit, or `null` if no such entry exists.
     */
    public Entry<Integer, Integer> best(int cost) {
        return map.floorEntry(cost);
    }

    /**
     * Adds a new (cost, performance) entry to the database.
     * 
     * <p>If an existing entry has the same or lower cost but better or equal 
     * performance, the new entry is ignored. Otherwise, the new entry is added 
     * to the database, and any existing entries that are now dominated by the 
     * new entry (i.e., have higher cost and lower or equal performance) are removed.</p>
     * 
     * <p>The time complexity of this operation is O(n) in the worst case using 
     * a `SortedTableMap`. If a skip list is used instead, the expected time 
     * complexity can be improved to O((1 + r) log n), where r is the number of 
     * entries removed.</p>
     *
     * @param c The cost of the new entry.
     * @param p The performance of the new entry.
     */
    public void add(int c, int p) {
        Entry<Integer, Integer> other = map.floorEntry(c);  // Find the entry with the highest cost <= c
        if (other != null && other.getValue() >= p)         // If that entry's performance >= p
            return;                                         // (c, p) is dominated, so ignore it
        
        map.put(c, p);  // Add the new entry to the database

        // Remove all entries that are now dominated by the new entry
        other = map.higherEntry(c);
        while (other != null && other.getValue() <= p) {
            map.remove(other.getKey());
            other = map.higherEntry(c);
        }
    }
}
