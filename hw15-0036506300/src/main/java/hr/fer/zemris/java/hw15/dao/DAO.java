package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

public interface DAO {

	// --------------------- methods for BlogEntry --------------------------------------
	/**
	 * Gets the entry with given <code>id</code>. If no such entry exists, <code>null</code>
	 * is returned.
	 * 
	 * @param id key of the entry
	 * @return entry or <code>null</code> if no entry with <code>id</code> exists
	 * @throws DAOException
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Gets the entry with given <code>id</code>. If no such entry exists, <code>null</code>
	 * is returned.
	 * 
	 * @param id key of the entry
	 * @return entry or <code>null</code> if no entry with <code>id</code> exists
	 * @throws DAOException
	 */
	public BlogEntry getBlogEntry(String id) throws DAOException;
	
	/**
	 * Gets a list of entries from the given {@link BlogUser} <code>creator</code>.
	 * @param creator
	 * @return list of entries from the given {@link BlogUser} <code>creator</code>.
	 * @throws DAOException
	 */
	public List<BlogEntry> getBlogEntries(BlogUser creator) throws DAOException;
	
	/**
	 * Persists the given <code>newEntry</code>.
	 * @param newEntry
	 * @throws DAOException
	 */
	public void insertBlogEntry(BlogEntry newEntry) throws DAOException;
	
	// --------------------- methods for BlogComment ------------------------------------
	
	/**
	 * Gets blog comments of the given {@link BlogEntry} <code>blogEntry</code>.
	 * @param blogEntry
	 * @return blog comments of the given {@link BlogEntry} <code>blogEntry</code>.
	 * @throws DAOException
	 */
	public List<BlogComment> getBlogComments(BlogEntry blogEntry) throws DAOException;
	
	/**
	 * Persists the given <code>newComment</code>.
	 * @param newEntry
	 * @throws DAOException
	 */
	public void insertBlogComment(BlogComment newComment) throws DAOException;
	
	//---------------------- methods for BlogUser ---------------------------------------
	
	/**
	 * Gets a list of all blog users.
	 * @return list of all blog users
	 * @throws DAOException
	 */
	public List<BlogUser> getBlogUsers() throws DAOException;
	
	/**
	 * Gets the {@link BlogUser} with the given <code>id</code>.
	 * @param id
	 * @return user with given id
	 * @throws DAOException
	 */
	public BlogUser getBlogUser(Long id) throws DAOException;
	
	/**
	 * Gets the {@link BlogUser} with the given <code>nick</code>.
	 * @param nick
	 * @return user with given nick
	 * @throws DAOException
	 */
	public BlogUser getBlogUser(String nick) throws DAOException;
	
	/**
	 * Persists the given <code>user</code>.
	 * @param user
	 * @throws DAOException
	 */
	public void insertBlogUser(BlogUser user) throws DAOException;
	
}