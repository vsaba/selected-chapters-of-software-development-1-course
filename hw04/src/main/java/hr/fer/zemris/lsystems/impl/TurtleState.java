package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.oprpp1.math.Vector2D;

/**
 * Class which represents a turtle which draws lines on the panel
 * @author Vito Sabalic
 *
 */
public class TurtleState {
	
	/**
	 * Vector which represents the current location of the turtle
	 */
	private Vector2D current;
	
	/**
	 * Vector which represents which direction the turtle is currently facing
	 */
	private Vector2D direction;
	/**
	 * Represents the color of the line that the turtle draws
	 */
	private Color color;
	/**
	 * Represents the length of the line which will be drawn
	 */
	private double unitLength;
		
	/**
	 * A simple constructor which assigns the given values to all the respective private variables
	 * @param current The given value of the current position
	 * @param direction The given value of the current facing direction
	 * @param color The given value of the new color
	 * @param unitLength The given value of the new unit length
	 */
	public TurtleState(Vector2D current, Vector2D direction, Color color, double unitLength) {
		this.current = current;
		this.color = color;
		this.unitLength = unitLength;
		this.direction = direction;
	}

	/**
	 * Getter for the current position variable
	 * @return returns the current position
	 */
	public Vector2D getCurrent() {
		return current;
	}

	/**
	 * Sets the current position variable to the provided argument
	 * @param current the provided value
	 */
	public void setCurrent(Vector2D current) {
		this.current = current;
	}

	/**
	 * Getter for the current direction variable
	 * @return return the current direction variable
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Sets the current direction to the provided argument
	 * @param direction the provided value
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * Getter for the current color
	 * @return return the current color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the current color to the provided argument
	 * @param color the provided value
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Getter for the current unit length
	 * @return returns the current unit length
	 */
	public double getUnitLength() {
		return unitLength;
	}

	/**
	 * Sets the current unit length to the provided argument
	 * @param unitLength the provided value
	 */
	public void setUnitLength(double unitLength) {
		this.unitLength = unitLength;
	}
	
	/**
	 * Creates a copy of the current turtle state class
	 * @return returns the new copy of the current turtle state class
	 */
	public TurtleState copy() {
		return new TurtleState(this.current, this.direction, this.color, this.unitLength);
	}

}
