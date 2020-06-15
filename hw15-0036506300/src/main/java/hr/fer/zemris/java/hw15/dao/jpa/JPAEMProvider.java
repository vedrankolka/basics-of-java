package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAOException;

/**
 * Entity Manager Provider to provide Thread localized entrity managers across
 * the application.
 * 
 * @author Vedran Kolka
 *
 */
public class JPAEMProvider {

	private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

	/**
	 * Gets an entity manager.<br>
	 * Works in the lazy fetch type.
	 * 
	 * @return
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = locals.get();
		if (em == null) {
			em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			locals.set(em);
		}
		return em;
	}

	/**
	 * Removes the entitiy manager from this thread and tries to commit the
	 * transaction that was begun upon getting the manager for the firt time in this
	 * thread.
	 * 
	 * @throws DAOException
	 */
	public static void close() throws DAOException {
		EntityManager em = locals.get();
		if (em == null) {
			return;
		}
		DAOException dex = null;
		try {
			em.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			em.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if (dex != null)
			throw dex;
	}

}