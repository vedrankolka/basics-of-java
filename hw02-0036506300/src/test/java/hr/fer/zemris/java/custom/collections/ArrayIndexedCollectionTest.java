package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {
	
	public static ArrayIndexedCollection testCollection;
	
	@BeforeAll
	public static void initializeTestCollection() {
		testCollection = new ArrayIndexedCollection(4);
		testCollection.add("Leonarda");
		testCollection.add(Integer.valueOf(6));
		testCollection.add("Štefica");
		testCollection.add((Object)Double.valueOf(3.14));
	}

	//constructor tests
	
	@Test
	public void defaultConstructorTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		assertEquals(16, collection.getCapacity());
		assertEquals(0, collection.size());
	}
	
	@Test
	public void constructorWithValidInitialCapacityTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(1);
		assertEquals(1, collection.getCapacity());
		assertEquals(0, collection.size());
	}
	
	@Test
	public void constructorWithInvalidCapacityTest() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
	}
	
	/**
	 * Tests the constructor when <code>initialCapacity</code> is greater than <code>size</code> of <code>other</code>.
	 */
	@Test
	public void constructorWithValidOtherCollectionAndInitialCapacityTest1() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(testCollection, 9);
		assertEquals(testCollection.size(), collection.size());
		assertEquals(9, collection.getCapacity());
		assertTrue(collection.contains("Leonarda"));
		assertFalse(collection.contains(null));
	}
	
	/**
	 * Tests the constructor when <code>initialCapacity</code> is less than <code>size</code> of <code>other</code>.
	 */
	@Test
	public void constructorWithValidOtherCollectionAndInitialCapacityTest2() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(testCollection, 3);
		assertEquals(testCollection.size(), collection.size());
		assertEquals(testCollection.getCapacity(), collection.getCapacity());
		assertTrue(collection.contains("Leonarda"));
		assertFalse(collection.contains(null));
	}
	
	
	@Test
	public void constructorWithValidOtherCollectionTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(testCollection);
		assertEquals(testCollection.size(), collection.size());
		assertEquals(16, collection.getCapacity());
		assertTrue(collection.contains("Leonarda"));
		assertFalse(collection.contains(null));
	}
	
	@Test
	public void constructorWithNullAsOtherCollectionTest() {
		assertThrows(NullPointerException.class,
				() -> new ArrayIndexedCollection(null) );
	}
	
	//method tests

	@Test
	public void addNonNullObjectTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(testCollection, 4);
		assertEquals(4, collection.size());
		assertEquals(4, collection.getCapacity());
		collection.add("Lacazette");
		assertEquals(5, collection.size());
		assertEquals(8, collection.getCapacity());
		assertTrue(collection.contains("Lacazette"));
	}
	
	@Test
	public void addNullTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(testCollection, 4);
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
		ArrayIndexedCollection collection = new ArrayIndexedCollection(testCollection);
		collection.clear();
		assertEquals(0, collection.size());
		assertEquals(16, collection.getCapacity());
		assertFalse(collection.contains("Leonarda"));
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(0));
	}
	
	@Test
	public void insertAtValidIndexTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(testCollection, 4);
		collection.insert("Ramsey", 2);
		assertEquals("Ramsey", collection.get(2));
		assertEquals(5, collection.size());
		assertEquals(8, collection.getCapacity());
	}
	
	@Test
	public void insertAtFirstValidIndexTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(testCollection, 4);
		collection.insert("Ramsey", 0);
		assertEquals("Ramsey", collection.get(0));
		assertEquals(5, collection.size());
		assertEquals(8, collection.getCapacity());
	}
	
	@Test
	public void insertAtLastValidIndexTest() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(testCollection, 4);
		collection.insert("Ramsey", 4);
		assertEquals("Ramsey", collection.get(4));
		assertEquals(5, collection.size());
		assertEquals(8, collection.getCapacity());
	}
	
	@Test
	public void insertAtInvalidIndexTest1() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(testCollection, 4);
		assertThrows(IndexOutOfBoundsException.class, () -> collection.insert("Ramsey", -1));
	}
	
	@Test
	public void insertAtInvalidIndexTest2() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(testCollection, 4);
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
		ArrayIndexedCollection collection = new ArrayIndexedCollection(testCollection);
		collection.remove(1);
		assertFalse(collection.contains(Integer.valueOf(6)));
		assertEquals(3, collection.size());
	}
	
	@Test
	public void removeAtInvalidIndex1() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(testCollection);
		assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(-1));
	}
	
	@Test
	public void removeAtInvalidIndex2() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(testCollection);
		assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(-1));
	}
	
}
