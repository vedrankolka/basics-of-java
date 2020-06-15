package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.web.forms.LoginForm;

/**
 * Servlet that renders a page which offers a login and registration if the user
 * is not logged in or a logut if the user is logged in.
 * <p>
 * The page also lists all registered blog users with links to their blogs.
 * 
 * @author Vedran Kolka
 *
 */
@WebServlet(urlPatterns = { "/servleti/main" })
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		serve(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		serve(req, resp);
	}

	/**
	 * Serves the request by displaying the content described.
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void serve(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			List<BlogUser> bloggers = DAOProvider.getDAO().getBlogUsers();
			req.setAttribute("bloggers", bloggers);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		// if no user is logged in, see if there is a form and try to do the login
		HttpSession ses = req.getSession();
		boolean ok = false;
		if (ses.getAttribute("currentUserID") == null) {
			ok = login(req, resp);
		}
		// if the login procces was ok, then the user is logged in and we redirect
		if (ok) {
			resp.sendRedirect("main");
			// if not, just render the page for the user to fill in the form again
		} else {
			req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
		}

	}

	/**
	 * Loggs in the user by setting its information to the sessions attributes.
	 * @param req
	 * @param resp
	 * @return true if the login succeded, false otherwise
	 * @throws ServletException
	 * @throws IOException
	 */
	private boolean login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession ses = req.getSession();
		req.setCharacterEncoding("UTF-8");

		LoginForm lf = new LoginForm();
		lf.fillFromRequest(req).validate();

		if (lf.hasErrors()) {
			req.setAttribute("form", lf);
			return false;
		} else {
			BlogUser currentUser = DAOProvider.getDAO().getBlogUser(lf.getNick());
			ses.setAttribute("currentUserID", currentUser.getId());
			ses.setAttribute("currentUserNick", currentUser.getNick());
			ses.setAttribute("currentUserFN", currentUser.getFirstName());
			ses.setAttribute("currentUserLN", currentUser.getLastName());
			ses.setAttribute("currentUserEmail", currentUser.getEmail());
			return true;

		}
	}

}
