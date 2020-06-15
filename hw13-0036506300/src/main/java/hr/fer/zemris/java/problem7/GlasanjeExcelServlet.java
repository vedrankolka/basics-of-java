package hr.fer.zemris.java.problem7;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Creates an excel file with two columns:<br>
 * The first column has names of the bands.<br>
 * The second column has the numbers of votes for the respective bands.
 * 
 * @author Vedran Kolka
 *
 */
@WebServlet(name = "glasanje-xls", urlPatterns = { "/glasanje-xls" })
public class GlasanjeExcelServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Map<Integer, Integer> votes = (Map<Integer, Integer>) req.getSession().getAttribute("votes");
		Map<Integer, String[]> bands = (Map<Integer, String[]>) req.getSession().getAttribute("bands");

		// create the workbook
		try (HSSFWorkbook hwb = new HSSFWorkbook()) {
			// create a sheet and the appropriate header row
			HSSFSheet sheet = hwb.createSheet("sheet");
			HSSFRow rowhead = sheet.createRow(0);
			rowhead.createCell(0).setCellValue("Band");
			rowhead.createCell(1).setCellValue("Votes");
			int rowNumber = 1;
			for (Integer id : votes.keySet()) {
				HSSFRow row = sheet.createRow(rowNumber);
				row.createCell(0).setCellValue(bands.get(id)[0]);
				row.createCell(1).setCellValue(votes.get(id));
				rowNumber++;
			}

			resp.setHeader("Content-Disposition", "attachment; filename=\"votes.xls\"");
			OutputStream os = resp.getOutputStream();
			hwb.write(os);
		}

	}

}
