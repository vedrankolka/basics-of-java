package hr.fer.zemris.java.hw15.web.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.model.BlogEntry;

/**
 * A form for filling information about a single blog entry.<br>
 * The form also offers methods to fill itself via an obtained request or fill a
 * {@link BlogEntry} be its own content.
 * <p>
 * The form also offers a method {@link BlogEntryForm#validate()} to note errors if anything was incorrectly filled.
 * 
 * @author Vedran Kolka
 *
 */
public class BlogEntryForm extends Form {
	/** title of the blog entry */
	private String title;
	/** text of the blog entry */
	private String text;

	/**
	 * Fills its own property values with values from the given {@link HttpServletRequest} <code>req</code>.
	 * @param req
	 * @return itself
	 */
	public BlogEntryForm fillFromRequest(HttpServletRequest req) {
		title = prepare(req.getParameter("title"));
		text = prepare(req.getParameter("text"));
		return this;
	}

	/**
	 * Fills the title and text from the form and the current date with
	 * {@link BlogEntry#setLastModifiedAt(Date)} to the given {@link BlogEntry}
	 * <code>be</code>.
	 * 
	 * @param be
	 * @return the given <code>be</code> with updated information
	 */
	public BlogEntry fillInBlogEntry(BlogEntry be) {
		be.setTitle(title);
		be.setText(text);
		be.setLastModifiedAt(new Date());
		return be;
	}

	/**
	 * Fills its information from the given {@link BlogEntry} <code>be</code>.
	 * @param be
	 * @return itself
	 */
	public BlogEntryForm fillFromBlogEntry(BlogEntry be) {
		title = prepare(be.getTitle());
		text = prepare(be.getText());
		return this;
	}

	/**
	 * Checks if anything is incorrectly filled in the form.
	 * The errors are noted in a map and obtainable via {@link Form#getError(String)} method,
	 * where the key of an error message is the property name.
	 * @return itself
	 */
	public BlogEntryForm validate() {
		clearErrors();

		if (title == null || title.isBlank()) {
			putError("title", "Title cannot be empty.");
		}

		if (text == null || text.isBlank()) {
			putError("text", "Text cannot be empty.");
		}

		return this;
	}

	public String getTitle() {
		return title;
	}

	public String getText() {
		return text;
	}

}
