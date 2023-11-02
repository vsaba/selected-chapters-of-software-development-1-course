package hr.fer.oprpp1.hw04.db;

/**
 * A class which is implemented as a strategy for the {@link IComparisonOperator} interface
 * @author Vito Sabalic
 *
 */
public class ComparisonOperators {
	
	/**
	 * Returns true if the first argument is smaller than the second argument
	 */
	public static final IComparisonOperator LESS = (value1, value2) -> value1.compareTo(value2) < 0;
	
	/**
	 * Returns true if the first argument is smaller or equal than the second argument
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) <= 0;
	
	/**
	 * Returns true if the first argument is larger than the second argument
	 */
	public static final IComparisonOperator GREATER = (value1, value2) -> value1.compareTo(value2) > 0;
	
	/**
	 * Returns true if the first argument is larger or equal than the second argument
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) >= 0;
	
	/**
	 * Returns true if the first argument is equal than the second argument
	 */
	public static final IComparisonOperator EQUALS = (value1, value2) -> value1.compareTo(value2) == 0;
	
	/**
	 * Returns true if the first argument is not equal than the second argument
	 */
	public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> value1.compareTo(value2) != 0;
	

	/**
	 * Returns true if the first argument is structurally equal to the second argument
	 */
	public static final IComparisonOperator LIKE = new IComparisonOperator() {
		
		@Override
		public boolean satisfied(String value1, String value2) {			
			
			if(value2.contains("*")) {
				value2 = value2.replace("*", ".*");
			}
			
			value1 = value1.toLowerCase();
			value2 = value2.toLowerCase();
			
			return value1.matches(value2);
		}
	};

}
