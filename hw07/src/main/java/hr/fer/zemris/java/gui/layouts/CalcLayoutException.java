package hr.fer.zemris.java.gui.layouts;

/**
 * An exception which extends the {@link RuntimeException} class. To be thrown when an exception occurs in the calc layout creation process
 * @author Vito Sabalic
 *
 */
public class CalcLayoutException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * A basic constructor, it sends the construction process to its parent class {@link RuntimeException}
	 */
	public CalcLayoutException() {
		super();
	}

	/**
	 * Sends the construction process to its parent class {@link RuntimeException} with the provided message
	 * @param message the provided exception message
	 */
	public CalcLayoutException(String message) {
		super(message);
	}
	
}
