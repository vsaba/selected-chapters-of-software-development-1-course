package hr.fer.oprpp1.hw04.db;

/**
 * Interface which models a test for two strings
 * @author Vito Sabalic
 *
 */
public interface IComparisonOperator {
	
	/**
	 * Performs a test on the provided strings
	 * @param value1 The first string
	 * @param value2 The second string
	 * @return Returns true if the test is passed, false otherwise
	 */
	boolean satisfied(String value1, String value2);
}
