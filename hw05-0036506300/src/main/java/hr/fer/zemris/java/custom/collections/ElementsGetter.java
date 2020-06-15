package hr.fer.zemris.java.custom.collections;

import java.util.NoSuchElementException;

/**
 * Models an object that gets elements of a collection one by one.
 * @author Vedran Kolka
 *
 */
public interface ElementsGetter<T> {

	/**
	 * CHecks if the collection has another element to return.
	 * @return <code>true</code> if it has, <code>false</code> otherwise
	 */
	boolean hasNextElement();
	
	/**
	 * Returns the next element of the collection if it has one to return.
	 * @return next element of the collection
	 * @throws NoSuchElementException if it doesn't have an element to return
	 */
	T getNextElement();
	
	/**
	 * 
	 * @param p
	 */
	default void processRemaining(Processor<T> p) {
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
