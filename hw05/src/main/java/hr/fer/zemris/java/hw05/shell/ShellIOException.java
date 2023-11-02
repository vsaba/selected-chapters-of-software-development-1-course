package hr.fer.zemris.java.hw05.shell;

/**
 * An exception that represents a IOException for the implemented shell
 * @author Vito Sabalic
 *
 */
public class ShellIOException extends RuntimeException{

	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * A simple constructor which delegates the construction process to the RuntimeException class
	 */
	public ShellIOException() {
		super();
	}
	
	
	
	/**
	 * A constructor which delegates the construction process with a message to the RuntimeException class
	 * @param message The provided message
	 */
	public ShellIOException(String message) {
		super(message);
	}
}
