package hr.fer.zemris.java.hw14.model;

import java.beans.JavaBean;

/**
 * Razred koji modelira anketu.
 * 
 * @author Vedran Kolka
 *
 */
@JavaBean
public class Poll {

	/** Jedinstveni identifikator ankete */
	private long id;
	/** Naslov ankete */
	private String title;
	/** Poruka koja se prikazuje uz prikazivanje opcija glasanja */
	private String message;

	/**
	 * Konstruktor.
	 * 
	 * @param pollID  identifikator ankete
	 * @param title   naslov ankete
	 * @param message poruka za prikazati prilikom izbora opcija za glasanje
	 */
	public Poll(long pollID, String title, String message) {
		this.id = pollID;
		this.title = title;
		this.message = message;
	}

	/**
	 * Konstruktor koji ne inicijalizira nijednu člansku varijablu.
	 */
	public Poll() {
	}

	/**
	 * Konstruktor koji ID stavlja na 0.
	 * @param title
	 * @param message
	 */
	public Poll(String title, String message) {
		this (0, title, message);
	}
	
	public long getId() {
		return id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
}
