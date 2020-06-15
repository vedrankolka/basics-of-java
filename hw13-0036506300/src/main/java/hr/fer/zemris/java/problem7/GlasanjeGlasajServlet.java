package hr.fer.zemris.java.problem7;

import java.io.IOException;
import java.util.SortedMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * A servlet that reads the band id given in the parameters of the request,
 * loads the map of votes and increases the vote count with the given id by one.
 * 
 * @author Vedran Kolka
 *
 */
@WebServlet(name = "glasaj", urlPatterns = { "/glasanje-glasaj" })
public class GlasanjeGlasajServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// read the vote
		String idString = req.getParameter("id").toString();
		int votedID = Integer.parseInt(idString);
		
		String fileName = req.getServletContext().getRealPath(GlasanjeServlet.VOTES_RELATIVE_PATH);

		try {
			SortedMap<Integer, Integer> votes = BandsContestUtil.loadVotes(fileName);
			if (votes == null) {
				String defFileName = req.getServletContext().getRealPath(GlasanjeServlet.DEFINITION_RELATIVE_PATH);
				votes = BandsContestUtil.createVotes(defFileName);
			}
			votes.put(votedID, votes.get(votedID) + 1);
			BandsContestUtil.storeVotes(fileName, votes);
		} catch (IOException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");

	}

}
