package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * An implementation of the help command.
 * Implements the {@link ShellCommand} interface
 * @author Vito Sabalic
 *
 */
public class HelpCommand implements ShellCommand{
	
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
	public HelpCommand() {
		commandName = "help";
		commandDescription = new ArrayList<>();
		
		commandDescription.add("If an argument is provided, lists the description of the wanted command");
		commandDescription.add("otherwise, lists the description of all the commands");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isEmpty()) {
			env.commands().forEach((k, v) -> env.writeln(k));
			return ShellStatus.CONTINUE;
		}
		
		List<String> args = new ArrayList<>();
		
		try {
			args = ParseArgumentsUtil.extractArguments(arguments);
		}
		catch(IllegalArgumentException e) {
			env.writeln(e.getMessage());
		}
		
		
		if(args.size() != 1) {
			env.writeln("Please enter only one argument!");
			return ShellStatus.CONTINUE;
		}
		
		if(env.commands().containsKey(args.get(0))) {
			env.writeln(env.commands().get(args.get(0)).getCommandName());
			env.commands().get(args.get(0))
							.getCommandDescription()
							.forEach((p) -> env.writeln("  " + p));	
		}
		else {
			env.writeln("The given command doesn't exist!");
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
