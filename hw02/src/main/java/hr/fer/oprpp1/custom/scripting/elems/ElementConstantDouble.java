package hr.fer.oprpp1.custom.scripting.elems;

import hr.fer.oprpp1.custom.scripting.nodes.Node;

/**
 * A class which represents a double value which is stored inside a {@link Node} class
 * @author Vito Sabalic
 *
 */
public class ElementConstantDouble extends Element{

	/**
	 * the double value
	 */
	private double value;
	
	/**
	 * A simple constructor which assigns the provided value to the current double value
	 * @param value the value to be assigned
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return String.valueOf(this.value);
	}
	
	/**
	 * Returns the int value
	 * @return returns the int value
	 */
	public double getValue() {
		return value;
	}
	
	
}
