package maps;

import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Enumeration;

/**
 * Implementazione principale, rappresenta l'adaptee che permette di istanziare mappe e accedervi
 * attraverso un'ampia varietà di metodi di ispezione, modifica e ottenimento di viste associate ad una mappa.
 * In tutti i metodi di iterazione, o di rimozione tramite iteratore, che ho implementato non si verifica
 * il lancio di alcuna eccezione se non espressamente indicato dalla firma del metodo. Questo perchè ho effettuato
 * i dovuti controllo dovunque necessario prima di chiamare metodi che potenzialmente possono lanciare eccezioni. 
 * Tali metodi infatti vengono chiamati se sono nella situazione "safe" di impossibilità di ottenere errori.
 */
public class MapAdapter implements Map {
    /**
     * Attributo privato della classe MapAdapter. Per realizzare la mappa ho scelto di usare come supporto la classe
     * Hashtable, che usa chiavi e valori. Secondo la documentazione di Hastable sono supportate chiavi e valori nulli.<p>
     * L'attributo è privato per forzare l'utilizzatore di un'istanza di questa classe a modificarla solo tramite i metodi
     * che sono di seguito implementati, e dunque secondo le modalità previste in questa implementazione.
     */
    private Hashtable<Object, Object> adaptee;

    /**
     * Costruttore pubblico della classe MapAdapter che permette di istanziare nuove istanze di questa classe.
     * Al momento dell'inizializzazione è prevista la creazione di una nuova mappa vuota. Dunque l'utilizzatore
     * di un'istanza di MapAdapter non può creare una mappa con degli elementi. Per inserire qualsiasi elemento sono
     * infatti previsti due metodi di aggiunta di entries nella mappa: put e putAll.
     */
    public MapAdapter() {
        adaptee = new Hashtable<Object, Object>();
    }

    /**
     * Metodo che permette la cancellazione della mappa. Lo scopo è quello di rilasciare ogni singola entry memorizzata nella
     * mappa e azzerare così la dimensione della stessa. Dopo la sua esecuzione la dimensione della mappa è nulla.
     * Uso clear() previsto dalla classe Hashtable. Non restituisce alcun valore.
     */
    @Override
    public void clear() {
        adaptee.clear();
    }

    /**
     * Metodo di ispezione della mappa per conoscere la presenza di una specifica chiave passata per argomento.
     * @param key la chiave specificata di cui si vuole ispezionare la presenza
     * @return true o false, rispettivamente a seconda dell'effettiva presenza o assenza della chiave specificata
     * @throws NullPointerException se il valore passato per argomento è null
     */
    @Override
    public boolean containsKey(Object key) throws NullPointerException {
        return adaptee.containsKey(key);
    }

    /**
     * Metodo di ispezione della mappa per conoscere la presenza di un specifico valore passato per argomento.
     * @param value il valore specificato di cui si vuole ispezionare la presenza
     * @return true o false, rispettivamente a seconda dell'effettiva presenza o assenza del valore specificato
     * @throws NullPointerException se il valore passato per argomento è null
     */
    @Override
    public boolean containsValue(Object value) throws NullPointerException {
        return adaptee.contains(value);
    }

    /**
     * Metodo di restituzione di una vista contenente tutte le entrie della mappa. Al momento della sua esecuzione
     * viene istanziato un oggetto della classe EntrySet, interna a MapAdapter e privata. Tale istanza permetterà di 
     * ispezionare tutte le entries presenti nella mappa.
     * @return una nuova istanza della classe EntrySet
     */
    @Override
    public Set entrySet() {
        return new EntrySet();
    }

    /**
     * Metodo di confronto che permette di comparare questa istanza della mappa, dunque this, con l'oggetto passato
     * per argomento. Per scelta implementativa gestisco all'interno di questo metodo le eccezioni, così da permettere
     * all'utilizzatore di un'istanza della classe MapAdapter di non doversene preoccupare perchè in tali casi restituisco
     * false dopo aver catturato l'eccezione ClassCastException o NullPointerException.
     * Il confronto genera true nelle seguenti casistiche:
     * <p><ul>
     *   <li>l'oggetto passato per argomento corrisponde a this,</li>
     *   <li>o è un istanza di HMap, ha stessa dimensione e contenuto della mappa this.</li>
     * </ul><p>
     * In tutti gli altri casi ritengo gli oggetti diversi.
     * @param o l'oggetto da confrontare con l'istanza this della mappa
     * @return true nelle casistiche sopra appuntate, false altrimenti
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Map))
            return false;
        Map m = (Map) o;
        if (m.size() != size())
            return false;
        try {
            Iterator i = entrySet().iterator();
            while (i.hasNext()) {
                Entry e = (Entry) i.next();
                Object key = e.getKey();
                Object value = e.getValue();
                if (value == null) {
                    if (!(m.get(key) == null && m.containsKey(key)))
                        return false;
                } else {
                    if (!value.equals(m.get(key)))
                        return false;
                }
            }
        } catch (ClassCastException unused) {
            return false;
        } catch (NullPointerException unused) {
            return false;
        }
        return true;
    }

    /**
     * Metodo di ispezione della mappa che permette di ottenere il valore associato alla chiave specificata.
     * @param key la chiave specificata, di cui si vuole conoscere il valore associato
     * @return il valore associato alla chiave specificata
     * @throws NullPointerException se il valore passato per argomento è null
     */
    @Override
    public Object get(Object key) throws NullPointerException {
        return adaptee.get(key);
    }

    /**
     * Metodo di ispezione della mappa che permette di ottenere il valore intero di codice di hash 
     * associato a tutte le entries. Il valore calcolato corrisponde alla somma dei codici di hash di 
     * tutte le entries e utilizzo un iteratore per assicurarmi di considerare tutte le entries della mappa.
     * @return il codice di hash corrispondente alle entries della mappa
     */
    @Override
    public int hashCode() {
        int h = 0;
        Iterator i = entrySet().iterator();
        while (i.hasNext()) {
            Entry e = (Entry) i.next();
            h += e.hashCode();
        }
        return h;
    }

    /**
     * Metodo di ispezione della mappa che permette di conoscere il suo stato di vuotezza valutando se la mappa
     * this presenta o meno delle entries al suo interno. In tale casistica viene restituito true in riferimento
     * all'effettivo stato di vuotezza della mappa, altrimenti viene restituito false. Con stato di vuotezza intendo
     * dimensione della mappa pari a zero, indicante l'assenza di qualsiasi entry al suo interno.
     * @return true o false, rispettivamente a seconda dell'effettivo stato di vuotezza o della presenza di entries nella mappa
     */
    @Override
    public boolean isEmpty() {
        return adaptee.isEmpty();
    }

    /**
     * Metodo di restituzione di una vista contenente tutte le chiavi della mappa. Al momento della sua esecuzione
     * viene istanziato un oggetto della classe KeySet, interna a MapAdapter e privata. Tale istanza permetterà di 
     * ispezionare tutte le chiavi presenti nella mappa.
     * @return una nuova istanza della classe KeySet
     */
    @Override
    public Set keySet() {
        return new KeySet();
    }

    /**
     * Metodo di inserimento nella mappa di una nuova coppia chiave-valore. Il metodo permette di aggiungere una sola
     * nuova coppia chiave valore. Utilizzo il metodo di put() di Hastable che non permette di aggiungere alcuna chiave
     * o valore nulli. Assumo che l'utilizzatore non voglia inserire una chiave o un valore nullo perchè ne individuo una 
     * possibile adoperazione. Per questo motivo sono presenti due test che verifichino il lancio dell'eccezione 
     * NullPointerException al tentativo di aggiunere una entry che abbia chiave o valore nulli.
     * @param key la chiave della nuova entry da aggiungere alla mappa
     * @param value il valore della nuova entry da aggiungere alla mappa
     * @throws NullPointerException al tentativo di aggiunere una entry che abbia chiave o valore nulli
     */
    @Override
    public Object put(Object key, Object value) throws NullPointerException{
        return adaptee.put(key, value);
    }

    /**
     * Metodo di inserimento nella mappa di tutte le coppie chiave-valore contenute nella mappa specificata per
     * argomento. Le entries aggiunte alla mappa this non sono reference ma nuove entries con pari chiave e valore. 
     * Utilizzo il metodo put() di Hastable che non permette di aggiungere alcuna chiave o valore nulli. Assumo che 
     * l'utilizzatore non voglia inserire una chiave o un valore nulli perchè ne individuo una possibile adoperazione. 
     * Per questo motivo è presente un test che verifichi il lancio dell'eccezione NullPointerException al tentativo di 
     * aggiungere un'entry non valida.<p>
     * @param t la mappa specificata le cui entries si vogliono aggiunere alla mappa
     * @throws NullPointerException al tentativo di aggiungere una coppia chiave-valore non valida e dunque non supportata
     */
    @Override
    public void putAll(Map t) throws NullPointerException{
        Iterator i = t.entrySet().iterator();
        while (i.hasNext()) {
            Entry e = (Entry) i.next();
            put(e.getKey(), e.getValue());
        }
    }

    /**
     * Metodo di modifica della mappa che permette di rimuovere una entry specificando una sua chiave. Non è possibile
     * rimuovere una chiave non nulla perchè la mappa non supporta alcuna entry di questo tipo. Non è inoltre possibile
     * rimuovere un'entry che non sia presente nella mappa this.
     * @param key la chiave associata all'entry che si vuole rimuovere
     * @return il valore mappato alla chiave specificata
     * @throws NullPointerException se il valore passato per argomento è null
     */
    @Override
    public Object remove(Object key) throws NullPointerException {
        return adaptee.remove(key);
    }

    /**
     * Metodo di ispezione della mappa che permette di conoscerne la dimensione. Il valore che viene restituito è la dimensione
     * della mappa che è esattamente uguale al numero di entries presenti in essa e coincide dunque alla cardinalità della
     * mappa this.
     * @return la dimensione della mappa
     */
    @Override
    public int size() {
        return adaptee.size();
    }

    /**
     * Metodo di restituzione di una vista contenente tutti i valori ospitati nella mappa. Al momento della sua esecuzione
     * viene istanziato un oggetto della classe ValuesCollection, interna a MapAdapter e privata. Tale istanza permetterà di 
     * ispezionare tutti i valori contenuti nelle entries della mappa.
     * @return una nuova istanza della classe ValuesCollection
     */
    @Override
    public Collection values() {
        return new ValuesCollection();
    }

    /**
     * Classe interna che rappresenta la vista set di entries. Fornisce una logica di consultazione delle entries
     * di cui è composto tramite un iteratore che viene restituito dal suo metodo iterator(). Implementa l'interfaccia
     * HSet e fornisce metodi per manipolare e interogare l'insieme delle entries. Da questa vista è possibile rimuovere
     * delle entries della mappa, ma non aggiungerne. Il motivo di questa scelta implementativa è per evitare che chi 
     * voglia ispezionare una mappa abbia la possibilità di aggiungerne del contenuto che potrebbe essere non previsto.
     */
    private class EntrySet implements Set {
        /**
         * Metodo di aggiunta di un'entry alla vista di entries. L'operazione non è supportata per evitare che chi 
         * voglia ispezionare una mappa abbia la possibilità di aggiungerne del contenuto che potrebbe essere non previsto.
         * Per questo motivo al tentativo di aggiunta di una entry viene lanciata eccezione, indipendentemente dall'oggetto
         * passato per argomento.
         * @throws UnsupportedOperationException con messaggio "operazione add non supportata"
         */
        @Override
        public boolean add(Object entry) throws UnsupportedOperationException {
            throw new UnsupportedOperationException("operazione add non supportata");
        }

        /**
         * Metodo di aggiunta di una collezione di entries alla vista di entries. L'operazione non è supportata per evitare 
         * che chi voglia ispezionare una mappa abbia la possibilità di aggiungerne del contenuto che potrebbe essere non previsto.
         * Per questo motivo al tentativo di aggiunta di qualsiasi entry viene lanciata eccezione, indipendentemente dall'oggetto
         * passato per argomento.
         * @throws UnsupportedOperationException con messaggio "operazione add non supportata"
         */
        @Override
        public boolean addAll(Collection c) throws UnsupportedOperationException {
            throw new UnsupportedOperationException("operazione addAll non supportata");
        }

        /**
         * Metodo che permette la cancellazione delle entries della mappa con conseguente cancellazione del contenuto della mappa
         * stessa. Lo scopo è quello di rilasciare ogni singola entry memorizzata nella mappa e azzerare così la dimensione 
         * della stessa. Dopo la sua esecuzione la dimensione della mappa è nulla. Uso clear() di MapAdapter. Non restituisce alcun valore.
         */
        @Override
        public void clear() {
            MapAdapter.this.clear();
        }

        /**
         * Metodo di ispezione della vista delle entries per conoscere la presenza di una specifica entry passata per argomento.
         * @param o l'entry specificata di cui si vuole ispezionare la presenza
         * @return true o false, rispettivamente a seconda dell'effettiva presenza o assenza dell'entry specificata
         * @throws NullPointerException se il valore passato per argomento è null
         */
        @Override
        public boolean contains(Object o) throws NullPointerException {
            if (!(o instanceof Entry))
                return false;
            Entry e = (Entry) o;
            Object value = MapAdapter.this.get(e.getKey());
            return value != null && value.equals(e.getValue());
        }

        /**
         * Metodo di ispezione della vista delle entries per conoscere la presenza di tutte le entry appartenenti
         * ad una specifica collezione passata per argomento.
         * @param c la collezione di entries specificata di cui si vuole ispezionare la presenza
         * @return true o false, rispettivamente a seconda dell'effettiva presenza o assenza di tutte le entries specificate
         * @throws NullPointerException se il valore passato per argomento è null
         */
        @Override
        public boolean containsAll(Collection c) throws NullPointerException {
            Iterator i = c.iterator();
            while (i.hasNext())
                if (!contains(i.next()))
                    return false;
            return true;
        }

        /**
         * Metodo di confronto che permette di comparare questa istanza di EntrySet, dunque this, con l'oggetto passato
         * per argomento.
         * Il confronto genera true nelle seguenti casistiche:
         * <p><ul>
         *   <li>l'oggetto passato per argomento corrisponde a this,</li>
         *   <li>o è un istanza di EntrySet, ha stessa dimensione e contenuto dell set di entries this.</li>
         * </ul><p>
         * In tutti gli altri casi ritengo gli oggetti confrontati diversi, in accordo con
         * <a href="https://www2.cs.duke.edu/csed/java/jdk1.4.2/docs/api/index.html"> questa documentazione</a>.
         * @param o l'oggetto da confrontare con l'istanza this di EntrySet
         * @return true nelle casistiche sopra appuntate, false altrimenti
         * @throws NullPointerException se il valore passato per argomento è null
         */
        @Override
        public boolean equals(Object o) throws NullPointerException {
            if (this == o) return true;
            if (!(o instanceof EntrySet))
                return false;
            EntrySet set = (EntrySet) o;

            if (this.size() != set.size())
                return false;
        
            Iterator iterator = iterator();
            while (iterator.hasNext()) {
                Object e = iterator.next();
                SimpleEntry entry = (SimpleEntry) e;
            
                if (!set.contains(entry)) {
                    return false;
                }
            }
            return true;
        }

        /**
         * Metodo di ispezione del set di entries che permette di ottenere il valore intero di codice di hash 
         * associato a tutte le entries. Il valore calcolato corrisponde alla somma dei codici di hash di 
         * tutte le entries e utilizzo un iteratore per assicurarmi di considerare tutte le entries dell'entry set.
         * @return il codice di hash corrispondente alle entries della mappa
         */
        @Override
        public int hashCode() {
            int h = 0;
            Iterator iterator = iterator();
            while (iterator.hasNext()) {
                Object entry = iterator.next();
                h += entry.hashCode();
            }
            return h;
        }

        /**
         * Metodo di ispezione dell'entry set che permette di conoscere il suo stato di vuotezza valutando se l'istanza di EntrySet
         * this presenta o meno delle entries al suo interno. In tale casistica viene restituito true in riferimento
         * all'effettivo stato di vuotezza del set di entries, altrimenti viene restituito false. Con stato di vuotezza intendo
         * dimensione dell'entry set pari a zero, indicante l'assenza di qualsiasi entry al suo interno.
         * @return true o false, rispettivamente a seconda dell'effettivo stato di vuotezza o della presenza di entries nel set
         */
        @Override
        public boolean isEmpty() {
            return MapAdapter.this.isEmpty();
        }

        /**
         * Metodo che restituisce un'iteratore istanziato dalla classe EntryIterator. La logica di iterazione è perfettamente coerente
         * con l'implementazione EntryIterator e permette di iterare le entries del set associato alla mappa.
         * @return una nuova istanza della classe EntryIterator
         */
        @Override
        public Iterator iterator() {
            return new EntryIterator();
        }

        /**
         * Metodo di modifica dell'entry set che permette di rimuovere una entry. Non è possibile rimuovere una 
         * entry non nulla perchè la mappa non supporta alcuna entry di questo tipo. Non è inoltre possibile
         * rimuovere un'entry che non sia presente nel set di entries.
         * @param o l'oggetto che si vuole rimuovere dal set di entries
         * @return true o false, rispettivamente a seconda della possibilità o meno di effettuare la rimozione richiesta
         * @throws NullPointerException se il valore passato per argomento è null
         */
        @Override
        public boolean remove(Object o) throws NullPointerException {
            if (!(o instanceof Entry))
                return false;

            Entry e = (Entry) o;
            Object key = e.getKey();
            Object value = e.getValue();
        
            if (MapAdapter.this.containsKey(key)) {
                Object storedValue = MapAdapter.this.get(key);
            
                if (value == null) {
                    if (storedValue == null) {
                        MapAdapter.this.remove(key);
                        return true;
                    }
                } else {
                    if (value.equals(storedValue)) {
                        MapAdapter.this.remove(key);
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Metodo di modifica dell'entry set che permette di rimuovere tutte le entries appartenenti alla collezione
         * specificata. Non è possibile rimuovere alcuna entry nulla perchè la mappa non supporta alcuna entry con 
         * chiave nulla. Non è inoltre possibile rimuovere una qualsiasi entry che non sia presente nel set di entries
         * associato a un'istanza della mappa.
         * @param c la collezione di oggetto che si vuole rimuovere dal set di entries
         * @return true o false, rispettivamente a seconda della possibilità o meno di effettuare la rimozione richiesta
         */
        @Override
        public boolean removeAll(Collection c) {
            boolean modified = false;
            Iterator i = iterator();
            while (i.hasNext()) {
                if (c.contains(i.next())) {
                    i.remove();
                    modified = true;
                }
            }
            return modified;
        }

        /**
         * Metodo di modifica del set di entries per offrire la possibilità di trattenere in esso solo le entries 
         * che siano presenti sia nel set di entries che nella collezione specificata. Il test può fallire nei casi
         * in cui completare questa operazione non sia possibile. Casistiche possono essere per esempio:
         * <p><ul>
         *   <li> presenza di entries nulle nella collezione specificata</li>
         *   <li> presenza di alcune entries nella collezione specificata che non sono presenti nel set di entries</li>
         * </ul><p>
         * @return true o false a seconda della possibilità oppure no di trattenere tutte le entries specificate
         */
        @Override
        public boolean retainAll(Collection c) {
            boolean modified = false;
            Iterator i = iterator();
            while (i.hasNext()) {
                if (!c.contains(i.next())) {
                    i.remove();
                    modified = true;
                }
            }
            return modified;
        }

        /**
         * Metodo di ispezione dell'entry set che permette di conoscerne la dimensione. Il valore che viene restituito è la dimensione
         * dell'entry set che è esattamente uguale al numero di entries presenti in esso e coincide dunque alla cardinalità della
         * mappa this.
         * @return la dimensione dell'entry set pari alla dimensione della mappa
         */
        @Override
        public int size() {
            return MapAdapter.this.size();
        }

        /**
         * Metodo per restituire un array ospitante tutte le entries presenti nell'entry set.
         * La scelta di utilizzare una classe esterna con metodi di ispezione delle entries, ovvero SimpleEntry per
         * le entries della mappa, mi torna utile in questo caso. Riesco infatti a garantire in questo metodo una
         * deep copy e non una shallow copy. Questa garanzia mi permette di essere sicuro che chi riceve questo
         * array non può agire in alcun modo sul contenuto della mappa.
         * @return l'array richiesto contenente una copia profonda di tutte le entries
         * @throws NoSuchElementException se la chiamata a next() da parte dell'iteratore fallisce
         */
        @Override
        public Object[] toArray() throws NoSuchElementException {
            Object[] result = new Object[size()];
            Iterator i = iterator();
            for (int j = 0; j < result.length; j++) {
                SimpleEntry entry = (SimpleEntry) i.next();
                result[j] = new SimpleEntry(entry.getKey(), entry.getValue());
            }
            return result;
        }

        /**
         * Metodo per restituire un array ospitante tutte le entries presenti nell'entry set. Le copie delle entries
         * vengono inserite direttamente nell'array ricevuto per argomento, se abbastanza lungo da ospitare le
         * entries, altrimenti ne viene istanziato uno di lunghezza sufficiente.<p>
         * La scelta di utilizzare una classe esterna con metodi di ispezione delle entries, ovvero SimpleEntry per
         * le entries della mappa, mi torna utile in questo caso. Riesco infatti a garantire in questo metodo una
         * deep copy e non una shallow copy. Questa garanzia mi permette di essere sicuro che chi riceve questo
         * array non può agire in alcun modo sul contenuto della mappa.<p> 
         * Utilizzo java reflect come descritto su 
         * <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/package-summary.html#package.description"> disponibile dalla versione JDK1.1 del 19/2/1997</a>. 
         * La versione di java per microcontrolli con cui sono tenuto a lavorare risale invece al 8/12/1998.
         * @return l'array richiesto contenente una copia profonda di tutte le entries
         * @throws NoSuchElementException se la chiamata a next() da parte dell'iteratore fallisce
         */
        @Override
        public Object[] toArray(Object[] a) throws NoSuchElementException {
            int size = size();
            if (a.length < size)
                a = (Object[])java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), size);
            Iterator i = iterator();
            for (int j = 0; j < size; j++){
                SimpleEntry entry = (SimpleEntry) i.next();
                a[j] = new SimpleEntry(entry.getKey(), entry.getValue());
            }
            if (a.length > size)
                a[size] = null;
            return a;
        }
    }

    /**
     * Classe interna che rappresenta la vista set di chiavi. Fornisce una logica di consultazione delle chiavi
     * di cui è composto tramite un iteratore che viene restituito dal suo metodo iterator(). Implementa l'interfaccia
     * HSet e fornisce metodi per manipolare e interogare l'insieme delle chiavi. Da questa vista è possibile rimuovere
     * delle chiavi associate a entries della mappa, ma non aggiungerne. Il motivo di questa scelta implementativa è per 
     * evitare che chi voglia ispezionare una mappa abbia la possibilità di aggiungerne del contenuto che potrebbe 
     * essere non previsto.
     */
    private class KeySet implements Set {
        /**
         * Metodo di aggiunta di una chiave alla vista di chiavi. L'operazione non è supportata per evitare che chi 
         * voglia ispezionare una mappa abbia la possibilità di aggiungerne del contenuto che potrebbe essere non previsto.
         * Per questo motivo al tentativo di aggiunta di una chiave viene lanciata eccezione, indipendentemente dall'oggetto
         * passato per argomento.
         * @throws UnsupportedOperationException con messaggio "operazione add non supportata"
         */
        @Override
        public boolean add(Object key) throws UnsupportedOperationException {
            throw new UnsupportedOperationException("operazione add non supportata");
        }

        /**
         * Metodo di aggiunta di una collezione di chiavi alla vista di chiavi. L'operazione non è supportata per evitare 
         * che chi voglia ispezionare una mappa abbia la possibilità di aggiungerne del contenuto che potrebbe essere non previsto.
         * Per questo motivo al tentativo di aggiunta di qualsiasi chiave viene lanciata eccezione, indipendentemente dall'oggetto
         * passato per argomento.
         * @throws UnsupportedOperationException con messaggio "operazione add non supportata"
         */
        @Override
        public boolean addAll(Collection c) throws UnsupportedOperationException {
            throw new UnsupportedOperationException("operazione addAll non supportata");
        }

        /**
         * Metodo che permette la cancellazione delle chiavi della mappa con conseguente cancellazione del contenuto della mappa
         * stessa. Lo scopo è quello di rilasciare ogni singola entry memorizzata nella mappa e azzerare così la dimensione 
         * della stessa. Dopo la sua esecuzione la dimensione della mappa è nulla. Uso clear() di MapAdapter. Non restituisce alcun valore.
         */
        @Override
        public void clear() {
            MapAdapter.this.clear();
        }

        /**
         * Metodo di ispezione della vista delle chiavi per conoscere la presenza di una specifica chiave passata per argomento.
         * @param key la chiave specificata di cui si vuole ispezionare la presenza
         * @return true o false, rispettivamente a seconda dell'effettiva presenza o assenza della chiave specificata
         */
        @Override
        public boolean contains(Object key) {
            return MapAdapter.this.containsKey(key);
        }

        /**
         * Metodo di ispezione della vista delle chiavi per conoscere la presenza di tutte le chiavi appartenenti
         * ad una specifica collezione passata per argomento.
         * @param c la collezione di entries specificata di cui si vuole ispezionare la presenza
         * @return true o false, rispettivamente a seconda dell'effettiva presenza o assenza di tutte le chiavi specificate
         * @throws NullPointerException se il valore passato per argomento è null
         */
        @Override
        public boolean containsAll(Collection c) throws NullPointerException {
            Iterator i = c.iterator();
            while (i.hasNext())
                if (!contains(i.next()))
                    return false;
            return true;
        }

        /**
         * Metodo di ispezione del key set che permette di conoscere il suo stato di vuotezza valutando se l'istanza di KeySet
         * this presenta o meno delle chiavi al suo interno. In tale casistica viene restituito true in riferimento
         * all'effettivo stato di vuotezza del set di chiavi, altrimenti viene restituito false. Con stato di vuotezza intendo
         * dimensione del key set pari a zero, indicante l'assenza di qualsiasi chiave al suo interno.
         * @return true o false, rispettivamente a seconda dell'effettivo stato di vuotezza o della presenza di entries nel set
         */
        @Override
        public boolean isEmpty() {
            return MapAdapter.this.isEmpty();
        }

        /**
         * Metodo che restituisce un'iteratore istanziato dalla classe KeyIterator. La logica di iterazione è perfettamente coerente
         * con l'implementazione KeyIterator e permette di iterare le chiavi del set associato alla mappa.
         * @return una nuova istanza della classe KeyIterator
         */
        @Override
        public Iterator iterator() {
            return new KeyIterator();
        }

        /**
         * Metodo di modifica del key set che permette di rimuovere una chiave. Non è possibile rimuovere una 
         * chiave non nulla perchè la mappa non supporta alcuna entry con chiave nulla. Non è inoltre possibile
         * rimuovere una chiave che non sia presente nel set di chiavi.
         * @param key la chiave che si vuole rimuovere dal set di chiavi
         * @return true o false, rispettivamente a seconda della possibilità o meno di effettuare la rimozione richiesta
         * @throws NullPointerException se il valore passato per argomento è null
         */
        @Override
        public boolean remove(Object key) throws NullPointerException {
            return MapAdapter.this.remove(key) != null;
        }

        /**
         * Metodo di modifica del key set che permette di rimuovere tutte le chiavi appartenenti alla collezione
         * specificata. Non è possibile rimuovere alcuna entry nulla perchè la mappa non supporta alcuna entry di 
         * questo tipo. Non è inoltre possibile rimuovere una qualsiasi chiave associata a una entry che non sia presente 
         * nel set di chiavi associato a un'istanza della mappa.
         * @param c la collezione di oggetto che si vuole rimuovere dal set di chiavi
         * @return true o false, rispettivamente a seconda della possibilità o meno di effettuare la rimozione richiesta
         */
        @Override
        public boolean removeAll(Collection c) {
            boolean modified = false;
            Iterator i = iterator();
            while (i.hasNext()) {
                Object key = i.next();
                if (c.contains(key)) {
                    i.remove();
                    modified = true;
                }
            }
            return modified;
        }

        /**
         * Metodo di modifica del set di chiavi per offrire la possibilità di trattenere in esso solo le entries le cui 
         * chiavi siano presenti sia nel set di chiavi che nella collezione specificata. Il test può fallire nei casi
         * in cui completare questa operazione non sia possibile. Casistiche possono essere per esempio:
         * <p><ul>
         *   <li> presenza di chiavi nulle nella collezione specificata</li>
         *   <li> presenza di alcune chiavi nella collezione specificata che non sono presenti nel set di chiavi</li>
         * </ul><p>
         * @return true o false a seconda della possibilità oppure no di trattenere tutte le entries specificate
         */
        @Override
        public boolean retainAll(Collection c) {
            boolean modified = false;
            Iterator i = iterator();
            while (i.hasNext()) {
                Object key = i.next();
                if (!c.contains(key)) {
                    i.remove();
                    modified = true;
                }
            }
            return modified;
        }

        /**
         * Metodo di ispezione del key set che permette di conoscerne la dimensione. Il valore che viene restituito è la dimensione
         * della key set che è esattamente uguale al numero di chiavi presenti in esso e coincide dunque alla cardinalità della
         * mappa this.
         * @return la dimensione del key set pari alla dimensione della mappa
         */
        @Override
        public int size() {
            return MapAdapter.this.size();
        }

        /**
         * Metodo per restituire un array ospitante tutte le chiavi presenti nel set di chiavi.
         * La scelta di utilizzare una classe esterna con metodi di ispezione delle entries, ovvero SimpleEntry per
         * le entries della mappa, non mi torna utile in questo caso. Questo metodo con gli strumenti che ho a 
         * disposizione non mi permette di creare una deep copy delle chiavi e sono costretto a effettuare una
         * shallow copy.
         * @return l'array richiesto contenente una copia superficiale di tutte le chiavi
         */
        @Override
        public Object[] toArray() {
            Object[] result = new Object[size()];
            Iterator i = iterator();
            for (int j = 0; j < result.length; j++){
                result[j] = i.next();
            }
            return result;
        }

        /**
         * Metodo per restituire un array ospitante tutte le chiavi presenti nel key set. Le copie delle chiavi
         * vengono inserite direttamente nell'array ricevuto per argomento, se abbastanza lungo da ospitare le
         * chiavi, altrimenti ne viene istanziato uno di lunghezza sufficiente.<p>
         * La scelta di utilizzare una classe esterna con metodi di ispezione delle entries, ovvero SimpleEntry per
         * le entries della mappa, non mi torna utile in questo caso. Non riesco infatti a garantire in questo metodo una
         * deep copy e mi devo limitare a una shallow copy.<p>
         * Utilizzo java reflect come descritto su 
         * <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/package-summary.html#package.description"> disponibile dalla versione JDK1.1 del 19/2/1997</a>. 
         * La versione di java per microcontrolli con cui sono tenuto a lavorare risale invece al 8/12/1998.
         * @return l'array richiesto contenente una copia superficiale di tutte le chiavi
         * @throws NoSuchElementException se la chiamata a next() da parte dell'iteratore fallisce
         */
        @Override
        public Object[] toArray(Object[] a) throws NoSuchElementException {
            int size = size();
            if (a.length < size)
                a = (Object[])java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), size);
            Iterator i = iterator();
            for (int j = 0; j < size; j++){
                a[j] = i.next();
            } 
            if (a.length > size)
                a[size] = null;
            return a;
        }
    }

    /**
     * Classe interna che rappresenta la vista collezione di valori. Fornisce una logica di consultazione dei valori
     * di cui è composto tramite un iteratore che viene restituito dal suo metodo iterator(). Implementa l'interfaccia
     * HCollection e fornisce metodi per manipolare e interogare l'insieme dei valori. Da questa vista è possibile rimuovere
     * dei valori della mappa, ma non aggiungerne. Il motivo di questa scelta implementativa è per evitare che chi 
     * voglia ispezionare una mappa abbia la possibilità di aggiungerne del contenuto che potrebbe essere non previsto.
     */
    private class ValuesCollection implements Collection {
        /**
         * Metodo di aggiunta di un valore alla vista di valori. L'operazione non è supportata per evitare che chi 
         * voglia ispezionare una mappa abbia la possibilità di aggiungerne del contenuto che potrebbe essere non previsto.
         * Per questo motivo al tentativo di aggiunta di un valore viene lanciata eccezione, indipendentemente dall'oggetto
         * passato per argomento.
         * @throws UnsupportedOperationException con messaggio "operazione add non supportata"
         */
        @Override
        public boolean add(Object value) throws UnsupportedOperationException {
            throw new UnsupportedOperationException("operazione add non supportata");
        }

        /**
         * Metodo di aggiunta di una collezione di valori alla vista di entries. L'operazione non è supportata per evitare 
         * che chi voglia ispezionare una mappa abbia la possibilità di aggiungerne del contenuto che potrebbe essere non previsto.
         * Per questo motivo al tentativo di aggiunta di qualsiasi valore viene lanciata eccezione, indipendentemente dall'oggetto
         * passato per argomento.
         * @throws UnsupportedOperationException con messaggio "operazione add non supportata"
         */
        @Override
        public boolean addAll(Collection c) throws UnsupportedOperationException {
            throw new UnsupportedOperationException("operazione addAll non supportata");
        }

        /**
         * Metodo che permette la cancellazione dei valori della mappa con conseguente cancellazione del contenuto della mappa
         * stessa. Lo scopo è quello di rilasciare ogni singola entry memorizzata nella mappa e azzerare così la dimensione 
         * della stessa. Dopo la sua esecuzione la dimensione della mappa è nulla. Uso clear() di MapAdapter. Non restituisce alcun valore.
         */
        @Override
        public void clear() {
            MapAdapter.this.clear();
        }

        /**
         * Metodo di ispezione della vista dei valori per conoscere la presenza di un specifico valore passato per argomento.
         * @param value il valore specificato di cui si vuole ispezionare la presenza
         * @return true o false, rispettivamente a seconda dell'effettiva presenza o assenza del valore specificato
         * @throws NullPointerException se il valore passato per argomento è null
         */
        @Override
        public boolean contains(Object value) throws NullPointerException {
            return MapAdapter.this.containsValue(value);
        }

        /**
         * Metodo di ispezione della vista dei valori per conoscere la presenza di tutti i valori appartenenti
         * ad una specifica collezione passata per argomento.
         * @param c la collezione di valori specificati di cui si vuole ispezionare la presenza
         * @return true o false, rispettivamente a seconda dell'effettiva presenza o assenza di tutti i valori specificati
         * @throws NullPointerException se il valore passato per argomento è null
         */
        @Override
        public boolean containsAll(Collection c) throws NullPointerException {
            Iterator i = c.iterator();
            while (i.hasNext())
                if (!contains(i.next()))
                    return false;
            return true;
        }

        /**
         * Metodo di ispezione della collezione di valoru che permette di conoscere il suo stato di vuotezza valutando se l'istanza di 
         * ValuesCollection this presenta o meno dei valori al suo interno. In tale casistica viene restituito true in riferimento
         * all'effettivo stato di vuotezza della collezione di valori, altrimenti viene restituito false. Con stato di vuotezza intendo
         * dimensione della collezione di valori pari a zero, indicante l'assenza di qualsiasi valore al suo interno.
         * @return true o false, rispettivamente a seconda dell'effettivo stato di vuotezza o della presenza di valori nella collezione
         */
        @Override
        public boolean isEmpty() {
            return MapAdapter.this.isEmpty();
        }

        /**
         * Metodo che restituisce un'iteratore istanziato dalla classe ValueIterator. La logica di iterazione è perfettamente coerente
         * con l'implementazione ValueIterator e permette di iterare la collezione di valori associata alla mappa.
         * @return una nuova istanza della classe ValueIterator
         */
        @Override
        public Iterator iterator() {
            return new ValueIterator();
        }

        /**
         * Metodo di modifica della collezione di valori che permette di rimuovere un valore dalla collezione.
         * Non è possibile rimuovere un valore non nullo perchè la mappa non supporta alcuna entry con valore null. 
         * Non è inoltre possibile rimuovere un'entry che non sia presente nella collezione di valori.
         * @param value il valore che si vuole rimuovere dalla collezione di valori
         * @return true o false, rispettivamente a seconda della possibilità o meno di effettuare la rimozione richiesta
         */
        @Override
        public boolean remove(Object value) {
            Iterator i = iterator();
            while (i.hasNext()) {
                if(i.next().equals(value)) {
                    i.remove();
                    return true;
                }
            }
            return false;
        }

        /**
         * Metodo di modifica della collezione di valori che permette di rimuovere tutti i valori appartenenti alla collezione
         * specificata. Non è possibile rimuovere alcun valore nullo perchè la mappa non supporta alcuna entry con valore 
         * nullo. Non è inoltre possibile rimuovere una qualsiasi chiave associata a una entry che non sia presente nella 
         * collezione di valori associata a un'istanza della mappa.
         * @param c la collezione di oggetto che si vuole rimuovere dalla collezione di valori
         * @return true o false, rispettivamente a seconda della possibilità o meno di effettuare la rimozione richiesta
         */
        @Override
        public boolean removeAll(Collection c) {
            boolean modified = false;
            Iterator i = iterator();
            while (i.hasNext()) {
                if (c.contains(i.next())) {
                    i.remove();
                    modified = true;
                }
            }
            return modified;
        }

        /**
         * Metodo di modifica della collezione di valori per offrire la possibilità di trattenere in esso solo i valori 
         * che siano presenti sia nella collezione di valori che nella collezione specificata. Il test può fallire nei casi
         * in cui completare questa operazione non sia possibile. Casistiche possono essere per esempio:
         * <p><ul>
         *   <li> presenza di valori nulli nella collezione specificata</li>
         *   <li> presenza di alcuni valori nella collezione specificata che non sono presenti nella collezione di valori</li>
         * </ul><p>
         * @return true o false a seconda della possibilità oppure no di trattenere tutti i valori specificati
         */
        @Override
        public boolean retainAll(Collection c) {
            boolean modified = false;
            Iterator i = iterator();
            while (i.hasNext()) {
                if (!c.contains(i.next())) {
                    i.remove();
                    modified = true;
                }
            }
            return modified;
        }

        /**
         * Metodo di ispezione della collezione di valori che permette di conoscerne la dimensione. Il valore che viene 
         * restituito è la dimensione della collezione di valori che è esattamente uguale al numero di valori presenti 
         * nella mappa e coincide dunque alla cardinalità della mappa this. Questo assumento che la mappa contenga
         * entries coppie chiave-valore con unicità anche nel valore. Qualora non si voglia porre anche questa restrizione,
         * è possibile cambiare la scelta implementativa restituendo come valore il numero di valori unicamente
         * presenti nelle entries della mappa, senza contare quante volte ogni valore è contenuto nelle entries della mappa.
         * @return la dimensione della collezione di valori pari alla dimensione della mappa, con la restrizione sopra posta
         */
        @Override
        public int size() {
            return MapAdapter.this.size();
        }

        /**
         * Metodo per restituire un array ospitante tutti i valori presenti nella collezione di valori.
         * La scelta di utilizzare una classe esterna con metodi di ispezione delle entries, ovvero SimpleEntry per
         * le entries della mappa, non mi torna utile in questo caso. Questo metodo con gli strumenti che ho a 
         * disposizione non mi permette di creare una deep copy dei valori e sono costretto a effettuare una
         * shallow copy.
         * @return l'array richiesto contenente una copia superficiale di tutti i valori
         */
        @Override
        public Object[] toArray() {
            Object[] result = new Object[size()];
            Iterator i = iterator();
            for (int j = 0; j < result.length; j++)
                result[j] = i.next();
            return result;
        }

        /**
         * Metodo per restituire un array ospitante tutti i valori presenti nella collezione di valori. Le copie dei valori
         * vengono inserite direttamente nell'array ricevuto per argomento, se abbastanza lungo da ospitare i valori,
         * altrimenti ne viene istanziato uno di lunghezza sufficiente.<p>
         * La scelta di utilizzare una classe esterna con metodi di ispezione delle entries, ovvero SimpleEntry per
         * le entries della mappa, non mi torna utile in questo caso. Non riesco infatti a garantire in questo metodo una
         * deep copy e mi devo limitare a una shallow copy.<p>
         * Utilizzo java reflect come descritto su 
         * <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/package-summary.html#package.description"> disponibile dalla versione JDK1.1 del 19/2/1997</a>. 
         * La versione di java per microcontrolli con cui sono tenuto a lavorare risale invece al 8/12/1998.
         * @return l'array richiesto contenente una copia superficiale di tutti i valori
         * @throws NoSuchElementException se la chiamata a next() da parte dell'iteratore fallisce
         */
        @Override
        public Object[] toArray(Object[] a) throws NoSuchElementException{
            int size = size();
            if (a.length < size)
                a = (Object[])java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), size);
            Iterator i = iterator();
            for (int j = 0; j < size; j++)
                a[j] = i.next();
            if (a.length > size)
                a[size] = null;
            return a;
        }
    }

    /**
     * Classe interna privata che implementa l'interfaccia HIterator. Fornisce una logica di iterazione
     * delle entries contenute in EntrySet. Non è possibile istanziare dall'esterno alcuna istanza di questa
     * classe, ma risulta utile al metodo iterator() di EntrySet, che restituisce un iteratore istanziandone
     * uno da questa classe. Fornisce i metodi hasNext() next() e remove() per l'iterazione, metodi utili e di
     * obbligatoria implementazione per via della loro presenza all'interno dell'interfaccia HIterator.
     */
    private class EntryIterator implements Iterator {
        /**
         * Attributo privato che rappresenta l'insieme delle chiavi. Utilizzo la classe Enumeration ampliando
         * la compatibilità dell'iteratore con tutti gli object, dunque per upcasting qualsiasi oggetto il cui 
         * data type è derivato da Object è compatibile con questo iteratore.
         */
        private Enumeration<Object> keys;

        /**
         * Attributo privato che rappresenta il vero e proprio puntatore, riflette lo stato del puntatore 
         * all'interno dell'enumerazione che viene iterata. Può essere interrogato per ottenere l'ultima chiave
         * che è stata iterata. Può anche essere usato come puntatore della chiave associata a una entry da rimuovere,
         * a patto che sia stata effettuata in precedenza almeno una chiamata a next()
         */
        private Object currentKey;

        /**
         * Costruttore interno che permette di istanziare un oggetto della classe legando l'iteratore
         * alle coppie chiave-valore presenti nella mappa. Viene usato dal metodo iterator() di MapAdapter
         */
        EntryIterator() {
            keys = adaptee.keys();
            currentKey = null;
        }

        /**
         * Metodo di ispezione dell'iteratore che permette di interrogare l'iteratore stesso per verificare
         * la possibilità di futura iterazione sull'istanza this di EntryIterator. La possibilità di iterare
         * è strettamente legata alla presenza di ulteriori elementi che non sono ancora stati iterati.
         * @return true se è possibile iterare oltre
         */
        @Override
        public boolean hasNext() {
            return keys.hasMoreElements();
        }

        /**
         * Metodo di ispezione dell'iteratore che permette di iterare sull'istanza this di EntryIterator.
         * Può lanciare eccezione nel caso in cui non ci siano ulteriori elementi da iterare.
         * @return true se è possibile iterare oltre
         * @throws NoSuchElementException se non ci sono ulteriori elementi da iterare
         */
        @Override
        public Object next() throws NoSuchElementException {
            currentKey = keys.nextElement();
            return new SimpleEntry(currentKey, adaptee.get(currentKey));
        }

        /**
         * Metodo di rimozione dell'elemento iterato per ultimo sull'istanza this di EntryIterator.
         * Può lanciare eccezione nei casi in cui non sia stato precedentemente chiamato next(), 
         * oppure se la chiave è nulla. Tecnicamente questo caso non si deve verificare, perchè avendo
         * implementato la mappa con Hashtable non sono permesse copie chiave-valore con anche solo uno
         * dei due attributi nulli.
         * @throws IllegalStateException al tentativo di iterare senza aver prima chiamato next()
         */
        @Override
        public void remove() throws IllegalStateException {
            if (currentKey == null)
                throw new IllegalStateException("Impossibile rimuovere senza una chiamata precedente a next()");
            adaptee.remove(currentKey);
            currentKey = null;
        }
    }

    /**
     * Classe interna privata che implementa l'interfaccia HIterator. Fornisce una logica di iterazione
     * delle chiavi contenuti in KeySet. Non è possibile istanziare dall'esterno alcuna istanza di questa
     * classe, ma risulta utile al metodo iterator() di KeySet, che restituisce un iteratore istanziandone
     * uno da questa classe. Fornisce i metodi hasNext() next() e remove() per l'iterazione, metodi utili e di
     * obbligatoria implementazione per via della loro presenza all'interno dell'interfaccia HIterator.
     */
    private class KeyIterator implements Iterator {
        /**
         * Attributo privato che rappresenta l'insieme delle chiavi. Utilizzo la classe Enumeration ampliando
         * la compatibilità dell'iteratore con tutti gli object, dunque per upcasting qualsiasi oggetto il cui 
         * data type è derivato da Object è compatibile con questo iteratore.
         */
        private Enumeration<Object> keys;

        /**
         * Attributo privato che rappresenta il vero e proprio puntatore, riflette lo stato del puntatore 
         * all'interno dell'enumerazione che viene iterata. Può essere interrogato per ottenere l'ultima chiave
         * che è stata iterata. Può anche essere usato come puntatore della chiave associata a una entry da rimuovere,
         * a patto che sia stata effettuata in precedenza almeno una chiamata a next()
         */
        private Object currentKey;

        /**
         * Costruttore interno che permette di istanziare un oggetto della classe legando l'iteratore
         * alle coppie chiave-valore presenti nella mappa. Viene usato dal metodo iterator() di MapAdapter
         */
        public KeyIterator(){
            keys = adaptee.keys();
            currentKey = null;
        }

        /**
         * Metodo di ispezione dell'iteratore che permette di interrogare l'iteratore stesso per verificare
         * la possibilità di futura iterazione sull'istanza this di KeyIterator. La possibilità di iterare
         * è strettamente legata alla presenza di ulteriori elementi che non sono ancora stati iterati.
         * @return true se è possibile iterare oltre
         */
        @Override
        public boolean hasNext() {
            return keys.hasMoreElements();
        }

        /**
         * Metodo di ispezione dell'iteratore che permette di iterare sull'istanza this di KeyIterator.
         * Può lanciare eccezione nel caso in cui non ci siano ulteriori elementi da iterare.
         * @return true se è possibile iterare oltre
         * @throws NoSuchElementException se non ci sono ulteriori elementi da iterare
         */
        @Override
        public Object next() throws NoSuchElementException {
            currentKey = keys.nextElement();
            return currentKey;
        }

        /**
         * Metodo di rimozione dell'elemento iterato per ultimo sull'istanza this di KeyIterator.
         * Può lanciare eccezione nei casi in cui non sia stato precedentemente chiamato next(), 
         * oppure se la chiave è nulla. Tecnicamente questo caso non si deve verificare, perchè avendo
         * implementato la mappa con Hashtable non sono permesse copie chiave-valore con anche solo uno
         * dei due attributi nulli.
         * @throws IllegalStateException al tentativo di iterare senza aver prima chiamato next()
         */
        @Override
        public void remove() throws IllegalStateException {
            if (currentKey == null)
                throw new IllegalStateException("Impossibile rimuovere senza una chiamata precedente a next()");
            adaptee.remove(currentKey);
            currentKey = null;
        }
    }

    /**
     * Classe interna privata che implementa l'interfaccia HIterator. Fornisce una logica di iterazione
     * dei valori contenuti in ValuesCollection. Non è possibile istanziare dall'esterno alcuna istanza di questa
     * classe, ma risulta utile al metodo iterator() di ValuesCollection, che restituisce un iteratore istanziandone
     * uno da questa classe. Fornisce i metodi hasNext() next() e remove() per l'iterazione, metodi utili e di
     * obbligatoria implementazione per via della loro presenza all'interno dell'interfaccia HIterator.
     */
    private class ValueIterator implements Iterator {
        /**
         * Attributo privato che rappresenta l'insieme di valori. Utilizzo la classe Enumeration ampliando
         * la compatibilità dell'iteratore con tutti gli object, dunque per upcasting qualsiasi oggetto il cui 
         * data type è derivato da Object è compatibile con questo iteratore.
         */
        private Enumeration<Object> values;

        /**
         * Attributo privato che rappresenta il vero e proprio primo puntatore, riflette lo stato del puntatore 
         * all'interno dell'enumerazione che viene iterata. Può essere interrogato per ottenere il valore associato all'ultima 
         * entry che è stata iterata.
         */
        private Object currentValue;

        /**
         * Attributo privato che rappresenta il vero e proprio secondo puntatore, riflette lo stato del puntatore 
         * all'interno dell'enumerazione che viene iterata. Può essere interrogato per ottenere l'ultima chiave
         * che è stata iterata. Può anche essere usato come puntatore della chiave associata a una entry da rimuovere,
         * a patto che sia stata effettuata in precedenza almeno una chiamata a next()
         */
        private Object currentKey;

        /**
         * Costruttore interno che permette di istanziare un oggetto della classe legando l'iteratore
         * alle coppie chiave-valore presenti nella mappa. Viene usato dal metodo iterator() di MapAdapter
         */
        public ValueIterator(){
            values = adaptee.elements();
            currentValue = null;
            currentKey = null;
        }

        /**
         * Metodo di ispezione dell'iteratore che permette di interrogare l'iteratore stesso per verificare
         * la possibilità di futura iterazione sull'istanza this di ValueIterator. La possibilità di iterare
         * è strettamente legata alla presenza di ulteriori elementi che non sono ancora stati iterati.
         * @return true se è possibile iterare oltre
         */
        @Override
        public boolean hasNext() {
            return values.hasMoreElements();
        }

        /**
         * Metodo di ispezione dell'iteratore che permette di iterare sull'istanza this di ValueIterator.
         * Può lanciare eccezione nel caso in cui non ci siano ulteriori elementi da iterare.
         * @return true se è possibile iterare oltre
         * @throws NoSuchElementException se non ci sono ulteriori elementi da iterare
         */
        @Override
        public Object next() throws NoSuchElementException {
            currentValue = values.nextElement();
            // cerco chiave per il valore
            for (Enumeration<Object> e = adaptee.keys(); e.hasMoreElements();) {
                Object key = e.nextElement();
                if (adaptee.get(key).equals(currentValue)) {
                    currentKey = key;
                    break;
                }
            }
            return currentValue;
        }

        /**
         * Metodo di rimozione dell'elemento iterato per ultimo sull'istanza this di ValueIterator.
         * Può lanciare eccezione nei casi in cui non sia stato precedentemente chiamato next(), 
         * oppure se la chiave è nulla. Tecnicamente questo caso non si deve verificare, perchè avendo
         * implementato la mappa con Hashtable non sono permesse copie chiave-valore con anche solo uno
         * dei due attributi nulli.
         * @throws IllegalStateException al tentativo di iterare senza aver prima chiamato next()
         */
        @Override
        public void remove() throws IllegalStateException {
            if (currentValue == null || currentKey == null)
                throw new IllegalStateException("Impossibile rimuovere senza una chiamata precedente a next()");
            adaptee.remove(currentKey);
            currentValue = null;
            currentKey = null;
        }
    }
}