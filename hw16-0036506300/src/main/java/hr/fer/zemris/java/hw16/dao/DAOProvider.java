package hr.fer.zemris.java.hw16.dao;

import hr.fer.zemris.java.hw16.dao.impl.DAOImpl;

/**
 * Singleton of a {@link DAO} implementation.
 * @author Vedran Kolka
 *
 */
public class DAOProvider {
	/** dao used for the persistence layer */
	private static DAO dao = new DAOImpl();
	
	public static DAO getDAO() {
		return dao;
	}
	
}