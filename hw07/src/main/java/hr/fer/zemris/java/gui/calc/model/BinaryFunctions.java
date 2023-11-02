package hr.fer.zemris.java.gui.calc.model;

import java.util.function.DoubleBinaryOperator;

/**
 * A class which contains all necessary binary functions
 * @author Vito Sabalic
 *
 */
public class BinaryFunctions {
	
	
	/**
	 * Divides the operands, a / b
	 */
	public static DoubleBinaryOperator DIVIDE = (a, b) -> a/b;
	/**
	 * Multiplies the operands, a * b 
	 */
	public static DoubleBinaryOperator MULTIPLY = (a, b) -> a * b;
	/**
	 * Subtracts the operands, a - b
	 */
	public static DoubleBinaryOperator SUB = (a, b) -> a - b;
	/**
	 * Calculates the sum of the operands, a + b
	 */
	public static DoubleBinaryOperator SUM = (a, b) -> a + b;
	/**
	 * Calculates the power, a^b
	 */
	public static DoubleBinaryOperator POW = Math::pow;
	/**
	 * Calculates the n-th root of the operand
	 */
	public static DoubleBinaryOperator ROOT = (a, b) -> Math.pow(a, 1 / b);
	

}
