package hr.fer.zemris.java.problem2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet that expects parameters a and b that are integer numbers which are
 * used as boundaries for calculating a table of sines and cosines for each
 * integer from a (included) to b (included).
 * 
 * @author Vedran Kolka
 *
 */
@WebServlet(name = "trigonometric", urlPatterns = { "/trigonometric" })
public class TrigonometricServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	/** default value for a if not given or given incorrectly */
	private static final int DEFAULT_A = 0;
	/** default value for b if not given or given incorrectly */
	private static final int DEFAULT_B = 360;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String aString = req.getParameter("a");
		String bString = req.getParameter("b");

		int a = getOrDefault(aString, DEFAULT_A);
		int b = getOrDefault(bString, DEFAULT_B);

		if (a > b) {
			int temp = a;
			a = b;
			b = temp;
		} else if (b > a + 720) {
			b = a + 720;
		}

		List<Double> sines = new ArrayList<>();
		List<Double> cosines = new ArrayList<Double>();

		for (int i = a; i <= b; ++i) {
			double angle = Math.toRadians(i);
			sines.add(Math.sin(angle));
			cosines.add(Math.cos(angle));
		}
		
		req.setAttribute("a", a);
		req.setAttribute("b", b);
		req.setAttribute("sines", sines);
		req.setAttribute("cosines", cosines);

		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}

	/**
	 * Tries to parse the given <code>value</code> to an integer. If it cannot, then
	 * the default value is returned, otherwise the parsed value.
	 * 
	 * @param value        to parse
	 * @param defaultValue to return if <code>value</code> is not parsable to an
	 *                     integer
	 * @return parsed value or default value if <code>value</code> is not parsable
	 */
	private int getOrDefault(String value, int defaultValue) {
		int result = defaultValue;
		if (value != null) {
			try {
				result = Integer.parseInt(value);
			} catch (NumberFormatException ignorable) {
				// default value is used
			}
		}
		return result;
	}

}
