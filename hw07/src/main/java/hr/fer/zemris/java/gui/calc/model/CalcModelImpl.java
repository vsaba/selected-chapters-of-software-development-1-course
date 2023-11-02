package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * An implementation of the {@link CalcModel} interface
 * @author Vito Sabalic
 *
 */
public class CalcModelImpl implements CalcModel{
	
	/**
	 * True if calculator is editable, false otherwise
	 */
	private boolean editable;
	
	/**
	 * True if current number is negative, false otherwise
	 */
	private boolean isNegative;
	
	/**
	 * The current value in String format
	 */
	private String value;
	
	/**
	 * The current frozen value
	 */
	private String frozenValue;
	
	/**
	 * The current value in numeric format
	 */
	private double numericValue;
	
	/**
	 * The current active operand
	 */
	private double activeOperand;
	
	/**
	 * The current pending operator
	 */
	private DoubleBinaryOperator pendingOperator;
	
	/**
	 * List of all listeners
	 */
	private List<CalcValueListener> listeners;


	
	/**
	 * Constructor which initializes the implementation
	 */
	public CalcModelImpl() {
		editable = true;
		isNegative = false;
		value = "";
		frozenValue = null;
		activeOperand = Double.NaN;
		pendingOperator = null;
		this.listeners = new ArrayList<>();
	}
	
	@Override
	public void addCalcValueListener(CalcValueListener l) {

		listeners.add(l);
		
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		
		listeners.remove(l);
		
	}

	@Override
	public double getValue() {
		return isNegative ? -1 * this.numericValue : this.numericValue;
	}

	@Override
	public void setValue(double value) {
		
		this.numericValue = Math.abs(value);
		this.frozenValue = String.valueOf(value);
		
		this.value = "";
		
		this.editable = false;
		this.isNegative = value < 0;
		
		informListeners();
		
		return;
		
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		this.value = "";
		this.numericValue = 0;
		this.isNegative = false;
		
		informListeners();
		
	}

	@Override
	public void clearAll() {
		
		editable = true;
		isNegative = false;
		value = "";
		numericValue = 0;
		frozenValue = null;
		activeOperand = Double.NaN;
		pendingOperator = null;
		
		informListeners();
		
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		
		if(!editable) {
			throw new CalculatorInputException("Editing while the calculator is not editable is not allowed");
		}
		
		this.isNegative = !this.isNegative;
		
		informListeners();
		
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		
		if(!editable) {
			throw new CalculatorInputException("Editing while the calculator is not editable is not allowed");
		}
		
		if(this.value.isEmpty()) {
			throw new CalculatorInputException("The value is not set!");
		}
		
		if(this.value.contains(".")) {
			throw new CalculatorInputException("The value already has a decimal point");
		}
		
		this.value += ".";
		
		informListeners();
		
		return;
		
		
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		
		if(!editable) {
			throw new CalculatorInputException("Editing while the calculator is not editable is not allowed");
		}
		
		String pom = this.value + String.valueOf(digit);
		double newNumeric;
		
		try {
			newNumeric = Double.parseDouble(pom);
		}
		catch(NumberFormatException e) {
			throw new CalculatorInputException("Invalid digit entered!");
		}
		
		this.value = pom;
		this.numericValue = newNumeric;
		
		if(this.value.length() >= 309) {
			throw new CalculatorInputException("The number is too large");
		}
		
		informListeners();
		
		return;
		
	}

	@Override
	public boolean isActiveOperandSet() {
		
		return !(Double.isNaN(activeOperand));
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		
		if(Double.isNaN(this.activeOperand)) {
			throw new IllegalStateException("The active operand is not set");
		}
		
		return this.activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		if(Double.isNaN(activeOperand)) {
			throw new IllegalArgumentException();
		}
		
		this.activeOperand = activeOperand;
		this.value = "";
		this.frozenValue = null;
		this.isNegative = false;
		this.numericValue = 0;
		this.editable = true;
		
	}

	@Override
	public void clearActiveOperand() {
		this.activeOperand = Double.NaN;
		
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		
		return this.pendingOperator;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		
		this.pendingOperator = op;
		
	}

	@Override
	public void freezeValue(String value) {
		this.frozenValue = value;
		this.editable = false;
		
	}

	@Override
	public boolean hasFrozenValue() {
		
		return this.frozenValue != null;
	}
	
	@Override
	public String toString() {
		
		if(this.frozenValue != null) {
			return this.frozenValue;
		}
		
		if(this.value.isEmpty() || this.numericValue == 0) {
			return isNegative ? "-0" : "0";
		}
		
		this.value = removeLeadingZeroes(this.value);
		
		return isNegative ? "-" + this.value : this.value;
	}
	
	
	
	/**
	 * Removes leading zeroes from the provided String value. Takes into account decimal points.
	 * @param value The provided String value
	 * @return Returns a String without leading zeroes.
	 */
	private String removeLeadingZeroes(String value) {
		
		char data[] = value.toCharArray();
		int index = 0;
		
		
		while(index < data.length && data[index] == '0' && data[index] != '.') {
			index++;
		}
		
		if(index == 0) {
			return value;
		}
		
		if(value.contains(".")) {
			index --;
		}
		 
		
		return value.substring(index, value.length());
	}
	
	/**
	 * Informs the saved listeners that a value has changed
	 */
	private void informListeners() {
		for(CalcValueListener l : listeners) {
			l.valueChanged(this);
		}
	}
	
	

}
