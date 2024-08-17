package maps;

/**
 * Interfaccia pubblica che rappresenta una generica collezione, può ospitare multiple istanze di uguale valore.
 * Fornisce metodi di ispezione, modifica e iterazione della collezione che rappresenta.
 */
public interface Collection {
    /**
     * Metodo di aggiunta di un oggetto alla collezione this. L'oggetto per upcasting può essere
     * un qualsiasi oggetto derivato dalla classe Object. 
     */
    boolean add(Object o);

    /**
     * Metodo di aggiunta alla collezione this di tutti gli oggetti appartenenti alla collezione specificata. 
     */
    boolean addAll(Collection c);

    /**
     * Metodo di cancellazione di tutti gli elementi della collezione. L'operazione causa il rilascio di tutti gli oggetti
     * contenuti nella collezione con conseguente azzeramento della dimensione della collezione stessa. 
     */
    void clear();

    /**
     * Metodo di ispezione della collezione che permette di verificare la presenza di un certo oggetto all'interno della collezione.
     * Restituisce true se l'elemento ispezionato viene trovato all'interno della collezione this, false altrimenti.
     */
    boolean contains(Object o);

    /**
     * Metodo di ispezione della collezione che permette di verificare la presenza di tutti gli elementi della collezione specificata
     * all'interno della collezione this. Il metodo restituisce true se e solo se tutti gli elementi della collezione specificata
     * si trovano all'interno della collezione, false altrimenti.
     */
    boolean containsAll(Collection c);

    /**
     * Metodo per effettuare un confronto di uguaglianza tra questa collezione e l'oggetto specificato.
     * Restituisce true se gli elementi confrontati si equivalgono, false altrimenti.
     */
    boolean equals(Object o);

    /**
     * Metodo che restituisce un valore pari al codice di hash corrispondente alla collezione this.
     */
    int hashCode();

    // Return true if this collection contains no elements
    /**
     * Metodo di ispezione che permette di verificare lo stato di vuotezza della collezione.
     * Viene restituito true se e solo se la collezione e vuota e di conseguenza la sua dimensione è pari a zero.
     */
    boolean isEmpty();

    /**
     * Metodo che fornisce un iteratore per iterare gli elementi della collezione this.
     */
    Iterator iterator();

    /**
     * Metodo che rimuove una singola istanza dell'elemento specificato dalla collezione, se presente.
     * Restituisce true se la rimozione viene effettuata, false altrimenti.
     */
    boolean remove(Object o);

    /**
     * Metodo di rimozione di tutti gli elementi presenti nella collezione specificata dalla collezione this.
     * In caso di impossibilità a compiere l'operazione viene restituto false, true altrimenti.
     */
    boolean removeAll(Collection c);

    /**
     * Metodo di trattenimento dei soli oggetti della collezione this appartenenti alla collezione specificata
     */
    boolean retainAll(Collection c);

    /**
     * Metodo di ispezione della collezione finalizzato a conoscere la dimentione della stessa basandosi sul numero
     * di elementi contenuti nella collezione.
     */
    int size();

    /**
     * Metodo di restituzione di un array contenente tutti gli elementi presenti in questa collezione
     */
    Object[] toArray();

    /**
     * Metodo che restituisce un array contenete tutti gli elementi presenti in questa collezione.
     * Gli elementi se possono essere ospitati nell'array passato per argomenti vengono restituiti proprio
     * in questo array, altrimenti viene creato un nuovo array.
     */
    Object[] toArray(Object[] a);
}