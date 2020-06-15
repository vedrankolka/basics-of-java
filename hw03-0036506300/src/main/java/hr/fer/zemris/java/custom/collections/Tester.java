package hr.fer.zemris.java.custom.collections;

/**
 * Models an object which knows how to test an object
 * @author Vedran Kolka
 *
 */
public interface Tester {

	/**
	 * Tests the given <code>obj</code>
	 * @param obj
	 * @return <code>true</code> if <code>obj</code> is accepted, <code>false</code> otherwise
	 */
	boolean test(Object obj);
}
