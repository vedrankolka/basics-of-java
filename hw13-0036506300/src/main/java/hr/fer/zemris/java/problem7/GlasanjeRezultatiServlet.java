package hr.fer.zemris.java.problem7;

import java.io.IOException;
import java.util.SortedMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * A servlet which loads the votes from the file and shows the results in a table,
 * on a pie chart and offers their download in an excel file.
 * @author Vedran Kolka
 *
 */
@WebServlet(name = "rezultati", urlPatterns = {"/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String fileName = req.getServletContext().getRealPath(GlasanjeServlet.VOTES_RELATIVE_PATH);
		SortedMap<Integer, Integer> votes = BandsContestUtil.loadVotes(fileName);
		if (votes == null) {
			String defFileName = req.getServletContext().getRealPath(GlasanjeServlet.DEFINITION_RELATIVE_PATH);
			votes = BandsContestUtil.createVotes(defFileName);
		}
		
		req.setAttribute("votes", votes);
		req.getSession().setAttribute("votes", votes);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	
}
