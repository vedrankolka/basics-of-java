package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.web.forms.CommentForm;
@WebServlet("/servleti/showEntry")
public class ShowEntryServlet extends HttpServlet {

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

		try {

			Long blogEntryID = Long.parseLong(req.getParameter("id"));
			BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(blogEntryID);
			String authorNick = req.getParameter("authorNick");

			if (blogEntry == null) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No blog entry with id: " + blogEntryID);
				return;
			}
			// if the gotten entry exists, give it to the jsp to render
			req.setAttribute("blogEntry", blogEntry);
			// if a form was sent, check if it is valid
			if (req.getParameter("commentForm") != null) {
				CommentForm cf = new CommentForm().fillFromRequest(req).validate();
				// if the form is invalid, send it to the jsp to render
				if (cf.hasErrors()) {
					req.setAttribute("commentForm", cf);
					req.setAttribute("authorNick", authorNick);
					req.getRequestDispatcher("/WEB-INF/pages/showBlogEntry.jsp").forward(req, resp);
					// else add the comment and redirect so refresh does not add the comment again
				} else {
					BlogComment newComment = cf.fillInComment(new BlogComment());
					newComment.setBlogEntry(blogEntry);
					newComment.setPostedOn(new Date());

					blogEntry.getComments().add(newComment);
					DAOProvider.getDAO().insertBlogComment(newComment);
					resp.sendRedirect("author/" + authorNick + "/" + blogEntryID);
				}
				
			} else {
				req.setAttribute("authorNick", authorNick);
				req.getRequestDispatcher("/WEB-INF/pages/showBlogEntry.jsp").forward(req, resp);
			}

		} catch (DAOException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		} catch (NullPointerException | NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid blog entry id.");
		}

	}

}
