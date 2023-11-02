package hr.fer.oprpp1.hw02.prob1;

/**
 * A simple implementation of a lexer which creates tokens from the provided data input
 * @author Vito Sabalic
 *
 */
public class Lexer {
	
	/**
	 * the data to be tokenized
	 */
	private char[] data;
	
	/**
	 * the token to be returned
	 */
	private Token token; 
	
	/**
	 * the current index at which the data set is located
	 */
	private int currentIndex; 
	
	
	/**
	 * the current state of the lexer
	 */
	private LexerState state;
	
	
	
	/**
	 * A constructor which receives the data set which is to be tokenized and assigns it to the lexer
	 * @param text the value which is to be tokenized
	 */
	public Lexer(String text) { 
		if(text == null) {
			throw new NullPointerException("The input string cannot be null");
		}
		
		String s = text.trim();
		
		this.data = s.toCharArray();
		this.currentIndex = 0;
		this.state = LexerState.BASIC;
	}
	
	/**
	 * Sets the state of the lexer based on the {@link LexerState} enumeration
	 * @param state the state to be set
	 * @throws throws {@link NullPointerException} if the provided state value is null
	 */
	public void setState(LexerState state) {
		
		if(state == null) {
			throw new NullPointerException("The state cannot be null");
		}
		
		this.state = state;
	}
	
	
	
	/**
	 * Removes blank spaces, \n, \r and \t from the data set
	 */
	public void removeBlanks() {
		for(int i = currentIndex; i < data.length; i++) {
			if(checkIfSpace(i)) {
				currentIndex++;
				continue;
			}
			
			break;
		}
	}
	
	/**
	 * Checks whether the char at the current index is a blank space, \n, \r or \t
	 * @param index the index at which the char is set
	 * @return true if it is the aforementioned char, otherwise returns false
	 */
	public boolean checkIfSpace(int index) {
		if(data[index] == '\r' || data[index] == '\n' || data[index] == '\t' || data[index] == ' ') {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Sets the token of the currently analyzed data set.
	 * Determines the token based on the first char of the data set
	 * @return returns the found token type
	 */
	public TokenType setBasicToken() {
		TokenType token = null;
		
		for(int i = currentIndex; i < data.length; i++) {
			if(data[i] == '\\' && !(i + 1 == data.length)) {
				if(Character.isDigit(data[currentIndex + 1])) {
					token = TokenType.WORD;
					break;
				}
			}
			
			if(Character.isLetter(data[i])) {
				token = TokenType.WORD;
				break;
			}
			else if(Character.isDigit(data[i])) {
				token = TokenType.NUMBER;
				break;
			}
			else if(!(Character.isLetterOrDigit(data[i]))){
				token = TokenType.SYMBOL;
				break;
			}
		}
		
		return token;
	}
	
	
	
	/**
	 * Sets the token of the currently analyzed data set while in the EXTENDED state of work
	 * @return returns the current {@link TokenType} 
	 */
	public TokenType setExtendedToken() {
		if(Character.isLetterOrDigit(data[currentIndex])) {
			return TokenType.WORD;
		}
		
		return TokenType.SYMBOL;
	}
	
	
	
	/**
	 * Generates the next token based on the current data set at which the lexer is positioned.
	 * @return returns the next assigned token
	 * @throws throws {@link LexerException} if an error occurs during the analyzing of the current data set
	 */
	public Token nextToken() { 
		
		removeBlanks();
		
		if(currentIndex > data.length) {
			throw new LexerException("Cannot read the next token if there is no input string");
		}
		
		if(data.length - currentIndex == 0) {
			
			currentIndex++;
			token = new Token(TokenType.EOF, null);
			return token;
			
		}
		
		TokenType type;
		
		if(state == LexerState.BASIC) {
			type = setBasicToken();
		}
		else {
			type = setExtendedToken();
		}
		
		
		String toBeReturned = new String();
	
		
		if(type == TokenType.SYMBOL) {
			if(data[currentIndex] == '\\') {
				throw new LexerException();
			}
			token = new Token(type, data[currentIndex++]);
			removeBlanks();
			return token;
		}
		
		
		if(state == LexerState.BASIC) {
		
			for(; currentIndex < data.length; currentIndex++) {
				if(data[currentIndex] == '\\') {
					if(currentIndex + 1 == data.length || (!(Character.isDigit(data[currentIndex + 1])) && data[currentIndex +1 ] != '\\')) {
						throw new LexerException();
					}
					
					if(Character.isDigit(data[currentIndex + 1])) {
						toBeReturned = toBeReturned.concat(String.valueOf(data[++currentIndex]));
						continue;
					}
					
					currentIndex++;
				}
				
				else if((type == TokenType.WORD && !(Character.isLetter(data[currentIndex])))) {
					removeBlanks();
					break;
				}
				
				else if((type == TokenType.NUMBER && !(Character.isDigit(data[currentIndex])))) {
					removeBlanks();
					break;
				}
				
				if(checkIfSpace(currentIndex)) {
					removeBlanks();
					break;
				}
				
				toBeReturned = toBeReturned.concat(String.valueOf(data[currentIndex]));
			}
			
		}
		else {
			
			if(type == TokenType.SYMBOL) {
				token = new Token(type, String.valueOf(data[currentIndex++]));
				return token;
			}
			
			for(; currentIndex < data.length; currentIndex++) {
				if(data[currentIndex] == '\\') {
					toBeReturned = toBeReturned.concat(String.valueOf(data[currentIndex]));
					continue;
				}
				
				if(!(Character.isLetterOrDigit(data[currentIndex])) || checkIfSpace(currentIndex)) {
					removeBlanks();
					break;
				}
				
				toBeReturned = toBeReturned.concat(String.valueOf(data[currentIndex]));
				
			}
			
			token = new Token(type, toBeReturned);
			return token;
		}
		
		
		
		if(type == TokenType.NUMBER) {
		
			try {
				token = new Token(type, Long.parseLong(toBeReturned));
			} catch (Exception e) {
				throw new LexerException("Number is too large to parse");
			}
		
		}
		else {
			token = new Token(type, toBeReturned);
		}
		
		
		return token;
	}
	
	
	

	/**
	 * Returns the currently assigned token to the lexer
	 * @return returns the currently assigned token to the lexer
	 */
	public Token getToken() {
		return token;
	}


}
