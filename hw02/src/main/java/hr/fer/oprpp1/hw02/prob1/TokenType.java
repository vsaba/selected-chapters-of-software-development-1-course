package hr.fer.oprpp1.hw02.prob1;

/**
 * An enumeration which represents the possible values of tokens which {@link Lexer} produces
 * @author Vito Sabalic
 *
 */
public enum TokenType {
	
	/**
	 * Represents the end of the data type which the lexer is processing
	 */
	EOF,
	
	
	/**
	 * Represents a word
	 */
	WORD,
	
	/**
	 * Represents a number
	 */
	NUMBER,
	
	/**
	 * Represents a symbol
	 */
	SYMBOL

}
