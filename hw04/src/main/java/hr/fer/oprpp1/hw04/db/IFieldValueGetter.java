package hr.fer.oprpp1.hw04.db;

/**
 * Interface which models a getter for a {@link StudentRecord}
 * @author Vito Sabalic
 *
 */
public interface IFieldValueGetter {
	
	/**
	 * Getter for a specific value of the {@link StudentRecord} class
	 * @param record The provided record
	 * @return returns the specific value of the {@link StudentRecord} class
	 */
	String get(StudentRecord record);

}
