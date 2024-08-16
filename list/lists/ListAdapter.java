package lists;

// PLEASE NOTE: even if this implementation is correct, but I din't pay attention to backing
// this means that if you wanna use this feel free to, but you will likely need to make a few
// changes in order to make this work the best for you

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

/**
 * Adapter for the HList interface that uses a Vector of Object.
 * This class provides an implementation of the HList interface by adapting a Vector object.
 */
public class ListAdapter<E> implements List<E> {
    /**
    * Object vector used as the concrete basis of the list.
    */
    protected Vector<E> vector;

    /**
     * ListAdapter constructor, it initializes a new vector of Objects.
     */
    public ListAdapter() {
        this.vector = new Vector<E>();
    }

    // metodi di HCollection

    /**
     * Adds a new element to the list
     *
     * @param o the element to add
     * @return true if the add operation was successful
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean add(Object o) {
        vector.addElement((E) o);
        return true;
    }

    /**
     * Adds all elements in a collection to the list.
     *
     * @param c the collection containing the elements to add
     * @return true if list was modified successfully
     */
    @Override
    public boolean addAll(Collection<E> c) {
        boolean modified = false;
        Iterator<E> it = c.iterator();
        while (it.hasNext()) {
            if (add(it.next())) {
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Removes all elements from the list
     */
    @Override
    public void clear() {
        vector.removeAllElements();
    }

    /**
     * Verify if the list contains a specified element
     *
     * @param o the element whose presence you want to know
     * @return true if the list contains the specified element
     */
    @Override
    public boolean contains(Object o) {
        return vector.contains(o);
    }

    /**
     * Tests whether the list contains all elements of a specified collection.
     *
     * @param c the collection to check
     * @return true if the list contains all the elements of the collection
     */
    @Override
    public boolean containsAll(Collection<E> c) {
        Iterator<E> it = c.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compare this list with the specified object. I used the wildcard <?> instead of E in the cast within the equals method to 
     * avoid unchecked cast warnings. In Java, generic types are erased during compilation, meaning the exact type of the generic 
     * cannot be checked at runtime. By using <?>, we indicate that we don't care about the specific type of the generic in this context, 
     * only that the object is an instance of the generic class ListAdapter. This approach maintains type safety without triggering 
     * compiler errors or warnings.
     *
     * @param o the object to compare with this list
     * @return true if the specified object is equal to this list
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof ListAdapter) {
            ListAdapter<?> other = (ListAdapter<?>) o;
            return vector.equals(other.vector);
        }
        return false;
    }

    /**
     * Returns the hash code for this list.
     *
     * @return the hash code for this list
     */
    @Override
    public int hashCode() {
        return vector.hashCode();
    }

    /**
     * Check if the list is empty.
     *
     * @return true if the list is empty
     */
    @Override
    public boolean isEmpty() {
        return vector.isEmpty();
    }

    /**
     * Returns an iterator for the list.
     *
     * @return an iterator for the list
     */
    @Override
    public Iterator<E> iterator() {
        return new ListAdapterIterator();
    }

    /**
     * Removes the first occurrence of the specified element from the list.
     *
     * @param o the element to remove
     * @return true if the item was successfully removed
     */
    @Override
    public boolean remove(Object o) {
        return vector.removeElement(o);
    }

    /**
     * Rimuove tutti gli elementi della lista presenti in una collezione specificata.
     *
     * @param c la collezione contenente gli elementi da rimuovere
     * @return true se la lista è stata modificata
     */
    @Override
    public boolean removeAll(Collection<E> c) {
        boolean modified = false;
        Iterator<E> it = c.iterator();
        while (it.hasNext()) {
            if (remove(it.next())) {
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Mantiene solo gli elementi della lista presenti in una collezione specificata.
     *
     * @param c la collezione contenente gli elementi da mantenere
     * @return true se la lista è stata modificata
     * @throws ArrayIndexOutOfBoundsException se un indice è fuori dall'intervallo valido
     */
    @Override
    public boolean retainAll(Collection<E> c) throws ArrayIndexOutOfBoundsException {
        boolean modified = false;
        for (int i = 0; i < vector.size(); i++) {
            if (!c.contains(vector.elementAt(i))) {
                vector.removeElementAt(i);
                i--;
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Restituisce la dimensione della lista.
     *
     * @return la dimensione della lista
     */
    @Override
    public int size() {
        return vector.size();
    }

    /**
     * Restituisce un array contenente tutti gli elementi della lista.
     *
     * @return un array contenente tutti gli elementi della lista
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[vector.size()];
        vector.copyInto(array);
        return array;
    }

    /**
     * Restituisce un array contenente tutti gli elementi della lista;
     * l'array restituito sarà dello stesso tipo dell'array specificato.
     *
     * @param a l'array nel quale memorizzare gli elementi della lista
     * @return un array contenente tutti gli elementi della lista
     */
    @Override
    public Object[] toArray(Object[] a) {
        if (a.length < vector.size()) {
            return toArray();
        }
        vector.copyInto(a);
        if (a.length > vector.size()) {
            a[vector.size()] = null;
        }
        return a;
    }

    // metodi di HList

    /**
     * Aggiunge un elemento alla lista in una posizione specificata.
     *
     * @param index la posizione in cui aggiungere l'elemento
     * @param element l'elemento da aggiungere
     * @throws ArrayIndexOutOfBoundsException se l'indice è fuori dall'intervallo valido
     */
    @SuppressWarnings("unchecked")
    @Override
    public void add(int index, Object element) {
        vector.insertElementAt((E) element, index);
    }

    /**
     * Aggiunge tutti gli elementi di una collezione alla lista a partire da una posizione specificata.
     *
     * @param index la posizione da cui iniziare ad aggiungere gli elementi
     * @param c la collezione contenente gli elementi da aggiungere
     * @return true se la lista è stata modificata
     * @throws ArrayIndexOutOfBoundsException se l'indice è fuori dall'intervallo valido
     */
    @Override
    public boolean addAll(int index, Collection<E> c) {
        boolean modified = false;
        Iterator<E> it = c.iterator();
        while (it.hasNext()) {
            add(index++, it.next());
            modified = true;
        }
        return modified;
    }

    /**
     * Restituisce l'elemento in una posizione specificata.
     *
     * @param index la posizione dell'elemento da restituire
     * @return l'elemento nella posizione specificata
     * @throws ArrayIndexOutOfBoundsException se l'indice è fuori dall'intervallo valido
     */
    @Override
    public E get(int index) throws ArrayIndexOutOfBoundsException {
        return vector.elementAt(index);
    }

    /**
     * Restituisce l'indice della prima occorrenza dell'elemento specificato nella lista.
     *
     * @param o l'elemento da cercare
     * @return l'indice della prima occorrenza dell'elemento, o -1 se non è presente
     */
    @Override
    public int indexOf(Object o) {
        return vector.indexOf(o);
    }

    /**
     * Restituisce l'indice dell'ultima occorrenza dell'elemento specificato nella lista.
     *
     * @param o l'elemento da cercare
     * @return l'indice dell'ultima occorrenza dell'elemento, o -1 se non è presente
     * @throws IndexOutOfBoundsException se l'indice è fuori dall'intervallo valido
     */
    @Override
    public int lastIndexOf(Object o) throws IndexOutOfBoundsException {
        return vector.lastIndexOf(o);
    }

    /**
     * Restituisce un iteratore di lista per la lista.
     *
     * @return un iteratore di lista per la lista
     */
    @SuppressWarnings("unchecked")
    @Override
    public Iterable<E> listIterator() {
        return (Iterable<E>) new ListAdapterListIterator(0);
    }

    /**
     * Restituisce un iteratore di lista per la lista a partire da una posizione specificata.
     *
     * @param index la posizione iniziale dell'iteratore di lista
     * @return un iteratore di lista per la lista
     */
    @SuppressWarnings("unchecked")
    @Override
    public Iterable<E> listIterator(int index) {
        return (Iterable<E>) new ListAdapterListIterator(index);
    }

    /**
     * Rimuove l'elemento in una posizione specificata.
     *
     * @param index la posizione dell'elemento da rimuovere
     * @return l'elemento rimosso
     * @throws ArrayIndexOutOfBoundsException se l'indice è fuori dall'intervallo valido
     */
    @Override
    public Object remove(int index) throws ArrayIndexOutOfBoundsException {
        Object element = vector.elementAt(index);
        vector.removeElementAt(index);
        return element;
    }

    /**
     * Sostituisce l'elemento in una posizione specificata con un nuovo elemento.
     *
     * @param index la posizione dell'elemento da sostituire
     * @param element il nuovo elemento
     * @return l'elemento precedente nella posizione specificata
     * @throws ArrayIndexOutOfBoundsException se l'indice è fuori dall'intervallo valido
     */
    @SuppressWarnings("unchecked")
    @Override
    public E set(int index, Object element) throws ArrayIndexOutOfBoundsException {
        Object oldElement = vector.elementAt(index);
        vector.setElementAt((E)element, index);
        return (E)oldElement;
    }

    /**
     * Restituisce una sublist della lista tra due indici specificati.
     *
     * @param fromIndex l'indice iniziale (inclusivo)
     * @param toIndex l'indice finale (esclusivo)
     * @return una sublist tra i due indici specificati
     * @throws ArrayIndexOutOfBoundsException se un indice è fuori dall'intervallo valido
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) throws ArrayIndexOutOfBoundsException {
        return new SubList(this, fromIndex, toIndex);
    }

    // classi iteratori interne

    /**
     * Implementazione dell'iteratore per ListAdapter.
     */
    protected class ListAdapterIterator implements Iterator<E> {
        int cursor;
        int lastRet = -1;

        /**
         * Verifica se ci sono altri elementi nella lista.
         *
         * @return true se ci sono altri elementi nella lista
         */
        public boolean hasNext() {
            return cursor != vector.size();
        }

        /**
         * Restituisce il prossimo elemento nella lista.
         *
         * @return il prossimo elemento nella lista
         * @throws ArrayIndexOutOfBoundsException se non ci sono più elementi
         */
        public E next() throws ArrayIndexOutOfBoundsException {
            int i = cursor;
            if (i >= vector.size()) {
                throw new RuntimeException("No such element");
            }
            cursor = i + 1;
            return (E) vector.elementAt(lastRet = i);   // necessary
        }

        /**
         * Rimuove l'ultimo elemento restituito dall'iteratore.
         */
        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            vector.removeElementAt(lastRet);
            cursor = lastRet;
            lastRet = -1;
        }
    }

    /**
     * Implementazione dell'iteratore di lista per ListAdapter.
     */
    protected class ListAdapterListIterator extends ListAdapterIterator implements ListIterator<E> {
        ListAdapterListIterator(int index) {
            cursor = index;
        }

        /**
         * Verifica se ci sono elementi precedenti nella lista.
         *
         * @return true se ci sono elementi precedenti nella lista
         */
        public boolean hasPrevious() {
            return cursor != 0;
        }

        /**
         * Verifica se ci sono elementi successivi nella lista.
         *
         * @return true se ci sono elementi successivi nella lista
         * @implNote also != size() should work since cursor +2 is never made, but < is better
         */
        public boolean hasNext() {
            return cursor < size();
        }

        /**
         * Restituisce l'elemento precedente nella lista.
         *
         * @return l'elemento precedente nella lista
         * @throws ArrayIndexOutOfBoundsException se non ci sono elementi precedenti
         */
        public E previous() throws ArrayIndexOutOfBoundsException {
            int i = cursor - 1;
            if (i < 0) {
                throw new RuntimeException("No such element");
            }
            cursor = i;
            return (E) vector.elementAt(lastRet = i);   // necessary
        }

        /**
         * Restituisce l'elemento successivo nella lista.
         *
         * @return l'elemento successivo nella lista
         * @throws ArrayIndexOutOfBoundsException se non ci sono elementi precedenti
         */
        public E next() throws ArrayIndexOutOfBoundsException {
            int i = cursor+ 1;
            if (i > size()) {
                throw new RuntimeException("No such element");
            }
            cursor = i;
            return (E) vector.elementAt(lastRet = i);   // necessary
        }

        /**
         * Restituisce l'indice del prossimo elemento nella lista.
         *
         * @return l'indice del prossimo elemento nella lista
         */
        public int nextIndex() {
            return cursor;
        }

        /**
         * Restituisce l'indice dell'elemento precedente nella lista.
         *
         * @return l'indice dell'elemento precedente nella lista
         */
        public int previousIndex() {
            return cursor - 1;
        }

        /**
         * Sostituisce l'ultimo elemento restituito dall'iteratore con un nuovo elemento.
         *
         * @param o il nuovo elemento
         * @throws ArrayIndexOutOfBoundsException se l'indice è fuori dall'intervallo valido
         */
        @SuppressWarnings("unchecked")
        public void set(Object o) throws ArrayIndexOutOfBoundsException {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            vector.setElementAt((E) o, lastRet);
        }

        /**
         * Aggiunge un elemento alla lista nella posizione corrente dell'iteratore.
         *
         * @param o l'elemento da aggiungere
         * @throws ArrayIndexOutOfBoundsException se l'indice è fuori dall'intervallo valido
         */
        @SuppressWarnings("unchecked")
        public void add(Object o) throws ArrayIndexOutOfBoundsException {
            int i = cursor;
            vector.insertElementAt((E) o, i);
            cursor = i + 1;
            lastRet = -1;
        }
    }

    /**
     * Implementazione della sottoclasse SubList che permette di modificare la lista principale
     * attraverso le operazioni sulla sublista.
     */
    protected class SubList extends ListAdapter<E> {
        private ListAdapter<E> parent;
        private int indexFirst;
        private int size;

        /**
         * Costruttore della classe SubList.
         *
         * @param parent la lista principale da cui è stata creata la sublist
         * @param fromIndex l'indice iniziale della sublist (inclusivo)
         * @param toIndex l'indice finale della sublist (esclusivo)
         */
        SubList(ListAdapter<E> parent, int fromIndex, int toIndex) {
            this.parent = parent;
            this.indexFirst = fromIndex;
            this.size = toIndex-fromIndex;
            this.vector = new Vector<>(parent.vector.subList(fromIndex, toIndex));
        }

        /**
         * Restituisce la dimensione logica della sublist
         */
        @Override
        public int size() {
            return size;
        }

        /**
         * Aggiunge un elemento alla sublist
         *
         * @param o l'elemento da aggiungere
         */
        @Override
        public boolean add(Object o) {
            super.add(indexFirst, o);
            parent.add(indexFirst, o);
            size++;
            return true;
        }
        
        /**
         * Aggiunge un elemento alla sublist in una posizione specificata.
         *
         * @param index la posizione in cui aggiungere l'elemento
         * @param o l'elemento da aggiungere
         */
        @Override
        public void add(int index, Object o) {
            super.add(index, o);
            parent.add(indexFirst + index, o);
            size++;
        }

        /**
         * Aggiunge tutti gli elementi di una collezione alla sublist
         *
         * @param c la collezione contenente gli elementi da aggiungere
         * @return true se la sublist è stata modificata
         */
        @Override
        public boolean addAll(Collection<E> c) {
            boolean modified = super.addAll(c);
            if (modified) {
                parent.addAll(indexFirst, c);       
                size += c.size();
            }
            return modified;
        }

        /**
         * Aggiunge tutti gli elementi di una collezione alla sublist a partire da una posizione specificata.
         *
         * @param index la posizione da cui iniziare ad aggiungere gli elementi
         * @param c la collezione contenente gli elementi da aggiungere
         * @return true se la sublist è stata modificata
         */
        @Override
        public boolean addAll(int index, Collection<E> c) {
            boolean modified = super.addAll(index, c);
            if (modified) {
                parent.addAll(indexFirst + index, c);
                size += c.size();
            }
            return modified;
        }

        /**
         * Restituisce l'elemento in una posizione specificata della sublist.
         *
         * @param index la posizione dell'elemento da restituire
         * @return l'elemento nella posizione specificata
         * @throws ArrayIndexOutOfBoundsException se l'indice è fuori dall'intervallo valido
         */
        @Override
        public E get(int index) throws ArrayIndexOutOfBoundsException {
            return super.get(index);
        }

        /**
         * Restituisce l'indice della prima occorrenza dell'elemento specificato nella sublist.
         *
         * @param o l'elemento da cercare
         * @return l'indice della prima occorrenza dell'elemento, o -1 se non è presente
         */
        @Override
        public int indexOf(Object o) {
            int index = super.indexOf(o);
            return (index == -1) ? -1 : indexFirst + index;
        }

        /**
         * Restituisce l'indice dell'ultima occorrenza dell'elemento specificato nella sublist.
         *
         * @param o l'elemento da cercare
         * @return l'indice dell'ultima occorrenza dell'elemento, o -1 se non è presente
         * @throws IndexOutOfBoundsException se l'indice è fuori dall'intervallo valido
         */
        @Override
        public int lastIndexOf(Object o) throws IndexOutOfBoundsException {
            int index = super.lastIndexOf(o);
            return (index == -1) ? -1 : indexFirst + index;
        }

        /**
         * Rimuove l'elemento in una posizione specificata della sublist.
         *
         * @param index la posizione dell'elemento da rimuovere
         * @return l'elemento rimosso
         * @throws ArrayIndexOutOfBoundsException se l'indice è fuori dall'intervallo valido
         */
        @Override
        public Object remove(int index) throws ArrayIndexOutOfBoundsException {
            Object element = super.remove(index);
            parent.remove(indexFirst + index);
            size--;
            return element;
        }

        /**
         * Rimuove l'elemento se presente nella SubList
         *
         * @return true se la rimozione è effettuabile altrimenti false
         */
        @Override
        public boolean remove(Object o) {
            int index = indexOf(o);
            if (index == -1) {
                return false;
            }
            remove(index);
            return true;
        }

        /**
        * Rimuove tutti gli elementi della sublist presenti in una collezione specificata.
        *
        * @param c la collezione contenente gli elementi da rimuovere
        * @return true se la sublist è stata modificata, altrimenti false
         */
        @Override
        public boolean removeAll(Collection<E> c) {
            boolean modified = false;
            for (int i = 0; i < size(); i++) {
                if (c.contains(vector.elementAt(i))) {
                    remove(i);
                    i--; // Adjust index after removal
                    modified = true;
                }
            }
            return modified;
        }

        /**
        * Mantiene solo gli elementi della sublist presenti in una collezione specificata.
        * Rimuove tutti gli elementi della sublist che non sono presenti nella collezione specificata.
        *
        * @param c la collezione contenente gli elementi da mantenere
        * @return true se la sublist è stata modificata, altrimenti false
        */
        @Override
        public boolean retainAll(Collection<E> c) {
            boolean modified = false;

            for (int i = 0; i < size(); i++) {
                if (!c.contains(get(i))) {
                    remove(i);
                    i--; // Adjust index after removal
                    modified = true;
                }
            }

            if (this instanceof SubList) {
                SubList subList = (SubList) this;
                subList.size = this.size() - subList.indexFirst;
            }
            return modified;
        }

        /**
        * Verifica se la sublist contiene un elemento specificato.
        *
        * @param o l'elemento da verificare
        * @return true se la sublist contiene l'elemento specificato, altrimenti false
        */
        @Override
        public boolean contains(Object o) {
            for (int i = 0; i < size(); i++) {
                if (vector.elementAt(i) != null && vector.elementAt(i).equals(o)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Sostituisce l'elemento in una posizione specificata della sublist con un nuovo elemento.
         *
         * @param index la posizione dell'elemento da sostituire
         * @param element il nuovo elemento
         * @return l'elemento precedente nella posizione specificata
         * @throws ArrayIndexOutOfBoundsException se l'indice è fuori dall'intervallo valido
         */
        @SuppressWarnings("unchecked")
        @Override
        public E set(int index, Object element) throws ArrayIndexOutOfBoundsException {
            Object oldElement = super.set(index, element);
            parent.set(indexFirst + index, element);
            return (E) oldElement;
        }

        /**
         * Restituisce una sublist della sublist tra due indici specificati.
         *
         * @param fromIndex l'indice iniziale (inclusivo)
         * @param toIndex l'indice finale (esclusivo)
         * @return una sublist tra i due indici specificati
         * @throws ArrayIndexOutOfBoundsException se un indice è fuori dall'intervallo valido
         */
        @Override
        public List<E> subList(int fromIndex, int toIndex) throws ArrayIndexOutOfBoundsException {
            return new SubList(parent, indexFirst + fromIndex, indexFirst + toIndex);
        }
    }
}
