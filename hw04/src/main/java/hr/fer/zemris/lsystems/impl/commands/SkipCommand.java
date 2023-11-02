package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * A class which implements a method which moves the turtle of the {@link TurtleState} but doesn't draw a line
 * @author Vito Sabalic
 *
 */
public class SkipCommand implements Command{
	
	/**
	 * The factor at the which the line would be drawn 
	 */
	private double step;
	
	/**
	 * A constructor which accepts the factor at the which the line would be drawn
	 * @param step The provided value
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	
	/**
	 * Calculates all the necessary variables for a line to be drawn but doesn't draw the line
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState tmp = ctx.getCurrentState();
		
		Vector2D temp = tmp.getDirection().scaled(tmp.getUnitLength() * step);
		
		Vector2D newPos = tmp.getCurrent().added(temp);
		
		tmp.setCurrent(newPos);
		
		return;
	}
}
