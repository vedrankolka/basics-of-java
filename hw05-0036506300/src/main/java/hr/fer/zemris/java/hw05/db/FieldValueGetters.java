package hr.fer.zemris.java.hw05.db;
/**
 * A class which offers implemented IFieldValueGetters for all the fields
 * of the class StudentRecord.
 * @author Vedran Kolka
 *
 */
public class FieldValueGetters {

	public static final IFieldValueGetter FIRST_NAME = r -> r.getFirstName();
	public static final IFieldValueGetter LAST_NAME = r -> r.getLastName();
	public static final IFieldValueGetter JMBAG = r -> r.getJmbag();
}
