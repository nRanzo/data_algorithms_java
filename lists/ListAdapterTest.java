import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;

/**
 * La classe ListAdapterTest contiene unit test per la classe ListAdapter.
 * Utilizza il framework JUnit per eseguire test automatizzati.
 */
public class ListAdapterTest {

    private ListAdapter list;
    private ListAdapter sublist;

    /**
     * Imposta l'ambiente di test. Inizializza un ListAdapter con alcuni elementi e
     * crea una sublist.
     */
    @Before
    public void setUp() {
        list = new ListAdapter();
        list.add("Element1");
        list.add("Element2");
        list.add("Element3");
        list.add("Element4");
        list.add("Element5");

        // sublist contiene gli elementi "Element2", "Element3", "Element4"
        sublist = (ListAdapter) list.subList(1, 4);
    }

    /**
     * Pulisce l'ambiente di test. Annulla le liste per assicurare l'indipendenza
     * tra i test.
     */
    @After
    public void tearDown() {
        list = null;
        sublist = null;
    }

    /**
     * Test per il metodo add(Object o).
     * Verifica che l'elemento sia aggiunto correttamente alla lista e alla sublista.
     */
    @Test
    public void testAdd() {
        list.add("NewElement");
        assertEquals(6, list.size());
        assertTrue(list.contains("NewElement"));

        sublist.add("SubNewElement");
        assertEquals(4, sublist.size());
        assertTrue(sublist.contains("SubNewElement"));
    }

    /**
     * Test per il metodo add(int index, Object element).
     * Verifica che l'elemento sia aggiunto correttamente alla posizione specificata
     * nella lista e nella sublista.
     */
    @Test
    public void testAddAtIndex() {
        list.add(2, "IndexedElement");
        assertEquals(6, list.size());
        assertEquals("IndexedElement", list.get(2));

        sublist.add(1, "SubIndexedElement");
        assertEquals(4, sublist.size());
        assertEquals("SubIndexedElement", sublist.get(1));
    }

    /**
     * Test per il metodo addAll(HCollection c).
     * Verifica che tutti gli elementi della collezione siano aggiunti
     * correttamente alla lista e alla sublista.
     */
    @Test
    public void testAddAll() {
        ListAdapter otherList = new ListAdapter();
        otherList.add("ElementA");
        otherList.add("ElementB");
        
        list.addAll(otherList);
        assertEquals(7, list.size());
        assertTrue(list.contains("ElementA"));
        assertTrue(list.contains("ElementB"));

        sublist.addAll(otherList);
        assertEquals(7, sublist.size());
        assertTrue(sublist.contains("ElementA"));
        assertTrue(sublist.contains("ElementB"));
    }

    /**
     * Test per il metodo clear().
     * Verifica che tutti gli elementi siano rimossi dalla lista e dalla sublista.
     */
    @Test
    public void testClear() {
        list.clear();
        assertTrue(list.isEmpty());

        sublist.clear();
        assertTrue(sublist.isEmpty());
    }

    /**
     * Test per il metodo contains(Object o).
     * Verifica che la lista e la sublista contengano o meno gli elementi specificati.
     */
    @Test
    public void testContains() {
        assertTrue(list.contains("Element1"));
        assertFalse(list.contains("NonExistentElement"));

        assertTrue(sublist.contains("Element2"));
        assertFalse(sublist.contains("Element5"));
    }

    /**
     * Test per il metodo containsAll(HCollection c).
     * Verifica che la lista contenga tutti gli elementi della collezione.
     */
    @Test
    public void testContainsAll() {
        ListAdapter otherList = new ListAdapter();
        otherList.add("Element1");
        otherList.add("Element2");

        assertTrue(list.containsAll(otherList));

        otherList.add("NonExistentElement"); 
        assertFalse(list.containsAll(otherList));
    }

    /**
     * Test per il metodo equals(Object o).
     * Verifica che due liste siano uguali.
     */
    @Test
    public void testEquals() {
        ListAdapter otherList = new ListAdapter();
        otherList.add("Element1");
        otherList.add("Element2");
        otherList.add("Element3");
        otherList.add("Element4");
        otherList.add("Element5");

        assertTrue(list.equals(otherList));
    }

    /**
     * Test per il metodo isEmpty().
     * Verifica che la lista sia vuota o non vuota.
     */
    @Test
    public void testIsEmpty() {
        ListAdapter emptyList = new ListAdapter();
        assertTrue(emptyList.isEmpty());

        assertFalse(list.isEmpty());
    }

    /**
     * Test per il metodo iterator().
     * Verifica che l'iteratore possa scorrere correttamente tutti gli elementi
     * della lista e della sublista.
     */
    @Test
    public void testIterator() {
        HIterator it = list.iterator();
        int count = 0;
        while (it.hasNext()) {
            it.next();
            count++;
        }
        assertEquals(5, count);

        it = sublist.iterator();
        count = 0;
        while (it.hasNext()) {
            it.next();
            count++;
        }
        assertEquals(3, count);
    }

    /**
     * Test per il metodo remove(Object o).
     * Verifica che l'elemento specificato venga rimosso dalla lista e dalla sublista.
     */
    @Test
    public void testRemove() {
        assertTrue(list.remove("Element3"));
        assertEquals(4, list.size());

        assertTrue(sublist.remove("Element2"));
        assertEquals(2, sublist.size());
    }

    /**
     * Test per il metodo remove(Object o).
     * Verifica che l'elemento specificato non possa essere rimosso dalla lista e dalla sublista.
     * In tal caso il metodo contains deve restituire false
     */
    @Test
    public void testRemoveNull() {
        assertFalse(list.remove("Element300"));
        assertEquals(5, list.size());
        assertFalse(list.contains("Element300"));

        assertFalse(sublist.remove("Element200"));
        assertEquals(3, sublist.size());
        assertFalse(sublist.contains("Element200"));
    }

    /**
     * Test per il metodo remove(int index).
     * Verifica che l'elemento alla posizione specificata venga rimosso
     * dalla lista e dalla sublista.
     */
    @Test
    public void testRemoveAtIndex() {
        list.remove(2);
        assertEquals(4, list.size());
        assertFalse(list.contains("Element3"));

        sublist.remove(1);
        assertEquals(2, sublist.size());
        assertFalse(sublist.contains("Element3"));
    }

    /**
     * Test per il metodo removeAll(HCollection c).
     * Verifica che tutti gli elementi della collezione specificata
     * vengano rimossi dalla lista e dalla sublista.
     */
    @Test
    public void testRemoveAll() {
        ListAdapter otherList = new ListAdapter();
        otherList.add("Element2");
        otherList.add("Element3");

        list.removeAll(otherList);
        assertEquals(3, list.size());
        assertFalse(list.contains("Element2"));
        assertFalse(list.contains("Element3"));

        sublist.removeAll(otherList);
        assertEquals(1, sublist.size());
        assertFalse(sublist.contains("Element2"));
    }

    /**
     * Test per il metodo retainAll(HCollection c).
     * Verifica che solo gli elementi presenti nella collezione
     * specificata siano mantenuti nella lista e nella sublista.
     */
    @Test
    public void testRetainAll() {
        ListAdapter otherList = new ListAdapter();
        otherList.add("Element2");
        otherList.add("Element3");
        otherList.add("Element4");

        list.retainAll(otherList);
        assertEquals(3, list.size());
        assertTrue(list.contains("Element2"));
        assertTrue(list.contains("Element3"));
        assertTrue(list.contains("Element4"));

        sublist.retainAll(otherList);
        assertEquals(2, sublist.size());
        assertTrue(sublist.contains("Element2"));
        assertTrue(sublist.contains("Element3"));
    }

    /**
     * Test per il metodo size().
     * Verifica che la dimensione della lista e della sublista sia corretta.
     */
    @Test
    public void testSize() {
        assertEquals(5, list.size());
        assertEquals(3, sublist.size());
    }

    /**
     * Test per il metodo toArray().
     * Verifica che l'array risultante contenga tutti gli elementi della lista
     * e della sublista.
     */
    @Test
    public void testToArray() {
        Object[] array = list.toArray();
        assertEquals(5, array.length);
        assertEquals("Element1", array[0]);

        array = sublist.toArray();
        assertEquals(3, array.length);
        assertEquals("Element2", array[0]);
    }

    /**
     * Test per il metodo toArray(Object[] a).
     * Verifica che l'array specificato venga riempito correttamente
     * con gli elementi della lista e della sublista.
     */
    @Test
    public void testToArrayWithParameter() {
        Object[] array = new Object[5];
        list.toArray(array);
        assertEquals("Element1", array[0]);

        array = new Object[3];
        sublist.toArray(array);
        assertEquals("Element2", array[0]);
    }

    /**
     * Test per il metodo get(int index).
     * Verifica che gli elementi alla posizione specificata siano correttamente
     * recuperati dalla lista e dalla sublista.
     */
    @Test
    public void testGet() {
        assertEquals("Element1", list.get(0));
        assertEquals("Element3", list.get(2));

        assertEquals("Element2", sublist.get(0));
        assertEquals("Element4", sublist.get(2));
    }

    /**
     * Test per il metodo indexOf(Object o).
     * Verifica che l'indice dell'elemento specificato sia correttamente
     * recuperato dalla lista e dalla sublista.
     */
    @Test
    public void testIndexOf() {
        assertEquals(0, list.indexOf("Element1"));
        assertEquals(2, list.indexOf("Element3"));
        assertEquals(-1, list.indexOf("NonExistentElement"));

        assertEquals(2, sublist.indexOf("Element3"));
        assertEquals(-1, sublist.indexOf("Element5"));
    }

    /**
     * Test per il metodo lastIndexOf(Object o).
     * Verifica che l'ultimo indice dell'elemento specificato sia correttamente
     * recuperato dalla lista e dalla sublista.
     */
    @Test
    public void testLastIndexOf() {
        list.add("Element3");
        assertEquals(5, list.lastIndexOf("Element3"));

        sublist.add("Element4");
        assertEquals(4, sublist.lastIndexOf("Element4"));
    }

    /**
     * Test per il metodo listIterator().
     * Verifica che il list iterator possa scorrere correttamente tutti gli
     * elementi della lista e della sublista.
     */
    @Test
    public void testListIterator() {
        HListIterator it = list.listIterator();
        assertTrue(it.hasNext());
        assertEquals("Element1", it.next());

        it = sublist.listIterator();
        assertTrue(it.hasNext());
        assertEquals("Element2", it.next());
    }

    /**
     * Test per il metodo listIterator(int index).
     * Verifica che il list iterator inizi correttamente dalla posizione specificata
     * nella lista e nella sublista.
     */
    @Test
    public void testListIteratorWithIndex() {
        HListIterator it = list.listIterator(2);
        assertEquals("Element3", it.next());

        it = sublist.listIterator(1);
        assertEquals("Element3", it.next());
    }

    /**
     * Test per il metodo set(int index, Object element).
     * Verifica che l'elemento alla posizione specificata sia correttamente
     * sostituito nella lista e nella sublista.
     */
    @Test
    public void testSet() {
        list.set(1, "NewElement");
        assertEquals("NewElement", list.get(1));

        sublist.set(1, "SubNewElement");
        assertEquals("SubNewElement", sublist.get(1));
    }

    /**
     * Test per il metodo subList(int fromIndex, int toIndex).
     * Verifica che la sublista creata contenga correttamente gli elementi
     * specificati.
     */
    @Test
    public void testSubList() {
        HList newSublist = list.subList(1, 3);
        assertEquals(2, newSublist.size());
        assertEquals("Element2", newSublist.get(0));
    }

    /**
     * Test di un Edge Case: Verifica che un'eccezione sia lanciata quando
     * si tenta di aggiungere un elemento fuori dai limiti.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddIndexOutOfBounds() {
        list.add(10, "OutOfBounds");
    }

    /**
     * Test di un Edge Case: Verifica che un'eccezione sia lanciata quando
     * si tenta di recuperare un elemento fuori dai limiti.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetIndexOutOfBounds() {
        list.get(10);
    }

    /**
     * Test di un Edge Case: Verifica che un'eccezione sia lanciata quando
     * si tenta di rimuovere un elemento fuori dai limiti.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveIndexOutOfBounds() {
        list.remove(10);
    }

    /**
     * Test di un Edge Case: Verifica che un'eccezione sia lanciata quando
     * si tenta di creare una sublista con indici fuori dai limiti.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubListOutOfBounds() {
        list.subList(0, 6);
    }

    /**
     * Test di integrità per verificare che le modifiche alla lista principale
     * siano corrette.
     */
    @Test
    public void testMainListModification() {
        list.set(2, "ModifiedElement");
        assertEquals("ModifiedElement", list.get(2));
    }

    /**
     * Test di integrità per verificare che le modifiche alla sublista siano
     * riflesse correttamente nella lista principale.
     */
    @Test
    public void testSublistModification() {
        sublist.set(0, "ModifiedElement");
        assertEquals("ModifiedElement", list.get(1));
    }

    /**
     * Test di invarianza per verificare che la dimensione della lista rimanga
     * invariata dopo aggiunte e rimozioni.
     */
    @Test
    public void testInvariantSize() {
        int initialSize = list.size();
        list.add("NewElement");
        list.remove("NewElement");
        assertEquals(initialSize, list.size());
    }

    /**
     * Test di invarianza per verificare che l'ordine degli elementi rimanga
     * invariato dopo aggiunte e rimozioni.
     */
    @Test
    public void testInvariantOrder() {
        list.add("NewElement");
        list.remove("NewElement");
        assertEquals("Element1", list.get(0));
        assertEquals("Element2", list.get(1));
    }

    /**
     * Test per la creazione di una sottolista da un'altra sottolista esistente.
     * Verifica che la nuova sottolista contenga gli elementi corretti.
     */
    @Test
    public void testSublistFromSublist() {
        ListAdapter subsublist = (ListAdapter) sublist.subList(1, 3);

        assertEquals(2, subsublist.size());
        assertEquals("Element3", subsublist.get(0));
        assertEquals("Element4", subsublist.get(1));
    }

    /**
     * Test per l'aggiunta di elementi a una sottolista creata da un'altra sottolista.
     * Verifica che le modifiche siano riflesse nelle liste originali.
     */
    @Test
    public void testAddToSublistFromSublist() {
        ListAdapter subsublist = (ListAdapter) sublist.subList(0, 2);

        subsublist.add(2, "NewElement");

        assertEquals(3, subsublist.size());
        assertEquals("NewElement", subsublist.get(2));

        assertEquals(3, sublist.size());
    }

    /**
     * Test per la rimozione di elementi da una sottolista creata da un'altra sottolista.
     * Verifica che le modifiche siano riflesse nelle liste originali.
     */
    @Test
    public void testRemoveFromSublistFromSublist() {
        ListAdapter subsublist = (ListAdapter) sublist.subList(0, 2);

        subsublist.remove("Element2");

        assertEquals(1, subsublist.size());
        assertEquals("Element2", subsublist.get(0));

        assertEquals("Element2", sublist.get(0));
    }

    /**
     * Test per la modifica di elementi in una sottolista creata da un'altra sottolista.
     * Verifica che le modifiche siano riflesse nelle liste originali.
     */
    @Test
    public void testModifyInSublistFromSublist() {
        ListAdapter subsublist = (ListAdapter) sublist.subList(0, 2);

        subsublist.set(1, "ModifiedElement");

        assertEquals("ModifiedElement", subsublist.get(1));
    }
}
