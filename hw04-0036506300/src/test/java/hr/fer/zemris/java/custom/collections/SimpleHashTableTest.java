package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.collections.SimpleHashtable.TableEntry;

public class SimpleHashTableTest {

	@Test
	public void defaultCapacityConstructorTest() {
		SimpleHashtable<String, Integer> ht = new SimpleHashtable<>();
		assertEquals(16, ht.getCapacity());
	}
	
	@Test
	public void givenCapacityConstructorTest() {
		SimpleHashtable<String, Integer> ht1 = new SimpleHashtable<>(12);
		assertEquals(16, ht1.getCapacity());
		SimpleHashtable<String, Integer> ht2 = new SimpleHashtable<>(1);
		assertEquals(1, ht2.getCapacity());
		SimpleHashtable<String, Integer> ht3 = new SimpleHashtable<>(44);
		assertEquals(64, ht3.getCapacity());
		SimpleHashtable<String, Integer> ht4 = new SimpleHashtable<>(8);
		assertEquals(8, ht4.getCapacity());
	}
	
	@Test
	public void invalidCpacityConstructorTest() {
		assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<String, Integer>(0));
	}
	
	@Test
	public void putAFewEntriesTest() {
		SimpleHashtable<String, Integer> ht = new SimpleHashtable<>();
		ht.put("Ivana", 2);
		ht.put("Ante", 2);
		ht.put("Jasna", 2);
		ht.put("Kristina", 5);
		ht.put("Ivana", 5);
		assertEquals(5, ht.get("Kristina"));
		assertEquals(4, ht.size());
	}
	
	@Test
	public void removeTest() {
		SimpleHashtable<String, Integer> ht = new SimpleHashtable<>();
		ht.put("Ivana", 2);
		ht.put("Ante", 3);
		ht.put("Jasna", 2);
		ht.remove("Ante");
		assertFalse(ht.containsKey("Ante"));
		assertFalse(ht.containsValue(3));
	}
	
	@Test
	public void aLotOfThingsTest() {
		SimpleHashtable<String, Integer> ht = new SimpleHashtable<>();
		ht.put("Ivana", 2);
		ht.put("Ante", 3);
		ht.put("Jasna", 2);
		assertThrows(NullPointerException.class, () -> ht.put(null, 1));
		assertEquals(3, ht.size());
		ht.remove("Pero");
		ht.remove(null);
		assertNull(ht.get(null));
		assertNull(ht.get("Pero"));
		assertFalse(ht.containsKey(null));
		assertTrue(ht.containsValue(3));
	}
	
	@Test
	public void capacityRealocationTest() {
		SimpleHashtable<String, Integer> ht = new SimpleHashtable<>(3);
		assertEquals(4, ht.getCapacity());
		ht.put("Ivana", 2);
		ht.put("Ante", 3);
		ht.put("Jasna", 2);
		assertEquals(8, ht.getCapacity());
		assertTrue(ht.containsKey("Ivana"));
		assertTrue(ht.containsKey("Ante"));
		assertTrue(ht.containsKey("Jasna"));
		assertEquals(3, ht.size());
	}
	
	@Test
	public void iteratorNextTest() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		for(TableEntry<String, Integer> te : examMarks) {
			assertNotNull(te);
		}
		Set<String> set = new TreeSet<>();
		set.add("Ivana");
		set.add("Ante");
		set.add("Jasna");
		set.add("Kristina");
		checkContent(examMarks, set);
	}
	
	@Test
	public void emptyHashtableIteratorTest() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		Iterator<TableEntry<String, Integer>> it1 = examMarks.iterator();
		assertFalse(it1.hasNext());
		assertThrows(NoSuchElementException.class, () -> it1.next());
	}
	
	@Test
	public void iterateOverAModifiedHashtable() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		Iterator<TableEntry<String, Integer>> it1 = examMarks.iterator();
		
		try {
			while(it1.hasNext()) {
				it1.next();
				examMarks.remove("Jasna");
			}
			fail();
		} catch(ConcurrentModificationException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void removeIteratorTest() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		Iterator<TableEntry<String, Integer>> it1 = examMarks.iterator();
		while(it1.hasNext()) {
			TableEntry<String, Integer> e = it1.next();
			if( "Jasna".equals(e.getKey())) {
				it1.remove();
			}
		}
		
		Set<String> set = new TreeSet<>();
		set.add("Ivana");
		set.add("Ante");
		set.add("Kristina");
		checkContent(examMarks, set);
	}
	
	@Test
	public void removeTwoTimesInARow() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		Iterator<TableEntry<String, Integer>> it1 = examMarks.iterator();
		it1.next();
		it1.remove();
		assertThrows(IllegalStateException.class, () -> it1.remove());
	}
	
	@Test
	public void removeNothingTest() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		Iterator<TableEntry<String, Integer>> it1 = examMarks.iterator();
		assertThrows(IllegalStateException.class, () -> it1.remove());
	}
	
	@Test
	public void twoIteratorsTest() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Pero", 1);
		examMarks.put("Leonarda", 5);
		
		Iterator<TableEntry<String, Integer>> it1 = examMarks.iterator();
		Iterator<TableEntry<String, Integer>> it2 = examMarks.iterator();
		assertEquals(it1.next(), it2.next());
		it1.next();
		assertNotEquals(it1.next(), it2.next());
		it2.next();
		assertEquals(it1.next(), it2.next());
		it1.remove();
		assertThrows(ConcurrentModificationException.class, () -> it2.next());
	}
	
	/*
	 * Compares if all of the elements in the set are contained in the hashtable
	 * and given by the iterator of the hashtable.
	 */
	private void checkContent(SimpleHashtable<String, Integer> hashtable, Set<String> set) {
		Iterator<TableEntry<String, Integer>> it1 = hashtable.iterator();
		while(it1.hasNext()) {
			//the returned element should be in the set
			String s = it1.next().getKey();
			assertTrue(set.contains(s));
			set.remove(s);
		}
		assertEquals(0, set.size());
	}
	
}
