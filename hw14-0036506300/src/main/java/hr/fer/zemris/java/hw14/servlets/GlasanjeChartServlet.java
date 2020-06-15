package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Servlet koji kreira kružni dijagram koji prikazuje udio glasova po opcijama
 * za anketu čiji je id dan u pramaterima.<br>
 * Dijagram se ispisuje klijentu kao png slika.
 * 
 * @author Vedran Kolka
 *
 */
@WebServlet(name = "glasanje-grafika", urlPatterns = { "/servleti/glasanje-grafika" })
public class GlasanjeChartServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	/** Razina udjela ispod koje ne prikazujemo opcije */
	public static final double THRESHOLD = 1e-3;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");

		OutputStream os = resp.getOutputStream();
		List<PollOption> options;

		try {
			long pollID = Long.parseLong(req.getParameter("pollID"));
			options = DAOProvider.getDao().getPollOptions(pollID);
			options.sort((po1, po2) -> Integer.compare(po2.getVotesCount(), po1.getVotesCount()));
		} catch (Exception e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			e.printStackTrace();
			return;
		}

		JFreeChart chart = createChart(options);
		int width = 450;
		int height = 400;
		ChartUtils.writeChartAsPNG(os, chart, width, height);
	}

	/**
	 * Kreira dijagram koji prikazuje udio glasova za pojedine opcije.
	 * 
	 * @param options lista opcija
	 * @return JFreeCHart koji prikazuje rezultate glasanja
	 */
	private JFreeChart createChart(List<PollOption> options) {
		// izbrojimo sve glasove
		int numberOfVotes = options.stream().mapToInt(po -> po.getVotesCount()).sum();

		DefaultPieDataset dataset = new DefaultPieDataset();

		for (PollOption po : options) {
			// izračunamo udio za ovu opciju, ako je premali ne prikazujemo ga
			double value = po.getVotesCount() / (double) numberOfVotes;
			if (value < THRESHOLD)
				continue;

			dataset.setValue(po.getTitle(), value);
		}
		JFreeChart chart = ChartFactory.createPieChart(null, dataset, true, false, false);
		return chart;
	}

}
