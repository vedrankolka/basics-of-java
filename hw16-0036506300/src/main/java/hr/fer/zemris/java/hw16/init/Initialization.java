package hr.fer.zemris.java.hw16.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.hw16.dao.DAOProvider;

/**
 * Promatrač koji inicijalizira sloj za perzistenciju podataka pri
 * inicijalizaciji aplikacije.
 * 
 * @author Vedran Kolka
 *
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		DAOProvider.getDAO().load(sce.getServletContext().getRealPath(""));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
