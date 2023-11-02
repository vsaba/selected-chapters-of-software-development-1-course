package hr.fer.oprpp1.custom.collections;


/**Interface which serves as a model for performing an operation
 * @author Vito Sabalic
 *
 */
public interface Processor {
	
	
	/**
	 * Method performs an operation with the given value
	 * @param value the value with which an operation is performed
	 */
	void process(Object value);
}
