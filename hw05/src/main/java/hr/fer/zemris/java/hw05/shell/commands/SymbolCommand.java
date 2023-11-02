package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * An implementation of the symbol command.
 * Implements the {@link ShellCommand} interface
 * @author Vito Sabalic
 *
 */
public class SymbolCommand implements ShellCommand{
	
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
	public SymbolCommand() {
		commandName = "symbol";
		commandDescription = new ArrayList<>();
		
		commandDescription.add("Writes the meaning of the provided symbol");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		List<String> args = new ArrayList<>();
		
		try {
			args = ParseArgumentsUtil.extractArguments(arguments);
		}
		catch(IllegalArgumentException e) {
			env.writeln(e.getMessage());
		}
		
		if(args.size() > 2 || args.size() < 1) {
			env.writeln("Invalid number of arguments");
			return ShellStatus.CONTINUE;
		}
		
		boolean change = args.size() == 2;
		
		if(change && (args.get(1).length() != 1)) {
			env.writeln("Incorrect new symbol");
			return ShellStatus.CONTINUE;
		}
		
		if(args.get(0).equalsIgnoreCase("PROMPT")) {
			if(change) {
				char oldSymbol = env.getPromptSymbol();
				env.setPromptSymbol(args.get(1).toCharArray()[0]);
				env.writeln("Symbol for PROMPT changed from '" + oldSymbol + "' to '" + env.getPromptSymbol() + "'");
				
			}
			else {
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
			}
		}
		else if(args.get(0).equalsIgnoreCase("MORELINES")) {
			if(change) {
				char oldSymbol = env.getMorelinesSymbol();
				env.setMorelinesSymbol(args.get(1).toCharArray()[0]);
				env.writeln("Symbol for MORELINES changed from '" + oldSymbol + "' to '" + env.getMorelinesSymbol() + "'");
				
			}
			else {
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
			}
		}
		else if(args.get(0).equalsIgnoreCase("MULTILINE")) {
			if(change) {
				char oldSymbol = env.getMultilineSymbol();
				env.setMultilineSymbol(args.get(1).toCharArray()[0]);
				env.writeln("Symbol for MULTILINE changed from '" + oldSymbol + "' to '" + env.getMultilineSymbol() + "'");
				
			}
			else {
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
			}
		}
		else {
			env.writeln("Incorrect argument. Key word doesn't exist");
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
