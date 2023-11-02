package hr.fer.oprpp1.math;

/**
 * A simple implementation of a 2D vector
 * @author Vito Sabalic
 *
 */
public class Vector2D {
	
	/**
	 * The x value of the vector
	 */
	private double x;
	
	/**
	 * The y value of the vector
	 */
	private double y;
	
	/**
	 * A simple constructor which assigns the provided x and y values to the current x and y values
	 * @param x The provided x value
	 * @param y The provided y value
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * A getter for the x value
	 * @return Returns the x value
	 */
	public double getX() {
		return x;
	}

	/**
	 * A getter for the y value
	 * @return Returns the y value
	 */
	public double getY() {
		return y;
	}
	

	/**
	 * Adds the x and y value from the provided {@link Vector2D} to the respective values of this vector
	 * @param offset The vector to be added to this vector
	 */
	public void add(Vector2D offset) {
		this.x += offset.getX();
		this.y += offset.getY();
		
	}
	
	/**
	 * Adds the x and y value from the provided {@link Vector2D} to the respective values of this vector
	 * @param offset The vector to be added to this vector
	 * @return Returns a new instance of a {@link Vector2D} with the newly calculated x and y values
	 */
	public Vector2D added(Vector2D offset) {
		return new Vector2D(this.x + offset.getX(), this.y + offset.getY());
	}
	
	/**
	 * Rotates the vector for the given angle
	 * @param angle The angle at which the vector rotates
	 */
	public void rotate(double angle) {
		double newX = x * Math.cos(angle) - y * Math.sin(angle);
		
		double newY = x * Math.sin(angle) + y * Math.cos(angle);
		
		this.x = newX;
		this.y = newY;
	}
	
	/**
	 * Rotates the vector for the given angle
	 * @param angle The angle at which the vector rotates
	 * @return Returns a new instance of a {@link Vector2D} with the newly calculated x and y values
	 */
	public Vector2D rotated(double angle) {
						
		return new Vector2D(x * Math.cos(angle) - y * Math.sin(angle), x * Math.sin(angle) + y * Math.cos(angle));
	}
	
	/**
	 * Multiplies the x and y values with the provided argument. 
	 * @param scaler The number to multiply the vector
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}
	
	/**
	 * Multiplies the x and y values with the provided argument.
	 * @param scaler The number to multiply the vector
	 * @return Returns a new instance of a {@link Vector2D} with the newly calculated x and y values
	 */
	public Vector2D scaled(double scaler) {

		return new Vector2D(x * scaler, y * scaler);
	}
	
	/**
	 * Creates a new instance of the current vector
	 * @return Returns the new instance of the {@link Vector2D} class
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}

}
