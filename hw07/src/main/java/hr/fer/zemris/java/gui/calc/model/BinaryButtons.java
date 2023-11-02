package hr.fer.zemris.java.gui.calc.model;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

/**
 * An implementations which represents a button which computes binary operations. Extends the JButton class.
 * @author Vito Sabalic
 *
 */
public class BinaryButtons extends JButton{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The implementation of the {@link CalcModel} interface
	 */
	private CalcModel model;
	
	
	/**
	 * The action which the button performs
	 */
	private ActionListener action;
	
	/**
	 * The operation the button performs
	 */
	private DoubleBinaryOperator operator;
	
	
	/**
	 * A simple constructor which assigns all the provided values to their respective current values
	 * @param text The provided name
	 * @param model The provided implementation of the {@link CalcModel} interface
	 * @param operator The provided operator
	 */
	public BinaryButtons(String text, CalcModel model, DoubleBinaryOperator operator) {
		super(text);
		
		this.setBackground(Color.LIGHT_GRAY);
		this.model = model;
		this.operator = operator;
		this.action = createListener();
		
		this.addActionListener(action);
	}
	
	
	/**
	 * Inverts the function of the button. Exclusively used for power, x^n, operation.
	 * @param isChecked
	 */
	public void invertFunction(boolean isChecked) {
		
		this.setText(isChecked ? "x^(1/n)" : "x^n");
		
		if(isChecked) {
			operator = BinaryFunctions.ROOT;
		}
		else {
			operator = BinaryFunctions.POW;
		}
		
	}	
	
	/**
	 * Creates a new {@link ActionListener} based on the current operator
	 * @return returns a new {@link ActionListener}
	 */
	public ActionListener createListener() {
		
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.isActiveOperandSet()) {
					double value = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
					
					model.setValue(value);
					model.clearActiveOperand();
					model.setPendingBinaryOperation(null);
				}
				
				model.setActiveOperand(model.getValue());
				model.setPendingBinaryOperation(operator);
				
			}
		};
	}

}
