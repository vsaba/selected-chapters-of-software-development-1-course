package hr.fer.oprpp1.hw04.db;

/**
 * A class which models a conditional expression of a provided query
 * @author Vito Sabalic
 *
 */
public class ConditionalExpression {
	
	/**
	 * An instance of a {@link IFieldValueGetter} interface, specifies the expression part of the query
	 */
	private IFieldValueGetter getter; 
	
	/**
	 * The literal part of the query
	 */
	private String literal;
	
	/**
	 * An instance of a {@link IComparisonOperator} interface, specifies the operator part of the query
	 */
	private IComparisonOperator operator;
	
	
	/**
	 * A simple constructor which assigns all the provided values to their respective variables
	 * @param getter The provided value
	 * @param literal The provided value
	 * @param operator The provided value
	 */
	public ConditionalExpression(IFieldValueGetter getter, String literal, IComparisonOperator operator) {
		
		this.getter = getter;
		this.literal = literal;
		this.operator = operator;
		
	}


	/**
	 * Getter for the getter variable
	 * @return returns the getter variable
	 */
	public IFieldValueGetter getGetter() {
		return getter;
	}

	/**
	 * Getter for the literal variable
	 * @return returns the literal variable
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Getter for the operator variable
	 * @return returns the operator variable
	 */
	public IComparisonOperator getOperator() {
		return operator;
	}

}
