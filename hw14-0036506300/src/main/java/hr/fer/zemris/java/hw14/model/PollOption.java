package hr.fer.zemris.java.hw14.model;

import java.util.Objects;

/**
 * Razred koji modelira jedan od izbora u online anketi.
 * 
 * @author Vedran Kolka
 *
 */
public class PollOption {

	/** jedinstveni identifikator opcije */
	private long id;
	/** naslov opcije */
	private String title;
	/** Poveznica prema sadržaju koji opisuje opciju */
	private String optionLink;
	/** Jedinstveni identifikator ankete kojoj ova opcija pripada */
	private long pollID;
	/** Broj glasova za ovu opciju */
	private int votesCount;

	/**
	 * Konstruktor koji omogućuje postavljanje svih članskih varijabli.
	 * 
	 * @param id         identifikator opcije
	 * @param title      naslov opcije
	 * @param optionLink poveznica za sadržaj koji opisuje opciju
	 * @param pollID     identifikator ankete kojoj opcija pripada
	 * @param votesCount broj glasova za opciju
	 * @throws NullPointerException ako je <code>title</code> <code>null</code>
	 */
	public PollOption(long id, String title, String optionLink, long pollID, int votesCount) {
		this.id = id;
		this.title = Objects.requireNonNull(title);
		this.optionLink = optionLink;
		this.pollID = pollID;
		this.votesCount = votesCount;
	}

	/**
	 * Konstruktor koji postavlja broj glasova na 0.
	 * 
	 * @param id         identifikator opcije
	 * @param title      naslov opcije
	 * @param optionLink poveznica za sadržaj koji opisuje opciju
	 * @param pollID     identifikator ankete kojoj opcija pripada
	 * @throws NullPointerException ako je <code>title</code> <code>null</code>
	 */
	public PollOption(long id, String title, String optionLink, long pollID) {
		this(id, title, optionLink, pollID, 0);
	}

	/**
	 * Konstruktor koji postavlja id i broj glasova na 0.
	 * 
	 * @param title
	 * @param optionLink
	 * @param pollID
	 */
	public PollOption(String title, String optionLink, long pollID) {
		this(0, title, optionLink, pollID);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOptionLink() {
		return optionLink;
	}

	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	public long getPollID() {
		return pollID;
	}

	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	public int getVotesCount() {
		return votesCount;
	}

	public void setVotesCount(int votesCount) {
		this.votesCount = votesCount;
	}
}
