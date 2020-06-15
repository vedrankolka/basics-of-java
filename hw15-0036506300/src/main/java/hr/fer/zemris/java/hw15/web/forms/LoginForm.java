package hr.fer.zemris.java.hw15.web.forms;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.web.util.Util;

/**
 * A form for filling information about a login attempt.<br>
 * The form also offers methods to fill itself via an obtained request.
 * <p>
 * The form also offers a method {@link LoginForm#validate()} to note errors if anything was incorrectly filled.
 * 
 * @author Vedran Kolka
 *
 */
public class LoginForm extends Form{
	/** Nickname typed in the form */
	private String nick;
	/** Password typed in the form */
	private String password;
	
	/**
	 * Fills its own property values with values from the given {@link HttpServletRequest} <code>req</code>.
	 * @param req
	 * @return itself
	 */
	public LoginForm fillFromRequest(HttpServletRequest req) {
		nick = prepare(req.getParameter("nick"));
		password = prepare(req.getParameter("password"));
		return this;
	}

	/**
	 * Checks if anything is incorrectly filled in the form.
	 * The errors are noted in a map and obtainable via {@link Form#getError(String)} method,
	 * where the key of an error message is the property name.
	 * @return itself
	 */
	public LoginForm validate() {
		clearErrors();
		DAO dao = DAOProvider.getDAO();
		// if there is not a nickname entered, check if the form has not yet been filled, or if it is invalid
		if (nick == null || nick.isBlank()) {
			// if there is no nick but there is a password, write error
			if (password != null && !password.isBlank()) {
				putError("nick", "No nickname provided.");
				// TODO remove
				System.out.println(password);
			// if there is no password, then the form is empty, so record the error but dont write anything
			} else {
				putError("nick", "");
			}
		// else if there is a nickname, check if it exists
		} else if (dao.getBlogUser(nick) == null) {
			putError("nick", "The nickname You entered does not exist.");
		// if it exists check if the password is correct
		} else {
			String storedHash = dao.getBlogUser(nick).getPasswordHash();
			String thisHash = Util.hashPassword(password);
			if (!storedHash.equals(thisHash)) {
				putError("password", "Incorrect password.");
			}
		}
		return this;
	}

	public String getNick() {
		return nick;
	}

}
