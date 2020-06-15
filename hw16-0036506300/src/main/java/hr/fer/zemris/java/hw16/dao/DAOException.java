package hr.fer.zemris.java.hw16.dao;
/**
 * Exception throw by {@link DAO} implementations.
 * @author Vedran Kolka
 *
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message) {
		super(message);
	}
	
	public DAOException(Throwable cause) {
		super(cause);
	}
	
	public DAOException() {
	}
}