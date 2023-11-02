package hr.fer.zemris.lsystems.impl;


import java.awt.Color;

import hr.fer.oprpp1.custom.collections.Dictionary;
import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;


/**
 * An implementation of a {@link LSystemBuilder} with all the necessary method to create and draw a fractal
 * @author Vito Sabalic
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder{

	
	
	/**
	 * The initial unit Length
	 */
	private double unitLength;
	
	/**
	 * The initial position of the turtle
	 */
	private Vector2D origin;
	
	/**
	 * The initial angle of the turtle
	 */
	private double angle;
	
	
	/**
	 * The initial axiom of the fractal production
	 */
	private String axiom;
	
	
	/**
	 * A scales for the initial unit length
	 */
	private double unitLengthDegreeScaler;
	
	/**
	 * A {@link Dictionary} which contains all the provided productions
	 */
	private Dictionary<Character, String> productions;
	
	
	/**
	 * A {@link Dictionary} which contains all the provided commands
	 */
	private Dictionary<Character, Command> commands;
	
	
	/**
	 * A class which implements the {@link LSystem} interface and has methods to draw a fractal
	 * @author Vito Sabalic
	 *
	 */
	public class LSystemImpl implements LSystem {

		/**
		 * Generates a string of productions based on the provided level
		 */
		@Override
		public String generate(int level) {
			String s = axiom;
			for(int i = 0; i < level; i++) {
				
				char[] data = s.toCharArray();
				s = "";
				for(char c: data) {
					String production = productions.get(c);
					if(production != null) {
						s = s.concat(production);
					}
					else {
						s = s.concat(String.valueOf(c));
					}
				}
			}
			
			
			return s;
		}
		
		/**
		 * Draws the fractal at the provided level of productions
		 */
		@Override
		public void draw(int level, Painter painter) {
			
			double newUnitLength = unitLength * (Math.pow(unitLengthDegreeScaler, level));
			
			Context ctx = new Context();
			
			Vector2D direction = new Vector2D(1, 0);
			direction.rotate(Math.toRadians(angle));
			TurtleState state = new TurtleState(origin, direction, Color.BLACK, newUnitLength);
			
			ctx.pushState(state);
			
			String commandLine = generate(level);
			
			char[] data = commandLine.toCharArray();
			
			for(char c: data) {
				Command command = commands.get(c);
				
				if(command != null) {
					command.execute(ctx, painter);
				}
			}
			
		}
		
	}
	
	/**
	 * A simple constructor which assigns all the variables to their default values
	 */
	public LSystemBuilderImpl() {
		this.productions = new Dictionary<>();
		this.commands = new Dictionary<>();
		this.unitLength = 0.1;
		this.unitLengthDegreeScaler = 1;
		this.origin = new Vector2D(0, 0);
		this.angle = 0;
		this.axiom = "";
	}
	
	
	/**
	 * Sets the unit length to the provided unit length
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}
	
	/**
	 * Sets the origin to the provided origin
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}
	
	/**
	 * Sets the angle to the provided angle
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}
	
	/**
	 * Sets the axiom to the provided axiom
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}
	
	/**
	 * Sets the current unitLengthDegreeScales to the provided value
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}
	
	/**
	 * Adds a production to the productions variable based on the provided symbol
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		this.productions.put(symbol, production);
		return this;
	}
	
	/**
	 * Adds a command to the commands variable based on the provided symbol
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		if(action.contains("draw")) {
			
			double value = extractNumber(action);
			this.commands.put(symbol, new DrawCommand(value));
			
		}
		else if(action.contains("skip")) {
			double value = extractNumber(action);
			this.commands.put(symbol, new SkipCommand(value));
		}
		else if(action.contains("scale")) {
			double value = extractNumber(action);
			this.commands.put(symbol, new ScaleCommand(value));
		}
		else if(action.contains("rotate")) {
			double value = extractNumber(action);
			this.commands.put(symbol, new RotateCommand(value));
		}
		else if(action.contains("push")) {
			
			this.commands.put(symbol, new PushCommand());
			
		}
		else if(action.contains("pop")) {
			this.commands.put(symbol, new PopCommand());
			
		}
		else if(action.contains("color")) {
			
			int value = Integer.valueOf(extractColor(action), 16);
			this.commands.put(symbol, new ColorCommand(value));
	
		}
		return this;
	}
	
	
	/**
	 * Runs the entire configuration of the {@link LSystemBuilderImpl} based on the provided {@link String} array
	 */
	@Override
	public LSystemBuilder configureFromText(String[] lines) {
		for(String line: lines) {
			
			line = line.trim();
			String str = extractKeyWord(line);
			
			if(str.equalsIgnoreCase("origin")) {
				String s = line.replace(str, "");
				s = s.trim();
				double x = extractNumber(s);
				s = s.replaceFirst(String.valueOf(x), "");
				s = s.trim();
				double y = extractNumber(s);
				
				setOrigin(x, y);
				
			}
			else if(str.equalsIgnoreCase("angle")) {
				
				String s = line.replace(str, "");
				s = s.trim();
				double angle = extractNumber(s);
				
				setAngle(angle);
				
			}
			else if(str.equalsIgnoreCase("unitLength")) {
				
				String s = line.replace(str, "");
				s = s.trim();
				double length = extractNumber(s);
				
				setUnitLength(length);
			
			}
			else if(str.equalsIgnoreCase("unitLengthDegreeScaler")) {
				String s = line.replace(str, "");
				s = s.trim();
				double x = extractNumber(s);
				if(s.contains("/")) {
					s = s.substring(s.indexOf("/") + 1, s.length());
					s = s.trim();
					double y = extractNumber(s);
					
					setUnitLengthDegreeScaler(x / y);
					
				}
				else {
					setUnitLengthDegreeScaler(x);
				}
				
			}
			else if(str.equalsIgnoreCase("command")) {
				String s = line.replace(str, "");
				s = s.trim();
				char c = s.toCharArray()[0];
				s = extractCommand(s);
				registerCommand(c, s);
				
			}
			else if(str.equalsIgnoreCase("axiom")) {
				
				String s = line.replace(str, "");
				s = s.trim();
				
				setAxiom(s);
				
			}
			else if(str.equalsIgnoreCase("production")) {
				String s = line.replace(str, "");
				s = s.trim();
				char c = s.toCharArray()[0];
				s = extractCommand(s);
				registerProduction(c, s);
				
			}
		}
		
		
		return this;
	}
	
	/**
	 * Returns a new instance of the {@link LSystemImpl} class
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}
	
	
	/**
	 * Extracts a number from the provided string
	 * @param s The provided string
	 * @return return the number that is found
	 */
	private double extractNumber(String s) {
		char[] data = s.toCharArray();
		int index = 0;
		while(index < data.length) {
			if(Character.isDigit(data[index]) || data[index] == '-') {
				break;
			}
			index++;
		}
		
		boolean negative = false;
		if(data[index] == '-') {
			negative = true;
			index++;
		}
		
		int startIndex = index;
		
		while(index < data.length && Character.isDigit(data[index])) {
			index++;
		}
		
		if(index < data.length && data[index] == '.') {
			index++;
		}
		
		while(index < data.length && Character.isDigit(data[index])) {
			index++;
		}
		
		int endIndex = index;
		
		String value = new String(data, startIndex, endIndex - startIndex);
		
		double number = Double.parseDouble(value);
		if(negative) {
			number = number * -1;
		}
		
		return number;
	}
	
	/**
	 * Extracts the string which represents a color from the provided string
	 * @param s The provided string
	 * @return Return the string which represents a color
	 */
	private String extractColor(String s) {
		s = s.replace("color", "");
		s = s.trim();
		
		return s;
	}
	
	/**
	 * Extracts a command from the provided string
	 * @param s The provided string
	 * @return Returns a string which represents a command
	 */
	private String extractCommand(String s) {
		char[] data = s.toCharArray();
		int index = 0;
		
		index++;
		
		for(int i = index; i < data.length; i++) {
			if(data[index] == '\r' || data[index] == '\n' || data[index] == '\t' || data[index] == ' ') {
				index++;
				continue;
			}
			break;
		}
		
		return new String(data, index, data.length - index);
	}
	
	/**
	 * Extracts a keyword from the provided string
	 * @param s The provided string
	 * @return Returns a string which represents a keyword
	 */
	private String extractKeyWord(String s) {
		char[] data = s.toCharArray();
		
		int index = 0;
		while(index < data.length && Character.isLetter(data[index])) {
			index++;
		}
		
		int endIndex = index;
		
		return new String(data, 0, endIndex);
	}
	
	
}
