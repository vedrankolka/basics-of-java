package hr.fer.zemris.java.custom.collections;

public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;
	
	public EmptyStackException() {
		
	}
	
	public EmptyStackException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
