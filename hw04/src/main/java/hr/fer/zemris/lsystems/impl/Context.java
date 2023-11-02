package hr.fer.zemris.lsystems.impl;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Class which is used to maintain and handle different instances of the {@link TurtleState} class
 * @author Vito Sabalic
 *
 */
public class Context {
	
	/**
	 * The stack used to maintain isntances of the {@link TurtleState} class
	 */
	ObjectStack<TurtleState> stack;
	
	/**
	 * A simple constructor
	 */
	public Context() {
		this.stack = new ObjectStack<>();
	}
	
	/**
	 * Getter for the state currently at the top of the stack
	 * @return the state currently at the top of the stack
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	
	/**
	 * Pushes the provided TurtleState instance to the top of the stack
	 * @param state The provided TurtleState instance
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
		
		return;
	}
	
	/**
	 * Removes the state which is currently at the top of the stack
	 */
	public void popState() {
		stack.pop();
		
		return;
	}

}
