package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * A class which implements a method which draws a line on the frame
 * @author Vito Sabalic
 *
 */
public class DrawCommand implements Command{
	
	/**
	 * The factor at the which the line will be drawn
	 */
	private double step;
	
	/**
	 * A constructor which accepts the factor at the which the line will be drawn
	 * @param step The provided value
	 */
	public DrawCommand(double step) {
		this.step = step;
	}
	
	/**
	 * Draws a new line on the frame based on the current state of the {@link TurtleState} class which 
	 * is located on the stack of the ctx argument
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState tmp = ctx.getCurrentState();
		
		Vector2D temp = tmp.getDirection().scaled(tmp.getUnitLength() * step);
		
		Vector2D newPos = tmp.getCurrent().added(temp);
		
		painter.drawLine(tmp.getCurrent().getX(), tmp.getCurrent().getY(), newPos.getX(), newPos.getY(), tmp.getColor(), 1f);
		
		tmp.setCurrent(newPos);
		
		return;
		
	}

}
