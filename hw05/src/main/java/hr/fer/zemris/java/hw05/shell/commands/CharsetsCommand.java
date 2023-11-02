package hr.fer.zemris.java.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * An implementation of the charsets command.
 * Implements the {@link ShellCommand} interface
 * @author Vito Sabalic
 *
 */
public class CharsetsCommand implements ShellCommand{
	
	/**
	 * The name of the command
	 */
	private String commandName;
	
	/**
	 * The description of the command
	 */
	private List<String> commandDescription;
	
	/**
	 * A simple constructor which assigns all the necessary attributes
	 */
	public CharsetsCommand() {
		commandName = "charsets";
		commandDescription = new ArrayList<>();
		
		commandDescription.add("Lists all the names of the supported charsets");
		
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.isEmpty()) {
			env.writeln("Incorrect number of arguments!");
			return ShellStatus.CONTINUE;
		}
		
		Charset.availableCharsets().forEach((k, v) -> env.writeln(k));
		
		return ShellStatus.CONTINUE;

	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(commandDescription);
	}

}
