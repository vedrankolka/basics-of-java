package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * A representation of a single student record.
 * @author Vedran Kolka
 *
 */
public class StudentRecord {
	
	private String jmbag;
	private String lastName;
	private String firstName;
	private int grade;
	
	/**
	 * 
	 * @param jmbag
	 * @param lastName
	 * @param firstName
	 * @param grade
	 * @throws IllegalArgumentException if grade is less than 1 or greater than 5
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int grade) {
		if(grade<1 || grade>5) {
			throw new IllegalArgumentException("Grade must be between 1 and 5 (inclusive).");
		}
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.grade = grade;
	}

	public String getJmbag() {
		return jmbag;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public int getGrade() {
		return grade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(jmbag);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof StudentRecord)) {
			return false;
		}
		StudentRecord other = (StudentRecord) obj;
		return Objects.equals(jmbag, other.jmbag);
	}
	
}
