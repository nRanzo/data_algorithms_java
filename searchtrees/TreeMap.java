package searchtrees;

import java.util.Comparator;

import lists.Position;
import lists.ArrayList;
import maps.AbstractSortedMap;
import priorityqueue.Entry;
import trees.InvalidPositionException;

/**
 * A map implementation that uses a binary search tree.
 *
 * @param <K> The type of keys maintained by this map.
 * @param <V> The type of mapped values.
 */
public class TreeMap<K, V> extends AbstractSortedMap<K, V> {

    protected BalanceableBT<K, V> tree = new BalanceableBT<>();

    /**
     * Constructs an empty TreeMap with natural ordering of keys.
     */
    public TreeMap() {
        super();
        tree.addRoot(null);
    }

    /**
     * Constructs an empty TreeMap with a custom comparator for ordering keys.
     *
     * @param c The comparator used to order the keys.
     */
    public TreeMap(Comparator<K> c) {
        super(c);
        tree.addRoot(null);
    }

    // ---- Utility Methods ----

    /**
     * Checks whether the key is valid by comparing it with itself.
     */
    protected boolean checkKey(K key) throws IllegalArgumentException {
        try {
            return (compare(key, key) == 0);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Incompatible key");
        }
    }

    /**
     * Expands an external node by inserting the entry and creating two external children.
     */
    private void expandExternal(Position<Entry<K, V>> p, Entry<K, V> entry) throws IllegalArgumentException {
        try{
            tree.set(p, entry);
        tree.addLeft(p, null); // left child
        tree.addRight(p, null); // right child
        }
        catch(InvalidPositionException e) {
            // should never happen
            throw new IllegalArgumentException("check key");
        }
    }

    /**
     * Returns the position in the tree with the given key, or the last visited external node.
     */
    private Position<Entry<K, V>> treeSearch(Position<Entry<K, V>> p, K key) throws IllegalArgumentException {
        try{
            if (isExternal(p)) {
                return p;
            }
            int comp = compare(key, p.getElement().getKey());
            if (comp == 0) {
                return p;
            } else if (comp < 0) {
                return treeSearch(left(p), key);
            } else {
                return treeSearch(right(p), key);
            }
        }
        catch(InvalidPositionException e) {
            // should never happen
            throw new IllegalArgumentException("check key");
        }
    }

    /**
     * Returns the position with the maximum key in the subtree rooted at p.
     */
    protected Position<Entry<K, V>> treeMax(Position<Entry<K, V>> p) throws IllegalArgumentException {
        try{
            Position<Entry<K, V>> walk = p;
            while (isInternal(walk)) {
                walk = right(walk);
            }
            return parent(walk);
        }
        catch(InvalidPositionException e) {
            // should never happen
            throw new IllegalArgumentException("check key");
        }
    }

    // ---- Query Methods ----

    /**
     * Returns the number of entries (key-value pairs) in the map.
     * <p>
     * The size is calculated by counting only the internal nodes in the underlying 
     * binary search tree, since external nodes are placeholders and do not store actual entries.
     * The tree's size includes both internal and external nodes, so the formula 
     * (tree.size() - 1) / 2 is used to count only internal nodes (entries).
     * </p>
     *
     * @return The number of entries in the map.
     */
    @Override
    public int size() {
        return (tree.size() - 1) / 2; // only internal nodes count as entries
    }

    /**
     * Checks if the map is empty.
     * <p>
     * The map is considered empty if it contains no entries (key-value pairs).
     * This is equivalent to checking if the size of the map is zero.
     * </p>
     *
     * @return true if the map contains no entries, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Determines if the given position is the root of the tree.
     * <p>
     * This method delegates the root-checking logic to the underlying tree structure.
     * It checks if the given position corresponds to the root of the binary search tree.
     * </p>
     *
     * @param p The position to check.
     * @return true if the position is the root, false otherwise.
     * @throws InvalidPositionException if the position is invalid (e.g., null or not part of the tree).
     */
    protected boolean isRoot(Position<Entry<K, V>> p) throws InvalidPositionException {
        return tree.isRoot(p);
    }

    /**
     * Retrieves the value associated with the specified key.
     * <p>
     * The method searches the binary search tree for the given key. If the key is found, 
     * the corresponding value is returned. If the key is not found (i.e., it maps to 
     * an external node), null is returned. The tree may be rebalanced after the search
     * to optimize future operations.
     * </p>
     *
     * @param key The key whose associated value is to be returned.
     * @return The value associated with the specified key, or null if the key is not found.
     * @throws IllegalArgumentException if the key is invalid or not comparable.
     */
    @Override
    public V get(K key) throws IllegalArgumentException {
        try{
            checkKey(key);
            Position<Entry<K, V>> p = treeSearch(root(), key);
            rebalanceAccess(p);
            if (isExternal(p)) {
                return null;
            }
            return p.getElement().getValue();
        }
        catch(InvalidPositionException e) {
            // should never happen
            throw new IllegalArgumentException("check key");
        }
    }

    // ---- Update Methods ----

    /**
     * Associates the specified value with the specified key in this map.
     * <p>
     * If the key is not already present in the map, a new entry is created and inserted.
     * If the key is already present, its value is updated with the new value provided.
     * The tree may be rebalanced after the insertion or update to maintain optimal structure.
     * </p>
     *
     * @param key The key with which the specified value is to be associated.
     * @param value The value to be associated with the specified key.
     * @return The previous value associated with the key, or null if there was no mapping for the key.
     * @throws IllegalArgumentException if the key is invalid or not comparable.
     */
    @Override
    public V put(K key, V value) throws IllegalArgumentException {
        try {
            checkKey(key);
            Entry<K, V> newEntry = createMapEntry(key, value);
            Position<Entry<K, V>> p = treeSearch(root(), key);
            if (isExternal(p)) { // key is new
                expandExternal(p, newEntry);
                rebalanceInsert(p);
                return null;
            } else {
                V old = p.getElement().getValue();
                try {
                    tree.set(p, newEntry);
                }
                catch(InvalidPositionException e) {
                    throw new IllegalArgumentException("set method throw InvPosExc, check key and value");
                }
                rebalanceAccess(p);
                return old;
            }
        }
        catch (InvalidPositionException e) {
            // should never happen
            throw new IllegalArgumentException("check key");
        }
    }

    /**
     * Removes the entry for the specified key from this map, if it is present.
     * <p>
     * The method searches the tree for the given key. If the key is found, its corresponding 
     * entry is removed. If the key is not found, null is returned. The tree may be rebalanced
     * after the removal to maintain optimal structure.
     * </p>
     *
     * @param key The key whose entry is to be removed.
     * @return The value associated with the removed key, or null if the key was not found.
     * @throws IllegalArgumentException if the key is invalid or not comparable.
     */
    @Override
    public V remove(K key) throws IllegalArgumentException {
        try {
            checkKey(key);
            Position<Entry<K, V>> p = treeSearch(root(), key);
            if (isExternal(p)) {
                rebalanceAccess(p);
                return null;
            }
            V old = p.getElement().getValue();
            if (isInternal(left(p)) && isInternal(right(p))) { // both children are internal
                Position<Entry<K, V>> replacement = treeMax(left(p));
                tree.set(p, replacement.getElement());
                p = replacement;
            }
            Position<Entry<K, V>> leaf = (isExternal(left(p)) ? left(p) : right(p));
            Position<Entry<K, V>> sib = sibling(leaf);
            tree.remove(leaf);
            tree.remove(p);
            rebalanceDelete(sib);
            return old;
        }
        catch(InvalidPositionException e) {
            // should never happen
            throw new IllegalArgumentException("set method throw InvPosExc, check key and value");
        }
    }

    // ---- Tree Operations ----

    /**
     * Returns the root position of the binary search tree.
     * <p>
     * This method provides direct access to the root node of the underlying binary search tree.
     * The root node is the topmost node in the tree, with no parent. It serves as the entry point 
     * for all tree traversal operations.
     * </p>
     *
     * @return The root position of the tree.
     */
    public Position<Entry<K, V>> root() {
        return tree.root();
    }

    /**
     * Returns the left child of the given position in the binary search tree.
     * <p>
     * The left child of a node is the node directly connected to the left of the current node.
     * This method delegates to the underlying tree structure to retrieve the left child.
     * </p>
     *
     * @param p The position whose left child is to be returned.
     * @return The left child position of the given node.
     * @throws InvalidPositionException if the position is invalid or does not have a left child.
     */
    public Position<Entry<K, V>> left(Position<Entry<K, V>> p) throws InvalidPositionException {
        return tree.left(p);
    }

    /**
     * Returns the right child of the given position in the binary search tree.
     * <p>
     * The right child of a node is the node directly connected to the right of the current node.
     * This method delegates to the underlying tree structure to retrieve the right child.
     * </p>
     *
     * @param p The position whose right child is to be returned.
     * @return The right child position of the given node.
     * @throws InvalidPositionException if the position is invalid or does not have a right child.
     */
    public Position<Entry<K, V>> right(Position<Entry<K, V>> p) throws InvalidPositionException {
        return tree.right(p);
    }

    /**
     * Returns the parent of the given position in the binary search tree.
     * <p>
     * The parent of a node is the node directly connected above the current node.
     * This method delegates to the underlying tree structure to retrieve the parent node.
     * </p>
     *
     * @param p The position whose parent is to be returned.
     * @return The parent position of the given node.
     * @throws InvalidPositionException if the position is invalid or is the root (has no parent).
     */
    public Position<Entry<K, V>> parent(Position<Entry<K, V>> p) throws InvalidPositionException {
        return tree.parent(p);
    }

    /**
     * Returns the sibling of the given position in the binary search tree.
     * <p>
     * The sibling of a node is the node directly connected to the same parent as the current node,
     * but on the opposite side (left or right). This method delegates to the underlying tree structure
     * to retrieve the sibling node.
     * </p>
     *
     * @param p The position whose sibling is to be returned.
     * @return The sibling position of the given node.
     * @throws InvalidPositionException if the position is invalid or has no sibling.
     */
    protected Position<Entry<K, V>> sibling(Position<Entry<K, V>> p) throws InvalidPositionException {
        return tree.sibling(p);
    }

    // ---- Entry Operations ----

    /**
     * Returns the entry with the maximum key in the map.
     * <p>
     * This method locates and returns the entry with the largest key in the map.
     * If the map is empty, it returns null. The maximum key is found by traversing
     * the rightmost path in the binary search tree.
     * </p>
     *
     * @return The entry with the maximum key, or null if the map is empty.
     */
    public Entry<K, V> lastEntry() {
        if (isEmpty()) {
            return null;
        }
        return treeMax(root()).getElement();
    }

    /**
     * Returns the entry with the greatest key less than or equal to the specified key.
     * <p>
     * This method searches the tree for the specified key. If the key is found, 
     * it returns the corresponding entry. If the key is not found, the method 
     * traverses upwards in the tree to find the largest key that is less than the specified key.
     * If no such key exists, it returns null.
     * </p>
     *
     * @param key The key to compare against.
     * @return The entry with the greatest key less than or equal to the specified key,
     *         or null if no such entry exists.
     * @throws IllegalArgumentException if the key is invalid or not comparable.
     */
    public Entry<K, V> floorEntry(K key) throws IllegalArgumentException {
        try {
            checkKey(key);
            Position<Entry<K, V>> p = treeSearch(root(), key);
            if (isInternal(p)) {
                return p.getElement();
            }
            while (!isRoot(p)) {
                if (p == right(parent(p))) {
                    return parent(p).getElement();
                }
                p = parent(p);
            }
            return null; // no entry less than or equal to key
        }
        catch (InvalidPositionException e) {
            // should never happen
            throw new IllegalArgumentException("check key");
        }
    }

    /**
     * Returns the entry with the greatest key strictly less than the specified key.
     * <p>
     * This method searches the tree for the specified key. If the key is found, it checks 
     * the left subtree for the largest key that is less than the specified key. 
     * If the key is not found, the method traverses upwards in the tree to find the 
     * largest key that is strictly less than the specified key. If no such key exists, it returns null.
     * </p>
     *
     * @param key The key to compare against.
     * @return The entry with the greatest key strictly less than the specified key,
     *         or null if no such entry exists.
     * @throws IllegalArgumentException if the key is invalid or not comparable.
     */
    public Entry<K, V> lowerEntry(K key) throws IllegalArgumentException {
        try{
            checkKey(key);
            Position<Entry<K, V>> p = treeSearch(root(), key);
            if (isInternal(p) && isInternal(left(p))) {
                return treeMax(left(p)).getElement();
            }
            while (!isRoot(p)) {
                if (p == right(parent(p))) {
                    return parent(p).getElement();
                }
                p = parent(p);
            }
            return null; // no entry less than key
        }
        catch(InvalidPositionException e) {
            // should never happen
            throw new IllegalArgumentException("check key");
        }
        
    }

    // ---- Submap and Entry Set ----

    /**
     * Returns an iterable collection of all entries in the map, ordered by the keys.
     * <p>
     * This method performs an in-order traversal of the binary search tree to collect
     * all internal nodes, which represent the valid key-value pairs in the map. The resulting
     * entries are returned in a sorted order according to their keys.
     * </p>
     *
     * @return An iterable collection of all entries in the map, ordered by the keys.
     * @throws IllegalArgumentException if there is an issue with retrieving the entries.
     */
    @Override
    public Iterable<Entry<K, V>> entrySet() throws IllegalArgumentException {
        try{
            ArrayList<Entry<K, V>> buffer = new ArrayList<>(size());
            for (Position<Entry<K, V>> p : tree.inorder()) {
                if (isInternal(p)) {
                    buffer.add(p.getElement());
                }
            }
            return buffer;
        }
        catch(InvalidPositionException e) {
            // should never happen
            throw new IllegalArgumentException("check key");
        }
    }

    /**
     * Returns an iterable collection of entries with keys in the specified range [fromKey, toKey).
     * <p>
     * This method gathers all entries whose keys fall within the specified range (inclusive of fromKey
     * and exclusive of toKey). The entries are retrieved using an in-order traversal, ensuring they
     * are returned in sorted order.
     * </p>
     *
     * @param fromKey The lower bound (inclusive) of the keys to include.
     * @param toKey The upper bound (exclusive) of the keys to include.
     * @return An iterable collection of entries with keys in the specified range.
     * @throws IllegalArgumentException if the key range is invalid.
     */
    public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) {
        ArrayList<Entry<K, V>> buffer = new ArrayList<>(size());
        if (compare(fromKey, toKey) < 0) {
            subMapRecurse(fromKey, toKey, root(), buffer);
        }
        return buffer;
    }

    /**
     * Recursively collects entries within the specified key range [fromKey, toKey) into a buffer.
     * <p>
     * This method performs a recursive in-order traversal of the binary search tree, adding entries
     * to the buffer if their keys fall within the specified range. The traversal ensures that the entries
     * are added in sorted order.
     * </p>
     *
     * @param fromKey The lower bound (inclusive) of the keys to include.
     * @param toKey The upper bound (exclusive) of the keys to include.
     * @param p The current position in the tree during traversal.
     * @param buffer The buffer to collect matching entries.
     * @throws IllegalArgumentException if the position is invalid.
     */
    private void subMapRecurse(K fromKey, K toKey, Position<Entry<K, V>> p, 
                               ArrayList<Entry<K, V>> buffer) throws IllegalArgumentException {
        try{
            if (isInternal(p)) {
                if (compare(p.getElement().getKey(), fromKey) < 0) {
                    subMapRecurse(fromKey, toKey, right(p), buffer);
                } else {
                    subMapRecurse(fromKey, toKey, left(p), buffer);
                    if (compare(p.getElement().getKey(), toKey) < 0) {
                        buffer.add(p.getElement());
                        subMapRecurse(fromKey, toKey, right(p), buffer);
                    }
                }
            }
        }
        catch(InvalidPositionException e) {
            // should never happen
            throw new IllegalArgumentException("check key");
        }
    }

    // ---- Balancing Hooks (To be Implemented by Subclasses) ----

    /**
     * Balances the tree after an insertion operation.
     * <p>
     * Subclasses should override this method to provide specific balancing logic,
     * such as rotations for AVL or Red-Black Trees.
     * </p>
     *
     * @param p The position where the insertion occurred.
     */
    protected void rebalanceInsert(Position<Entry<K, V>> p) throws InvalidPositionException {
        // Implement balancing logic for insertions
    }

    /**
     * Balances the tree after a deletion operation.
     * <p>
     * Subclasses should override this method to provide specific balancing logic,
     * such as rotations for AVL or Red-Black Trees.
     * </p>
     *
     * @param p The position where the deletion occurred.
     */
    protected void rebalanceDelete(Position<Entry<K, V>> p) throws InvalidPositionException {
        // Implement balancing logic for deletions
    }

    /**
     * Balances the tree after an access operation.
     * <p>
     * Subclasses should override this method to provide specific balancing logic,
     * such as splaying in a Splay Tree.
     * </p>
     *
     * @param p The position that was accessed.
     */
    protected void rebalanceAccess(Position<Entry<K, V>> p) {
        // Implement balancing logic for access
    }

    /**
     * Checks if the given position in the tree is an internal node.
     * <p>
     * An internal node is a node that has at least one child (i.e., not a leaf). 
     * This method delegates to the underlying tree structure to perform the check.
     * </p>
     *
     * @param p The position to check.
     * @return true if the position is an internal node; false otherwise.
     * @throws InvalidPositionException if the position is invalid.
     */
    protected boolean isInternal(Position<Entry<K, V>> p) throws InvalidPositionException {
        return tree.isInternal(p);
    }

    /**
     * Checks if the given position in the tree is an external node (leaf).
     * <p>
     * An external node, or leaf, is a node that has no children. This method delegates 
     * to the underlying tree structure to perform the check.
     * </p>
     *
     * @param p The position to check.
     * @return true if the position is an external node (leaf); false otherwise.
     * @throws InvalidPositionException if the position is invalid.
     */
    protected boolean isExternal(Position<Entry<K, V>> p) throws InvalidPositionException {
        return tree.isExternal(p);
    }
}
