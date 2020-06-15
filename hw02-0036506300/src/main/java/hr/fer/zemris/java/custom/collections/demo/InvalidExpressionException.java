package hr.fer.zemris.java.custom.collections.demo;

public class InvalidExpressionException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
private String message;
	
	public InvalidExpressionException() {
		
	}
	
	public InvalidExpressionException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

}
