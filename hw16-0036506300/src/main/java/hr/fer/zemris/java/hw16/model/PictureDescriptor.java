package hr.fer.zemris.java.hw16.model;

import java.util.Objects;

/**
 * Objekt koji čuva podatke o slici u galeriji.
 * 
 * @author Vedran Kolka
 *
 */
public class PictureDescriptor {
	/** ime slike */
	private String filename;
	/** opis slike */
	private String description;
	/** polje tag-ova slike */
	private String[] tags;

	/**
	 * Konstruktor.
	 * 
	 * @param filename ime slike
	 * @param description opis slike
	 * @param tags polje tagova slike
	 */
	public PictureDescriptor(String filename, String description, String[] tags) {
		this.filename = filename;
		this.description = description;
		this.tags = tags;
	}

	public String getFilename() {
		return filename;
	}

	public String getDescription() {
		return description;
	}

	public String[] getTags() {
		return tags;
	}

	@Override
	public int hashCode() {
		return Objects.hash(filename);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PictureDescriptor))
			return false;
		PictureDescriptor other = (PictureDescriptor) obj;
		return Objects.equals(filename, other.filename);
	}

}
