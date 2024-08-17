package maps;

/**
 * Interfaccia pubblica che estende HCollection e rappresenta un generico set. Assumo che il set non debba contenere
 * multiple istanze dello stesso valore. Dunque una possibile scelta implementativa può essere quella di controllare
 * e garantisce l'unicità degli elementi di cui il set è composto.
 * Fornisce metodi di ispezione, modifica e iterazione del set che rappresenta.
 */
public interface Set extends Collection {
    /**
     * Metodo di aggiunta di un istanza della classe Object al set. Per upcasting questo metodo può essere
     * chiamato con qualsiasi oggetto derivato da Object come per esempio String.
     */
    boolean add(Object o);

    /**
     * Metodo di aggiunta al set di tutti gli elementi contenuti in una specifica collezione.
     * Un elemento della collezione già presente nel set non deve essere aggiunto.
     */
    boolean addAll(Collection c);

    /**
     * Metodo di cancellazione di tutti gli elementi del set. Dopo la sua esecuzione il set non deve contenere
     * alcun elemento e di conseguenza la sua dimensione deve essere pari a zero.
     */
    void clear();

    /**
     * Metodo per verificare la presenza di un oggetto all'interno del set. L'oggetto può essere di qualsiasi 
     * data type perchè per upcasting un qualsiasi derivato di Object viene trattato come istanza della classe
     * Object. Restituisce true se presente nel set, altrimenti false.
     */
    boolean contains(Object o);

    /**
     * Metodo per verificare la presenza nel set di una collezione di oggetti. Ogni oggetto della collezione può 
     * essere di qualsiasi data type perchè per upcasting un qualsiasi derivato di Object viene trattato come istanza 
     * della classe Object. Restituisce true se il set contiene tutti gli elementi presenti nella collezione specificata, 
     * altrimenti false.
     */
    boolean containsAll(Collection c);

    /**
     * Metodo per confrontare il set this con l'oggetto specificato per valutare se sono uguali oppure no.
     * Restituisce true se sono uguali, false altrimenti.
     */
    boolean equals(Object o);
    
    /**
     * Metodo per ricevere l'hashCode corrispondente al set. Il valore restituito deve essere un intero.
     */
    int hashCode();

    /**
     * Metodo di ispezione del set finalizzato a conoscere lo stato di vuotezza dello stesso. Se il set this è 
     * vuoto viene restituito true, false altrimenti.
     */
    boolean isEmpty();

    /**
     * Metodo che restituisce un iteratore del set per permettere la navigazione dello stesso.
     */
    Iterator iterator();

    /**
     * Metodo che rimuove un elemento specificato dal set se in esso presente. Restituisce true in caso
     * di rimozione possibile e quindi effettuata, false se invece la rimozione non sia possibile per via
     * dell'assenza dell'elemento da rimuvere all'interno del set this.
     */
    boolean remove(Object o);

    /**
     * Metodo che rimuove dal set this tutti gli elementi contenuti nel set indicato. Restituisce true in caso
     * di rimozione possibile e quindi effettuata, false se invece la rimozione non sia possibile per via
     * dell'assenza di elementi da rimuvere all'interno del set this.
     */
    boolean removeAll(Collection c);

    /**
     * Metodo di trattenimento dal set dei soli elementi presenti nella collezione specificata.
     */
    boolean retainAll(Collection c);

    /**
     * Metodo che restituisce la cardinalità del set. Il valore che viene restituito deve essere pari al numero
     * di elementi presenti all'interno del set.
     */
    int size();

    /**
     * Metodo che restituisce un array contente tutti gli elementi presenti nel set this.
     */
    Object[] toArray();
 
    /**
     * Metodo che restituisce un array contenete tutti gli elementi presenti in questo set.
     * Gli elementi se possono essere ospitati nell'array passato per argomenti vengono restituiti proprio
     * in questo array, altrimenti viene creato un nuovo array.
     */
    Object[] toArray(Object[] a);
}