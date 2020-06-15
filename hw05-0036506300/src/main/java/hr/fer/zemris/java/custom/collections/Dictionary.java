package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * An implementation of a simple dictionary that holds keys(of type K) paired with their values
 * (of type V).
 * Key cannot be <code>null</code>, value can be <code>null</code>.
 * @author Vedran Kolka
 *
 */
public class Dictionary<K, V> {

	/**
	 * A key paired with a value.
	 * Represents an entry in the dictionary.
	 * @author Vedran Kolka
	 *
	 * @param <K>
	 * @param <V>
	 */
	private static class DictionaryEntry<K, V>{
		private K key;
		private V value;
		
		/**
		 * Creates an entry with the given <code>key</code> and <code>value</code>.
		 * @param key
		 * @param value
		 * @throws NullPointerException if <code>key</code> is <code>null</code>
		 */
		private DictionaryEntry(K key, V value) {
			Objects.requireNonNull(key);
			this.key = key;
			this.value = value;
		}

		@Override
		public int hashCode() {
			return Objects.hash(key);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof DictionaryEntry)) {
				return false;
			}
			@SuppressWarnings("unchecked")
			DictionaryEntry<K, V> other = (DictionaryEntry<K, V>) obj;
			return Objects.equals(key, other.key);
		}
		
		
	}
	
	/**
	 * An ArrayIndexedCollection that the dictionary uses for entry storage.
	 */
	private ArrayIndexedCollection<DictionaryEntry<K, V>> dictionary;
	
	public Dictionary() {
		dictionary = new ArrayIndexedCollection<>();
	}
	
	/**
	 * Checks if the dictionary is empty.
	 * @return true if it is, false otherwise
	 */
	public boolean isEmpty() {
		return dictionary.isEmpty();
	}
	
	/**
	 * Returns the number of entries stored in the dictionary
	 * @return size of dictionary
	 */
	public int size() {
		return dictionary.size();
	}
	
	/**
	 * Removes all entries from the dictionary.
	 */
	public void clear() {
		dictionary.clear();
	}
	
	/**
	 * Adds the given <code>key</code> paired with the given <code>value</code>
	 * if the key is not contained in the dictionary. If it is, then the <code>value</code>
	 * is updated to the given <code>value</code>. 
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {
		DictionaryEntry<K, V> newEntry = new DictionaryEntry<K, V>(key, value);
		if(dictionary.contains(newEntry)) {
			int index = dictionary.indexOf(newEntry);
			dictionary.get(index).value = value;
		} else {
			dictionary.add(newEntry);
		}
	}
	
	/**
	 * Returns the value paired with the given <code>key</code>.
	 * @param key
	 * @return value paired with the given <code>key</code>, or <code>null</code>
	 * if the key is not contained in the dictionary
	 */
	public V get(K key) {
		if(key==null) {
			return null;
		}
		ElementsGetter<DictionaryEntry<K, V>> getter = dictionary.createElementsGetter();
		while(getter.hasNextElement()) {
			DictionaryEntry<K, V> entry = getter.getNextElement();
			if(entry.key.equals(key)) {
				return entry.value;
			}
		}
		
		return null;
	}
	
}
