package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * A command-line application which reads a single command-line argument: a postfix notation expression to evaluate.
 * The argument must be enclosed in quotation marks.
 * Example of a valid expression: "8 4 /" .
 * @author Vedran Kolka
 *
 */
public class StackDemo {

	private static final String[] VALID_OPERANDS = {"+", "-", "/", "*", "%"};
	
	public static void main(String[] args) {
		//ends the program if the number of arguments is incorrect
		if(args.length!=1) {
			System.out.println("Number of arguments should be one.");
			return;
		}
		args[0] = args[0].trim();
		String[] elements = args[0].split("\\s+");

		try {
			int result = evaluate(elements);
			System.out.println(args[0] + " = " + result);
		} catch(InvalidExpressionException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * Evaluates the given expression given through <code>elements</code>.
	 * @param elements
	 * @return value of expression
	 * @throws InvalidExpressionException
	 */
	public static int evaluate(String[] elements) {
		
		ObjectStack stack = new ObjectStack();
		for(int i = 0 ; i< elements.length ; ++i) {
			try {
				int number = Integer.parseInt(elements[i]);
				stack.push(number);
			} catch(NumberFormatException e) {
				try {
					int temporaryResult = operate((Integer)stack.pop(), (Integer)stack.pop(), elements[i]);
					stack.push(temporaryResult);
				} catch(EmptyStackException ex) {
					throw new InvalidExpressionException("Invalid expression.");
				}
			}

		}
		if(stack.size()!=1) {
			throw new InvalidExpressionException("Number of operands and operators was not correct.");
		}
		
		return (Integer) stack.pop();
	}
	
	/**
	 * Carries out the <code>operation</code> among <code>first</code> and <code>second</code>.
	 * @param second
	 * @param first
	 * @param operation
	 * @return result of the operation
	 * @throws InvalidExpressionException if <code>operation</code> is not supported
	 */
	private static int operate(int second, int first, String operation) {
		int i = -1;
		//finding the chosen operation
		for(i = 0 ; i<VALID_OPERANDS.length ; ++i) {
			if(VALID_OPERANDS[i].equals(operation)) {
				break;
			}
		}
		if(i==-1) {
			throw new InvalidExpressionException( "'" + operation + "' is not a supported operation.");
		}
		switch(i) {
		case 0: 
			return first + second;
		case 1:
			return first - second;
		case 2:
		case 4:
			if(second==0) {
				throw new InvalidExpressionException("Cannot divide by zero");
			}
			return i==2 ? first / second : first % second;
		case 3:
			return first * second;
		default:
			throw new RuntimeException("Unexpected operation.");
			
		}
	}
	
}
