package hr.fer.zemris.java.hw16.web.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw16.dao.DAOException;
import hr.fer.zemris.java.hw16.dao.DAOProvider;

/**
 * Servlet koji dohvaća sliku imena danog pod parametrom "fileName".
 * 
 * @author Vedran Kolka
 *
 */
@WebServlet("/image")
public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String fileName = req.getParameter("fileName");
		if (fileName == null) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		try {
			BufferedImage image = DAOProvider.getDAO().getImage(fileName, req.getServletContext().getRealPath(""));
			ImageIO.write(image, "jpg", resp.getOutputStream());
		} catch (DAOException e) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
		}

	}

}
