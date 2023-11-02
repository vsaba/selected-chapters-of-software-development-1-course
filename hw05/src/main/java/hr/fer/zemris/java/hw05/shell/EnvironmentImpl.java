package hr.fer.zemris.java.hw05.shell;

import java.util.Collections;

import java.util.Scanner;
import java.util.SortedMap;

import hr.fer.zemris.java.hw05.shell.commands.ShellCommand;

/**
 * An implementation of the {@link Environment} interface
 * @author Vito Sabalic
 *
 */
public class EnvironmentImpl implements Environment{
	
	
	/**
	 * A map containing all the supported commands of this Environment
	 */
	private SortedMap<String, ShellCommand> commands;
	
	/**
	 * The multiline symbol
	 */
	private char multilineSymbol;
	
	/**
	 * The prompt symbol
	 */
	private char promptSymbol;
	
	/**
	 * The morelines symbol
	 */
	private char moreLinesSymbol;
	
	/**
	 * The scanner which reads inputs from the System.in
	 */
	private Scanner sc;
	
	
	/**
	 * A constructor which assigns all the necessary parameters to the environment
	 * @param commands The provided commands map
	 * @param multilineSymbol The provided multiline symbol
	 * @param promptSymbol The provided prompt symbol
	 * @param moreLinesSymbol The provided moreLines symbol
	 */
	public EnvironmentImpl(SortedMap<String, ShellCommand> commands, char multilineSymbol, char promptSymbol, char moreLinesSymbol) {
		this.commands = commands;
		this.multilineSymbol = multilineSymbol;
		this.promptSymbol = promptSymbol;
		this.moreLinesSymbol = moreLinesSymbol;
		this.sc = new Scanner(System.in);
	}
	

	@Override
	public String readLine() throws ShellIOException {
		
		String toReturn = new String();
		
		if(sc.hasNextLine()) {
			toReturn = sc.nextLine();
		}
		
		return toReturn;
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.printf(text);
		
		return;
		
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
		
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return this.multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		this.multilineSymbol = symbol;
		
	}

	@Override
	public Character getPromptSymbol() {

		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		this.promptSymbol = symbol;
		
	}

	@Override
	public Character getMorelinesSymbol() {
		return moreLinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.moreLinesSymbol = symbol;
		
	}

}
