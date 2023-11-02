package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class which implements a method which changes the color of the line
 * @author Vito Sabalic
 *
 */
public class ColorCommand implements Command{
	
	/**
	 * The new color of the line
	 */
	private int color;
	
	/**
	 * A constructor which accepts the new color
	 * @param color The provided value
	 */
	public ColorCommand(int color) {
		this.color = color;
	}
	
	/**
	 *Changes the color of the next line which will be drawn
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState tmp = ctx.getCurrentState();
		tmp.setColor(new Color(color));
		
		return;
	}

}
