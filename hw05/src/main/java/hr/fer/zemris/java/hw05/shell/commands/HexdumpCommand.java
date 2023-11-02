package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.Util;
import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellIOException;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * An implementation of the hexdump command.
 * Implements the {@link ShellCommand} interface.
 * @author Vito Sabalic
 *
 */
public class HexdumpCommand implements ShellCommand{
	
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
	public HexdumpCommand() {
		commandName = "hexdump";
		commandDescription = new ArrayList<>();
		
		commandDescription.add("Writes the content of the provided file");
		commandDescription.add("in hexadecimal numbers");
		commandDescription.add("Each row represents, at most, 16 bytes of data from the file");
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
		
		if(!Files.isRegularFile(p)) {
			env.writeln("The provided file must be a file!");
			return ShellStatus.CONTINUE;
		}
		
		byte[] bytes = new byte[4096];
	
		try(InputStream is = Files.newInputStream(p)) {
			int i;
			int brojCitanja = 1;
			while((i = is.read(bytes)) != -1) {
				
				int index = 0;
				
				
				while(index < i) {
					env.write(numberOfHex(brojCitanja++) + ": ");
					List<Byte> zaIspis = new ArrayList<>();
					for(int j = 0; j < 16 && j < i; j++) {
						zaIspis.add(bytes[index]);
						
						index++;
					}
					
					
					byte[] b = new byte[zaIspis.size()];
					
					for(int j = 0; j < b.length; j++) {
						b[j] = zaIspis.get(j);
					}
					
					env.write(writeHex(b) + " ");
					
					env.writeln(writeText(b));
				
				}
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
	
	
	/**
	 * Calculates the total sum of the bytes read
	 * @param number The number of the line
	 * @return Returns a String representing the total sum of the bytes read in hex string format
	 */
	private String numberOfHex(int number) {
		long num = number * 16;
		
		String str = Long.toHexString(num);
		
		String s = new String();
		
		for(int i = 0; i < 8 - str.length(); i++) {
			s += "0";
		}
		
		return s + str;
	}
	
	/**
	 * Writes the provided byte array in hex string format
	 * @param bytes The provided byte array
	 * @return Returns the String version of the provided bytes in hex string format
	 */
	private String writeHex(byte[] bytes) {
		
		String s = new String();
		
		char[] data = Util.bytetohex(bytes).toCharArray();
		
		
		for(int i = 0; i < data.length; i++) {
			s = s.concat(String.valueOf(data[i]));
			s = s.concat(String.valueOf(data[++i]));
			
			if(i == 15){
				s = s.concat("|");
			}
			
			s = s.concat(" ");
			
			if(i == 31) {
				s = s.concat("|");
			}
		}
		
		if(s.length() < 49) {
			for(int i = s.length(); i < 49; i++) {
				
				if(i == 23) {
					s = s.concat("|");
				}
				if(i == 48) {
					s = s.concat("|");
					break;
				}
				s = s.concat(" ");
			}
		}
		
		return s;
		
	}
	
	/**
	 * Writes the text of the provided byte array
	 * @param bytes The provided byte array
	 * @return Returns the text of the provided byte array in {@link String} format
	 */
	private String writeText(byte[] bytes) {
		
		
		for(int i = 0; i < bytes.length; i++) {
			if(bytes[i] < 32 || bytes[i] > 127) {
				bytes[i] = 46;
			}
		} 
		
		return new String(bytes, StandardCharsets.UTF_8);
	}

}
