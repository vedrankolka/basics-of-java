package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that models a database of StudentRecords.
 * @author Vedran Kolka
 *
 */
public class StudentDatabase {

	/**
	 * A list of all the StudentRecords used for iteration over the StudentRecords.
	 */
	private List<StudentRecord> list;
	/**
	 * A map of all the StudentRecords used for fast information retrieval.
	 */
	private Map<String, StudentRecord> index;
	
	/**
	 * @param records - Strings that represent whole StudentRecords.
	 * @throws IllegalArgumentException if there are duplicates in the given <code>records</code>
	 */
	public StudentDatabase(List<String> records) {
		
		list = new ArrayList<StudentRecord>();
		index = new HashMap<String, StudentRecord>();
		
		for(String line : records) {
			//split the line to read all data
			String[] properties = line.split("\\t+");
			//the first must be a jmbag
			String jmbag = properties[0];
			if(index.containsKey(jmbag)) {
				throw new IllegalArgumentException("The database cannot contain duplicates.");
			}
			String lastName = properties[1];
			String firstName = properties[2];
			int grade = Integer.parseInt(properties[3]);
			StudentRecord record = new StudentRecord(jmbag, lastName, firstName, grade);
			index.put(jmbag, record);
			list.add(record);
			
		}
	}
	
	/**
	 * Returns the record with the jmbag <code>jmbag</code>.
	 * @param jmbag of the record
	 * @return record with the given <code>jmbag</code> or <code>null</code> if there
	 * is not a record with the given <code>jmbag</code>
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}
	
	/**
	 * Returns a new List with records accepted by the given <code>filter</code>.
	 * @param filter which determines if a record is accepted
	 * @return list of all accepted records
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filteredList = new ArrayList<StudentRecord>();
		for(StudentRecord r : list) {
			if(filter.accepts(r)) {
				filteredList.add(r);
			}
		}
		return filteredList;
	}
	
	/**
	 * Returns the number of student records in the database.
	 * @return size
	 */
	public int size() {
		return list.size();
	}
	
}
