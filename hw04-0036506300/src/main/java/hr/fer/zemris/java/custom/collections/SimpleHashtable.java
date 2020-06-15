package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * An implementation of a hashtable that uses linked lists for solving the collision problem.
 * @author Vedran Kolka
 *
 * @param <K> - key of hashing, cannot be <code>null</code>
 * @param <V> - value associated with the key
 */
public class SimpleHashtable <K, V>  implements Iterable<SimpleHashtable.TableEntry<K,V>> {

	private static final int DEFAULT_TABLE_CAPACITY = 16;
	/**
	 * The coefficient which the size/capacity ratio should not go over
	 */
	private static final double SEVENTY_FIVE_PERCENT = 0.75;
	private long modificationCount;
	/**
	 * Array of references to table entries.
	 */
	private TableEntry<K, V>[] table;
	/**
	 * Number of entries stored in the hashtable.
	 */
	private int size;
	
	/**
	 * An entry of a hashtable. Holds a key paired with its value.
	 * @author Vedran Kolka
	 *
	 * @param <K> key - cannot be <code>null</code>
	 * @param <V> value
	 */
	public static class TableEntry<K, V>{
		
		private K key;
		private V value;
		/**
		 * Reference to the next table entry in the slot of this table entry.
		 */
		private TableEntry<K, V> next;
		
		public TableEntry(K key, V value) {
			Objects.requireNonNull(key);
			this.key = key;
			this.value = value;
		}
		
		public K getKey() {
			return key;
		}
		
		public V getValue() {
			return value;
		}
		
		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return key + "=" + value;
		}
	}
	
	private class SimpleHashtableIterator implements Iterator<TableEntry<K, V>>{

		/**
		 * The modificationCount that the iterator saves when it is created.
		 */
		private long savedModificationCount;
		/**
		 * The next entry to be returned.
		 */
		private TableEntry<K, V> currentEntry;
		/**
		 * The last entry that was returned.
		 */
		private TableEntry<K, V> lastEntry;
		/**
		 * Index of the current slot in the table.
		 */
		private int currentIndex;
		
		private SimpleHashtableIterator() {
			savedModificationCount = modificationCount;
			currentEntry = table[0];
		}
		
		@Override
		public boolean hasNext() {
			checkModificationCount();
			//if the current slot has more entries return true
			if(currentEntry!=null) {
				return true;
			}
			//else look for entries in the next slots
			currentIndex++;
			while(currentIndex<table.length && table[currentIndex]==null) {
				currentIndex++;
			}
			//if the while loop ended because the end of the table was reached
			//there are no more elements
			if(currentIndex>=table.length) {
				return false;
			}
			//else set the currentEntry to the next one to be returned
			currentEntry = table[currentIndex];
			return true;
		}

		@Override
		public TableEntry<K, V> next() {
			checkModificationCount();
			if(!hasNext()) {
				throw new NoSuchElementException("No more entries to get.");
			}
			lastEntry = currentEntry;
			currentEntry = currentEntry.next;
			return lastEntry;
		}
		
		public void remove() {
			checkModificationCount();
			if(lastEntry==null) {
				throw new IllegalStateException("There is nothing to remove.");
			}
			SimpleHashtable.this.remove(lastEntry.key);
			lastEntry = null;
			savedModificationCount = modificationCount;
		}
		
		/**
		 * Checks if the hashtable has been modified from outside the iterator.
		 * @throws ConcurrentModificationException if it has
		 */
		private void checkModificationCount() {
			if(savedModificationCount!=modificationCount) {
				throw new ConcurrentModificationException("The hashtable has been modified illeaglly!");
			}
		}
		
	}
	
	/**
	 * Creates a hashtable of size equal to the first greater potency of 2
	 * than the given <code>size</code> (e.g. for given <code>size</code> 30,
	 * a table of size 32 is created).
	 * @param capacity of the hashtable to create
	 * @throws IllegalArgumentException if size is less than 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		capacity = determineSize(capacity);
		table = new TableEntry[capacity];
	}
	
	/**
	 * Creates a hashtable of capacity 16.
	 * @param capacity of the hashtable to create
	 */
	public SimpleHashtable() {
		this(DEFAULT_TABLE_CAPACITY);
	}
	
	/**
	 * Determines the next potency of 2 greater or equal to the given <code>capacity</code>.
	 * @param capacity
	 * @return capacity to which the table should be allocated
	 * @throws IllegalArgumentException if capacity is less than 1
	 */
	private int determineSize(int capacity) {
		if(capacity<1) {
			throw new IllegalArgumentException("Size must be greater than 0. It was " + capacity);
		}
		int newSize = 1;
		while(newSize<capacity) {
			newSize *= 2;
		}
		return newSize;
	}
	
	/**
	 * Returns the number of table entries stored in the hashtable.
	 * @return number of entries in the hashtable
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Checks if the hashtable is empty.
	 * @return <code>true</code> if the hashtable has no entries stored,
	 * <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return size==0;
	}
	
	/**
	 * Checks if the hashtable contains an entry with the given <code>key</code>.
	 * @param key to look for
	 * @return <code>true</code> if it does, <code>false</code> otherwise
	 */
	public boolean containsKey(K key) {
		return getEntry(key)!=null;
	}
	
	/**
	 * Checks if any of the entries contains the given <code>value</code>.
	 * @param value to look for
	 * @return <code>true</code> if it does, <code>false</code> otherwise
	 */
	public boolean containsValue(V value) {
		
		for(int i = 0 ; i<table.length ; ++i) {
			//the beginning of each slot
			TableEntry<K, V> entry = table[i];
			//search the whole slot
			while(entry!=null) {
				if(Objects.equals(entry.value, value)) {
					return true;
				}
				entry = entry.next;
			}
		}
		return false;
	}
	
	/**
	 * Searches the slot where the entry with key <code>key</code> should be
	 * and returns it if it is found. Otherwise returns <code>null</code>.
	 * @param key to look for
	 * @return entry with key <code>key</code> if it is in the table, <code>null</code> otherwise
	 */
	private TableEntry<K, V> getEntry(K key) {

		if(key==null) {
			return null;
		}
		//the beginning of the slot where the entry should be
		TableEntry<K, V> entry = table[getAdress(key)];
		//search the whole slot
		while(entry!=null) {
			if(entry.key.equals(key)) {
				return entry;
			}
			entry = entry.next;
		}
		return null;
		
	}
	
//	/**
//	 * Searches the slot where the entry with key <code>key</code> should be
//	 * and returns a reference to the previous entry that hold the reference to
//	 * the entry with the given <code>key</code>. Cannot search if the slot is empty and
//	 * cannot search the first entry (because it cannot return the reference to the previous of
//	 * the first) .
//	 * This method is used internally for better performances.
//	 * @param key to look for
//	 * @return reference to the previous entry of the one it is looking for,
//	 * or the <b>reference to the last entry in the list if the <code>key</code> is not
//	 * contained in the slot<b>.
//	 * @throws NullPointerException if key is <code>null</code> or if the slot is empty
//	 */
//	private TableEntry<K, V> getPreviousEntry(K key) {
//		Objects.requireNonNull(key);
//		TableEntry<K, V> previousEntry = table[key.hashCode()%table.length];
//		while(previousEntry.next != null) {
//			if(previousEntry.next.key.equals(key)) {
//				return previousEntry;
//			}
//			previousEntry = previousEntry.next;
//		}
//		return previousEntry;
//	}
	
	/**
	 * Adds an entry with <code>key</code> and <code>value</code> to the table if
	 * the table does not contain the given <code>key</code>. If it does, then the value
	 * is updated to the given <code>value</code>.
	 * @param key
	 * @param value
	 * @throws NullPointerException if <code>key</code> is <code>null</code>
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key);
		TableEntry<K, V> entry = getEntry(key);
		modificationCount++;
		if(entry!=null) {
			//if the table contains the given key, update the value
			entry.value = value;
			return;
		}
		//if it does not, add a new table entry
		TableEntry<K, V> newEntry = new TableEntry<>(key, value);
		TableEntry<K, V> currentEntry = table[getAdress(key)];
			
		if(currentEntry==null) {
			//if the slot is empty, add the new entry to the beginning
			table[getAdress(key)] = newEntry;
		} else {
			//else go to the last entry in the list and add the newEntry there
			while(currentEntry.next!=null) {
				currentEntry = currentEntry.next;
			}
			currentEntry.next = newEntry;
		}
		size++;
		checkCapacity();
	}
	
	/**
	 * Returns the value paired with the given <code>key</code> if it is contained in
	 * the table, <code>null</code> if it is not.
	 * @param key
	 * @return value paired with the given<code>key</code> if it is in the table,
	 * <code>null</code> otherwise
	 */
	public V get(K key) {
		TableEntry<K, V> entry = getEntry(key);
		if(entry==null) {
			return null;
		}
		return entry.value;
	}
	
	/**
	 * Removes the entry with the given <code>key</code> if it is in the table.
	 * @param key of entry to remove
	 */
	public void remove(K key) {
		
		if(key==null) {
			return;
		}
		TableEntry<K, V> entry = table[getAdress(key)];
		//if the slot is empty, return
		if(entry==null) {
			return;
		}
		//if the entry to remove is the first one, remove it
		if(entry.key.equals(key)) {
			table[getAdress(key)] = entry.next;
			modificationCount++;
			size--;
			return;
		}
		//else look for it in the list
		while(entry.next!=null) {
			//if the next entry has the key to remove, link this entry to the one after
			//the next entry
			if(entry.next.key.equals(key)) {
				entry.next = entry.next.next;
				modificationCount++;
				size--;
				return;
			}
			entry = entry.next;
		}
		
	}
	
	/**
	 * Removes all entries from the hashtable.
	 */
	public void clear() {
		modificationCount++;
		for(int i = 0 ; i<table.length ; ++i) {
			table[i] = null;
		}
		size = 0;
	}
	
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new SimpleHashtableIterator();
	}
	
	/**
	 * Returns the index of the slot where the key should be.
	 * @param key
	 * @return index of the slot in the table
	 * @throws NullPointerException if key is <code>null</code>
	 */
	private int getAdress(K key) {
		return Math.abs(key.hashCode()%table.length);
	}
	
	/**
	 * If the table is filled more than 75% it is reallocated and filled again
	 * so that the complexity of the operations stays O(1) .
	 */
	private void checkCapacity() {
		if(size<table.length*SEVENTY_FIVE_PERCENT) {
			return;
		}
		modificationCount++;
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] newTable = new TableEntry[table.length*2];
		TableEntry<K, V>[] oldTable = table;
		table = newTable;
		size = 0;
		for(TableEntry<K, V> te : oldTable) {
			while(te!=null) {
				put(te.key, te.value);
				te = te.next;
			}
		}
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for(int i = 0 ; i<table.length ; ++i) {
			TableEntry<K, V> entry = table[i];
			while(entry!=null) {
				sb.append(entry.toString()).append(' ');
				entry = entry.next;
			}
		}
		sb.append(']');
		return sb.toString();
	}
	
	/**
	 * Only for testing.
	 * @return the capacity of the table
	 */
	protected int getCapacity() {
		return table.length;
	}
	
}
