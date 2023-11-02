package hr.fer.zemris.java.hw05.shell;

import java.util.SortedMap;
import java.util.TreeMap;



import hr.fer.zemris.java.hw05.shell.commands.CatCommand;
import hr.fer.zemris.java.hw05.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw05.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw05.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw05.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw05.shell.commands.LsCommand;
import hr.fer.zemris.java.hw05.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw05.shell.commands.ShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw05.shell.commands.TreeCommand;

/**
 * The main class of the representation of the shell. It is a runnable class
 * @author Vito Sabalic
 *
 */
public class MyShell {
	
	/**
	 * The environment object
	 */
	private static Environment env;
	
	/**
	 * A map which contains all the supported commands
	 */
	private static SortedMap<String, ShellCommand> commands;
	
	public static void main(String[] args) {
		
		
		commands = new TreeMap<>();
		
		commands.put("cat", new CatCommand());
		commands.put("charsets", new CharsetsCommand());
		commands.put("copy", new CopyCommand());
		commands.put("help", new HelpCommand());
		commands.put("hexdump", new HexdumpCommand());
		commands.put("ls", new LsCommand());
		commands.put("mkdir", new MkdirCommand());
		commands.put("symbol", new SymbolCommand());
		commands.put("tree", new TreeCommand());
		
		env = new EnvironmentImpl(commands, '|', '>', '\\');
		
		
		startShell();
		
	}
	
	
	
	/**
	 * Starts the shell process.
	 * Reads from the system.in using the environment interface.
	 */
	private static void startShell() {
		
		try {
			env.writeln("Welcome to MyShell v 1.0");
			
			while(true) {
				env.write(env.getPromptSymbol() + " ");
				
				String read = env.readLine();
				read = read.trim();
				
				if(read.equalsIgnoreCase("exit")) {
					break;
				}
				
				if(read.isEmpty()) {
					continue;
				}
				
				while(read.endsWith(String.valueOf(env.getMorelinesSymbol()))) {
					env.write(String.valueOf(env.getMultilineSymbol()) + " ");
					
					read = read.substring(0, read.length() - 1);
					read += env.readLine();
					
				}
				
				read = read.trim();
				String command = extractCommand(read);
				command = command.toLowerCase();
				
				if(!env.commands().containsKey(command)) {
					env.writeln("Command doesn't exist");
					continue;
				}
				
				read = read.replaceFirst(command, "");
				read = read.trim();
				
				ShellCommand com = env.commands().get(command);
				
				ShellStatus status = com.executeCommand(env, read);
				
				if(status == ShellStatus.TERMINATE) {
					break;
				}
			}
		} catch (ShellIOException e) {
			
			e.printStackTrace();
		}
		
		
		
	}
	
	/**
	 * Extracts a command from the provided String
	 * @param line The provided String
	 * @return returns the extracted command
	 */
	private static String extractCommand(String line) {
		
		
		char[] data = line.toCharArray();
		int index = 0;
		
		while(index < data.length && Character.isLetter(data[index])) {
			index++;
		}
		
		
		return new String(data, 0, index);
		
		
	}

}
