package hr.fer.zemris.java.hw15.web.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.model.BlogUser;
/**
 * Utility class for handeling passwords and its hashed values.
 * @author Vedran Kolka
 *
 */
public class Util {
	/** {@link MessageDigest} used for calculation of the password digest. */
	private static MessageDigest md;

	// initialization of the message digest
	static {
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException ignorable) {
			// ignorable
		}
	}

	/** Instances of this class are not permitted. */
	private Util() {
	}

	/**
	 * Calculates the hash of the given <code>password</code> and the the hash as a
	 * hex-string.
	 * 
	 * @param password to hash
	 * @return hashed password as a hex-string
	 */
	public static String hashPassword(String password) {
		try {
			return bytetohex(md.digest(password.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException ignorable) {
			return null;
		}
	}

	/**
	 * Converts the given bytes to a String in hexadecimal representation of the
	 * bytes.
	 *
	 * @param bytearray bytes to convert
	 * @return hexadecimal representation of the given <code>byteArray</code>
	 */
	private static String bytetohex(byte[] byteArray) {

		StringBuilder sb = new StringBuilder();

		for (byte b : byteArray) {
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}
	
	/**
	 * Checks if the currently logged in user's nickname matches the nickname of the
	 * requested author.<br>
	 * Expects the initialize method to be called before.
	 * 
	 * @param req
	 * @return <code>true</code> if the nicknames match, <code>false</code>
	 *         otherwise
	 */
	public static boolean usersMatch(HttpServletRequest req) {
		String currentUserNick = (String) req.getSession().getAttribute("currentUserNick");
		BlogUser author = (BlogUser) req.getAttribute("author");
		String authorNick = author == null ? null : author.getNick();
		return currentUserNick != null && authorNick != null && authorNick.equals(currentUserNick);
	}

	/**
	 * Checks if the currently logged in user's nickname matches the given nickname.<br>
	 * Expects the initialize method to be called before.
	 * 
	 * @param req
	 * @param authorNick
	 * @return <code>true</code> if the nicknames match, <code>false</code>
	 *         otherwise
	 */
	public static boolean usersMatch(HttpServletRequest req, String authorNick) {
		String currentUserNick = (String) req.getSession().getAttribute("currentUserNick");
		return currentUserNick != null && authorNick != null && currentUserNick.equals(authorNick);
	}
}
