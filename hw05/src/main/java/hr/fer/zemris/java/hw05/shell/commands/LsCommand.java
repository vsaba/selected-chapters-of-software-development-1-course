package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellIOException;
import hr.fer.zemris.java.hw05.shell.ShellStatus;


/**
 * An implementation of the ls command.
 * Implements the {@link ShellCommand} interface
 * @author Vito Sabalic
 *
 */
public class LsCommand implements ShellCommand{
	
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
	public LsCommand() {
		commandName = "ls";
		commandDescription = new ArrayList<>();
		
		commandDescription.add("Writes to the console all the contents");
		commandDescription.add("from the provided directory");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		List<String> args = new ArrayList<>();
		
		if(arguments.isEmpty()) {
			try {
				return writeDirectory(env, Paths.get(""));
			}
			catch(IOException e) {
				throw new ShellIOException(e.getMessage());
			}
			
		}
		
		try {
			
			args = ParseArgumentsUtil.extractArguments(arguments);
			
		}
		catch(IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		
		if(args.size() > 1) {
			env.writeln("Invalid number of arguments, number of arguments can be 1 or 0");
			return ShellStatus.CONTINUE;
		}
		
		if(!Files.isDirectory(Paths.get(args.get(0)))) {
			env.writeln("Invalid argument. The provided argument is not a directoy!");
			return ShellStatus.CONTINUE;
		}
		
		try {
			
			if(args.size() > 1) {
				env.writeln("Invalid number of arguments, number of arguments can be 1 or 0");
				return ShellStatus.CONTINUE;
			}
			
			if(!Files.isDirectory(Paths.get(args.get(0)))) {
				env.writeln("Invalid argument. The current argument is not a directoy!");
				return ShellStatus.CONTINUE;
			}
			
			return writeDirectory(env, Paths.get(args.get(0)));
		}
		catch(IOException e) {
			throw new ShellIOException(e.getMessage());
		}
		
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(commandDescription);
	}
	
	
	
	
	/**
	 * Finds all the elements from the provided path and 
	 * writes them using the {@link Environment} interface
	 * @param env The provided {@link Environment} implementation
	 * @param path The path of the provided directory
	 * @return Returns a {@link ShellStatus} based on the execution of the method
	 * @throws IOException
	 */
	private ShellStatus writeDirectory(Environment env, Path path) throws IOException {

		List<Path> elements = new ArrayList<>();
		try(Stream<Path> str = Files.list(path)){
			elements = str.collect(Collectors.toList());
		}
		
		for(Path element : elements) {
			env.write(getAttributes(element) + " ");
			
			env.write(getSize(element) + " ");
			
			env.write(getDate(element) + " ");
			
			env.writeln(element.getFileName().toString());
		}
		
		
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Gets the attributes of the element at the provided path
	 * @param path The provided path
	 * @return Returns the String which represents the attributes
	 */
	private String getAttributes(Path path) {
		
		char[] data = new char[] {'-','-','-','-'};
		
		if(Files.isDirectory(path)) {
			data[0] = 'd';
		}
		if(Files.isReadable(path)) {
			data[1] = 'r';
		}
		if(Files.isWritable(path)) {
			data[2] = 'w';
		}
		if(Files.isExecutable(path)) {
			data[3] = 'x';
		}
		
		return new String(data);
	}
	
	
	/**
	 * Calculates the size of the provided element
	 * @param path The path of the provided element
	 * @return Returns the size of the provided element
	 * @throws IOException
	 */
	private String getSize(Path path) throws IOException {
		
		long size = 0;
		if(Files.isDirectory(path)) {
			try (Stream<Path> walk = Files.walk(path)) {
				
				size = walk.filter(Files::isRegularFile)
							.mapToLong(p -> {
								try {
									return Files.size(p);
								} catch (IOException e) {
									throw new ShellIOException(e.getMessage());
								}
							})
							.sum();
				
			} 
		}
		else {
			size = Files.size(path);
		}
		
		String value = String.valueOf(size);
		String str = new String();
		
		for(int i = 0; i < 10 - value.length(); i++) {
			str += " ";
		}
		
		return str + value;
	}
	
	/**
	 * Calculates the creation date of the provided element
	 * @param path The path of the provided element
	 * @return Returns the date as a String
	 * @throws IOException
	 */
	private String getDate(Path path) throws IOException {
		BasicFileAttributeView faView = Files.getFileAttributeView(
		path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
		);
		BasicFileAttributes attributes = faView.readAttributes();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		
		
		return formattedDateTime;
	}

}
