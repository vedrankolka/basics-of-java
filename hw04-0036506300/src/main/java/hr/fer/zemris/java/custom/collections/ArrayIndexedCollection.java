package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A resizable Array of Objects in which duplicates are allowed and <code>null</code> references are not allowed.
 * @author Vedran Kolka
 *
 */
public class ArrayIndexedCollection<T> implements List<T> {
	
	/**
	 * Counter of structure modifications of the collection.
	 */
	private long modificationCount;
	/**
	 * The default capacity that the Array is set to when the capacity is not specified.
	 */
	private static final int DEFAULT_CAPACITY = 16; 
	/**
	 * The number of elements currently stored in the collection.
	 */
	private int size;
	/**
	 * Array for storing elements in the collection.
	 */
	private T[] elements;
	
	private class ArrayIndexedCollectionGetter implements ElementsGetter<T>{

		/**
		 * modificationCount of the given <code>collection</code> at the moment of creating the getter
		 */
		private long savedModificationCount;
		/**
		 * index of the next element to return
		 */
		private int indexToGet;
		
		private ArrayIndexedCollectionGetter() {
			this.savedModificationCount = modificationCount;
		}
		
		@Override
		public boolean hasNextElement() {
			checkModificationCount();
			return indexToGet<size;
		}

		@Override
		public T getNextElement() {
			checkModificationCount();
			if(!hasNextElement()) {
				throw new NoSuchElementException("There are no more elements to return.");
			}
			return get(indexToGet++);
		}
		
		/**
		 * Checks if the collection structure has been modified since the creation of the getter.
		 * @throws ConcurrentModificationException if <code>collection</code> structure has been modified
		 */
		private void checkModificationCount() {
			if(savedModificationCount!=modificationCount) {
				throw new ConcurrentModificationException("collection structure was modified!");
			}
		}
		
	}
	
	/**
	 * Creates an ArrayIndexedCollection with the capacity of <code>initialCapacity</code>.
	 * @param initialCapacity
	 * @throws IllegalArgumentException if <code>initialCapacity</code> is less than 1
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity<1) {
			throw new IllegalArgumentException("InitialCapacity must be greater than 0. It was " + initialCapacity);
		}
		this.elements = (T[])new Object[initialCapacity];
		modificationCount++;
	}
	
	/**
	 * Creates an ArrayIndexedCollection with the capacity of <code>DEFAULT_CAPACITY</code> (16).
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}
	

	/**
	 * Creates an ArrayIndexedCollection of capacity <code>initialCapacity</code> and copies elements from
	 * <code>other</code> to <code>this</code>. If <code>initialCapacity</code> is less than the size of <code>other</code>
	 * the Array is created with capacity of the size of <code>other</code> instead.
	 * 
	 * @param other
	 * @param initialCapacity
	 * @throws IllegalArgumentException if <code>initialCapacity</code> is less than 1 and size of <code>other</code> is 0
	 * @throws NullPointerException if <code>other</code> is <code>null</code>
	 */
	public ArrayIndexedCollection(Collection<? extends T> other, int initialCapacity) {
		this(pickCapacity(other.size(),initialCapacity));
		addAll(Objects.requireNonNull(other));
		modificationCount++;
	}
	
	/**
	 * Returns the greater of <code>otherCapacity</code> and <code>initialCapacity</code>.
	 * @param otherCapacity
	 * @param initialCapacity
	 * @return greater of <code>otherCapacity</code> and <code>initialCapacity</code>
	 * @throws IllegalArgumentException if initialCapacity is less than 1
	 */
	private static int pickCapacity(int otherCapacity, int initialCapacity) {
		if(initialCapacity<1) {
			throw new IllegalArgumentException("initialCapacity must be greater than 1. It was " + initialCapacity);
		}
		return Math.max(otherCapacity, initialCapacity);
	}
	/**
	 * Creates an ArrayIndexedCollection of capacity <code>DEFAULT_CAPACITY</code>(16) and copies elements from
	 * <code>other</code> to <code>this</code>. If size of <code>other</code> is greater than 16 the Array is created
	 * with capacity of the size of <code>other</code> instead.
	 * @param other
	 * @throws NullPointerException if <code>other</code> is <code>null</code>
	 */
	public ArrayIndexedCollection(Collection<? extends T> other) {
		this(other, DEFAULT_CAPACITY);
	}
	
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * @throws NullPointerException if <code>value</code> is <code>null</code>
	 */
	@Override
	public void add(T value) {
		insert(value, size);
	}
	
	@Override
	public boolean contains(Object value) {
		return indexOf(value)!=-1;
	}
	
	@Override
	public boolean remove(Object value) {
		int indexToRemove = indexOf(value);
		if(indexToRemove==-1) {
			return false;
		}
		shiftLeft(indexToRemove);
		return true;
	}
	
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}
	
	/**
	 * @throws IndexOutOfBoundsException if index is less than 0 or greater than size-1
	 */
	@Override
	public T get(int index) {
		checkIndex(index);
		return elements[index];
	}
	
	@Override
	public void clear() {
		for(int i = 0 ; i<size ; ++i) {
			elements[i] = null;
		}
		size = 0;
		modificationCount++;
	}
	
	/**
	 * @throws NullPointerException if <code>value</code> is <code>null</code>
	 * @throws IndexOutOfBoundsException if <code>position</code>
	 * is less than 0 or greater than <code>size</code>
	 */
	@Override
	public void insert(T value, int position) {
		Objects.requireNonNull(value);
		shiftRight(position);
		elements[position] = value;
	}
	
	@Override
	public int indexOf(Object value) {
		if(value==null) {
			return -1;
		}
		for(int i = 0 ; i<size ; ++i) {
			if(value.equals(elements[i])) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * @throws IndexOutOfBoundsException if index is less than 0 or greater than size-1
	 */
	@Override
	public void remove(int index) {
		shiftLeft(index);
	}
	
	/**
	 * Checks if <code>index</code> is in the permitted range.
	 * @param index
	 * @throws IndexOutOfBoundsException
	 */
	private void checkIndex(int index) {
		if(index<0 || index>=size) {
			throw new IndexOutOfBoundsException("Size of array was " + size + " and index was " + index);
		}
	}
	
	/**
	 * Shifts elements left up to the element on index <code>index</code> so that it is overwritten. 
	 * @param index
	 * @throws IndexOutOfBoundsException
	 */
	private void shiftLeft(int index) {
		checkIndex(index);
		for(int i = index ; i<size-1 ; ++i) {
			elements[i] = elements[i+1];
		}
		elements[--size] = null;
		modificationCount++;
	}
	
	/**
	 * Shifts elements right from the <code>index</code> position so that the element on the <code>index</code> position
	 * is copied.
	 * @param index
	 * @throws IndexOutOfBoundsException if <code>index</code> is less than 0 or greater than <code>size</code>
	 */
	private void shiftRight(int index) {
		if(index<0 || index>size) {
			throw new IndexOutOfBoundsException("size was " + size + " and index was " + index);
		}
		if(size==elements.length) {
			elements = Arrays.copyOf(elements, 2*elements.length);
			modificationCount++;
		}
		for(int i = size-1 ; i>=index ; --i) {
			elements[i+1] = elements[i];
		}
		size++;
		modificationCount++;
	}
	
	/*
	 * Made for testing.
	 */
	protected int getCapacity() {
		return elements.length;
	}
	
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ArrayIndexedCollectionGetter();
	}
}
