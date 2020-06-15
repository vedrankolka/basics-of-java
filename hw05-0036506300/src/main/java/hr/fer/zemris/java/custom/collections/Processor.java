package hr.fer.zemris.java.custom.collections;

/**
 * Model of an object capable of performing some operation on the passed object.
 * @author Vedran Kolka
 *
 */
@FunctionalInterface
public interface Processor<T> {

	/**
	 * Task to be done with the given <code>value</code>.
	 * @param value
	 */
	void process(T value);
	
}
