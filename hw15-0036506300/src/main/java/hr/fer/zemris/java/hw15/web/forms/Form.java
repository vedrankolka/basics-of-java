package hr.fer.zemris.java.hw15.web.forms;

import java.util.HashMap;
import java.util.Map;

/**
 * An abstract form that offers a few methods for handeling errors of properties
 * of a concrete form implementation.
 * 
 * @author Vedran Kolka
 *
 */
public abstract class Form {
	/** map of error messages mapped by their respective properties */
	private Map<String, String> errors = new HashMap<>();

	/**
	 * Returns the error message with key <code>errorKey</code>.
	 * 
	 * @param errorKey
	 * @return error message mapped by the errorKey
	 */
	public String getError(String errorKey) {
		return errors.get(errorKey);
	}

	/**
	 * Maps the message <code>value</code> by the given <code>key</code>.
	 * @param key
	 * @param value
	 */
	protected void putError(String key, String value) {
		errors.put(key, value);
	}

	/**
	 * Clears all error messages
	 */
	protected void clearErrors() {
		errors.clear();
	}

	/**
	 * Returns <code>true</code> if the form has any errors noted in the map, <code>false</code> otherwise
	 * @return <code>true</code> if the form has any errors noted in the map, <code>false</code> otherwise
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	/**
	 * Makes sure the stored value is trimmed and not <code>null</code>.
	 * @param s
	 * @return trimmed s, or empty string if s was <code>null</code>
	 */
	protected String prepare(String s) {
		return s == null ? "" : s.trim();
	}

}
