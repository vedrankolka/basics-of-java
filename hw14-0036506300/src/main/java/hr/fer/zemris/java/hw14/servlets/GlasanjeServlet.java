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
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Servlet koji nudi opcije za glasanje za anketu čiji je ID dan kroz parametre
 * zahtijva u obliku linkova koji vode na stranicu koja prikazuje rezultate glasanja.
 * 
 * @author Vedran Kolka
 *
 */
@WebServlet(name = "glasanje", urlPatterns = { "/servleti/glasanje" })
public class GlasanjeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		long pollID = Long.parseLong(req.getParameter("pollID"));

		try {
			Poll poll = DAOProvider.getDao().getPoll(pollID);
			List<PollOption> options = DAOProvider.getDao().getPollOptions(pollID);
			req.setAttribute("title", poll.getTitle());
			req.setAttribute("message", poll.getMessage());
			req.setAttribute("options", options);
			
		} catch (DAOException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
