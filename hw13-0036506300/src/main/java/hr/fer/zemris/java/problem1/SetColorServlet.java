package hr.fer.zemris.java.problem1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet that sets this sessions background to the color given through the
 * parameters under key "color".
 * 
 * @author Vedran Kolka
 *
 */
@WebServlet(name = "setcolor", urlPatterns = { "/setcolor" })
public class SetColorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	/** default color = white */
	private static final String DEFAULT_COLOR = "FFFFFF";
	/** regular expression for hexadecimal rgb color recognition */
	private static final String HEX_COLOR_REGEX = "[0-9a-fA-F]+";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String color = req.getParameter("color");
		
		if(color == null || color.length() != 6 || !color.matches(HEX_COLOR_REGEX)) {
			color = DEFAULT_COLOR;
		}
		
		req.getSession().setAttribute("pickedBgCol", color);
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}

}
