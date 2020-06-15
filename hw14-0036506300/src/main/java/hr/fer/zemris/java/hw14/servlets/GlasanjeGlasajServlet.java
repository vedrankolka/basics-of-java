package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Servelt koji povećava broj glasova za opciju čiji je identifikator (id) dan u
 * parametrima za 1.
 * 
 * @author Vedran Kolka
 *
 */
@WebServlet(name = "glasaj", urlPatterns = { "/servleti/glasanje-glasaj" })
public class GlasanjeGlasajServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String stringID = req.getParameter("id");
		if(stringID == null) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No option was selected.");
			return;
		}

		try {
			// pokušamo povećati broj glasova u kritičnom odsječku
			long votedID = Long.parseLong(stringID);
			PollOption votedOption;
			synchronized (DAOProvider.getDao()) {
				votedOption = DAOProvider.getDao().getPollOption(votedID);
				votedOption.setVotesCount(votedOption.getVotesCount() + 1);
				DAOProvider.getDao().updatePollOption(votedOption);
			}
			
			resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + votedOption.getPollID());
		} catch (NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid id.");
			e.printStackTrace();
		} catch (DAOException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}

		
	}

}
