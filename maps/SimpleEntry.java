package maps;

import java.util.Objects;

/**
 * Classe pubblica che implementa l'interfaccia Entry. Rappresenta una generica coppia chiave-valore e fornisce
 * metodi di ispezione, modifica e ottenimento di informazioni come codice hash.<p>
 * Il motivo per cui ho reso pubblica questa classe è perchè non ha un legame stretto con MapAdapter: istanze di questa
 * classe infatti possono essere create anche singolarmente, come ho fatto in alcuni test. È dunque accessibile a qualsiasi
 * package perchè non legata all'adaptee.
 */
public class SimpleEntry implements Map.Entry {
    /**
     * Attributo privato della classe SimpleEntry che rappresenta la chiave dell'entry. Ho reso questo attributo
     * privato in modo tale che il suo valore possa essere scelto solamente durante la creazione di una entry.<p>
     * Per questo motivo non è presente un metodo setKey.<p>
     */
    private final Object key;
    /**
     * Attributo pubblico della classe SimpleEntry che rappresenta il valore dell'entry. L'attributo è private ma non final
     * per permettere a chi ha accesso ad un'istanza di questa classe di modificarne il contenuto attraverso un metodo
     * setValue() visibile all'esterno della classe.
     */
    private Object value;

    /**
     * Costruttore pubblico per inizializzare un'istanza di questa classe a una chiave e valore arbitrari.
     * Questo è l'unico modo per assegnare un valore alla chiave.<p>
     * Una limitazione della mappa potrebbe essere quella di non permettere di creare una entry la cui chiave
     * sia presente in un altra entries, così da garantire un'associazione univoca chiave -> entry corrispondente.<p>
     * Ho scelto di non implementare questo controllo, ma sono consapevole che prestare attenzione su questo aspetto
     * è una valida scelta implementativa. Nei test implementati non ci sono entries diverse con stessa chaive.
     */
    public SimpleEntry(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Metodo per ispezionare il valore dell'attributo key rappresentate la chiave di un'entry, che può essere solo
     * ispezionato.<p>
     */
    @Override
    public Object getKey() {
        return key;
    }

    /**
     * Metodo per ispezionare il valore dell'attributo value rappresentate il valore di un'entry.<p>
     */
    @Override
    public Object getValue() {
        return value;
    }

    /**
     * Metodo per assegnare un valore all'attributo pubblico value. Non vi è alcun limite sulla possibilità di 
     * modificare il valore dell'attributo value di una entry. Il vecchio valore viene restituito.
     */
    @Override
    public Object setValue(Object value) {
        Object oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    /**
     * Metodo per confrontare due istanze della classe, che ritengo uguali nella casistica in cui queste siano
     * references alla stessa istanza della classe, oppure nel caso in cui chiave e valore risultino uguali.<p>
     * Un'altra possibile scelta implementativa è ritenere due entries uguali se hanno stesso valore, ma la chaive
     * risulta diversa, quindi in presenza di tale caso questo metodo restituisce false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Map.Entry)) return false;
        Map.Entry entry = (Map.Entry) o;
        return Objects.equals(key, entry.getKey()) &&
                Objects.equals(value, entry.getValue());
    }

    /**
     * Metodo che restituisce il codice di hash associato a una coppia chiave-valore.
     */
    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    /**
     * Metodo di restituzione della coppia chiave valore. Non verrà testato perchè lo fornisco solo
     * per ragioni di completezza.
     */
    @Override
    public String toString() {
        return "[key: " + key + ", value: " + value + "]";
    }
}
