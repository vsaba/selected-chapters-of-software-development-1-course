package hr.fer.zemris.java.gui.calc.model;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;

/**
 * An implementations which represents a button which computes unary operations. Extends the JButton class.
 * @author Vito Sabalic
 *
 */
public class UnaryButtons extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * The name of the button
	 */
	private String name;
	/**
	 * The inverted name of the button
	 */
	private String invertedName;
	/**
	 * The {@link CalcModel} implementation
	 */
	private CalcModel model;
	/**
	 * The action to be performed
	 */
	private ActionListener action;
	/**
	 * The operation the button performs
	 */
	private DoubleUnaryOperator operator;
	/**
	 * The inverted operation the button performs
	 */
	private DoubleUnaryOperator invertedOperator;
	
	
	/**
	 * A simple constructor which assigns all the provided values to their respective current values
	 * @param name The provided name
	 * @param model The provided {@link CalcModel} implementation
	 * @param operator The provided operator
	 * @param invertedName The provided inverted name
	 * @param invertedOperator The provided inverted operator
	 */
	public UnaryButtons(String name, CalcModel model, DoubleUnaryOperator operator, String invertedName, DoubleUnaryOperator invertedOperator) {
		super(name);
		
		this.setBackground(Color.LIGHT_GRAY);
		
		this.name = name;
		this.invertedName = invertedName;
		this.model = model;
		this.operator = operator;
		this.invertedOperator = invertedOperator;
		this.action = createAction();
		
		this.addActionListener(action);
		
	}
	
	
	/**
	 * Inverts the name and operation of the button
	 */
	public void invertFunction() {
		String temp = name;
		name = invertedName;
		invertedName = temp;
		
		DoubleUnaryOperator tmp = operator;
		operator = invertedOperator;
		invertedOperator = tmp;
		
		
		this.setText(name);
		this.addActionListener(getAction());
	}
	
	
	
	
	/**
	 * Creates an action based on the operator value
	 * @return
	 */
	private ActionListener createAction() {
		
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setValue(operator.applyAsDouble(model.getValue()));
				
			}
		};
	}

}
