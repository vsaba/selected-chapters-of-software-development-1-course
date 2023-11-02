package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * A class which implements a method which removes a state from the {@link Context} stack
 * @author Vito Sabalic
 *
 */
public class PopCommand implements Command{
	
	/**
	 * Removes the state which is currently at the top of the ctx stack
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
		return;
	}

}
