package hr.fer.zemris.java.problem4;

import java.io.IOException;
import java.io.OutputStream;

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
 * A servlet that creates a pie chart of the usage of three operation systems
 * based on random data.
 * 
 * @author Vedran Kolka
 *
 */
@WebServlet(name = "reportImage", urlPatterns = {"/reportImage"})
public class ReportImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");

		OutputStream os = resp.getOutputStream();

		JFreeChart chart = createChart();
		int width = 450;
		int height = 400;
		ChartUtils.writeChartAsPNG(os, chart, width, height);
	}

	private JFreeChart createChart() {
		// create random data
		double windows = Math.random();
		double splitRatio = Math.random();
		double linux = (1 - windows) * splitRatio;
		
		double rest = (1 - windows) * (1 - splitRatio);
		double splitRatio2 = Math.random();
		double ios = rest * splitRatio2;
		double other = rest * (1 - splitRatio2);
		
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Windows", windows);
		dataset.setValue("Linux", linux);
		dataset.setValue("IOS", ios);
		dataset.setValue("other", other);

		JFreeChart chart = ChartFactory.createPieChart("OS usage", dataset, true, false, false);
		return chart;
	}

}
