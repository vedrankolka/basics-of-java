package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.web.forms.BlogEntryForm;

/**
 * Servlet that serves all requests that request content from a specific author.
 * 
 * @author Vedran Kolka
 *
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		serve(req, resp, true);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		serve(req, resp, false);
	}

	/**
	 * Determines what the client requested and serves the request accordingly.
	 * 
	 * @param req
	 * @param resp
	 * @param get  boolean indicating if the request was obtained via get method
	 *             (<code>true</code>) or post method (<code>false</code>.)
	 * @throws ServletException
	 * @throws IOException
	 */
	private void serve(HttpServletRequest req, HttpServletResponse resp, boolean get)
			throws ServletException, IOException {
		// initialize relevant info from the request and cache it
		initialize(req);
		BlogUser author = (BlogUser) req.getAttribute("author");
		String method = (String) req.getAttribute("method");
		Long requestedEntryID = (Long) req.getAttribute("requestedEntryID");

		if (author == null) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		// if there is no method and no id, just list all blog entries
		if (method == null && requestedEntryID == null) {
			listEntries(req, resp, author);
			return;
		}
		// if a specific entry was requested, show it
		if (requestedEntryID != null) {
			BlogEntry be = DAOProvider.getDAO().getBlogEntry(requestedEntryID);
			req.setAttribute("blogEntry", be);
			req.setAttribute("id", requestedEntryID);
			req.setAttribute("authorNick", author.getNick());
			req.getRequestDispatcher("/WEB-INF/pages/showBlogEntry.jsp").forward(req, resp);
			return;
		}

		BlogEntryForm bef = new BlogEntryForm();
		if (method.equals("edit")) {
			BlogEntry be = DAOProvider.getDAO().getBlogEntry(req.getParameter("id"));
			bef.fillFromBlogEntry(be);
		} else if (!method.equals("new")) {
			// if the request was not handled above and is not 'new' it is a bad request
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		// if the method was 'new' or 'edit' blank or filled form is added and forwarded
		req.setAttribute("entryForm", bef);
		req.setAttribute("authorNick", author.getNick());
		req.getRequestDispatcher("/WEB-INF/pages/editBlogEntry.jsp").forward(req, resp);
	}

	/**
	 * Renders a page that lists all blog entries of the requested author (given
	 * through the url).
	 * 
	 * @param req
	 * @param resp
	 * @param author
	 * @throws ServletException
	 * @throws IOException
	 */
	private void listEntries(HttpServletRequest req, HttpServletResponse resp, BlogUser author)
			throws ServletException, IOException {
		try {
			List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntries(author);
			req.setAttribute("entries", entries);
			req.getRequestDispatcher("/WEB-INF/pages/listEntries.jsp").forward(req, resp);
		} catch (DAOException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Parses the url to extract information about the requested author, requested
	 * method (new or edit) or requested blog entry.
	 * <p>
	 * The method puts the information in the request attributes.
	 * 
	 * @param req
	 */
	private void initialize(HttpServletRequest req) {

		String[] rest = req.getPathInfo().substring(1).split("/", 2);
		String authorNick = rest[0];

		try {
			BlogUser author = DAOProvider.getDAO().getBlogUser(authorNick);
			req.setAttribute("author", author);
		} catch (DAOException e) {
			return;
		}

		if (rest.length > 1) {
			if (rest[1].equals("new") || rest[1].equals("edit")) {
				req.setAttribute("method", rest[1]);
			} else {
				try {
					Long requestedEntryID = Long.parseLong(rest[1]);
					req.setAttribute("requestedEntryID", requestedEntryID);
				} catch (NumberFormatException e) {
					// nothing to do
				}
			}
		}

	}

}
