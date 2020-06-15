package hr.fer.zemris.java.hw17.trazilica.baza;

import java.util.Objects;

import hr.fer.zemris.java.hw17.trazilica.vektor.Vector;

public class SearchResult implements Comparable<SearchResult> {

	private String file;
	
	private Vector tfIdf;
	
	private Double matchFactor;

	public SearchResult(String file, Vector tfIdf, double matchFactor) {
		this.file = Objects.requireNonNull(file);
		this.tfIdf = Objects.requireNonNull(tfIdf);
		this.matchFactor = matchFactor;
	}
	
	public void setMatchFactor(Vector v) {
		matchFactor = tfIdf.calculateCosine(v);
	}

	public String getFile() {
		return file;
	}
	
	public Double getMatchFactor() {
		return matchFactor;
	}
	
	@Override
	public int compareTo(SearchResult o) {
		return Double.compare(o.matchFactor, matchFactor);
	}

}
