package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A linked list of Objects in which duplicates are allowed and <code>null</code> references are not allowed.
 * @author Vedran Kolka
 *
 */
public class LinkedListIndexedCollection implements List {

	/**
	 * A class which represents a node in the list
	 * @author Vedran Kolka
	 *
	 */
	private static class ListNode {
		private Object value;
		private ListNode previous;
		private ListNode next;
		
		private ListNode() {
			
		}
		
		/**
		 * @param value
		 * @throws NullPointerException if <code>value</code> is <code>null</code>
		 */
		private ListNode(Object value) {
			this.value = Objects.requireNonNull(value);
		}
	}
	
	private static class LinkedListIndexedCollectionGetter implements ElementsGetter {
		/**
		 * Reference to the node which <b><code>value</code></b> is to be returned.
		 */
		private ListNode nodeToReturn;
		
		/**
		 * modificationCount of the given <code>collection</code> at the moment of creating the getter
		 */
		private long savedModificationCount;
		/**
		 * collection whose elements are being returned
		 */
		private LinkedListIndexedCollection collection;
		
		private LinkedListIndexedCollectionGetter(LinkedListIndexedCollection collection) {
			this.collection = collection;
			this.nodeToReturn = collection.first;
			this.savedModificationCount = collection.modificationCount;
		}

		@Override
		public boolean hasNextElement() {
			checkModificationCount();
			return nodeToReturn!=null;
		}

		@Override
		public Object getNextElement() {
			checkModificationCount();
			if(!hasNextElement()) {
				throw new NoSuchElementException("There are no more elements to return.");
			}
			Object o = nodeToReturn.value;
			nodeToReturn = nodeToReturn.next;
			return o;
		}
		
		/**
		 * Checks if the collection structure has been modified since the creation of the getter.
		 * @throws ConcurrentModificationException if <code>collection</code> structure has been modified
		 */
		private void checkModificationCount() {
			if(savedModificationCount!=collection.modificationCount) {
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
	private ListNode first;
	/**
	 * Reference to the last element in the collection (index size-1).
	 */
	private ListNode last;
	
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
	public LinkedListIndexedCollection(Collection other) {
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
	public void add(Object value) {
		
		ListNode newNode = new ListNode(value);
		
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
		ListNode node = first;
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
	/**
	 * Returns the Object that is stored in the list at position <code>index</code>.
	 * @param index
	 * @return Object at index <code>index</code>
	 * @throws IndexOutOfBoundsException
	 */
	public Object get(int index) {
		return getListNode(index).value;
	}
	
	/**
	 * Inserts the given <code>value</code> at the given <code>position</code> which shifts elements after the
	 * <code>position</code> to the right by one.
	 * @param value
	 * @param position
	 * @throws NullPointerException if <code>value</code> is <code>null</code>
	 * @throws IndexOutOfBoundsException if <code>position</code> is less than 0 or greater than <code>size</code>
	 */
	public void insert(Object value, int position) {
		
		if(position<0 || position>size) {
			throw new IndexOutOfBoundsException("size of list was " + size + " and index was " + position);
		}
		
		ListNode newNode = new ListNode(value);

		if(position==0) {
			linkFirst(newNode);
		} else if(position==size) {
			linkLast(newNode);
		} else {
			ListNode previousAtIndex = getListNode(position);
			linkBefore(newNode, previousAtIndex);
		}
		size++;
		
	}
	
	/**
	 * Searches the list and returns the index of the first occurrence of the given <code>value</code>
	 * as determined by the <code>equals</code> method. If <code>value</code> is not found, it returns -1.
	 * @param value
	 * @return index of <code>value</code>, -1 if it is not found
	 */
	public int indexOf(Object value) {
		
		if(value==null) {
			return -1;
		}
		
		int i = 0;
		ListNode node = first;
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
	 * Removes the element at index <code>index</code>.
	 * @param index
	 * @throws IndexOutOfBoundsException if <code>index</code> is less than 0 or greater than <code>size</code>
	 */
	public void remove(int index) {
		ListNode oldNode = getListNode(index);
		unlink(oldNode);
		size--;
	}
	
	/**
	 * Returns the ListNode at index <code>index</code>.
	 * @param index
	 * @return ListNode
	 * @throws IndexOutOfBoundsException if <code>index</code> is less than 0 or greater than size-1
	 */
	private ListNode getListNode(int index) {
		checkIndex(index);
		ListNode node;
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
	private void linkFirst(ListNode node) {
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
	private void linkLast(ListNode node) {
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
	private void linkBefore(ListNode leftNode, ListNode rightNode) {
		Objects.requireNonNull(rightNode);
		leftNode.previous = rightNode.previous;
		rightNode.previous.next = leftNode;
		rightNode.previous = leftNode;
		leftNode.next = rightNode;
		modificationCount++;
	}
	
	/**
	 * Unlinks the <code>oldNode</code> from the list, making it eligable for the garbage collector.
	 * @param oldNode
	 */
	private void unlink(ListNode oldNode) {
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
	public ElementsGetter createElementsGetter() {
		return new LinkedListIndexedCollectionGetter(this);
	}
	
}
