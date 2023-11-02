package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellIOException;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * An implementation of the mkdir command.
 * Implements the {@link ShellCommand} interface
 * @author Vito Sabalic
 *
 */
public class MkdirCommand implements ShellCommand{
	
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
	public MkdirCommand() {
		commandName = "mkdir";
		commandDescription = new ArrayList<>();
		
		commandDescription.add("Creates a new directory");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = new ArrayList<>();
		
		if(arguments.isEmpty()) {
			env.writeln("Please enter an argument");
			return ShellStatus.CONTINUE;
		}
		
		try {
			args = ParseArgumentsUtil.extractArguments(arguments);
		}
		catch(IllegalArgumentException e) {
			env.writeln(e.getMessage());
		}
		
		
		
		if(args.size() > 1) {
			env.writeln("Invalid number of arguments, number of arguments can be 1 or 0");
			return ShellStatus.CONTINUE;
		}
		
		Path p = Paths.get(args.get(0));
		
		if(Files.isDirectory(p)) {
			env.writeln("Directory already exists!");
			return ShellStatus.CONTINUE;
		}
		
		
		try {
			Files.createDirectory(p);
			env.writeln("Successfully created directoy: " + p.getFileName());
		}
		catch(IOException e) {
			throw new ShellIOException(e.getMessage());
		}
		
		
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
