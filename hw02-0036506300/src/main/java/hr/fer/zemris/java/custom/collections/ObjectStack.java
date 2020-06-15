package hr.fer.zemris.java.custom.collections;

/**
 * An implementation of a stack with all the basic stack operations.
 * Stores duplicates and does not store <code>null</code> references.
 * @author Vedran Kolka
 *
 */
public class ObjectStack {

	/**
	 * ArrayIndexedCollection used to store elements of the stack.
	 */
	private ArrayIndexedCollection stack;
	
	public ObjectStack() {
		stack = new ArrayIndexedCollection();
	}
	
	/**
	 * Checks if the stack is empty.
	 * @return <code>true</code> if stack if empty, <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	/**
	 * Returns the size of the stack.
	 * @return <code>size</code> of stack
	 */
	public int size() {
		return stack.size();
	}
	
	/**
	 * Pushes the given <code>value</code> at the top of the stack.
	 * @param value
	 * @throws NullPointerException if <code>value</code> is <code>null</code>
	 */
	public void push(Object value) {
		stack.add(value);
	}
	
	/**
	 * Returns the element on top of the stack and removes it.
	 * @return element on top of the stack
	 * @throws EmptyStackException if stack is empty
	 */
	public Object pop() {
		Object value = peek();
		stack.remove(stack.size()-1);
		return value;
	}
	
	/**
	 * Return the element on top of the stack but does not remove it.
	 * @return element on top of the stack
	 * @throws EmptyStackException if stack is empty
	 */
	public Object peek() {
		if(isEmpty()) {
			throw new EmptyStackException();
		}
		return stack.get(stack.size()-1);
	}
	
	/**
	 * Removes all elements from the stack.
	 */
	public void clear() {
		stack.clear();
	}
	
}
