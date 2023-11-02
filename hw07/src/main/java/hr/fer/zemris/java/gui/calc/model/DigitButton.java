package hr.fer.zemris.java.gui.calc.model;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * An implementation of a button which adds a digit to the calculator
 * @author Vito Sabalic
 *
 */
public class DigitButton extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A constructor which initializes the button 
	 * @param text The name of the button
	 * @param modelImpl The implementation of the {@link CalcModel} interface
	 */
	public DigitButton(String text, CalcModel modelImpl) {
		super(text);
		
		this.setBackground(Color.LIGHT_GRAY);
		this.setFont(this.getFont().deriveFont(30f));
		
		ActionListener action = a -> {
			JButton b = (JButton)a.getSource();
			modelImpl.insertDigit(Integer.parseInt(b.getText()));
		};
		
		
		this.setFont(this.getFont().deriveFont(30f));
		this.addActionListener(action);
	}

}
