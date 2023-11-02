package hr.fer.oprpp1.hw02.prob1;

/**
 * The enumeration which consists of the states in which the {@link Lexer} can be in
 * @author Vito Sabalic
 *
 */
public enum LexerState {
	
	/**
	 * The basic state which uses all the provided tokens in the {@link TokenType} class
	 */
	BASIC,
	
	
	/**
	 * The extended state of the lexer which uses only the NUMBER and SYMBOL from the {@link TokenType} class
	 */
	EXTENDED
}
