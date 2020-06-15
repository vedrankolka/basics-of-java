package hr.fer.zemris.java.hw14;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.dao.sql.SQLConnectionProvider;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Promatrač koji inicijalizira okruženje aplikacije, stvarajući default-ni set
 * podataka ako već ne postoji.
 * 
 * @author Vedran Kolka
 *
 */
@WebListener
public class Initializatzion implements ServletContextListener {
	/** relativna staza do datoteke za konfiguraciju */
	private static final String PROPERTIES_PATH = "/WEB-INF/dbsettings.properties";

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		String dbPropertiesPath = sce.getServletContext().getRealPath(PROPERTIES_PATH);

		Map<String, String> properties;
		try {
			properties = loadProperties(new HashMap<>(), Paths.get(dbPropertiesPath));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		// pročitamo postavke i sastavimo url
		String host = properties.get("host");
		String port = properties.get("port");
		String name = properties.get("name");
		String user = properties.get("user");
		String password = properties.get("password");
		String driver = properties.get("driver");
		String url = "jdbc:derby://" + host + ":" + port + "/" + name;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass(driver);
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Error while initializing the pool.", e1);
		}
		cpds.setJdbcUrl(url);
		// postavimo pročitane postavke
		sce.getServletContext().setAttribute("dbpool", cpds);
		cpds.setJdbcUrl(url);
		cpds.setUser(user);
		cpds.setPassword(password);
		cpds.setInitialPoolSize(5);
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);

		try {
			// dohvati konekciju i dao, ako je relacija Polls tek stvorena ili ako je prazna
			// napuni relacije
			Connection con = ((DataSource) sce.getServletContext().getAttribute("dbpool")).getConnection();
			SQLConnectionProvider.setConnection(con);
			DAO dao = DAOProvider.getDao();
			if (dao.createPolls() || dao.getPolls().size() == 0) {
				fillTables();
			}
			SQLConnectionProvider.setConnection(null);
		} catch (DAOException | SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext().getAttribute("dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Puni tablice defaultnim podacima.
	 */
	private void fillTables() {
		DAO dao = DAOProvider.getDao();
		dao.createPollOptions();

		Poll bands = new Poll("Glasanje za omiljeni bend",
				"Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!");
		long bandsPollID = dao.insertPoll(bands);

		List<PollOption> bandOptions = new ArrayList<>();
		bandOptions.add(new PollOption("The Killers", "https://www.youtube.com/watch?v=sZTpLvsYYHw", bandsPollID));
		bandOptions.add(new PollOption("Green Day", "https://www.youtube.com/watch?v=NUTGr5t3MoY", bandsPollID));
		bandOptions.add(new PollOption("Hladno Pivo", "https://www.youtube.com/watch?v=Lw8_dbZmLDE", bandsPollID));
		bandOptions.add(new PollOption("Imagine Dragons", "https://www.youtube.com/watch?v=wmjyO-r1OhA", bandsPollID));
		bandOptions.add(new PollOption("Lenny Kravitz", "https://www.youtube.com/watch?v=eW2qlKa6oHw", bandsPollID));
		bandOptions.add(new PollOption("Bob Marley", "https://www.youtube.com/watch?v=x59kS2AOrGM", bandsPollID));
		bandOptions.add(new PollOption("KUKU$", "https://www.youtube.com/watch?v=ylozGYPtcgs", bandsPollID));
		bandOptions.add(new PollOption("J. Cole", "https://www.youtube.com/watch?v=eCGV26aj-mM", bandsPollID));
		dao.insertPollOptions(bandOptions);

		Poll cakes = new Poll("Glasanje za omiljeni kolač",
				"Od sljedećih kolača, koji Vam je kolač najdraži? Kliknite na link kako biste glasali!");
		long cakesPollID = dao.insertPoll(cakes);

		List<PollOption> cakeOptions = new ArrayList<>();
		cakeOptions.add(new PollOption("Limun kocke", "https://www.coolinarika.com/recept/1071251/", cakesPollID));
		cakeOptions.add(new PollOption("Torta od sira", "https://www.coolinarika.com/recept/759040/", cakesPollID));
		cakeOptions.add(new PollOption("Brašno",
				"https://bakerbettie.com/wp-content/uploads/2017/09/self-rising-flour-1.jpg", cakesPollID));
		dao.insertPollOptions(cakeOptions);
	}

	/**
	 * Učitava postavke iz datoteke sa predanom stazom <code>path</code> i dodaje ih
	 * u mapu koju vraća.
	 * 
	 * @param map  mapa koju treba napuniti, ako je <code>null</code> onda se stvara
	 *             nova mapa
	 * @param path staza do datoteke za konfiguraciju
	 * @return Map<String, String> s pročitanim postavkama
	 * @throws IOException ako se sa dane staze ne može čitati
	 */
	private Map<String, String> loadProperties(Map<String, String> mapToFill, Path path) throws IOException {

		Map<String, String> map = mapToFill == null ? new HashMap<>() : mapToFill;
		List<String> lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);

		for (String line : lines) {
			if (line.length() == 0 || line.startsWith("#"))
				continue;
			String[] l = line.split("=");
			String key = l[0].trim();
			String value = l[1].trim();
			map.put(key, value);
		}

		return map;
	}

}