package hr.fer.zemris.java.hw06.shell;
/**
 * An exception thrown by ShellCommands if they fail to communicate with the Environment.
 * @author Vedran Kolka
 * @version 1.0.0
 */
public class ShellIOException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ShellIOException() {
		
	}
	
	public ShellIOException(String message) {
		super(message);
	}
	
}
