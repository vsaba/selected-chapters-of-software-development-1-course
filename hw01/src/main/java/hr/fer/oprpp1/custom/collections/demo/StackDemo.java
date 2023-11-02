package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {
	
	public static void main(String[] args) {
		
		
		String[] expressions = args[0].split(" ");
		
		ObjectStack stack = new ObjectStack();
		stack.clear();
		
		for(String expression : expressions) {
			try {
				
				int number = Integer.parseInt(expression);
				stack.push(number);
				
			}
			catch(NumberFormatException e){
				
				if(expression.equals("+") || expression.equals("-") || expression.equals("/") || expression.equals("*") 
						|| expression.equals("%")) {
					
					int operatedNumber2 = (int)stack.pop();
					int operatedNumber1 = (int)stack.pop();
					
					switch(expression) {
					case "+":
						stack.push(operatedNumber1 + operatedNumber2);
						break;
						
					case "-":
						stack.push(operatedNumber1 - operatedNumber2);
						break;
					
					case "/":
						stack.push(operatedNumber1/operatedNumber2);
						break;
						
					case "*":
						stack.push(operatedNumber1*operatedNumber2);
						break;
					
					case "%":
						stack.push(operatedNumber1%operatedNumber2);
						break;
						
					default:
						break;
					}
					
					
				}
				else {
					throw new IllegalArgumentException("The provided argument cannot be used in an expression");
				}	
			}
		}
		
		if(stack.size() != 1) {
			System.err.println("Something went wrong");
		}
		else {
			System.out.println("The result is: " + stack.pop());
		}
	}
	
}
