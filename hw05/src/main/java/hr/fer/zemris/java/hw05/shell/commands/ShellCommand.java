package hr.fer.zemris.java.hw05.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * An interface which represents a supported command which is used in the shell implementation
 * @author Vito Sabalic
 *
 */
public interface ShellCommand {
	
	/**
	 * Executes a command
	 * @param env The provided {@link Environment}
	 * @param arguments The provided argument
	 * @return Returns a {@link ShellStatus} based on the execution process
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * A getter for the command name
	 * @return return the command name
	 */
	String getCommandName();
	
	/**
	 * A getter for the commands description
	 * @return returns the description
	 */
	List<String> getCommandDescription();
}
