package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {

	public static LinkedListIndexedCollection testCollection;
	
	@BeforeAll
	public static void initializeTestCollection() {
		testCollection = new LinkedListIndexedCollection();
		testCollection.add("Leonarda");
		testCollection.add(Integer.valueOf(6));
		testCollection.add("Štefica");
		testCollection.add((Object)Double.valueOf(3.14));
	}

	//constructor tests
	
	@Test
	public void defaultConstructorTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		assertEquals(0, collection.size());
	}
	
	@Test
	public void constructorWithValidOtherCollectionTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection(testCollection);
		assertEquals(testCollection.size(), collection.size());
		assertTrue(collection.contains("Leonarda"));
		assertFalse(collection.contains(null));
	}
	
	@Test
	public void constructorWithNullAsOtherCollectionTest() {
		assertThrows(NullPointerException.class,
				() -> new LinkedListIndexedCollection(null) );
	}
	
	//method tests

	@Test
	public void addNonNullObjectTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection(testCollection);
		assertEquals(4, collection.size());
		collection.add("Lacazette");
		assertEquals(5, collection.size());
		assertTrue(collection.contains("Lacazette"));
	}
	
	@Test
	public void addNullTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection(testCollection);
		assertEquals(4, collection.size());
		assertThrows(NullPointerException.class, () -> collection.add(null));
		assertEquals(4, collection.size());
	}
	
	@Test
	public void getByValidIndexTest() {
		assertEquals("Leonarda", testCollection.get(0));
		assertEquals(3.14, testCollection.get(3));
	}
	
	@Test
	public void getByInvalidIndexTest() {
		assertThrows(IndexOutOfBoundsException.class, () -> testCollection.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> testCollection.get(4));
	}
	
	@Test
	public void clearTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection(testCollection);
		collection.clear();
		assertEquals(0, collection.size());
		assertFalse(collection.contains("Leonarda"));
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(0));
	}
	
	@Test
	public void insertAtValidIndexTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection(testCollection);
		collection.insert("Ramsey", 2);
		assertEquals("Ramsey", collection.get(2));
		assertEquals(5, collection.size());
	}
	
	@Test
	public void insertAtFirstValidIndexTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection(testCollection);
		collection.insert("Ramsey", 0);
		assertEquals("Ramsey", collection.get(0));
		assertEquals(5, collection.size());
	}
	
	@Test
	public void insertAtLastValidIndexTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection(testCollection);
		collection.insert("Ramsey", 4);
		assertEquals("Ramsey", collection.get(4));
		assertEquals(5, collection.size());
	}
	
	@Test
	public void insertAtInvalidIndexTest1() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection(testCollection);
		assertThrows(IndexOutOfBoundsException.class, () -> collection.insert("Ramsey", -1));
	}
	
	@Test
	public void insertAtInvalidIndexTest2() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection(testCollection);
		assertThrows(IndexOutOfBoundsException.class, () -> collection.insert("Ramsey", 5));
	}
	
	@Test
	public void indexOfExistingValueTest() {
		assertEquals(0, testCollection.indexOf("Leonarda"));
	}
	
	@Test
	public void indexOfNonExistingValueTest() {
		assertEquals(-1, testCollection.indexOf("Petar"));
	}
	
	@Test
	public void indexOfNullTest() {
		assertEquals(-1, testCollection.indexOf(null));
	}
	
	@Test
	public void removeAtValidIndexTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection(testCollection);
		collection.remove(1);
		assertFalse(collection.contains(Integer.valueOf(6)));
		assertEquals(3, collection.size());
	}
	
	@Test
	public void removeAtInvalidIndex1() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection(testCollection);
		assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(-1));
	}
	
	@Test
	public void removeAtInvalidIndex2() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection(testCollection);
		assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(-1));
	}
}
	

