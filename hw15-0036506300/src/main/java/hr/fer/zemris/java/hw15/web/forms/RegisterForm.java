package hr.fer.zemris.java.hw15.web.forms;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.web.util.Util;

/**
 * A form for filling information about a single blog user.<br>
 * The form also offers methods to fill itself via an obtained request.
 * <p>
 * The form also offers a method {@link RegisterForm#validate()} to note errors if anything was incorrectly filled.
 * 
 * @author Vedran Kolka
 *
 */
public class RegisterForm extends Form {
	/** regular expresion for checing if an email is valid */
	public static String EMAIL_REGEX = "[\\w\\.]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}";
	/** entered first name of the user */
	private String firstName;
	/** entered last name of the user */
	private String lastName;
	/** entered email of the user */
	private String email;
	/** entered nickname of the user */
	private String nick;
	/** entered password */
	private String password;
	/** entered password again to confirm */
	private String confirmPassword;

	/**
	 * Fills its own property values with values from the given {@link HttpServletRequest} <code>req</code>.
	 * @param req
	 * @return itself
	 */
	public RegisterForm fillFromRequest(HttpServletRequest req) {

		firstName = prepare(req.getParameter("firstName"));
		lastName = prepare(req.getParameter("lastName"));
		email = prepare(req.getParameter("email"));
		nick = prepare(req.getParameter("nick"));
		password = prepare(req.getParameter("password"));
		confirmPassword = prepare(req.getParameter("confirmPassword"));

		return this;
	}

	/**
	 * Fills its information into the given {@link BlogUser} <code>user</code>.
	 * @param user
	 * @return user with updated information provided by the form
	 */
	public BlogUser fillInBlogUser(BlogUser user) {

		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setNick(nick);
		user.setPasswordHash(Util.hashPassword(password));

		return user;
	}

	/**
	 * Checks if anything is incorrectly filled in the form.
	 * The errors are noted in a map and obtainable via {@link Form#getError(String)} method,
	 * where the key of an error message is the property name.
	 * @return itself
	 */
	public RegisterForm validate() {
		
		clearErrors();
		
		// check first name
		if (firstName == null || firstName.isBlank()) {
			putError("firstName", "No first name provided.");
		}
		// check last name
		if (lastName == null || lastName.isBlank()) {
			putError("lastName", "No last name provided.");
		}
		// check email
		if (email == null || email.isBlank()) {
			putError("email", "No email provided.");
		} else if (email.length() > 30) {
			putError("email", "Email is too long.");
		} else if (!email.matches(EMAIL_REGEX)) {
			putError("email", "Invalid email.");
		}
		// check nickname
		if (nick == null || nick.isBlank()) {
			putError("nick", "No nick provided.");
		} else if (nick.length() > 50) {
			putError("nick", "Nickname too long.");
		} else if (DAOProvider.getDAO().getBlogUser(nick) != null) {
			putError("nick", "Nickname is already taken.");
		}
		// check passwords
		if (password == null || password.isBlank()) {
			putError("password", "No password provided.");
		} else if (confirmPassword == null || confirmPassword.isBlank()) {
			putError("confirmPassword", "No confirmation provided.");
		} else if (!password.equals(confirmPassword)) {
			putError("confirmPassword", "The passwords do not match.");
		}

		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getNick() {
		return nick;
	}

	
	
}
