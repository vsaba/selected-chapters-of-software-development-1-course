package hr.fer.zemris.java.gui.calc.model;

import java.util.function.DoubleUnaryOperator;

/**
 * A class which contains all necessary unary operations
 * @author Vito Sabalic
 *
 */
public class UnaryFunctions {

	/**
	 * Calculates the inverse
	 */
	public static DoubleUnaryOperator INVERT = a -> 1 / a;
	
	/**
	 * Calculates the sin
	 */
	public static DoubleUnaryOperator SIN = Math::sin;
	/**
	 * Calculates the cos
	 */
	public static DoubleUnaryOperator COS = Math::cos;
	/**
	 * Calculates the tan
	 */
	public static DoubleUnaryOperator TAN = Math::tan;
	/**
	 * Calculates the ctg
	 */
	public static DoubleUnaryOperator CTG = a -> 1 / Math.tan(a);
	/**
	 * Calculates the ln
	 */
	public static DoubleUnaryOperator LN = Math::log;
	/**
	 * Calculates the log
	 */
	public static DoubleUnaryOperator LOG = Math::log10;
	
	/**
	 * Calculates the arcsin
	 */
	public static DoubleUnaryOperator ARCSIN = Math::asin;
	/**
	 * Calculates the arccos
	 */
	public static DoubleUnaryOperator ARCCOS = Math::acos;
	/**
	 * Calculates the arctan
	 */
	public static DoubleUnaryOperator ARCTAN = Math::atan;
	/**
	 * Calculates the arcctg
	 */
	public static DoubleUnaryOperator ARCCTG = a -> 1 / Math.atan(a);
	/**
	 * Calculates the n-th power of 10
	 */
	public static DoubleUnaryOperator POW10 = a -> Math.pow(10, a);
	/**
	 * Calculates the n-th power of e
	 */
	public static DoubleUnaryOperator EPOW = a -> Math.pow(Math.E, a);
	
}
