package hr.fer.zemris.java.hw05.db;

/**
 * An interface which models objects which know how to decide if a comparison
 * is satisfied or not.
 * @author Vedran Kolka
 *
 */
public interface IComparisonOperator {
	/**
	 * Decides if the result of the comparison is satisfactory or not.
	 * @param value1
	 * @param value2
	 * @return <code>true</code> if the comparison is satisfied, <code>false</code> otherwise
	 */
	public boolean satisfied(String value1, String value2);
}
