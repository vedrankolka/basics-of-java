package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.web.forms.RegisterForm;

/**
 * A servlet that renders a page with a registration form and checks it.
 * 
 * @author Vedran Kolka
 *
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getSession().getAttribute("currentUserID") != null) {
			req.getRequestDispatcher("/WEB-INF/pages/loggedIn.jsp").forward(req, resp);
		} else {
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getSession().getAttribute("currentUserID") != null) {
			req.getRequestDispatcher("/WEB-INF/pages/loggedIn.jsp").forward(req, resp);
			return;
		}
		
		req.setCharacterEncoding("UTF-8");
		RegisterForm rf = new RegisterForm().fillFromRequest(req).validate();

		if (rf.hasErrors()) {
			req.setAttribute("form", rf);
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
		} else {
			try {
				BlogUser newUser = rf.fillInBlogUser(new BlogUser());
				DAOProvider.getDAO().insertBlogUser(newUser);
				req.getRequestDispatcher("main").forward(req, resp);
				
			} catch (DAOException e) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			}
		}

	}

}
