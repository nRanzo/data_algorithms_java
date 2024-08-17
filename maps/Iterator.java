package maps;

/**
 * Interfaccia pubblica che contiene i metodi che devono essere implementati per un generico iteratore.
 * Impone una logica di ispezione, iterazione e rimozione dei valori iterabili.
 */
public interface Iterator {
    /**
     * Metodo di ispezione dell'iteratore per permettere a chi utilizza l'iteratore di verificare l'effettiva
     * presenza di ulteriori elementi che si possono ancora iterare. La sua utilità è rilevante nei cicli while
     * e ovunque sia necessario iterare degli elemnti senza ricevere errori.
     */
    boolean hasNext();

    /**
     * Metodo di iterazione dell'iteratore per spostarsi da un elemento al successivo. Si assume che l'utilizzatore
     * prima di utilizzarlo controlli di poterlo fare, ma in caso contrario un'eccezione deve essere lanciata.
     */
    Object next();

    /**
     * Metodo di rimozione di un elemento tramite l'iteratore. Non è richiesto che l'elemento rimosso venga restituito,
     * perchè in caso di necessità può essere salvato prima di essere rimosso. Non si può rimuovere un elemento non iterato,
     * dunque in caso di mancata precedente chiamata a next() un'eccezione deve essere lanciata.
     */
    void remove();
}
