package hr.fer.zemris.java.problem7;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * A servlet that creates a pie chart showing the distribution of votes amongst
 * bands.<br>
 * The chart is written to the client as a png image.
 * 
 * @author Vedran Kolka
 *
 */
@WebServlet(name = "glasanje-grafika", urlPatterns = { "/glasanje-grafika" })
public class GlasanjeChartServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");

		OutputStream os = resp.getOutputStream();

		@SuppressWarnings("unchecked")
		JFreeChart chart = createChart((Map<Integer, Integer>) req.getSession().getAttribute("votes"),
				(Map<Integer, String[]>) req.getSession().getAttribute("bands"));
		int width = 450;
		int height = 400;
		ChartUtils.writeChartAsPNG(os, chart, width, height);
	}

	/**
	 * Creates a pie chart showing how the votes are distributed among the bands.
	 * 
	 * @param votes map of numbers of votes mapped by band ids
	 * @param bands map of band names mapped by their ids
	 * @return JFreeCHart showing results of the voting
	 */
	private JFreeChart createChart(Map<Integer, Integer> votes, Map<Integer, String[]> bands) {
		// count all the votes
		int numberOfVotes = 0;
		for (Integer i : votes.values()) {
			numberOfVotes += i;
		}

		DefaultPieDataset dataset = new DefaultPieDataset();

		for (Integer id : votes.keySet()) {
			// calculate the part of the chart for this band
			double value = votes.get(id) / (double) numberOfVotes;
			// if it is too small, do not show it
			if (value < 1e-3)
				continue;

			String label = bands.get(id)[0];
			dataset.setValue(label, value);
		}
		// the paragraph already has a heading so no title is set here
		JFreeChart chart = ChartFactory.createPieChart(null, dataset, true, false, false);
		return chart;
	}

}
