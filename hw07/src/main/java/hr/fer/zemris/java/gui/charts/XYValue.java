package hr.fer.zemris.java.gui.charts;

/**
 * A class representing a simple x, y point 
 * @author Vito Sabalic
 *
 */
public class XYValue {
	
	/**
	 * The x value
	 */
	private int x;
	
	
	/**
	 * The y value
	 */
	private int y;
	
	/**
	 * A simple constructor which assigns the provided values to their respective values
	 * @param x
	 * @param y
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * A getter for the x value
	 * @return Returns the x value
	 */
	public int getX() {
		return x;
	}

	/**
	 * A getter for the y value
	 * @return Returns the y value
	 */
	public int getY() {
		return y;
	}


}
