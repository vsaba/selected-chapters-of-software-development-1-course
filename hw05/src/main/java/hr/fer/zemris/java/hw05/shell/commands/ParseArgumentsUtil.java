package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * A helper class which implements methods for parsing arguments for the supported shell commands
 * @author Vito Sabalic
 *
 */
public class ParseArgumentsUtil {
	
	/**
	 * Extracts all the arguments from the provided String
	 * @param line The provided String
	 * @return Returns a list of all the found arguments
	 */
	public static List<String> extractArguments(String line) {
		List<String> arguments = new ArrayList<>();
		

		while(!line.isEmpty()) {
			
			char[] data = line.toCharArray();
			
			if(data.length == 1) {
				String str = new String(data);
				
				arguments.add(str);
				
				line = line.replaceFirst(str, "");
				line = line.trim();
				continue;
			}
			
			if(line.charAt(0) == '"') {
				
				String str = extractLiteral(line);
				arguments.add(str);
				
				if(!line.contains("\"" + str + "\"")) {
					str = str.replace("\\", "\\\\");
					str = str.replace("\"","\\\"");
				}
			
				str = "\"" + str + "\"";
				
				line = line.substring(str.length(), line.length());
				
				if(!line.isEmpty()) {
					if(line.charAt(0) != ' ') {
						throw new IllegalArgumentException("Incorrect literal argument!");
					}
				}
				
				line = line.trim();
				continue;
			}
			
			int index = 0;
			

			while(index < data.length && data[index] != ' ') {
				index++;
			}
			
			String s = new String(data, 0, index);
			
			arguments.add(s);
			
			line = line.substring(s.length(), line.length());
			
			line = line.trim();
		}
		
		
		
		return arguments;
	}
	
	/**
	 * Extracts a literal from the provided String
	 * @param line The provided String
	 * @return Returns the String situated inside the literal
	 */
	public static String extractLiteral(String line) {
		
		char[] data = line.toCharArray();
		int index = 1;
		
		String toReturn = new String();
		
		while(index < data.length && data[index] != '"') {
			
			if(data[index] == '\\') {
				if((index != data.length - 1) && (data[index + 1] == '"' || data[index + 1] == '\\')) {
					toReturn = toReturn.concat(String.valueOf(data[index + 1]));
					index += 2;
					continue;
				}
			}
			
			toReturn = toReturn.concat(String.valueOf(data[index++]));
			
		}
		
		return toReturn;
	}

}
