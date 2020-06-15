package hr.fer.zemris.java.hw05.db;
/**
 * An interface that models an object which gets a value from a field of the StudentRecord.
 * @author Vedran Kolka
 *
 */
public interface IFieldValueGetter {

	/**
	 * Gets a field from the given <code>record</code>.
	 * @param record
	 * @return field value of the record
	 */
	String get(StudentRecord record);
	
}
