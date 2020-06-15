package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;
/**
 * JPA implementation of {@link DAO}.
 * @author Vedran Kolka
 *
 */
public class JPADAOImpl implements DAO {

	// ---------------------------- BlogEntry methods ---------------

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}
	
	@Override
	public BlogEntry getBlogEntry(String id) throws DAOException {
		if (id == null || id.isBlank()) return null;
		
		try {
			Long beID = Long.parseLong(id);
			return getBlogEntry(beID);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	public List<BlogEntry> getBlogEntries(BlogUser creator) throws DAOException {
		return JPAEMProvider.getEntityManager().createNamedQuery("BlogEntry.getByCreator", BlogEntry.class)
				.setParameter("creator", creator).getResultList();
	}

	@Override
	public void insertBlogEntry(BlogEntry newEntry) throws DAOException {
		JPAEMProvider.getEntityManager().persist(Objects.requireNonNull(newEntry));
	}

	// ---------------------------- BlogComment methods -------------------------

	@Override
	public List<BlogComment> getBlogComments(BlogEntry blogEntry) throws DAOException {

		try {
			return JPAEMProvider.getEntityManager().createNamedQuery("BlogComment.getByEntry", BlogComment.class)
					.setParameter("be", blogEntry).getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void insertBlogComment(BlogComment newComment) throws DAOException {
		try {
			JPAEMProvider.getEntityManager().persist(Objects.requireNonNull(newComment));
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	// ---------------------------- BlogUser methods --------------------

	@Override
	public BlogUser getBlogUser(Long id) throws DAOException {
		return JPAEMProvider.getEntityManager().find(BlogUser.class, id);
	}

	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		List<BlogUser> dummyList = em.createNamedQuery("BlogUser.getByNick", BlogUser.class).setParameter("nick", nick)
				.getResultList();
		BlogUser bu = dummyList.size() > 0 ? dummyList.get(0) : null;
		return bu;
	}

	@Override
	public void insertBlogUser(BlogUser user) throws DAOException {
		JPAEMProvider.getEntityManager().persist(Objects.requireNonNull(user));
	}

	@Override
	public List<BlogUser> getBlogUsers() throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		List<BlogUser> users = em.createNamedQuery("BlogUser.getAll", BlogUser.class).getResultList();
		return users;
	}

}