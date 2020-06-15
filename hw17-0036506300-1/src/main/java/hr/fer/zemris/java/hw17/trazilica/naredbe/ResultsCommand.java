package hr.fer.zemris.java.hw17.trazilica.naredbe;

import java.util.List;

import hr.fer.zemris.java.hw17.trazilica.baza.Database;
import hr.fer.zemris.java.hw17.trazilica.baza.SearchResult;

/**
 * Command that shows the top {@link #NMB_OF_RESULTS_SHOWN} of the performed
 * search.
 * 
 * @author Vedran Kolka
 *
 */
public class ResultsCommand implements Command {
	/** Number of top search matches to show */
	public static int NMB_OF_RESULTS_SHOWN = 10;

	@Override
	public boolean execute(String args) {
		List<SearchResult> sr = Database.getDatabase().getSearchResults();

		if (sr == null) {
			System.out.println("Niste zadali upit ('query' naredba).");
			return true;
		}

		if (sr.isEmpty()) {
			System.out.println("Nema rezultata. Jeste li mislili \"darovit glumac zadnje akademske klase\"?");
			return true;
		}

		for (int i = 0; i < NMB_OF_RESULTS_SHOWN; ++i) {
			SearchResult result = sr.get(i);
			if (result.getMatchFactor() == 0)
				break;
			System.out.printf("[%d](%.4f) %s%n", i, result.getMatchFactor(), result.getFile());
		}

		return true;
	}

}
