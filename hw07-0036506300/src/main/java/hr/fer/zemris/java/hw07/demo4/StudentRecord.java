package hr.fer.zemris.java.hw07.demo4;
/**
 * Razred koji modelira zapis o studentu.
 * @author Vedran Kolka
 *
 */
public class StudentRecord {
	/**
	 * Jmbag of the student.
	 */
	private String jmbag;
	/**
	 * Last name of the student.
	 */
	private String lastName;
	/**
	 * First name of the student.
	 */
	private String firstName;
	/**
	 * Score on the first test.
	 */
	private double miScore;
	/**
	 * Score on the second test.
	 */
	private double ziScore;
	/**
	 * Score on the labosi brate.
	 */
	private double labScore;
	/**
	 * Grade.
	 */
	private int grade;
	
	/**
	 * Constructs the student record with all given properties.
	 * @param jmbag
	 * @param lastName
	 * @param firstName
	 * @param mIscore
	 * @param zIscore
	 * @param labScore
	 * @param grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName,
			double mIscore, double zIscore, double labScore, int grade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.miScore = mIscore;
		this.ziScore = zIscore;
		this.labScore = labScore;
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

	public double getMIscore() {
		return miScore;
	}

	public double getZIscore() {
		return ziScore;
	}

	public double getLabScore() {
		return labScore;
	}

	public int getGrade() {
		return grade;
	}
	
	@Override
	public String toString() {
		return String.format("%s	%s	%s	%.2f	%.2f	%.2f	%d",
				jmbag, lastName, firstName, miScore, ziScore, labScore, grade);
	}
	
}
