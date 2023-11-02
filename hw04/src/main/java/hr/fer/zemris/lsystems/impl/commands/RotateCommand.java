package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * A class which implements a method which rotates the direction vector of the current {@link TurtleState} class instance
 * @author Vito Sabalic
 *
 */
public class RotateCommand implements Command{

	/**
	 * The angle for which the vector will be rotated
	 */
	private double angle;
	
	/**
	 * A simple constructor which accepts an angle for which the vector will be rotated
	 * @param angle the provided value
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}
	
	/**
	 * Rotates the direction vector of the {@link TurtleState} class instance at the top of the ctx stack
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState tmp = ctx.getCurrentState();
		
		
		tmp.setDirection(tmp.getDirection().rotated(Math.toRadians(angle)));
	}
}
