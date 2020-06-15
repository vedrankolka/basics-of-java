package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;

/**
 * Servlet koji nudi poveznice na postojeće ankete.
 * 
 * @author Vedran Kolka
 *
 */
@WebServlet(name = "indexPolls", urlPatterns = { "/servleti/index.html" })
public class IndexPollsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// postavimo boju pozadine samo da bude ljepše
		req.getSession().setAttribute("backgroundColor", "#99FFFF");

		try {
			List<Poll> polls = DAOProvider.getDao().getPolls();
			req.setAttribute("polls", polls);
		} catch (DAOException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}

}
