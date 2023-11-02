package hr.fer.zemris.java.hw05.shell;

import java.util.SortedMap;

import hr.fer.zemris.java.hw05.shell.commands.ShellCommand;

/**
 * Represents the environment through which the user communicates with the shell
 * @author Vito Sabalic
 *
 */
public interface Environment {
	
	
	/**
	* Reads a line from the System.in
	* @return returns the read string
	* @throws ShellIOException
	*/
	String readLine() throws ShellIOException;
	 
	 
	/**
	* Writes a line to the System.out
	* @param text The text to be written
	* @throws ShellIOException
	*/
	void write(String text) throws ShellIOException;
	 
	 
	/**
	 * Writes a line with a \n at the end from the System.out
	 * @param text The text to be written
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;
	 
	 
	/**
	 * Represents all the commands implemented in this environment
	 * @return returns all the commands implemented
	 */
	SortedMap<String, ShellCommand> commands();
	 
	 
	/**
	 * Getter for multiline symbol
	 * @return returns the multiline symbol
	 */
	Character getMultilineSymbol();
	 
	 
	/**
	 * Setter for the multiline symbol
	 * @param symbol The new symbol
	 */
	void setMultilineSymbol(Character symbol);
	 
	/**
	* Getter for prompt symbol
	* @return returns the prompt symbol
	*/
	Character getPromptSymbol();
	 
	 
	/**
	 * Setter for the prompt symbol
	 * @param symbol The new symbol
	 */
	void setPromptSymbol(Character symbol);
	 
	/**
	 * Getter for morelines symbol
	 * @return returns the morelines symbol
	 */
	Character getMorelinesSymbol();
	 
	/**
	 * Setter for the morelines symbol
	 * @param symbol The new symbol
	 */
	void setMorelinesSymbol(Character symbol);

}
