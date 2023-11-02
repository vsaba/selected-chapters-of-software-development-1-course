package hr.fer.zemris.java.gui.calc.model;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * An implementation which represents the screen of the calculator
 * @author Vito Sabalic
 *
 */
public class CalculatorScreen extends JLabel implements CalcValueListener{

	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	/**
	 * A constructor which initializes the {@link JLabel} implementation
	 * @param model The implementation of the {@link CalcModel} interface
	 */
	public CalculatorScreen(CalcModel model) {
		model.addCalcValueListener(this);
		this.setText(model.toString());
		
		setOpaque(true);
		setHorizontalAlignment(SwingConstants.RIGHT);
		setBackground(Color.YELLOW);
		this.setFont(this.getFont().deriveFont(30f));
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	}
	
	
	
	@Override
	public void valueChanged(CalcModel model) {
		
		String s = model.toString();
		this.setText(s);
	}

}
