package hr.fer.zemris.java.problem7;

import java.io.IOException;
import java.util.SortedMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet which reads all the bands defined in a private file,
 * maps them by their respective ids and offers them to the client in a list
 * of links where each link votes for the displayed band and takes the client to the results page.
 * @author Vedran Kolka
 *
 */
@WebServlet(name = "glasanje", urlPatterns = { "/glasanje" })
public class GlasanjeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	/** A relative path to the file of voting results */
	static final String VOTES_RELATIVE_PATH = "/WEB-INF/glasanje-rezultati.txt";
	/** A relative path to the file with all the bands with their ids and links to representative songs */
	static final String DEFINITION_RELATIVE_PATH = "/WEB-INF/glasanje-definicija.txt";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String fileName = req.getServletContext().getRealPath(DEFINITION_RELATIVE_PATH);
		try {
			SortedMap<Integer, String[]> bands = BandsContestUtil.loadBands(fileName);
			req.setAttribute("bands", bands);
			req.getSession().setAttribute("bands", bands);
		} catch (IOException e) {
			// if the file could not be read, we have a problem
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
