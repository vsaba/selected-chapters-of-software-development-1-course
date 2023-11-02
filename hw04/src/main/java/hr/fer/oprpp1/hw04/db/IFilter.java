package hr.fer.oprpp1.hw04.db;

/**
 * Interface which models a simple filter
 * @author Vito Sabalic
 *
 */
public interface IFilter {
	
	/**
	 * A method which returns true if the provided argument passes the implemented test
	 * @param record The provided {@link StudentRecord}
	 * @return returns true if the provided argument passes the test, false otherwise
	 */
	boolean accepts(StudentRecord record);

}
