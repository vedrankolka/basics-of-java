package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Stvara excel datoteku s dva stupca:<br>
 * naslovi opcija<br>
 * brojevi glasova tih opcija.
 * 
 * @author Vedran Kolka
 *
 */
@WebServlet(name = "glasanje-xls", urlPatterns = { "/servleti/glasanje-xls" })
public class GlasanjeExcelServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<PollOption> options;
		String sheetTitle;
		// dohvatimo podatke ako možemo
		try {
			long pollID = Long.parseLong(req.getParameter("pollID"));
			options = DAOProvider.getDao().getPollOptions(pollID);
			options.sort((po1, po2) -> Integer.compare(po2.getVotesCount(), po1.getVotesCount()));
			sheetTitle = DAOProvider.getDao().getPoll(pollID).getTitle();
		} catch (Exception e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			e.printStackTrace();
			return;
		}

		// kreiramo dokument
		try (HSSFWorkbook hwb = new HSSFWorkbook()) {
			// kreiramo stranicu i odgovarajuće zaglavlje
			HSSFSheet sheet = hwb.createSheet(sheetTitle);
			HSSFRow rowhead = sheet.createRow(0);
			rowhead.createCell(0).setCellValue("Opcija");
			rowhead.createCell(1).setCellValue("Broj glasova");
			// napunimo stranicu
			int rowNumber = 1;
			for (PollOption po : options) {
				HSSFRow row = sheet.createRow(rowNumber);
				row.createCell(0).setCellValue(po.getTitle());
				row.createCell(1).setCellValue(po.getVotesCount());
				rowNumber++;
			}
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);

			resp.setHeader("Content-Disposition", "attachment; filename=\"glasanje.xls\"");
			OutputStream os = resp.getOutputStream();
			hwb.write(os);
		}
	}
}
