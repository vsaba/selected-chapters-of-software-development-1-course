package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellIOException;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * An implementation of the cat command.
 * Implements the {@link ShellCommand} interface
 * @author Vito Sabalic
 *
 */
public class CatCommand implements ShellCommand{
	
	/**
	 * The command name
	 */
	private String commandName;
	
	/**
	 * The description of the command
	 */
	private List<String> commandDescription;
	
	/**
	 * A simple constructor which assigns all the necessary attributes
	 */
	public CatCommand() {
		commandName = "cat";
		commandDescription = new ArrayList<>();
		
		commandDescription.add("This command opens the provided file");
		commandDescription.add("and writes its content on the console");
	}
	
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isEmpty()) {
			env.writeln("Incorrect number of arguments!");
			return ShellStatus.CONTINUE;
		}
		
		List<String> args = new ArrayList<>();
		
		try {
			args = ParseArgumentsUtil.extractArguments(arguments);
		}
		catch(IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		
		if(args.size() < 1 || args.size() > 2) {
			env.writeln("Invalid number of arguments!");
			return ShellStatus.CONTINUE;
		}
		
		if(!Files.exists(Paths.get(args.get(0)))) {
			env.writeln("The provided file doesn't exist");
			return ShellStatus.CONTINUE;
		}
		
		if(Files.isDirectory(Paths.get(args.get(0)))){
			env.writeln("A directory is not a valid argument");
			return ShellStatus.CONTINUE;
		}
		
		try(InputStream is = Files.newInputStream(Paths.get(args.get(0)))) {
			
			byte[] bytes = new byte[4096];
			
			String s = new String();
			
			if(args.size() == 2) {
				
				if(!Charset.availableCharsets().containsKey(args.get(1))) {
					env.writeln("Invalid charset!");
					return ShellStatus.CONTINUE;
				}
				
				int i;
				while((i = is.read(bytes)) != -1) {
					s = s.concat(new String(bytes, 0, i,Charset.availableCharsets().get(args.get(1))));
				}
			}
			else {
				int i;
				while((i = is.read(bytes)) != -1) {
					s = s.concat(new String(bytes, 0, i,StandardCharsets.UTF_8));
				}
			}
			
			env.writeln(s);
			
		} catch (IOException e) {
			
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
