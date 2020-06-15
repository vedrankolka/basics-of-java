package hr.fer.zemris.java.custom.collections;

/**
 * Models an object which knows how to test an object
 * @author Vedran Kolka
 *
 */
public interface Tester <T>{

	/**
	 * Tests the given <code>t</code>
	 * @param obj
	 * @return <code>true</code> if <code>t</code> is accepted, <code>false</code> otherwise
	 */
	boolean test(T t);
}
