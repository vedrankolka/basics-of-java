package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
 * Servlet koji prikazuje rezultate glasanja ankete s identifikatorom danim u
 * parametrima.
 * <p>
 * Rezultati su prikazani u tablici, dijagramu i dostupni su za preuzimanje u
 * xls datoteci.
 * 
 * @author Vedran Kolka
 *
 */
@WebServlet(name = "rezultati", urlPatterns = { "/servleti/glasanje-rezultati" })
public class GlasanjeRezultatiServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String stringPollID = req.getParameter("pollID").toString();
		long pollID = Long.parseLong(stringPollID);

		try {
			Poll poll = DAOProvider.getDao().getPoll(pollID);
			List<PollOption> options = DAOProvider.getDao().getPollOptions(pollID);
			List<PollOption> winners = getWinners(options);
			// sortiramo silazno po broju glasova
			options.sort((po1, po2) -> Integer.compare(po2.getVotesCount(), po1.getVotesCount()));

			req.setAttribute("poll", poll);
			req.setAttribute("options", options);
			req.setAttribute("winners", winners);
			
		} catch (DAOException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

	/**
	 * Metoda nađe najveći broj glasova i vrati listu svih pobjednika.
	 * 
	 * @param options opcije među kojima se određuje pobjednik
	 * @return listu pobjednika
	 */
	private List<PollOption> getWinners(List<PollOption> options) {
		int maxVotes = options.stream().mapToInt(po -> po.getVotesCount()).max().getAsInt();
		return options.stream().filter(po -> po.getVotesCount() == maxVotes).collect(Collectors.toList());
	}

}
