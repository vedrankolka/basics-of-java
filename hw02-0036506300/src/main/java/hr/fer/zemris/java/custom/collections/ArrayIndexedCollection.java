package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Objects;

/**
 * A resizable Array of Objects in which duplicates are allowed and <code>null</code> references are not allowed.
 * @author Vedran Kolka
 *
 */
public class ArrayIndexedCollection extends Collection {

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
	private Object[] elements;
	
	/**
	 * Creates an ArrayIndexedCollection with the capacity of <code>initialCapacity</code>.
	 * @param initialCapacity
	 * @throws IllegalArgumentException if <code>initialCapacity</code> is less than 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity<1) {
			throw new IllegalArgumentException("InitialCapacity must be greater than 0. It was " + initialCapacity);
		}
		this.elements = new Object[initialCapacity];
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
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		this(pickCapacity(other.size(),initialCapacity));
		addAll(Objects.requireNonNull(other));
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
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_CAPACITY);
	}
	
	@Override
	public boolean isEmpty() {
		return size==0;
	}
	
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * @throws NullPointerException if <code>value</code> is <code>null</code>
	 */
	@Override
	public void add(Object value) {
		Objects.requireNonNull(value);
		if(size==elements.length) {
			elements = Arrays.copyOf(elements, 2*elements.length);
		}
		elements[size++] = value;
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
	
	@Override
	public void forEach(Processor processor) {
		for(int i = 0 ; i<size ; ++i) {
			processor.process(elements[i]);
		}
	}
	
	/**
	 * Returns the Object at the given <code>index</code>.
	 * @param index
	 * @return Object at index <code>index</code>
	 * @throws IndexOutOfBoundsException if <code>index</code> is less than 0 or greater than size-1
	 */
	public Object get(int index) {
		checkIndex(index);
		return elements[index];
	}
	
	@Override
	public void clear() {
		for(int i = 0 ; i<size ; ++i) {
			elements[i] = null;
		}
		size = 0;
	}
	
	/**
	 * Inserts the given <code>value</code> at the given <code>position</code>.
	 * @param value
	 * @param position
	 * @throws NullPointerException if <code>value</code> is <code>null</code>
	 * @throws IndexOutOfBoundsException if <code>position</code> is less than 0 or greater than <code>size</code>
	 */
	public void insert(Object value, int position) {
		Objects.requireNonNull(value);
		shiftRight(position);
		elements[position] = value;
	}
	
	/**
	 * Searches the Collection and return the index of the first occurrence of the given <code>value</code>
	 * or -1 if <code>value</code> is not found.
	 * @param value
	 * @return index of the found Object, -1 if it is not found
	 */
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
	 * Removes the element on index <code>index</code>.
	 * @param index
	 * @throws IndexOutOfBoundsException if index is less than 0 or greater than size-1
	 */
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
		}
		for(int i = size-1 ; i>=index ; --i) {
			elements[i+1] = elements[i];
		}
		size++;
	}
	
	/*
	 * Made for testing.
	 */
	protected int getCapacity() {
		return elements.length;
	}
}
