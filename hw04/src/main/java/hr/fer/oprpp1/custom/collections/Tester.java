package hr.fer.oprpp1.custom.collections;

/**
 * An interface which defines a test to be made on the provided object
 * @author Vito Sabalic
 *
 */
public interface Tester<E> {

	/**
	 * Performs the test on the provided object
	 * @param obj the object on which the test is made
	 * @return returns true if the test succeeded, otherwise returns false
	 */
	boolean test(E obj);
	
}
