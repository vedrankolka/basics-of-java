package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DictionaryTest {

	@Test
	public void isEmptyTest() {
		Dictionary<String, Integer> d = new Dictionary<>();
		assertTrue(d.isEmpty());
		d.put("Leonarda", 5);
		assertFalse(d.isEmpty());
	}
	
	@Test
	public void sizeTest() {
		Dictionary<String, Integer> d = new Dictionary<>();
		assertEquals(0, d.size());
		d.put("Leonarda", 5);
		assertEquals(1, d.size());
	}
	
	@Test
	public void clearTest() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put("Leonarda", 5);
		d.put("Vedran", 4);
		assertFalse(d.isEmpty());
		d.clear();
		assertTrue(d.isEmpty());
	}
	
	@Test
	public void putRegularEntryTest() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put("Leonarda", 5);
		assertEquals(1, d.size());
		assertEquals(5, d.get("Leonarda"));
	}
	
	@Test
	public void putNullEntryTest() {
		Dictionary<String, Integer> d = new Dictionary<>();
		assertThrows(NullPointerException.class, () -> d.put(null, 4));
	}
	
	@Test
	public void putEmptyStringTest() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put("", 5);
		assertFalse(d.isEmpty());
	}
	
	@Test
	public void putExistingEntryTest() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put("Leonarda", 5);
		assertEquals(1, d.size());
		d.put("Leonarda", 3);
		assertEquals(1, d.size());
	}
	
	@Test
	public void putAFewEntriesTest() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put("Adem", 2);
		d.put("Badem", 3);
		d.put("Kradem", 4);
		d.put("Padem", 1);
		d.put("Stradem", null);
		d.put("Badem", 5); //overwrites Badem, 3
		assertEquals(5, d.size());
		assertEquals(5, d.get("Badem"));
		assertEquals(1, d.get("Padem"));
		assertNull(d.get("Stradem"));
	}
	
	@Test
	public void getNormalEntryTest() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put("Adem", 2);
		assertEquals(2, d.get("Adem"));
	}
	
	@Test
	public void getNullTest() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put("Adem", 2);
		d.put("Badem", 3);
		d.put("Kradem", 4);
		assertNull(d.get(null));
	}
	
	@Test
	public void getEmptyStringTest() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put("", 4);
		assertEquals(4, d.get(""));
	}
	
	@Test
	public void getNonExistingEntryTest() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put("Adem", 2);
		d.put("Badem", 3);
		d.put("Kradem", 4);
		assertNull(d.get("Padem"));
	}
	
	@Test
	public void getAFewEntriesTest() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put("Adem", 2);
		d.put("Badem", 3);
		d.put("Kradem", 4);
		assertEquals(3, d.get("Badem"));
		assertEquals(4, d.get("Kradem"));
		assertEquals(3, d.get("Badem"));
		assertEquals(2, d.get("Adem"));
	}
	
}
