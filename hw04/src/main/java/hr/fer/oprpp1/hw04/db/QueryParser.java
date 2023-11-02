package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * A class which represents a simple query parser
 * @author Vito Sabalic
 *
 */
public class QueryParser {
	
	/**
	 * The provided query
	 */
	private String query;
	
	
	/**
	 * Assigns the provided value to the current variable.
	 * Checks whether the provided value is valid
	 * @param query The provided value
	 */
	public QueryParser(String query) {
		
		if(query.contains("query")) {
			query = query.replaceFirst("query", "");
		}
		else {
			throw new IllegalArgumentException("must contain keyword query");
		}
		
		query = query.trim();
		
		this.query = query;
	}
	
	
	/**
	 * Checks whether the provided query is of the direct type
	 * @return True if it is, false otherwise
	 */
	public boolean isDirectQuery() {
		
		String str = query;
		
		String expression = parseExpression(str);
		str = str.replaceFirst(expression, "");
		str = str.trim();
		expression = expression.trim();
		
		String operator = parseOperator(str);
		str = str.replaceFirst(operator, "");
		str = str.trim();
		operator = operator.trim();
		
		String literal = parseLiteral(str);
		str = str.replaceFirst(literal, "");
		str = str.trim();
		
		if(str.isEmpty() && expression.equalsIgnoreCase("jmbag") && operator.equals("=")) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Extracts the Conditional expression of a direct query
	 * @return the literal of the query
	 * @throws throws {@link IllegalStateException} if the method is called and the provided query is not direct
	 */
	public String getQueriedJMBAG() {
		
		if(!(isDirectQuery())) {
			throw new IllegalStateException();
		}
		
		String str = query;
		
		String expression = parseExpression(str);
		str = str.replaceFirst(expression, "");
		str = str.trim();
		
		String operator = parseOperator(str);
		str = str.replaceFirst(operator, "");
		str = str.trim();
		
		String literal = parseLiteral(str);
		str = str.replaceFirst(literal, "");
		str = str.trim();
		literal = literal.replaceAll("\"", "");
		
		return literal;
	}
	
	
	/**
	 * Finds and groups the conditional expressions of the provided queries
	 * @return returns a list of the found conditional expressions
	 */
	public List<ConditionalExpression> getQuery(){
		
		List<ConditionalExpression> list = new ArrayList<>();
		IFieldValueGetter expression;
		IComparisonOperator operator;
		
		while(!(query.isEmpty())) {
			
			String expressionString = parseExpression(query);
			query = query.replaceFirst(expressionString, "");
			query = query.trim();
			
			if(expressionString.equalsIgnoreCase("and")) {
				continue;
			}
			
			String operatorString = parseOperator(query);
			query = query.replaceFirst(operatorString, "");
			query = query.trim();
			
			String literalString = parseLiteral(query);
			if(literalString.contains("*")) {
				query = query.replace(literalString, "");
			}
			else {
				query = query.replaceFirst(literalString, "");
			}
			query = query.trim();
			literalString = literalString.replaceAll("\"", "");
			
			
			if(operatorString.equalsIgnoreCase("LIKE")) {
				checkWildCard(literalString);
			}
			
			
			expression = getExpressionToken(expressionString);
			operator = getOperatorToken(operatorString);
			
			list.add(new ConditionalExpression(expression, literalString, operator));
				
		}
		
		
		return list;
	}
	
	/**
	 * Parses an expression from the provided argument
	 * @param s The provided argument
	 * @return Returns a string of the expression
	 */
	private String parseExpression(String s) {
		char[] data = s.toCharArray();
		int index = 0;
		
		while(index < data.length && Character.isLetter(data[index])) {
			index++;
		}
		
		String str = new String(data, 0, index);
		
		if(str.contains("like")) {
			str = str.replaceFirst("like", "");
		}
		else if(str.contains("LIKE")) {
			str = str.replaceFirst("LIKE", "");
		}
		
		return str;
	}
	
	/**
	 * Parses an operator from the provided argument
	 * @param s The provided argument
	 * @return Returns a string of the operator
	 */
	private String parseOperator(String s) {
		
		String str;
		char[] data = s.toCharArray();
		if(Character.isLetter(data[0])) {
			int index = 0;
			while(index < data.length && Character.isLetter(data[index])) {
				index++;
			}
			
			str = new String(data, 0, index);
			return str;
		}
		
		int index = 0;
		while(index < data.length && data[index] != '"') {
			index++;
		}
		
		str = new String(data, 0, index);
		str = str.trim();
		
		return str;
	}
	
	/**
	 * Parses an literal from the provided argument
	 * @param s The provided argument
	 * @return Returns a string of the literal
	 */
	private String parseLiteral(String s) {
		char[] data = s.toCharArray();
		int index = 1;
		if(data[0] != '"') {
			throw new IllegalArgumentException();
		}
		
		while(index < data.length && data[index] != '"') {
			index++;
		}
		
		return new String(data, 0, index + 1);
	}
	
	/**
	 * Assigns a new part of the conditional expression based on the provided string
	 * @param s the provided string
	 * @return Returns a {@link IFieldValueGetter} strategy instance
	 */
	private IFieldValueGetter getExpressionToken(String s) {
		IFieldValueGetter token;
		
		if(s.equalsIgnoreCase("firstName")) {
			token = FieldValueGetters.FIRST_NAME;
		}
		else if(s.equalsIgnoreCase("lastName")) {
			token = FieldValueGetters.LAST_NAME;
		}
		else if(s.equalsIgnoreCase("jmbag")) {
			token = FieldValueGetters.JMBAG;
		}
		else {
			throw new IllegalStateException();
		}
		
		return token;
	}
	
	
	/**
	 * Assigns a new part of the conditional expression based on the provided string
	 * @param s the provided string
	 * @return Returns a {@link IComparisonOperator} strategy instance
	 */
	private IComparisonOperator getOperatorToken(String s) {
		IComparisonOperator operator;
		
		if(s.equals("<")) {
			operator = ComparisonOperators.LESS;
		}
		else if(s.equals("<=")) {
			operator = ComparisonOperators.LESS_OR_EQUALS;
			
		}
		else if(s.equals(">")) {
			operator = ComparisonOperators.GREATER;
			
		}
		else if(s.equals(">=")) {
			operator = ComparisonOperators.GREATER_OR_EQUALS;
		}
		else if(s.equals("=")) {
			operator = ComparisonOperators.EQUALS;
			
		}
		else if(s.equals("!=")) {
			operator = ComparisonOperators.NOT_EQUALS;
			
		}
		else if(s.equalsIgnoreCase("LIKE")) {
			operator = ComparisonOperators.LIKE;
		}
		else {
			throw new IllegalStateException();
		}
			
		return operator;
	}
	
	/**
	 * Checks whether there are multiple wild cards in the provided string
	 * @param s the provided string
	 * @throws throws {@link IllegalArgumentException} if there are multiple wild cards in the provided string
	 */
	private void checkWildCard(String s) {
		
		char[] data = s.toCharArray();
		boolean contains = false;
		
		for(char c: data) {
			if(c == '*') {
				if(contains) {
					throw new IllegalArgumentException("There cannot be two wildcards in string literal");
				}
				
				contains = true;
			}
		}
		
	}

	
	
	
	
	
	
	
	
	
	
}
