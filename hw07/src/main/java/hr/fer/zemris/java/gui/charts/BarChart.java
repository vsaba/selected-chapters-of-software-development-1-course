package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * A class which is used for the storage of all the key values for the representation of the bar chart
 * @author Vito Sabalic
 *
 */
public class BarChart {
	
	
	/**
	 * A list of {@link XYValue} values
	 */
	private List<XYValue> values;
	
	
	/**
	 * The name of the x axis
	 */
	private String x_name;
	
	/**
	 * The name of the y axis
	 */
	private String y_name;
	
	/**
	 * The minimal y value
	 */
	private int minY;
	
	/**
	 * The maximum y value
	 */
	private int maxY;
	
	/**
	 * The distance between two y values
	 */
	private int deltaY;
	
	/**
	 * A constructor which assigns all the provided values to their respective attributes.
	 * Performs all the necessary checks to ensure the values are correct.
	 * @param values The provided {@link XYValue} values 
	 * @param x_name The provided x axis name
	 * @param y_name The provided y axis name
	 * @param minY The provided minimal Y values
	 * @param maxY The provided maximum Y values
	 * @param deltaY The provided distance between two y values
	 */
	public BarChart(List<XYValue> values, String x_name, String y_name, int minY, int maxY, int deltaY) {
		
		if(minY < 0) {
			throw new IllegalArgumentException("The provided min y value cannot be negative");
		}
	
		if(maxY <= minY) {
			throw new IllegalArgumentException("The provided max y value cannot be smaller than or same as min y value");
		}
		
		values.forEach(a -> {
			if(a.getY() < minY) {
				throw new IllegalArgumentException("The provided y-value from XYValue cannot "
						+ "be smaller than the provided min y value");
			}
		});
		
		while((maxY - minY) % deltaY != 0) {
			maxY++;
		}
		
		this.values = values;
		this.x_name = x_name;
		this.y_name = y_name;
		this.minY = minY;
		this.maxY = maxY;
		this.deltaY = deltaY;
	}

	/**
	 * A getter for the list of {@link XYValue} values
	 * @return return the list of {@link XYValue} values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	
	/**
	 * A getter for the x axis name
	 * @return Returns the x axis name
	 */
	public String getX_name() {
		return x_name;
	}

	/**
	 * A getter for the y axis name
	 * @return Returns the y axis name
	 */
	public String getY_name() {
		return y_name;
	}

	/**
	 * A getter for the minimal y value
	 * @return Returns the minimal y value
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * A getter for the maximum y value
	 * @return Returns the maximum y value
	 */
	public int getMaxY() {
		return maxY;
	}
	
	/**
	 * A getter for the delta y value
	 * @return Returns the delta y value
	 */
	public int getDeltaY() {
		return deltaY;
	}

}
