package hr.fer.zemris.java.gui.calc.model;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * An implementation which creates the GUI for the implemented calculator.
 * The main method is used as the EDT
 * @author Vito Sabalic
 *
 */
public class Calculator extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The implementation of the {@link CalcModel} interface
	 */
	private CalcModel model;
	
	/**
	 * A stack which is used in the calculator
	 */
	private Stack<Double> stack;
	
	
	/**
	 * A constructor which initiates the calculator and its GUI
	 */
	public Calculator() {
		super("Calculator");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		model = new CalcModelImpl();
		stack = new Stack<>();
		initGui();
	}
	
	/**
	 * Initializes the GUI
	 */
	private void initGui() {
		
		
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(4));
		
		
		JButton equalsButton = new JButton("=");
		ActionListener action = a -> {
			
			if(model.getPendingBinaryOperation() == null) {
				return;
			}
			
			double value = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
			model.clearActiveOperand();
			model.setPendingBinaryOperation(null);
			model.clear();
			
			model.setValue(value);
			
		};
		equalsButton.addActionListener(action);
		equalsButton.setBackground(Color.LIGHT_GRAY);

		JButton clearButton = new JButton("clr");
		clearButton.addActionListener(a -> model.clear());
		clearButton.setBackground(Color.LIGHT_GRAY);
		
		JButton clearAllButton = new JButton("reset");
		clearAllButton.addActionListener(a -> model.clearAll());
		clearAllButton.setBackground(Color.LIGHT_GRAY);
		
		JButton swapButton = new JButton("+/-");
		swapButton.addActionListener(a -> model.swapSign());
		swapButton.setBackground(Color.LIGHT_GRAY);
		
		JButton pointButton = new JButton(".");
		pointButton.addActionListener(a -> model.insertDecimalPoint());
		pointButton.setBackground(Color.LIGHT_GRAY);
		
		
		JButton pushButton = new JButton("push");
		pushButton.addActionListener(a -> stack.push(model.getValue()));
		pushButton.setBackground(Color.LIGHT_GRAY);
		
		
		JButton popButton = new JButton("pop");
		popButton.addActionListener(a -> model.setValue(stack.pop()));
		popButton.setBackground(Color.LIGHT_GRAY);
		
		cp.add(new CalculatorScreen(model), "1,1");
		cp.add(equalsButton, "1,6");
		cp.add(clearButton, "1,7");
		cp.add(clearAllButton, "2,7");
		cp.add(swapButton, "5, 4");
		cp.add(pointButton, "5,5");
		cp.add(pushButton, "3,7");
		cp.add(popButton, "4,7");
		
		
		
		
		List<UnaryButtons> unaryButtonsList = new ArrayList<>();
		
		unaryButtonsList.add(new UnaryButtons("1/x", model, UnaryFunctions.INVERT, "1/x", UnaryFunctions.INVERT));
		unaryButtonsList.add(new UnaryButtons("sin", model, UnaryFunctions.SIN, "arcsin", UnaryFunctions.SIN));
		unaryButtonsList.add(new UnaryButtons("log", model, UnaryFunctions.LOG, "10^x", UnaryFunctions.POW10));
		unaryButtonsList.add(new UnaryButtons("cos", model, UnaryFunctions.COS, "arccos", UnaryFunctions.ARCCOS));
		unaryButtonsList.add(new UnaryButtons("ln", model, UnaryFunctions.LN, "e^x", UnaryFunctions.EPOW));
		unaryButtonsList.add(new UnaryButtons("tan", model, UnaryFunctions.TAN, "arctg", UnaryFunctions.ARCTAN));
		unaryButtonsList.add(new UnaryButtons("ctg", model, UnaryFunctions.CTG, "arcctg", UnaryFunctions.ARCCTG));
		
		cp.add(unaryButtonsList.get(0), "2, 1"); 
		cp.add(unaryButtonsList.get(1), "2, 2");
		cp.add(unaryButtonsList.get(2), "3, 1");
		cp.add(unaryButtonsList.get(3), "3, 2");
		cp.add(unaryButtonsList.get(4), "4, 1");
		cp.add(unaryButtonsList.get(5), "4, 2");
		cp.add(unaryButtonsList.get(6), "5, 2");
		
		
		BinaryButtons power = new BinaryButtons("x^n", model, BinaryFunctions.POW);
		
		cp.add(power, "5,1");
		cp.add(new BinaryButtons("/", model, BinaryFunctions.DIVIDE), "2,6");
		cp.add(new BinaryButtons("*", model, BinaryFunctions.MULTIPLY), "3,6");
		cp.add(new BinaryButtons("-", model, BinaryFunctions.SUB), "4,6");
		cp.add(new BinaryButtons("+", model, BinaryFunctions.SUM), "5,6");
		
		
		JCheckBox checkbox = new JCheckBox("Inv");
		checkbox.setBackground(Color.LIGHT_GRAY);
		checkbox.addActionListener(a -> {
			for(UnaryButtons buttons : unaryButtonsList) {
				buttons.invertFunction();
			}
			
			power.invertFunction(checkbox.isSelected());
			
		});
		cp.add(checkbox, "5,7");
		
		cp.add(new DigitButton("0", model), "5,3");
		cp.add(new DigitButton("1", model), "4,3");
		cp.add(new DigitButton("2", model), "4,4");
		cp.add(new DigitButton("3", model), "4,5");
		cp.add(new DigitButton("4", model), "3,3");
		cp.add(new DigitButton("5", model), "3,4");
		cp.add(new DigitButton("6", model), "3,5");
		cp.add(new DigitButton("7", model), "2,3");
		cp.add(new DigitButton("8", model), "2,4");
		cp.add(new DigitButton("9", model), "2,5");
		
		
	}
	
	
	/**
	 * The main used as the EDT
	 * @param args The provided arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
	}
	

}
