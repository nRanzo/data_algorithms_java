package lists;

/*  This ArrayList implementation dynamically adjusts its internal array size to efficiently manage memory. 
    When elements are added, the array doubles its capacity if full, ensuring that insertion (both add(Object o) 
    and add(int index, Object element)) remains O(1) amortized. When elements are removed, if the list's size 
    falls below one-quarter of its capacity, the array halves in size to free up memory, making removal O(n) 
    due to element shifting. The get(int index) and set(int index, Object element) operations are O(1) since 
    they access the array directly. Similarly, size() and isEmpty() are O(1) as they simply return stored values. 
    The implementation balances between time efficiency for frequent operations and memory efficiency during 
    dynamic resizing. */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<E> implements List<E> {

    public static final int CAPACITY = 30; // Default initial capacity
    private E[] data;
    private int size = 0;

    public ArrayList() {
        this(CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ArrayList(int capacity) {
        data = (E[]) new Object[capacity];
    }

    protected void checkIndex(int i, int n) throws ArrayIndexOutOfBoundsException {
        if (i < 0 || i >= n)
            throw new ArrayIndexOutOfBoundsException("Illegal index: " + i + ", size was " + n);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean add(Object o) {
        if (size == data.length) {
            resize(2 * data.length); // Double the size if the array is full
        }
        data[size++] = (E) o;
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void add(int index, Object element) throws ArrayIndexOutOfBoundsException, IllegalStateException {
        checkIndex(index, size + 1);
        if (size == data.length)
            resize(2 * data.length);
        for (int k = size - 1; k >= index; k--)
            data[k + 1] = data[k];
        data[index] = (E) element;
        size++;
    }

    @Override
    public boolean addAll(Collection<E> c) {
        Object[] elements = c.toArray();
        int newSize = size + elements.length;
        if (newSize > data.length) {
            resize(Math.max(newSize, 2 * data.length));
        }
        System.arraycopy(elements, 0, data, size, elements.length);
        size = newSize;
        return elements.length > 0;
    }

    @Override
    public boolean addAll(int index, Collection<E> c) throws ArrayIndexOutOfBoundsException {
        checkIndex(index, size + 1); // Check if index is within valid range
        int collectionSize = c.size(); // Get the size of the collection to be added
        if (collectionSize == 0) return false; // If collection is empty, return false

        // Ensure there is enough space, resize if necessary
        while (size + collectionSize > data.length) {
            resize(2 * data.length);
        }

        // Shift elements to the right to make space for the new elements
        for (int i = size - 1; i >= index; i--) {
            data[i + collectionSize] = data[i];
        }

        // Copy elements from the collection to the array using an iterator
        Iterator<E> iterator = c.iterator();
        int i = index;
        while (iterator.hasNext()) {
            data[i++] = iterator.next();
        }

        size += collectionSize; // Update the size of the ArrayList
        return true;
    }

    @Override
    public boolean contains(E o) {
        return indexOf(o) >= 0;
    }

    @Override
    public boolean containsAll(Collection<E> c) {
        for (E o : c.toArray()) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
        resize(CAPACITY); // Reset to initial capacity if necessary
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E get(int index) throws ArrayIndexOutOfBoundsException {
        checkIndex(index, size);
        return data[index];
    }

    @Override
    public E set(int index, E element) throws ArrayIndexOutOfBoundsException {
        checkIndex(index, size);
        E oldElement = data[index];
        data[index] = (E) element;
        return oldElement;
    }

    @Override
    public int indexOf(E o) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(E o) {
        for (int i = size - 1; i >= 0; i--) {
            if (data[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public E remove(int index) throws ArrayIndexOutOfBoundsException {
        checkIndex(index, size);
        E removedElement = data[index];
        for (int k = index; k < size - 1; k++) {
            data[k] = data[k + 1];
        }
        data[--size] = null;
        
        // Shrink the array if the size falls below 1/4 of the capacity
        if (size > 0 && size <= data.length / 4) {
            resize(data.length / 2);
        }
        
        return removedElement;
    }

    @Override
    public boolean remove(E o) {
        int index = indexOf(o);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<E> c) {
        boolean modified = false;
        for (E o : c.toArray()) {
            while (remove(o)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<E> c) throws ArrayIndexOutOfBoundsException {
        boolean modified = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(data[i])) {
                remove(i);
                i--; // Adjust for the shift in elements
                modified = true;
            }
        }
        return modified;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] toArray() {
        E[] arrayCopy = (E[]) new Object[size];
        System.arraycopy(data, 0, arrayCopy, 0, size);
        return arrayCopy;
    }

    @Override
    public E[] toArray(E[] a) {
        if (a.length < size) {
            return toArray();
        }
        System.arraycopy(data, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) throws ArrayIndexOutOfBoundsException {
        checkIndex(fromIndex, size);
        checkIndex(toIndex, size + 1);
        ArrayList<E> subList = new ArrayList<E>(toIndex - fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            subList.add(data[i]);
        }
        return subList;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    @Override
    public Iterable<E> listIterator() {
        return new ArrayListIterator();
    }

    @Override
    public Iterable<E> listIterator(int index) {
        return new ArrayListIterator(index);
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity];
        System.arraycopy(data, 0, newData, 0, size);
        data = (E[]) newData;
    }

    private class ArrayListIterator implements ListIterator<E>, Iterator<E> {
        private int currentIndex = 0;
        private boolean removable = false;

        public ArrayListIterator(int index) {
            currentIndex = index;
        }

        public ArrayListIterator() {}
        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            removable = true;
            return data[currentIndex++];
        }

        @Override
        public boolean hasPrevious() {
            return currentIndex > 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            return data[--currentIndex];
        }

        @Override
        public int nextIndex() {
            return currentIndex;
        }

        @Override
        public int previousIndex() {
            return currentIndex - 1;
        }

        @Override
        public void remove() throws IllegalStateException {
            if(!removable)
                throw new IllegalStateException("No item to remove");
            ArrayList.this.remove(--currentIndex);
        }

        @Override
        public void set(E e) {
            ArrayList.this.set(currentIndex - 1, e);
        }

        @Override
        public void add(Object e) {
            ArrayList.this.add(currentIndex++, e);
        }
        @Override
        public Iterator<E> iterator() {
            return new ArrayListIterator();
        }
    }

}
