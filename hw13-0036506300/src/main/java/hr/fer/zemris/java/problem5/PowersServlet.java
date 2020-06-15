package hr.fer.zemris.java.problem5;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * A servlet that creates an excel document with the following parameters:<br>
 * <ul>
 * <li>'a' integer between -100 and 100 (both inclusive) - lower boundary of the
 * table
 * <li>'b' integer between -100 and 100 (both inclusive) - higher boundary of
 * the table
 * <li>'n' integer between 1 and 5 (both inclusive) - number of pages
 * </ul>
 * On i-th page it displays numbers from a to b to the power of i.
 * 
 * @author Vedran Kolka
 *
 */
@WebServlet(name = "powers", urlPatterns = { "/powers" })
public class PowersServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String aString = req.getParameter("a");
		String bString = req.getParameter("b");
		String nString = req.getParameter("n");
		// initialized only for the compiler, they will not stay 0
		int a = 0;
		int b = 0;
		int n = 0;

		boolean valid = true;
		String errorMessage = null;

		try {
			a = getOrThrow(aString, -100, 100);
		} catch (IllegalArgumentException e) {
			valid = false;
			errorMessage = "Invalid parameter 'a': " + aString;
		}

		try {
			b = getOrThrow(bString, -100, 100);
		} catch (IllegalArgumentException e) {
			valid = false;
			errorMessage = "Invalid parameter 'b': " + bString;
		}

		try {
			n = getOrThrow(nString, 1, 5);
		} catch (IllegalArgumentException e) {
			valid = false;
			errorMessage = "Invalid parameter 'n': " + nString;
		}

		if (!valid) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, errorMessage);
			return;
		}
		
		if (a > b) {
			int temp = a;
			a = b;
			b = temp;
		}
		
		// create the workbook
		try (HSSFWorkbook hwb = new HSSFWorkbook()) {

			for (int i = 1; i <= n; ++i) {
				// create a sheet and the appropriate header row
				HSSFSheet sheet = hwb.createSheet("sheet " + i);
				HSSFRow rowhead = sheet.createRow(0);
				rowhead.createCell(0).setCellValue("x");
				rowhead.createCell(1).setCellValue("x^" + i);
				// for each number, create a row in the sheet
				for (int j = 1, x = a; x <= b; ++j, ++x) {
					HSSFRow row = sheet.createRow(j);
					row.createCell(0).setCellValue(x);
					row.createCell(1).setCellValue(Double.toString(Math.pow(x, i)));
				}
			}

			resp.setHeader("Content-Disposition", "attachment; filename=\"powers.xls\"");
			OutputStream os = resp.getOutputStream();
			hwb.write(os);
		}
	}

	/**
	 * Checks if the parameter is not <code>null</code>, in the specified range (between min and max)
	 * and parsable to an integer. 
	 * @param param
	 * @param min
	 * @param max
	 * @return the parsed number
	 * @throws IllegalArgumentException if it is not all of the above
	 */
	private static int getOrThrow(String param, int min, int max) {
		if (param != null) {
			int value = Integer.parseInt(param);
			if (value >= min && value <= max)
				return value;
		}
		throw new IllegalArgumentException();
	}

}
