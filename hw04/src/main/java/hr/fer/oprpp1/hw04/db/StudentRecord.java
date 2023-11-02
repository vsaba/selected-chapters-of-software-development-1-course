package hr.fer.oprpp1.hw04.db;

/**
 * Class which represents a single student instance in the database
 * @author Vito Sabalic
 *
 */
public class StudentRecord {
	
	/**
	 * The jmbag of the student
	 */
	private String jmbag;
	
	/**
	 * The students first name
	 */
	private String firstName;
	
	
	/**
	 * The students last name
	 */
	private String lastName;
	
	/**
	 * The students mark
	 */
	private int mark;
	
	/**
	 * A simple constructor which assigns the given values to their respective variables
	 * @param jmbag The given jmbag value
	 * @param firstName The given first name value
	 * @param lastName The given last name value
	 * @param mark The given mark
	 */
	public StudentRecord (String jmbag, String firstName, String lastName, int mark) {
		this.jmbag = jmbag;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mark = mark;
	}

	/**
	 * Getter for the jmbag variable
	 * @return returns the jmbag value
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter for the firstName variable
	 * @return returns the first name 
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter for the lastName variable
	 * @return returns the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter for the mark variable
	 * @return returns the mark value
	 */
	public int getMark() {
		return mark;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof StudentRecord)) {
			return false;
		}
		StudentRecord r = (StudentRecord) obj;
		return jmbag.equals(r.getJmbag());
	}
	
	@Override
	public int hashCode() {
		return jmbag.hashCode();
	}
	
	@Override
	public String toString() {
		return "| "+ jmbag + " | " + lastName + " | " + firstName + " | " + mark + " |";
	}
}
