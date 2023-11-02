package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * A class which implements a method which multiplies the current unit length with the provided factor
 * @author Vito Sabalic
 *
 */
public class ScaleCommand implements Command{
	
	/**
	 * The factor which multiplies the unit length
	 */
	private double factor;
	
	/**
	 * A simple constructor which accepts a factor that multiplies the unit length
	 * @param factor the provided value
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	/**
	 * Executes a command which multiplies the current value of the unit length variable of the {@link TurtleState} 
	 * class instance currently situated at the top of the ctx stack
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState tmp = ctx.getCurrentState();
		
		tmp.setUnitLength(tmp.getUnitLength() * factor);
		
		return;
	}
}
