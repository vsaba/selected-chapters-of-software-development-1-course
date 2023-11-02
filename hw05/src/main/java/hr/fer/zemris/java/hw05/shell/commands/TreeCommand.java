package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellIOException;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * An implementation of the tree command.
 * Implements the {@link ShellCommand} interface
 * @author Vito Sabalic
 *
 */
public class TreeCommand implements ShellCommand{
	
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
	public TreeCommand() {
		commandName = "tree";
		commandDescription = new ArrayList<>();
		
		commandDescription.add("Writes the entire tree of the");
		commandDescription.add("provided directory to the console");
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
		
		if(!Files.isDirectory(p)) {
			env.writeln("Invalid argument. The provided argument is not a directory");
			return ShellStatus.CONTINUE;
		}
		
		if(Files.isDirectory(p)){
		
			try {
				Files.walkFileTree(p, new SimpleFileVisitor<Path>() {
	    		
					int inkrement = 0;
	    		
					@Override
					public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

						for(int i = 0; i < inkrement; i++) {
							env.write(" ");
						}
						env.writeln(dir.getFileName().toString());
						inkrement += 2;
						return FileVisitResult.CONTINUE;
					}
				 
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
							throws IOException
					{
						for(int i = 0; i < inkrement; i++) {
							env.write(" ");
						}
						env.writeln(file.getFileName().toString());
						return FileVisitResult.CONTINUE;
					}
					@Override
					public FileVisitResult postVisitDirectory(Path dir, IOException e)
							throws IOException
					{
						if (e == null) {
				             inkrement -= 2;
				             return FileVisitResult.CONTINUE;
						} else {
							throw e;
						}
					}
				});
	     	} catch (IOException e) {
				throw new ShellIOException(e.getMessage());
			}
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
