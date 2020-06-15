package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * A class that stores states of the turtle on a stack.
 * @author Vedran Kolka
 *
 */
public class Context {

	/**
	 * The stack for TurtleState storage.
	 */
	private ObjectStack<TurtleState> stack;
	
	public Context() {
		stack = new ObjectStack<TurtleState>();
	}
	
	/**
	 * Returns, but <b>does not remove</b> (like <code>peek()</code>)
	 * the state on top of the stack.
	 * @return the last TurtleState pushed on the stack
	 * @throws EmptyStackException if the stack was empty
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	
	/**
	 * Pushes the given <code>state</code> on the stack.
	 * @param TurtleState to push on the stack
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	/**
	 * Removes the state from the top of the stack.
	 * @throws EmptyStackException if the stack was empty
	 */
	public void popState() {
		stack.pop();
	}
	
}
