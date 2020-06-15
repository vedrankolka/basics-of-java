package hr.fer.zemris.java.hw05.db;

/**
 * An interface that models an object which knows if a StudentRecord is acceptable or not. 
 * @author Vedran Kolka
 *
 */
public interface IFilter {
	/**
	 * Checks if the given <code>record</code> is acceptable.
	 * @param record to check
	 * @return <code>true</code> if the given <code>record</code> is accepted,
	 * <code>false</code> otherwise
	 */
	 public boolean accepts(StudentRecord record);
}
