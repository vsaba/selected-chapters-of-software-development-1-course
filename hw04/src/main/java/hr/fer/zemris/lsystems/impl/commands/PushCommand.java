package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * A class which implements a method which pushes a copy of the current {@link TurtleState} class to the stack
 * @author Vito Sabalic
 *
 */
public class PushCommand implements Command{
	
	/**
	 * Pushes a copy of the current {@link TurtleState} value to the top of the ctx stack
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
		
		return;
		
	}

}
