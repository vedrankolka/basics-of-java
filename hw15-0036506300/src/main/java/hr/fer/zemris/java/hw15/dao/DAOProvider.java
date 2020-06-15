package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;
/**
 * Singleton of a {@link DAO} implementation.
 * @author Vedran Kolka
 *
 */
public class DAOProvider {
	/** dao used for the persistence layer */
	private static DAO dao = new JPADAOImpl();
	
	public static DAO getDAO() {
		return dao;
	}
	
}