package hr.fer.zemris.java.hw16.web.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw16.dao.DAOProvider;

/**
 * Servlet vraća umanjenu sličicu tražene slike imena danog u parametrima pod
 * ključem "path".
 * 
 * @author Vedran Kolka
 *
 */
@WebServlet("/thumbnails")
public class ThumbnailsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String webroot = req.getServletContext().getRealPath("");
		String fileName = req.getParameter("path");

		BufferedImage thumbnail = DAOProvider.getDAO().getThumbnail(fileName, webroot);
		ImageIO.write(thumbnail, "jpg", resp.getOutputStream());
	}

}
