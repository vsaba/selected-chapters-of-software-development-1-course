package hr.fer.zemris.java.gui.layouts;

/**
 * Represents a simple point position on the layout
 * @author Vito Sabalic
 *
 */
public class RCPosition {
	
	
	/**
	 * The x value
	 */
	private int x;
	
	
	/**
	 * The y value
	 */
	private int y;
	
	/**
	 * A simple constructor which assigns the provided values to their respective current values
	 * @param x The provided x value
	 * @param y The provided y value
	 */
	public RCPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * Creates an {@link RCPosition} instance based on the provided string
	 * @param text The provided string
	 * @return returns a new {@link RCPosition} instance
	 */
	public static RCPosition parse(String text) {
		
		String[] s = text.replaceAll(" ", "").split(",");
		
		
		int x;
		int y;
		
		try {
			x = Integer.parseInt(s[0]);
			y = Integer.parseInt(s[1]);
		}
		catch(NumberFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		
		return new RCPosition(x, y);
	}


	/**
	 * A getter for the x value
	 * @return returns the x value
	 */
	public int getX() {
		return x;
	}


	/**
	 * A getter for the y value
	 * @return returns the y value
	 */
	public int getY() {
		return y;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof RCPosition)) {
			return false;
		}
		
		RCPosition pos = (RCPosition) obj;
		
		return this.getX() == pos.getX() && this.getY() == pos.getY();
	}
	

}
