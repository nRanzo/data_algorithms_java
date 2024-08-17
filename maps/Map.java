package maps;

/**
 * Interfaccia pubblica rappresentante una mappa che deve essere dotata di specifici metodi di ispezione,
 * modifica e cancellazione di parte del o della totalità del suo contenuto. Fornisce un'interfaccia interna
 * rappresentante un'entry dotata anch'essa di metodi di modifica e ispezione del contenuto.
 */
public interface Map {

    /**
     * Interfaccia interna che rappresenta un'entry che deve possedere metodi di modifica del valore della coppia
     * chiave-valore e di ispezione di chiave o valore. Deve essere possibile confrontare istanze di Entry e ottenere
     * da un'istanza il corrispondente codice di hash.
     */
    interface Entry {
        /**
         * Metodo per ispezionare l'attributo rappresentante la chaive di una entry. 
         */
        Object getKey();
        
        /**
         * Metodo per ispezionare l'attributo rappresentatne un valore di una entry. 
         */
        Object getValue();
        
        /**
         * Metodo per modificare l'attributo rappresentatne un valore di una entry.
         */
        Object setValue(Object value);
        
        /**
         * Metodo per confrontare l'oggetto specificato con l'entry this.
         */
        boolean equals(Object o);
        
        /**
         * Metodo per restituire il codice di hash associato ad un'entry.
         */
        int hashCode();
    }

    /**
     * Metodo per permettere la cancellazione di una mappa, dopo la sua esecuzione la mappa su cui agisce
     * non deve più contenere alcun elemento e per questa ragione deve avere dimensione pari a zero.
     */
    void clear();

    /**
     * Metodo di ispezione della mappa che permette di conoscere la presenza della chaive specificata all'interno
     * della mappa. Restituisce true se la chiave indicata è presente nella mappa, false altrimenti.
     */
    boolean containsKey(Object key);

    // Return true if this map maps one or more keys to the specified value
    /**
     * Metodo di ispezione della mappa che permette di conoscere se le entries contenute nella mappa contengono il 
     * valore specificato. Restituisce true se almeno una mappa contiene il valore indicato, altrimenti false.
     */
    boolean containsValue(Object value);

    /**
     * Restituisce un set di entries associato alla mappa. La vista restituita è l'istanza di un oggetto di tipo set
     * che deve essere inizializzato al momento dell'esecuzione del metodo.
     */
    Set entrySet();

    /**
     * Metodo per confrontare l'oggetto passato specificato con l'entry this. Il confronto di uguaglianza restituisce
     * true se gli la mappa corrisponde in dimensione e contenuto all'oggetto specificato, altrimenti false.
     */
    boolean equals(Object o);

    /**
     * Metodo di ispezione della mappa che restituisce il valore associato alla chiave passata per argomento
     */
    Object get(Object key);

    /**
     * Metodo di ispezione della mappa che ne restituisce il codice di hash corrispondente.
     */
    int hashCode();

    /**
     * Metodo di ispezione della mappa che restituisce true se la mappa con contiene alcuna entry e di conseguenza
     * la sua dimensione risulta vuota. In tale casistica il valore restituito è true, altrimenti false se vi è almeno
     * un elemento e la dimensione della mappa non è dunque nulla.
     */
    boolean isEmpty();

    // Return a set view of the keys contained in this map
    /**
     * Metodo che restituisce un set delle chiavi contenute nella mappa. La vista restituita deve essere istanziata
     * all'interno di questo metodo al momento della sua esecuzione.
     */
    Set keySet();

    // Associate the specified value with the specified key in this map
    /**
     * Metodo che permette di inserire nella mappa una nuova coppia chiave-valore, per questo si deve occupare di 
     * istanziare una nuova istanza della classe Entry che deve implementare l'interfaccia contenuta in HMap.
     */
    Object put(Object key, Object value);

    // Copy all of the mappings from the specified map to this map
    /**
     * Metodo che inserisce nella mappa tutti gli elementi presenti nell'altra mappa passata per argomento.
     * Non è richiesto nè previsto che questo metodo restituisca un valore.
     */
    void putAll(Map t);

    /**
     * Metodo di rimozione di una entry che viene selezionata specificando la sua chiave. La entry da rimuovere
     * deve essere rimossa dalla mappa solo se effettivamente presente in essa.
     */
    Object remove(Object key);

    /**
     * Metodo di ispezione della mappa che ne restituisce il valore della dimensione, valore che deve essere pari al 
     * numero di entries contenute nella mappa.
     */
    int size();

    /**
     * Metodo che restiuisce una collezione di valori contenuti nella mappa. Un istanza della vista richiesta deve
     * essere inizializzata al momento dell'esecuzione di questo metodo.
     */
    Collection values();
}