package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A linked list of Objects in which duplicates are allowed and <code>null</code> references are not allowed.
 * @author Vedran Kolka
 *
 */
public class LinkedListIndexedCollection<T> implements List<T> {

	/**
	 * A class which represents a node in the list
	 * @author Vedran Kolka
	 *
	 */
	private static class ListNode<E> {
		private E value;
		private ListNode<E> previous;
		private ListNode<E> next;
		
		private ListNode() {
			
		}
		
		/**
		 * @param value
		 * @throws NullPointerException if <code>value</code> is <code>null</code>
		 */
		private ListNode(E value) {
			this.value = Objects.requireNonNull(value);
		}
	}
	
	private class LinkedListIndexedCollectionGetter implements ElementsGetter<T> {
		/**
		 * Reference to the node which <b><code>value</code></b> is to be returned.
		 */
		private ListNode<T> nodeToReturn;
		
		/**
		 * modificationCount of the given <code>collection</code> at the moment of creating the getter
		 */
		private long savedModificationCount;
		
		private LinkedListIndexedCollectionGetter() {
			this.nodeToReturn = first;
			this.savedModificationCount = modificationCount;
		}

		@Override
		public boolean hasNextElement() {
			checkModificationCount();
			return nodeToReturn!=null;
		}

		@Override
		public T getNextElement() {
			checkModificationCount();
			if(!hasNextElement()) {
				throw new NoSuchElementException("There are no more elements to return.");
			}
			T t = nodeToReturn.value;
			nodeToReturn = nodeToReturn.next;
			return t;
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
	 * Counter of structure modifications of the collection.
	 */
	private long modificationCount;
	
	/**
	 * The number of elements currently stored in the collection.
	 */
	private int size;
	/**
	 * Reference to the first element in the collection (index 0).
	 */
	private ListNode<T> first;
	/**
	 * Reference to the last element in the collection (index size-1).
	 */
	private ListNode<T> last;
	
	/**
	 * Default constructor. Sets all fields to default values.
	 */
	public LinkedListIndexedCollection() {
		
	}
	
	/**
	 * Creates a list and adds all the elements of <code>other</code> to <code>this</code>.
	 * @param other
	 * @throws NullPointerException if <code>other</code> is <code>null</code>
	 */
	public LinkedListIndexedCollection(Collection<? extends T> other) {
		this.addAll(Objects.requireNonNull(other));
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
		
		ListNode<T> newNode = new ListNode<>(value);
		
		if(isEmpty()) {
			linkFirst(newNode);
		}
		
		linkLast(newNode);
		size++;
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
		remove(indexToRemove);
		return true;
	}
	
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		int i = 0;
		ListNode<T> node = first;
		while(node!=null) {
			array[i++] = node.value;
			node = node.next;
		}
		return array;
	}
	
	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
		modificationCount++;
	}

	@Override
	public T get(int index) {
		return getListNode(index).value;
	}
	
	/**
	 * @throws NullPointerException if <code>value</code> is <code>null</code>
	 * @throws IndexOutOfBoundsException if <code>position</code> is less than 0 or greater than <code>size</code>
	 */
	@Override
	public void insert(T value, int position) {
		
		if(position<0 || position>size) {
			throw new IndexOutOfBoundsException("size of list was " + size + " and index was " + position);
		}
		
		ListNode<T> newNode = new ListNode<>(value);

		if(position==0) {
			linkFirst(newNode);
		} else if(position==size) {
			linkLast(newNode);
		} else {
			ListNode<T> previousAtIndex = getListNode(position);
			linkBefore(newNode, previousAtIndex);
		}
		size++;
		
	}
	
	@Override
	public int indexOf(Object value) {
		
		if(value==null) {
			return -1;
		}
		
		int i = 0;
		ListNode<T> node = first;
		while(node!=null) {
			if(node.value.equals(value)) {
				return i;
			}
			i++;
			node = node.next;
		}
		return -1;
		
	}
	
	/**
	 * @throws IndexOutOfBoundsException if index is less than 0 or greater than size-1
	 */
	public void remove(int index) {
		ListNode<T> oldNode = getListNode(index);
		unlink(oldNode);
		size--;
	}
	
	/**
	 * Returns the ListNode at index <code>index</code>.
	 * @param index
	 * @return ListNode
	 * @throws IndexOutOfBoundsException if <code>index</code> is less than 0 or greater than size-1
	 */
	private ListNode<T> getListNode(int index) {
		checkIndex(index);
		ListNode<T> node;
		if(index < size/2) {
			node = first;
			for(int i = 0 ; i<index ; ++i) {
				node = node.next;
			}
		} else {
			node = last;
			for(int i = size-1 ; i>index ; --i) {
				node = node.previous;
			}
		}
		return node;
	}
	
	/**
	 * Links the node at the beginning of the list.
	 * @param node
	 */
	private void linkFirst(ListNode<T> node) {
		node.previous = null;
		node.next = first;
		if(first!=null) {
			node.next = first;
		}
		first = node;
		modificationCount++;
	}
	
	/**
	 * Links the node at the end of the list.
	 * @param node
	 */
	private void linkLast(ListNode<T> node) {
		node.next = null;
		node.previous = last;
		if(last!=null) {
			last.next = node;
		}
		last = node;
		modificationCount++;
	}
	
	/**
	 * Links the leftNode (new to the list) before the rightNode (already in the list).
	 * @param leftNode
	 * @param rightNode
	 * @throws NullPointerException if <code>rightNode</code> is <code>null</code>
	 */
	private void linkBefore(ListNode<T> leftNode, ListNode<T> rightNode) {
		Objects.requireNonNull(rightNode);
		leftNode.previous = rightNode.previous;
		rightNode.previous.next = leftNode;
		rightNode.previous = leftNode;
		leftNode.next = rightNode;
		modificationCount++;
	}
	
	/**
	 * Unlinks the <code>oldNode</code> from the list, making it eligible for the garbage collector.
	 * @param oldNode
	 */
	private void unlink(ListNode<T> oldNode) {
		Objects.requireNonNull(oldNode);
		oldNode.next.previous = oldNode.previous;
		oldNode.previous.next = oldNode.next;
		modificationCount++;
	}
	
	/**
	 * Checks if <code>index</code> is in the permitted range.
	 * @param index
	 * @throws IndexOutOfBoundsException
	 */
	private void checkIndex(int index) {
		if(index<0 || index>=size) {
			throw new IndexOutOfBoundsException("Size of list was " + size + " and index was " + index);
		}
	}

	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new LinkedListIndexedCollectionGetter();
	}
	
}
