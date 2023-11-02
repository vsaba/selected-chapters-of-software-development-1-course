package hr.fer.oprpp1.custom.scripting.elems;

import hr.fer.oprpp1.custom.scripting.nodes.Node;

/**
 * A class which represents an integer which is stored inside a {@link Node} class
 * @author Vito Sabalic
 *
 */
public class ElementConstantInteger extends Element{

	/**
	 * the int value
	 */
	private int value;
	
	/**
	 * A simple constructor which assigns the provided value to the current int value
	 * @param value the value to be assigned
	 */
	public ElementConstantInteger(int value) {
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
	public int getValue() {
		return value;
	}
}
