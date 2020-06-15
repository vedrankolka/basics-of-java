package hr.fer.zemris.java.hw15.web.forms;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.model.BlogComment;

/**
 * A form for filling information about a single blog comment.<br>
 * The form also offers methods to fill itself via an obtained request or fill a
 * {@link BlogComment} with its own content.
 * <p>
 * The form also offers a method {@link CommentForm#validate()} to note errors
 * if anything was incorrectly filled.
 * 
 * @author Vedran Kolka
 *
 */
public class CommentForm extends Form {
	/** message of the comment */
	private String message;
	/** user email of the comment author */
	private String usersEMail;

	/**
	 * Fills its own property values with values from the given
	 * {@link HttpServletRequest} <code>req</code>.
	 * 
	 * @param req
	 * @return itself
	 */
	public CommentForm fillFromRequest(HttpServletRequest req) {
		message = prepare(req.getParameter("message"));
		usersEMail = prepare((String) req.getParameter("email"));
		if (usersEMail == null || usersEMail.isBlank()) {
			usersEMail = prepare((String) req.getSession().getAttribute("currentUserNick"));
		}
		return this;
	}

	/**
	 * Fills its information into the given {@link BlogComment} <code>toFill</code>.
	 * 
	 * @param toFill
	 * @return the filled BlogComment
	 */
	public BlogComment fillInComment(BlogComment toFill) {
		toFill.setMessage(message);
		toFill.setUsersEMail(usersEMail);
		return toFill;
	}

	/**
	 * Checks if anything is incorrectly filled in the form. The errors are noted in
	 * a map and obtainable via {@link Form#getError(String)} method, where the key
	 * of an error message is the property name.
	 * 
	 * @return itself
	 */
	public CommentForm validate() {
		clearErrors();
		if (message == null || message.isBlank()) {
			putError("message", "Message cannot be empty.");
		}
		if (usersEMail == null || usersEMail.isBlank()) {
			putError("email", "Email cannot be empty.");
		} else if (!usersEMail.matches(RegisterForm.EMAIL_REGEX)) {
			putError("email", "Email is not valid");
		}
		return this;
	}

	public String getMessage() {
		return message;
	}

	public String getUsersEMail() {
		return usersEMail;
	}

}
