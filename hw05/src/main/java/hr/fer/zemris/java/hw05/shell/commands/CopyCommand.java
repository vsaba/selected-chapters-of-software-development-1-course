package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
 * An implementation of the copy command.
 * Implements the {@link ShellCommand} interface
 * @author Vito Sabalic
 *
 */
public class CopyCommand implements ShellCommand{
	
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
	public CopyCommand() {
		commandName = "copy";
		commandDescription = new ArrayList<>();
		
		commandDescription.add("Copies the provided file from the first argument to the");
		commandDescription.add("provided file from the second argument");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = new ArrayList<>();
		
		if(arguments.isEmpty()) {
			env.writeln("Please write two arguments!");
			return ShellStatus.CONTINUE;
		}
		
		try {
			args = ParseArgumentsUtil.extractArguments(arguments);
		}
		catch(IllegalArgumentException e){
			env.writeln(e.getMessage());
		}
		
		if(args.size() != 2) {
			env.writeln("Please write two arguments!");
			return ShellStatus.CONTINUE;
		}
		
		Path p1 = Paths.get(args.get(0));
		Path p2 = Paths.get(args.get(1));
		
		if(!Files.exists(p1)) {
			env.writeln("The file to be copied doesn't exist");
			return ShellStatus.CONTINUE;
		}
		
		if(Files.isDirectory(p2)) {
			p2 = Paths.get(args.get(1), p1.getFileName().toString());
		}
		
		else if(Files.exists(p2)) {
			env.write("File already exists. Overwrite it? [y/n]: ");
			String s = env.readLine();
			
			if(s.equalsIgnoreCase("n") || s.equalsIgnoreCase("no")) {
				return ShellStatus.CONTINUE;
			}
		}
		
		try(InputStream is = Files.newInputStream(p1); OutputStream os = Files.newOutputStream(p2)){
			byte[] bytes = new byte[4096];
			int i;
			while((i = is.read(bytes)) != -1) {
				os.write(bytes, 0, i);
			}
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
