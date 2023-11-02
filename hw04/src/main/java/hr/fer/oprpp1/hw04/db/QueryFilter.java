package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * A class which represents a filter for the {@link StudentDatabase} class
 * @author Vito Sabalic
 *
 */
public class QueryFilter implements IFilter{

	/**
	 * A list of Conditional expressions
	 */
	private List<ConditionalExpression> expressions;
	
	/**
	 * A simple constructor which assigns the provided value to the current varaible
	 * @param expressions
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}
	
	
	@Override
	public boolean accepts(StudentRecord record) {
		for(ConditionalExpression e : expressions) {
			if(!(e.getOperator().satisfied(e.getGetter().get(record), e.getLiteral()))) {
				return false;
			}
		}
		return true;
	}

}
