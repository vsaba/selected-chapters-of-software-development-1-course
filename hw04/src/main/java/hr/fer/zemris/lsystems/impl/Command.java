package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * An interface which represents the command to be executed during the creation of the fractal graph
 * @author Vito Sabalic
 *
 */
public interface Command {
	
	/**
	 * Executes a command
	 * @param ctx The current context
	 * @param painter The painter which paints the lines
	 */
	void execute(Context ctx, Painter painter);

}
