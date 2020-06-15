package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

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
import hr.fer.zemris.java.hw15.web.util.Util;
/**
 * Edits an existing blog entry with id set in attributes with name
 * "blogEntryID".
 * @throws IOException
 * @throws ServletException
 */
@WebServlet("/servleti/update")
public class UpdateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		serve(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		serve(req, resp);
	}

	private void serve(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (!Util.usersMatch(req, req.getParameter("authorNick"))) {
			req.getSession().setAttribute("invalidEditError", "You are not logged in as the author of that blog.");
			resp.sendRedirect("main");
			return;
		}
		String authorNick = req.getParameter("authorNick");
		String id = req.getParameter("id");

		try {
			Long blogEntryID = id == null || id.isBlank() ? null : Long.parseLong(id);
			BlogEntryForm bef = new BlogEntryForm().fillFromRequest(req).validate();
			BlogUser author = DAOProvider.getDAO().getBlogUser(authorNick);
			// now the form is validated and sent back if it is not filled correctly
			if (bef.hasErrors()) {
				
				req.setAttribute("entryForm", bef);
				req.setAttribute("authorNick", authorNick);
				req.setAttribute("author", author);
				req.getRequestDispatcher("/WEB-INF/pages/editBlogEntry.jsp").forward(req, resp);
				return;
			}
			// now insert or update depending on the request id
			if (blogEntryID == null) {
				BlogEntry newEntry = bef.fillInBlogEntry(new BlogEntry());
				author.getBlogEntries().add(newEntry);
				newEntry.setCreator(author);
				DAOProvider.getDAO().insertBlogEntry(newEntry);
				blogEntryID = newEntry.getId();
			} else {
				BlogEntry toEdit = DAOProvider.getDAO().getBlogEntry(id);
				bef.fillInBlogEntry(toEdit);
			}
			resp.sendRedirect("author/" + authorNick);

		} catch (DAOException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch (NumberFormatException | NullPointerException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid blog entry ID");
		}
	}

}
